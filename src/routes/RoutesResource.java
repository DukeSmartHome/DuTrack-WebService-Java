package routes;

import org.json.*;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.Variant;
import org.restlet.resource.*;

import utility.ConnectionFactory;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.*;

/**
 * 
 * @author Dean Chen
 */
public class RoutesResource extends ServerResource {

	@Options
	public void doOptions(Representation entity) {
	    Form responseHeaders = (Form) getResponse().getAttributes().get("org.restlet.http.headers");
	    if (responseHeaders == null) {
	        responseHeaders = new Form();
	        getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
	    }
	    responseHeaders.add("Access-Control-Allow-Origin", "*");
	    responseHeaders.add("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
	    responseHeaders.add("Access-Control-Allow-Headers", "Content-Type");
	    responseHeaders.add("Access-Control-Allow-Credentials", "false");
	    responseHeaders.add("Access-Control-Max-Age", "60");
	}
	
	@Get
	public Representation represent(Variant variant) throws SQLException, NamingException {
		Connection conn = null;
		Statement s = null;

		// create connection
		conn = ConnectionFactory.getInstance();

		// create and execute SQL statement
		s = conn.createStatement();

		// get url parameter
		Object routeParam = getRequestAttributes().get("routeId");
		

		if (routeParam != null) {
			String parameter = getReference().getSegments().get(1);
			String routeId = routeParam.toString();
			
			if (parameter.equals(routeId)) {
				return new StringRepresentation(parameter);
			} else {
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
