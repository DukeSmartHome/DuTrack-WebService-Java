package restResources.routes;

import org.json.*;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.*;

import java.sql.*;


import utilities.*;
import xDomain.XDomain;
import xDomain.XDomainServerResource;

/**
 * 
 * @author Dean Chen
 */
public class RoutesResource extends XDomainServerResource {

	@Get
	public Representation represent(Variant variant) throws SQLException {
		XDomain.allowAllOrigins(this);

		Connection conn = null;
		Statement s = null;

		// create connection
		conn = ConnectionFactory.getDataConnection();

		// create and execute SQL statement
		s = conn.createStatement();

		// get url parameter
		Object routeParam = getRequestAttributes().get("routeId");
		
		Representation returnValue;
		if (routeParam != null) {
			String lastUrlSegment = getReference().getLastSegment();
			String routeId = routeParam.toString();

			if (lastUrlSegment.equals(routeId)) {
				returnValue = new JsonRepresentation(getRouteInformation(routeId, conn));
			} else if (lastUrlSegment.equals("wayPoints")) {
				// /routes/{routeId}/wayPoints
				returnValue = new JsonRepresentation(getRouteWayPoints(routeId, conn));
			} else if (lastUrlSegment.equals("stops")) {
				// /routes/{routeId}/stops
				returnValue = new JsonRepresentation(getRouteStops(routeId, conn));
			} else {
				// bad lastUrlSegment
				returnValue = new JsonRepresentation(new JSONArray());
			}
		} else {
			// /routes
			returnValue = getAllActiveRoutes(s);
		}
		
		s.close();
		conn.close();
		return returnValue;
	}

	private Representation getAllActiveRoutes(Statement s) throws SQLException {

		s.executeQuery("SELECT DISTINCT(`route`) FROM `vehicle_assignments` WHERE `route`!='Yard'");

		// make query
		ResultSet rs = s.getResultSet();

		// populate JSON result
		JSONArray result = new JSONArray();
		while (rs.next()) {
			result.put(rs.getString(1));
		}
		
		return new JsonRepresentation(result.toString());
	}

	
	private JSONArray getRouteInformation(String routeId, Connection conn) throws SQLException {
		String query = null;
		query= "SELECT `id`, `name`, `description`, `category` FROM `routes` WHERE `name`=?";
		
		PreparedStatement s = conn.prepareStatement(query);
		s.setString(1, routeId);
		
		JSONArray container = new JSONArray();
		
		// make query
		ResultSet rs = s.executeQuery();
		
		JSONObject route = null;
		if (rs.next()) {
			route = new JSONObject();
			try {
				route.put("id", rs.getString("id"));
				route.put("name", rs.getString("name"));
				route.put("description", rs.getString("description"));
				route.put("category", rs.getString("category"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			container.put(route);
		}
		
		return container;
	}
	
	private JSONArray getRouteWayPoints(String routeName, Connection conn) throws SQLException {
		return getCoordinates(routeName, conn, 0);
	}
	
	private JSONArray getRouteStops(String routeName, Connection conn) throws SQLException {
		return getCoordinates(routeName, conn, 1);
	}
	
	private int getRouteId(String routeName, Connection conn) throws SQLException {
		String query = null;
		query= "SELECT `id` FROM `routes` WHERE `name`=?";
		
		PreparedStatement s = conn.prepareStatement(query);
		s.setString(1, routeName);
		ResultSet rs = s.executeQuery();
		
		if (rs.next()) {
			return rs.getInt("id");
		} else {
			return -1;
		}
	}
	
	private JSONArray getCoordinates(String routeName, Connection conn, int stops) throws SQLException {
		JSONArray container = new JSONArray();
		int routeId = getRouteId(routeName, conn);
		
		if (routeId == -1) {
			return container;
		}
		
		String query = null;
		query= "SELECT `id`, `long`, `lat` FROM `routeCoordinates` WHERE `routeId`=? AND `isStop`=? ORDER BY `id`";
		
		PreparedStatement s = conn.prepareStatement(query);
		s.setInt(1, routeId);
		s.setInt(2, stops);

		// make query
		ResultSet rs = s.executeQuery();
		
		JSONObject route = null;
		while (rs.next()) {
			route = new JSONObject();
			try {
				//route.put("id", rs.getString("id"));
				route.put("lat", rs.getString("lat"));
				route.put("long", rs.getString("long"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			container.put(route);
		}
		
		return container;
	}

}
