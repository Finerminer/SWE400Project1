package domainModel;

import java.util.ArrayList;

public class UnitOfWork {
	
	private ArrayList<Friend> newFriends = new ArrayList<Friend>();
	private ArrayList<Friend> deletedFriends = new ArrayList<Friend>();
	private ArrayList<Friend> incomingRequest = new ArrayList<Friend>();
	private ArrayList<Friend> outgoingRequest = new ArrayList<Friend>();
	
	protected PersonMapper mapper = new PersonMapper();
	protected Person person;
	private static ThreadLocal<UnitOfWork> thread = new ThreadLocal<UnitOfWork>();
	
	
	public Person findPerson(String username, String password)
	{
		Person p = mapper.find(username, password);
		if(p != null)
		{
			person = p;
			loadFriends(person.getUserID());
			loadIncomingRequests(person.getUserID());
			loadOutgoingRequests(person.getUserID());
			return p;
		}
		return null;
	}
	
	public void createPerson(String username, String password,String displayName)
	{
		mapper.insertPerson(username, password, displayName);
	}
	
	/**
	 * Registers a friend as new by adding them to newFriends list.
	 * Removes them from the incoming/outgoing requests lists.
	 * @param friend the requested friend 
	 */
	public void registerNewFriend(Friend friend){
		if((!deletedFriends.contains(friend))||(!newFriends.contains(friend)))
		{
			incomingRequest.remove(friend);
			outgoingRequest.remove(friend);
			newFriends.add(friend);	
		}
	}
	
	/**
	 * Registers friend as deleted by adding them to deletedFriends list
	 * and removes them from all other lists
	 * @param friend to be deleted
	 */
	public void registerDeletedFriend(Friend friend){
		incomingRequest.remove(friend);
		outgoingRequest.remove(friend);
		newFriends.remove(friend);
		if(!deletedFriends.contains(friend)){
			deletedFriends.add(friend);	
		}
	}
	
	/**
	 * @param friend to be evaluated
	 * @return true if friend is in a list and false otherwise
	 */
	private boolean isFriendInList(Friend friend){
		if((!deletedFriends.contains(friend)) || (!newFriends.contains(friend))||
		   (!outgoingRequest.contains(friend)) || (!incomingRequest.contains(friend))){
			return false;
		}
		return true;
	}
	
	/**
	 * Registers potential friend as requesting a friend request 
	 * by adding them to incomingRequests list
	 * @param friend making the request
	 */
	public void registerIncomingRequest(Friend friend){
		if(!isFriendInList(friend))
		{
			incomingRequest.add(friend);
		}	
	}
	
	/**
	 * Registers potential friend as receiving a friend request
	 * by adding them to outgoingRequests list
	 * @param friend getting the request
	 */
	public void registerOutgoingRequests(Friend friend){
		if(!isFriendInList(friend))
		{
			outgoingRequest.add(friend);
		}	
	}	
	
	/**
	 * Removes every Friends from every list in UOW
	 */
	public void clearFriendsLists() {
		newFriends.clear();
		incomingRequest.clear();
		outgoingRequest.clear();
		deletedFriends.clear();
	}
	
	/**
	 * Locates DataMapper for a Person and invoke 
	 * the appropriate mapping method on their friends
	 */
	public void commit(){
		addNew();
		updatePending();
		removeDelete();
		clearFriendsLists();
	}
	
//	private void loadChanges() {
//		outgoingRequest = person.getOutgoingFriends();
//		newFriends = person.getFriends();
//	}

	/**
	 * Iterates through newFriends list and for each
	 * friend in list call mapper's insert method
	 * and passes it the persons userId and friends ? 
	 */
	private void addNew() {
		for(Friend f: newFriends)
		{
			System.out.println("Person: " + person.getUsername() + " " + person.getUserID());
			System.out.println("Friend: " + f.getUserName());
			mapper.addFriend(person.getUserID(), f.getUserName());
		}
		System.out.println("addedNew complete");
	}
	
	/**
	 * Iterates through incomingRequest & outgoingRequest lists
	 * and for each friend in a list call mapper's insert method
	 * and passes it the persons userId and friends ? 
	 */
	private void updatePending() {
		for(Friend f: incomingRequest)
		{
			System.out.println("Person: " + person.getUsername() + " " + person.getUserID());
			System.out.println("Friend: " + f.getUserName());
			mapper.addIncomingRequest(person.getUserID(), f.getUserName());
		}
		System.out.println("updateIncoming complete");
		for(Friend f: outgoingRequest)
		{
			System.out.println("Person: " + person.getUsername() + " " + person.getUserID());
			System.out.println("Friend: " + f.getUserName());
			mapper.addOutgoingRequest(person.getUserID(), f.getUserName());
		}
		System.out.println("updateOutgoing complete");
	}

	/**
	 * Iterates through deletedFriends list and for each
	 * friend in list call mapper's delete method
	 * and passes it the persons userId and friends ? 
	 */
	private void removeDelete() {
		for(Friend f: deletedFriends)
		{
			System.out.println("Person: " + person.getUsername() + " " + person.getUserID());
			System.out.println("Friend: " + f.getUserName());
			mapper.deleteFriend(person.getUserID(), f.getUserName());
		}
		System.out.println("deleteRemoved complete");
	}
	
	/**
	 * @return thread the current thread for a UnitOfWork
	 */
	public static UnitOfWork getThread() {
		return (UnitOfWork) thread.get();
	}

	/**
	 * Sets current thread for specific UnitOfWork
	 * @param unit the UnitOfWork
	 */
	public static void setThread(UnitOfWork unit) {
		thread.set(unit);
	}
	
	public ArrayList<Friend> getFriendsFromCurrent() {
		return person.getInitialFriends();
	}
	
	/**
	 * @return newFriends list
	 */
	public ArrayList<Friend> getNewFriends() {
		return newFriends;
	}

	/**
	 * @return deletedFriends list
	 */
	public ArrayList<Friend> getDeletedFriends() {
		return deletedFriends;
	}
	
	/**
	 * @return incomingRequest list
	 */
	public ArrayList<Friend> getIncomingRequests() {
		return incomingRequest;
	}
	
	/**
	 * @return outgoingRequest list
	 */
	public ArrayList<Friend> getOutgoingRequests(){
		return outgoingRequest;
	}

	/**
	 * @return person the UOW is using
	 */
	public Person getPerson() {
		return person;
	}

	public void modifyName(int userID, String newDisplayName) {
		mapper.modifyName(userID, newDisplayName);
	}

	public void makeFriendRequest(int userIDOfRequester, String userNameOfRequestee) {
		mapper.makeFriendRequest(person, userIDOfRequester, userNameOfRequestee);
	}
	
	public void acceptFriendRequest(int userIDOfRequester, String userNameOfRequestee){
		mapper.acceptFriendRequest(person, userIDOfRequester, userNameOfRequestee);
	}

	public void deleteFriendInList(int userIDOfRequester, String userNameOfRequestee) {
		mapper.deleteFriendInList(person, userIDOfRequester, userNameOfRequestee);
	}

	public void rejectRequest(int userIDOfRequestee, String userNameOfRequester) {
		mapper.rejectRequest(person, userIDOfRequestee, userNameOfRequester);	
	}

	public void loadIncomingRequests(int userID) {
		ArrayList<Friend> incomingFriends = mapper.loadIncomingRequests(userID); 
		person.loadIncomingRequests(incomingFriends);
	}

	public void loadOutgoingRequests(int userID) {
		ArrayList<Friend> outgoingFriends = mapper.loadOutgoingRequests(userID); 
		person.loadOutgoingRequests(outgoingFriends);
	}
	
	public void loadFriends(int userID){
		ArrayList<Friend> currentFriends = mapper.loadFriends(userID); 
		person.loadFriends(currentFriends);
	}

	public void removeOutgoing(Friend f) {
		this.outgoingRequest.remove(f);
	}
}
