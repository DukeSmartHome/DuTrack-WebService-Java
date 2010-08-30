package utilities;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {
	private static DataSource gtsDataSource = null;
	private static DataSource routesDataSource = null;
	
	private static Context envCtx = null;

	
	public static synchronized Connection getGtsConnection() throws SQLException {
		if (envCtx == null) envCtx = establishContext();
		try {
			gtsDataSource = (DataSource) envCtx.lookup("jdbc/restDB/gts");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return gtsDataSource.getConnection();
	}
	
	public static synchronized Connection getDataConnection() throws SQLException {
		if (envCtx == null) envCtx = establishContext();
		try {
			routesDataSource = (DataSource) envCtx.lookup("jdbc/restDB/data");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return routesDataSource.getConnection();
	}
	
	private static Context establishContext () {
		Context initCtx;
		try {
			initCtx = new InitialContext();
			Context envCtx;
			envCtx = (Context) initCtx.lookup("java:comp/env");

			if (envCtx == null) {
				throw new NamingException("No Context Found");
			}
			
			return envCtx;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
