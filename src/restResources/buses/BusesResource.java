package restResources.buses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import application.Source;

import utilities.ConnectionFactory;
import utilities.Queries;
import xDomain.XDomain;
import xDomain.XDomainServerResource;

/**
 * 
 * @author Dean Chen
 */
public class BusesResource extends XDomainServerResource {
	@Get
    public Representation represent() throws SQLException, JSONException {
		XDomain.allowAllOrigins(this);
		
		// first section of the REST URI
		String source = getReference().getSegments().get(1);
		// last section of the REST URI
		String location = getReference().getLastSegment();
		
		if (source.equals(Source.BUS.toString())) {
			
		} else if (source.equals(Source.ROUTE.toString())) {
			// retrieve all bus locations for a given route
			if (location.equals("busLocations")) {
				
				// get the routeIdRequested
				String routeId = getRequestAttributes().get("routeId").toString();
				
				// get all buses on a given route
				String[] busIds = Queries.getAllBusIdsOnRoute(routeId);
				
				// return all the bus locations as a json object
				return new JsonRepresentation(
						getBusLocations(busIds).toString());
			}
		}
		
		return null;
    }
	
	/**
	 * Retries all bus locations on route
	 * 
	 * @param busIds
	 * @return JSONArray containing locations for all buses
	 * @throws SQLException
	 * @throws JSONException
	 */
	private JSONArray getBusLocations(String[] busIds) throws SQLException, JSONException {
		JSONArray result = new JSONArray();
		
		for (String busId : busIds) {
			JSONObject busLocation = getBusLocation(busId);
			
			if (busLocation != null) result.put(busLocation);
		}
		
		return result;
	}
	
	/**
	 * retrieve bus location as a JSON object from the database
	 * @param busId
	 * @return bus location JSON object
	 * @throws SQLException 
	 * @throws NamingException 
	 * @throws JSONException 
	 */
	private JSONObject getBusLocation(String busId) throws SQLException, JSONException {
		
		String[] columnsToFetch = {"deviceID", "latitude", "longitude", "timestamp", "heading"};
		
		StringBuffer query = new StringBuffer();
		
		// construct select statement given the column names
		query.append("SELECT");
		for (String column : columnsToFetch) {
			query.append(" `" + column + "`,");
		}
		
		// delete last comma or qill cause sql error
		query.deleteCharAt(query.length()-1);

		query.append(" FROM `EventData`");
		query.append(" WHERE `deviceID` = ?");
		query.append(" ORDER BY `timestamp` DESC");
		query.append(" LIMIT 0,1");
		
		Connection conn = ConnectionFactory.getGtsConnection();
		PreparedStatement s = conn.prepareStatement(query.toString());
		s.setString(1, busId);
		
		JSONObject result = new JSONObject();
		
		ResultSet rs = s.executeQuery();
		if (rs.next()) {
			for (String column : columnsToFetch) {
				result.put(column, rs.getString(column));
			}
			conn.close();
			return result;
		}
		conn.close();
		return null;
	}
}
