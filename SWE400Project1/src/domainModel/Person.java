package domainModel;
import java.util.ArrayList;

public class Person {
	private ArrayList<Friend> friends = new ArrayList<Friend>();
	private ArrayList<MyPendingFriends> pending = new ArrayList<MyPendingFriends>();	

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
	
	public ArrayList<Friend> getFriends(){
		return friends;
	}
	
	public ArrayList<MyPendingFriends> getPending(){
		return pending;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	// Just for testing purposes
	public void setUserId(int userId) {
		this.userID = userId;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String toString()
	{
		return username + ":" + password + ":" + displayName;
	}
	
	/**
	 * Marks a friend as new
	 * @param f a new friend 
	 */
	public void markNew(Friend f){
		UnitOfWork.getThread().registerNewFriend(f);
	}
	
	/**
	 * Marks a friend as a incomingRequest
	 * @param f the requester  
	 */
	public void markIncoming(Friend f){
		UnitOfWork.getThread().registerIncomingRequest(f);
	}
	
	/**
	 * Marks a friend as a outgoingRequest
	 * @param f the requested friend 
	 */
	public void markOutgoing(Friend f){
		UnitOfWork.getThread().registerOutgoingRequests(f);
	}
	
	/**
	 * Marks a friend as deleted
	 * @param f a deleted friend
	 */
	public void markRemoved(Friend f){
		UnitOfWork.getThread().registerDeletedFriend(f);
	}
	
	public void addFriend(Friend f){
		markOutgoing(f);
		CommandToMakeFriendRequest friendRequest = new CommandToMakeFriendRequest(this.userID, f.getUserName());
		friendRequest.execute();
	}

	public void acceptFriendRequest(Friend f) {
		markNew(f);
		CommandToAcceptFriendRequest acceptedRequest = new CommandToAcceptFriendRequest(this.userID, f.getUserName());
		acceptedRequest.execute();
	}
	
	public void deleteFriend(Friend f) {
		markRemoved(f);
		// TODO Auto-generated method stub
	}
}