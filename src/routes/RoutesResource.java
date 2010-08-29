package routes;

import org.json.*;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.*;


/**
 * 
 * @author Dean Chen
 */
public class RoutesResource extends ServerResource {
	Connection connection = null;
	DataSource ds = null;

	public RoutesResource() {
		// make SQL connection
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			
			if (envCtx == null) {
				throw new Exception("No Context Found");
			}

			ds = (DataSource) envCtx.lookup("jdbc/restDB");

		} catch (Exception e) {
			// TODO: handle error
			e.printStackTrace();
		}
	}

	@Get
	public String getAllRoutes() {
		Connection conn = null;
		try {
			// create connection
			conn = ds.getConnection();
			
			// create and execute SQL statement
			Statement s;
			s = conn.createStatement();
			s.executeQuery("SELECT DISTINCT(`route`) FROM `vehicle_assignments` WHERE `route`!='Yard'");
			
			// make query
			ResultSet rs = s.getResultSet();
			
			// populate JSON result
			JSONArray result = new JSONArray();
			while (rs.next()) {
				result.put(rs.getString(1));
			}
			
			// close connection
			conn.close();
			
			return result.toString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
