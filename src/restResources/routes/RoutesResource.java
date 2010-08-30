package restResources.routes;

import org.json.*;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
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
		

		if (routeParam != null) {
			String parameter = getReference().getSegments().get(1);
			String routeId = routeParam.toString();
			
			if (parameter.equals(routeId)) {
				conn.close();
				return new StringRepresentation(parameter);
			} else {
				conn.close();
				return new StringRepresentation(routeId + ":" + parameter);
			}
		} else {
			Representation returnValue = getAllActiveRoutes(s);
			conn.close();
			return returnValue;
		}
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
	
	private Representation getRouteBusLocations(Statement s) {
		return null;
	}
	
	private Representation getRouteInformation(Statement s) {
		return null;
	}
	
	private Representation getRouteWayPoints(Statement s) {
		return null;
	}
	
	private Representation getRouteStops(Statement s) {
		return null;
	}
	
	

}
