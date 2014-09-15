package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Bd {
	static Connection connection;
	static Statement stmt = null;
	
	String root;
	String pass;
	String serverName;
	String baseName;
	

	public Bd(String root, String pass, String serverName, String baseName) {
		this.root = root;
		this.pass = pass;
		this.serverName = serverName;
		this.baseName = baseName;

	}

	public void connect() {
		String driverName = "com.mysql.jdbc.Driver";
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e1) {
			
			e1.printStackTrace();
		}
		String url = "jdbc:mysql://" + serverName + "/" + baseName;
		try{
			connection = DriverManager.getConnection(url, root, pass);
			stmt = connection.createStatement();
			System.out.println("MySQL connect");
		}catch(SQLException e){
			System.out.println("MySQL err connect "+e);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
			
				e1.printStackTrace();
				
				
			}
			connect();
			return;
			
		}

	}

	public void save(String line) throws SQLException, ClassNotFoundException {
		if (connection == null || connection.isClosed()) {
			connect();
		}
		try {
			synchronized (stmt) {
				System.out.println(line);
				stmt.executeUpdate(line);
			}

		} catch (Exception e) {
			System.out.println("Error save " + e);
		}

	}

	public Map<String, String> getData(String imei) {
		Map<String, String> map = new HashMap<String, String>();

		try {
			if (connection == null || connection.isClosed()) {
				connect();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		String query = "SELECT * FROM log WHERE imei = '" + imei
				+ "'  ORDER BY datetime DESC LIMIT 1";
		try {

			stmt.executeQuery(query);
			ResultSet rs = stmt.executeQuery(query);

			
			while (rs.next()) {
				map.put("lat", rs.getString("lat"));
				map.put("lng", rs.getString("lng"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;

	}

}
