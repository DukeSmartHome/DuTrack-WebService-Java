package utilities;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {
	private static DataSource dataSource = null;
	
	protected ConnectionFactory() {
		// singleton pattern
	}
	
	public static Connection getInstance() throws NamingException, SQLException {
		
		if (dataSource == null) {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
		
			if (envCtx == null) {
				throw new NamingException("No Context Found");
			}
			
			dataSource = (DataSource) envCtx.lookup("jdbc/restDB");
		}
		
		return dataSource.getConnection(); 
		
	}
}
