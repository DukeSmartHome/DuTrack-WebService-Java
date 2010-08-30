package utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Queries {
	
	public static String[] getAllBusIdsOnRoute(String routeId) throws SQLException {
		Connection conn = ConnectionFactory.getDataConnection();
		
		String query = "SELECT `deviceID`" +
				" FROM `vehicle_assignments`, `vehicle_mappings`" +
				" WHERE `route`=?" +
				" AND (vehicle_assignments.vehicleID = vehicle_mappings.vehicleID)";
		
		PreparedStatement s = conn.prepareStatement(query);
		s.setString(1, routeId);
		
		List<String> results = new ArrayList<String>();
		ResultSet rs = s.executeQuery();

		while (rs.next()) {
			results.add(rs.getString(1));
		}
		conn.close();
		return results.toArray(new String[results.size()]);
	}
}
