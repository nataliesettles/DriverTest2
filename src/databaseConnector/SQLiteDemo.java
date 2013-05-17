package databaseConnector;

import java.sql.*;

/**
 * This class demonstrates how to create a connection to a SQLite database using JDBC
 * with the DriverManager.
 * @author nataliesettles
 */

public class SQLiteDemo {

	//FIXME: This should be provided via external setup (config file), not hard coded!
	public static final String driver = "org.sqlite.JDBC";
	
	public static void main(String[] args) {
		
		// Step 1: Setup the Driver
		try {
			// Load the JDBC driver class dynamically.
			Driver d = (Driver)Class.forName(driver).newInstance();
			DriverManager.registerDriver(d);
		}
		catch(Exception e) {
			System.out.println("Error loading database driver: " + e.toString());
			return;
		}
		
		// Step 2: Create connection to database using database URL
		Connection con;
		try {
			//FIXME: Load database filename via external config.
			String url = "jdbc:sqlite:clinic.db";
			con = DriverManager.getConnection(url);
		}
		catch(SQLException e) {
			System.out.println("Error creating connection: " + e.toString());
			return;
		}
		
		// Step 3:Execute SQL query using using Statement object
		Statement stmt;
		ResultSet res;
		try {
			String sql = "SELECT FirstName, LastName, Email FROM Doctors ORDER by LastName";
			stmt = con.createStatement();
			res = stmt.executeQuery(sql);
		}
		catch(SQLException e) {
			System.out.println("Error creating or running statement: " + e.toString());
			try {
				con.close();
			}
			catch(Exception ex) {}
			return;
			}
		
		// Step 4: Read results
		try {
			String fName, lName, email;
			while(res.next()) {
				email = res.getString("Email");
				fName = res.getString("FirstName");
				lName = res.getString("LastName");
				System.out.println(fName + " " + lName + "  |  " + email);
			}
		}
		catch(Exception e) {
			System.out.println("Error processing results: " + e.toString());
			try {
				res.close();
				stmt.close();
				con.close();
			}
			catch(Exception ex) {}
			return;
		}
		
		// Step 5: Close stuff
		try {
			res.close();
			stmt.close();
			con.close();
		}
		catch(SQLException e) {
			System.out.println("Error closing connections: " + e.toString());
			return;
		}
	}
}
