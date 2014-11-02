package domainModel;

import java.util.ArrayList;

/*
 * The mapper between Person and PersonGateway. Allows tracking of friends, and finding a Person.
 */
public class PersonMapper {
	protected PersonGateway pGate = new PersonGateway();
	protected FriendGateway fGate = new FriendGateway(); 
	
	/**
	 * Finds a person based on userID
	 * @param userID the Persons ID
	 * @return the Person if one is there.
	 */
	public Person find(String username, String password){
		Person p = pGate.find(username, password);
		if(p == null)
			return null;
		return p;
	}
	
	/**
	 * Inserts a person based on their username, password, and displayName
	 * @param username the Persons username
	 * @param password the Persons password
	 * @param displayName the Persons displayName
	 * @return if the insert is successful.
	 */
	public boolean insertPerson(String username, String password,String displayName){
		pGate.insert(username, password,displayName);
		return false;
	}
	
	/**
	 * Deletes a person based on their userID.
	 * @param userID the Persons ID
	 * @return if the delete is successful.
	 */
	public boolean deletePerson(int userID){
		pGate.delete(userID);
		return false;
	}
	
	/**
	 * Connects two users into a friend table. 
	 * @param userID the user who is adding the friend.
	 * @param friendID the user who is being added.
	 */
	public Friend makeFriendRequest(Person user, int userIDOfRequester, String userNameOfRequestee){
		int friendID = getIDFromUsername(userNameOfRequestee);
		Person friend = pGate.find(friendID);
		String displayNameOfRequestee = friend.getDisplayName();
		return new Friend(userNameOfRequestee, displayNameOfRequestee);
	}

	public int getIDFromUsername(String userNameOfRequestee) {
		return pGate.find(userNameOfRequestee);
	}

	public void addIncomingRequest(int userID, String friendUserName) {
		int friendID = pGate.find(friendUserName);
		fGate.addRequest(friendID, userID);
	}

	public void addOutgoingRequest(int userID, String friendUserName) {
		int friendID = pGate.find(friendUserName);
		fGate.addRequest(userID, friendID);
	}

	public Friend deleteFriendInList(Person user, int userIDOfRequester, String userNameOfRequestee) {
		int friendID = getIDFromUsername(userNameOfRequestee);
		Person friend = pGate.find(friendID);
		String displayNameOfRequestee = friend.getDisplayName();
		return new Friend(userNameOfRequestee, displayNameOfRequestee);
	}

	public Friend acceptFriendRequest(Person user, int userIDOfRequester, String userNameOfRequestee) {
		int friendID = getIDFromUsername(userNameOfRequestee);
		Person friend = pGate.find(friendID);
		String displayNameOfRequestee = friend.getDisplayName();
		return new Friend(userNameOfRequestee, displayNameOfRequestee);
	}

	public void addFriend(int userID, String friendUserName) {
		int friendID = pGate.find(friendUserName);
		fGate.addFriend(userID, friendID);
	}

	public Friend rejectFriendRequest(Person user, int userIDOfRequestee, String userNameOfRequester) {
		int friendID = getIDFromUsername(userNameOfRequester);
		Person friend = pGate.find(friendID);
		String displayNameOfRequester = friend.getDisplayName();
		return new Friend(userNameOfRequester, displayNameOfRequester);
	}

	public void deleteFriend(int userID, String userName) {
		int friendID = pGate.find(userName);
		fGate.deleteFriend(userID, friendID);
	}

	public ArrayList<Friend> loadIncomingRequests(int userID) {		
		ArrayList<Integer> tmp = fGate.getIncomingRequests(userID);
		Person friend = null;
		ArrayList<Friend> incomingRequests = new ArrayList<Friend>();
		for(Integer i: tmp){
			friend = pGate.find(i);
			incomingRequests.add(new Friend(friend.getUsername(), friend.getDisplayName()));
		}
		return incomingRequests;
	}

	public ArrayList<Friend> loadOutgoingRequests(int userID) {
		ArrayList<Integer> tmp = fGate.getOutgoingRequests(userID);
		Person friend = null;
		ArrayList<Friend> outgoingRequests = new ArrayList<Friend>();
		for(Integer i: tmp){
			friend = pGate.find(i);
			outgoingRequests.add(new Friend(friend.getUsername(), friend.getDisplayName()));
		}
		return outgoingRequests;
	}

	public ArrayList<Friend> loadFriends(int userID) {
		ArrayList<Integer> tmp = fGate.getFriends(userID);
		Person friend = null;
		ArrayList<Friend> currentFriends = new ArrayList<Friend>();
		for(Integer i: tmp){
			friend = pGate.find(i);
			currentFriends.add(new Friend(friend.getUsername(), friend.getDisplayName()));
		}
		return currentFriends;
	}

	public void modifyName(int userID, String newDisplayName) {
		pGate.update(userID, newDisplayName);
	}

	public void deleteRequest(Person p, Friend f) {
		fGate.deleteRequest(p.getUserID(), pGate.find(f.getUserName()));
		fGate.deleteRequest(pGate.find(f.getUserName()), p.getUserID());
	}
}
