package domainModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FriendGateway {
	
	/**
	 * Get all friends of this user and will be returned in an ArrayList of Integers.
	 * @param userID
	 * @return ArrayList<Integer> friends
	 */
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
			System.out.println("Error finding friends, do they have friends?");
			e.printStackTrace();
		}
		return friends;
	}
	
	/**
	 * Adds friendship to the Friends table, doesn't matter the order.
	 * @param userID
	 * @param friendID
	 */
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
			System.out.println("Error adding friend.");
			e.printStackTrace();
		}
	}

	/**
	 * Will a friendship from the Friends table in the database. UserID and FriendID are interchangable
	 * @param userID
	 * @param friendID
	 */
	public void deleteFriend(int userID, int friendID) {
		String SQL = "delete from fitness1.Friends where (User_ID_Requester = ? AND User_ID_Requestee = ?) OR (User_ID_Requestee = ? AND User_ID_Requester = ?);";
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
			System.out.println("Error finding friend to delete.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Will get all the people who have friend requests with the Requestee
	 * @param userIDRequestee
	 * @return ArrayList<Integer> requesters
	 */
	public ArrayList<Integer> getIncomingRequests(int userIDRequestee) {
		String SQL = "select User_ID_Requester from fitness1.PendingFriendRequests where User_ID_Requestee = ?;";
		PreparedStatement stmt;
		ArrayList<Integer> requesters = new ArrayList<Integer>();
		try {
			stmt = DB.getConnection().prepareStatement(SQL);
			stmt.setInt(1, userIDRequestee);
			ResultSet results = stmt.executeQuery();
			
			while(results.next()) {
				requesters.add(results.getInt(1));
			}
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Error finding friends requests");
			e.printStackTrace();
		}
		return requesters;
	}
	
	/**
	 * Will get all the people who have been requested by the Requester
	 * @param userIDRequester
	 * @return ArrayList<Integer> requestees
	 */
	public ArrayList<Integer> getOutgoingRequests(int userIDRequester) {
		String SQL = "select User_ID_Requestee from fitness1.PendingFriendRequests where User_ID_Requester = ?;";
		PreparedStatement stmt;
		ArrayList<Integer> requestees = new ArrayList<Integer>();
		try {
			stmt = DB.getConnection().prepareStatement(SQL);
			stmt.setInt(1, userIDRequester);
			ResultSet results = stmt.executeQuery();
			while(results.next()) {
				requestees.add(results.getInt(1));
			}
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Error finding friend requests");
			e.printStackTrace();
		}
		return requestees;
	}
	
	/**
	 * Adds a request from the PendingFriendRequest table
	 * @param userIDRequester
	 * @param userIDRequestee
	 */
	public void addRequest(int userIDRequester, int userIDRequestee) {
		String SQL = "insert into fitness1.PendingFriendRequests (User_ID_Requester, User_ID_Requestee) values(?, ?);";
		PreparedStatement stmt = null;
		try {
			stmt = DB.getConnection().prepareStatement(SQL);
			stmt.setInt(1, userIDRequester);
			stmt.setInt(2, userIDRequestee);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Error adding friend request.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Deletes a request from the PendingFriendRequest table
	 * @param userIDRequester
	 * @param userIDRequestee
	 */
	public void deleteRequest(int userIDRequester, int userIDRequestee) {
		String SQL = "delete from fitness1.PendingFriendRequests where User_ID_Requester = ? AND User_ID_Requestee = ?;";
		PreparedStatement stmt = null;
		try {
			stmt = DB.getConnection().prepareStatement(SQL);
			stmt.setInt(1, userIDRequester);
			stmt.setInt(2, userIDRequestee);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Error finding friend request to delete.");
			e.printStackTrace();
		}
	}

}
