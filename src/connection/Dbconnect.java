package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dbconnect {

	public Connection getconnect() throws SQLException, ClassNotFoundException {
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		String databaseURL = "jdbc:ucanaccess://637630tranvanhieu.accdb";
		Connection connection = DriverManager.getConnection(databaseURL);
		return connection;
	}
}
