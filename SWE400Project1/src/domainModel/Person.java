package domainModel;
import java.util.ArrayList;

public class Person {
	private ArrayList<Friend> initialFriends = new ArrayList<Friend>();
	private ArrayList<Friend> initialIncomingFriends = new ArrayList<Friend>();
	private ArrayList<Friend> initialOutgoingFriends = new ArrayList<Friend>();

	private String username;
	private String password;
	private int userID;
	private String displayName;

	public int getUserID(){
		return userID;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
		
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setUserId(int userId) {
		this.userID = userId;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public ArrayList<Friend> getInitialFriends(){
		return initialFriends;
	}
	
	public ArrayList<Friend> getInitialIncomingFriends(){
		return initialIncomingFriends;
	}
	
	public ArrayList<Friend> getInitialOutgoingFriends(){
		return initialOutgoingFriends;
	}
	
	public void loadInitialFriends(ArrayList<Friend> loadFriends) {
		this.initialFriends = loadFriends;
	}
	
	public void loadInitialIncomingRequests(ArrayList<Friend> friends) {
		this.initialIncomingFriends = friends;
	}

	public void loadInitialOutgoingRequests(ArrayList<Friend> friends) {
		this.initialOutgoingFriends = friends;
	}
	
	public String toString()
	{
		return username + ":" + password + ":" + displayName;
	}
}