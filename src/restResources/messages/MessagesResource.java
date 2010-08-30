package restResources.messages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import utilities.ConnectionFactory;
import xDomain.XDomainServerResource;

/**
 * 
 * @author Dean Chen
 */
public class MessagesResource extends XDomainServerResource {
	
	@Get
	public Representation represent() throws SQLException {
		Object messageParam = getRequestAttributes().get("messageId");
		
		if (messageParam == null) {
			return new JsonRepresentation(getAllMessages());
		} else {
			String messageIdString = messageParam.toString();
			try {
				int messageId = Integer.parseInt(messageIdString);
				return new JsonRepresentation(getMessage(messageId));
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			
		}
	}
	
	private JSONArray getAllMessages() throws SQLException {

		String query = "SELECT `id`, `title` FROM `messages` ORDER BY `timestamp` DESC";
		JSONArray messages = new JSONArray();
		
		Connection conn = ConnectionFactory.getDataConnection();
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery(query);
		while (rs.next()) {
			JSONObject message = new JSONObject();
			try {
				message.put("id", rs.getString("id"));
				message.put("title", rs.getString("title"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			messages.put(message);
		}
		conn.close();
		return messages;
	}
	
	private JSONObject getMessage(int messageId) throws SQLException {
		Connection conn = ConnectionFactory.getDataConnection();
		
		String query = "SELECT `id`, `title`, `timestamp`, `body` FROM `messages` WHERE `id`=?";
		
		PreparedStatement s = conn.prepareStatement(query);
		s.setInt(1, messageId);
		
		ResultSet rs = s.executeQuery();
		if (rs.next()) {
			JSONObject message = new JSONObject();
			try {
				message.put("id", rs.getString("id"));
				message.put("title", rs.getString("title"));
				message.put("timestamp", rs.getString("timestamp"));
				message.put("body", rs.getString("body"));
				conn.close();
				return message;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			conn.close();
			return null;
		} else {
			conn.close();
			return null;
		}
	}
}
