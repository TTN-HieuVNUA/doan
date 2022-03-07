package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import connection.Dbconnect;
import servicedao.UserService;

public class UserDao implements UserService{

	Connection conn;
	PreparedStatement preparedStatement = null;
	Dbconnect dbconnect = new Dbconnect();

	@Override
	public void addUser(String lecCode, String lecName) {
		try {
			conn = dbconnect.getconnect();
			String sql = "INSERT INTO USERS (lecCode, lecName) VALUES (?,?)";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, lecCode);
			preparedStatement.setString(2, lecName);
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<String> getUser() {
		try {
			conn = dbconnect.getconnect();
			String sql = "select * from STUDENT";
			preparedStatement = conn.prepareStatement(sql);
			ResultSet result = preparedStatement.executeQuery();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
