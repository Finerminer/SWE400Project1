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
	
	public ArrayList<Friend> getInitialFriends(){
		return initialFriends;
	}
	
	public ArrayList<Friend> getInitialIncomingFriends(){
		return initialIncomingFriends;
	}
	
	public ArrayList<Friend> getInitialOutgoingFriends(){
		return initialOutgoingFriends;
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
	
	/**
	 * Marks a friend as new
	 * @param f a new friend 
	 */
	private void markNew(Friend f){
		UnitOfWork.getThread().registerNewFriend(f);
	}
	
	/**
	 * Marks a friend as a outgoingRequest
	 * @param f the requested friend 
	 */
	private void markOutgoing(Friend f){
		UnitOfWork.getThread().registerOutgoingRequests(f);
	}
	
	/**
	 * Marks a friend as deleted
	 * @param f a deleted friend
	 */
	private void markRemoved(Friend f){
		UnitOfWork.getThread().registerDeletedFriend(f);
	}
	
	private void markRemovePending(Friend f){
		UnitOfWork.getThread().registerDeletedPendingRequest(f);
	}
	
	public void makeFriendRequest(int userIDOfRequester, String userNameOfRequestee, String displayNameOfRequestee){
		Friend f = new Friend(userNameOfRequestee, displayNameOfRequestee); 
		markOutgoing(f);
	}
	
	public void accepted(String userNameOfRequestee, String displayNameOfRequestee) {
		Friend f = new Friend(userNameOfRequestee, displayNameOfRequestee);
		initialOutgoingFriends.remove(f);
	}
	
	public void acceptFriendRequest(int userIDOfRequester, String userNameOfRequestee, String displayNameOfRequestee) {
		if(!checkCurrent(userIDOfRequester)){
		}else{
			Friend f = new Friend(userNameOfRequestee, displayNameOfRequestee);
			markNew(f);
			markRemovePending(f);
		}
	}
	
	public void deleteFriendInList(int userIDOfRequester, String userNameOfRequestee, String displayNameOfRequestee) {
		if(!checkCurrent(userIDOfRequester)){
		}else{
			Friend f = new Friend(userNameOfRequestee, displayNameOfRequestee); 
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
			markRemoved(f);
		}
	}

	public void loadIncomingRequests(ArrayList<Friend> friends) {
		this.initialIncomingFriends = friends;
	}

	public void loadOutgoingRequests(ArrayList<Friend> friends) {
		this.initialOutgoingFriends = friends;
	}

	public void loadFriends(ArrayList<Friend> loadFriends) {
		this.initialFriends = loadFriends;
	}
}