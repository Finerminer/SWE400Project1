package domainModel;
import java.util.ArrayList;

public class Person {
	private ArrayList<Friend> friends = new ArrayList<Friend>();
	private ArrayList<Friend> incomingFriends = new ArrayList<Friend>();
	private ArrayList<Friend> outgoingFriends = new ArrayList<Friend>();

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
	
	public ArrayList<Friend> getIncomingFriends(){
		return incomingFriends;
	}
	
	public ArrayList<Friend> getOutgoingFriends(){
		return outgoingFriends;
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
	
	public String toString()
	{
		return username + ":" + password + ":" + displayName;
	}
	
//	/**
//	 * Marks a friend as new
//	 * @param f a new friend 
//	 */
//	private void markNew(Friend f){
//		UnitOfWork.getThread().registerNewFriend(f);
//	}
	
	/**
	 * Marks a friend as a incomingRequest
	 * @param f the requester  
	 */
	@SuppressWarnings("unused")
	private void markIncoming(Friend f){
		UnitOfWork.getThread().registerIncomingRequest(f);
	}
//	
//	/**
//	 * Marks a friend as a outgoingRequest
//	 * @param f the requested friend 
//	 */
//	private void markOutgoing(Friend f){
//		UnitOfWork.getThread().registerOutgoingRequests(f);
//	}
	
	/**
	 * Marks a friend as deleted
	 * @param f a deleted friend
	 */
	private void markRemoved(Friend f){
		UnitOfWork.getThread().registerDeletedFriend(f);
	}
	
	public void makeFriendRequest(int userIDOfRequester, String userNameOfRequestee, String displayNameOfRequestee){
		if(!checkCurrent(userIDOfRequester)){
		}else{
			Friend f = new Friend(userNameOfRequestee, displayNameOfRequestee); 
			outgoingFriends.add(f);
			//markOutgoing(f);
		}
	}
	
    //user.acceptFriendRequest(userIDOfRequester, userNameOfRequestee, displayNameOfRequestee);
	public void acceptFriendRequest(int userIDOfRequester, String userNameOfRequestee, String displayNameOfRequestee) {
		if(!checkCurrent(userIDOfRequester)){
		}else{
			Friend f = new Friend(userNameOfRequestee, displayNameOfRequestee);
			friends.add(f);
			//markNew(f);
		}
	}
	
	public void deleteFriendInList(int userIDOfRequester, String userNameOfRequestee, String displayNameOfRequestee) {
		if(!checkCurrent(userIDOfRequester)){
		}else{
			Friend f = new Friend(userNameOfRequestee, displayNameOfRequestee); 
			friends.remove(f);
			markRemoved(f);
		}
	}
	
	public boolean checkCurrent(int userIDOfRequester){
		if(userIDOfRequester==this.userID)
			return true;
		else
			return false;
	}

	public void rejectFriendRequest(int userIDOfRequestee, String userNameOfRequester, String displayNameOfRequester) {
		if(!checkCurrent(userIDOfRequestee)){	
		}else{
			Friend f = new Friend(userNameOfRequester, displayNameOfRequester);
			incomingFriends.remove(f);
			markRemoved(f);
		}
	}

	public void loadIncomingRequests(ArrayList<Friend> friends) {
		incomingFriends = friends;
	}

	public void loadOutgoingRequests(ArrayList<Friend> friends) {
		outgoingFriends = friends;
	}
}