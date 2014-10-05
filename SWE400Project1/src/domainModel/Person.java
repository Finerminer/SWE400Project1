package domainModel;
import java.util.ArrayList;

import command.MakeFriendRequest;

public class Person {
	private ArrayList<Friend> friends = new ArrayList<Friend>();
	private ArrayList<MyPendingFriends> pending = new ArrayList<MyPendingFriends>();	
	
	protected PersonMapper dm = new PersonMapper();
	
	public UnitOfWork uow = new UnitOfWork();
	
	private String username;
	private String password;
	private int userID;
	private String displayName;

	public boolean addPerson(){
		dm.insertPerson(username, password, displayName);
		markNew();
		return false;
	}
	
	public boolean removePerson(){
		dm.deletePerson(userID);
		markRemoved();
		return false;
	}
	
	public boolean updatePerson(){
		dm.updatePerson(userID, username, password, displayName);
		markDirty();
		return false;
	}
	
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
	
	public void addFriend(String userNameOfRequestee){
		MakeFriendRequest friendRequest = new MakeFriendRequest(this.userID, userNameOfRequestee);
		friendRequest.execute();
	}
	
	/**
	 * Marks a person object as new
	 */
	private void markNew(){
		UnitOfWork.getThread().registerNew(this);
	}
	
	/**
	 * Marks a person object as dirty
	 */
	private void markDirty(){
		UnitOfWork.getThread().registerDirty(this);
	}
	
	/**
	 * Marks a person object as removed
	 */
	private void markRemoved(){
		UnitOfWork.getThread().registerRemoved(this);
	}
}
