package domainModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FriendGateway {
	
	public ArrayList<Integer> getFriends(int userID) {
		String SQL = "select * from fitness1.Friends where User_ID_Requester = ? OR User_ID_Requestee = ?;";
		PreparedStatement stmt;
		ArrayList<Integer> friends = new ArrayList<Integer>();
		try {
			stmt = DB.getConnection().prepareStatement(SQL);
			stmt.setInt(1, userID);
			stmt.setInt(2, userID);
			ResultSet results = stmt.executeQuery();
			
			while(results.next()) {
				if(results.getInt(1) == userID)
					friends.add(results.getInt(2));
				else
					friends.add(results.getInt(1));
			}
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Error finding people.");
			e.printStackTrace();
		}
		return friends;
	}
	
	public void addFriend(int userID, int friendID) {
		String SQL = "insert into fitness1.Friends (User_ID_Requester, User_ID_Requestee) values(?, ?);";
		PreparedStatement stmt = null;
		try {
			stmt = DB.getConnection().prepareStatement(SQL);
			stmt.setInt(1, userID);
			stmt.setInt(2, friendID);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Error inserting person.");
			e.printStackTrace();
		}
	}

	public void deleteFriend(int userID, int friendID) {
		String SQL = "delete from fitness1.Friends where (User_ID_Requester = ? AND User_ID_Requestee = ?) OR (User_ID_Requestee = ? AND User_ID_Requester = ?;";
		PreparedStatement stmt = null;
		try {
			stmt = DB.getConnection().prepareStatement(SQL);
			stmt.setInt(1, userID);
			stmt.setInt(2, friendID);
			stmt.setInt(3, userID);
			stmt.setInt(4, friendID);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Error finding person to delete.");
			e.printStackTrace();
		}	
	}

}
