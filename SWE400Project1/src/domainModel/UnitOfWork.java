package domainModel;

import java.util.ArrayList;

public class UnitOfWork {
	private ArrayList<Friend> newFriends = new ArrayList<Friend>();
	private ArrayList<Friend> deletedFriends = new ArrayList<Friend>();
	private ArrayList<Friend> incomingRequests = new ArrayList<Friend>();
	private ArrayList<Friend> outgoingRequests = new ArrayList<Friend>();
	private ArrayList<Friend> deletedPendingRequest = new ArrayList<Friend>();
	
	protected PersonMapper mapper = new PersonMapper();
	protected Person person;
	private static ThreadLocal<UnitOfWork> thread = new ThreadLocal<UnitOfWork>();
	
	public Person findPerson(String username, String password)
	{
		Person p = mapper.find(username, password);
		if(p != null)
		{
			person = p;
			loadInitialFriends(person.getUserID());
			loadInitialIncomingRequests(person.getUserID());
			loadInitialOutgoingRequests(person.getUserID());
			return p;
		}
		return null;
	}
	
	private void loadInitialFriends(int userID){
		ArrayList<Friend> currentFriends = mapper.loadFriends(userID); 
		person.loadInitialFriends(currentFriends);
	}
	
	private void loadInitialIncomingRequests(int userID) {
		ArrayList<Friend> incomingFriends = mapper.loadIncomingRequests(userID); 
		person.loadInitialIncomingRequests(incomingFriends);
	}

	private void loadInitialOutgoingRequests(int userID) {
		ArrayList<Friend> outgoingFriends = mapper.loadOutgoingRequests(userID); 
		person.loadInitialOutgoingRequests(outgoingFriends);
	}	

	public void createPerson(String username, String password,String displayName)
	{
		mapper.insertPerson(username, password, displayName);
	}
	
	/**
	 * Registers a friend as new by adding them to newFriends list.
	 * However there must be a incoming request for friend
	 * Removes them from the incoming/outgoing requests lists.
	 * @param friend the requested friend 
	 */
	public void registerNewFriend(Friend friend){
		boolean friendFound = false;
		if(person != null){
			for(Friend f: person.getInitialIncomingFriends()){
				if(f.getUserName().equals(friend.getUserName())){
					friendFound = true;
				}
			}	
		}
		if(friendFound || isFriendInThisList(incomingRequests, friend.getUserName())){
			if( (!isFriendInThisList(deletedPendingRequest, friend.getUserName())) &&
				(!isFriendInThisList(newFriends, friend.getUserName())) &&
				(!isFriendInThisList(deletedFriends, friend.getUserName())) ){
				removeFriendInArrayList(incomingRequests, friend.getUserName());
				removeFriendInArrayList(outgoingRequests, friend.getUserName());
				deletedPendingRequest.add(friend);
				newFriends.add(friend);	
			}
		}
	}
	
	public boolean isFriendInThisList(ArrayList<Friend> list, String name){
		for(Friend f: list){
			if(f.getUserName().equals(name)){
				return true;
			}
		}
		return false;
	}
	
	private void removeFriendInArrayList(ArrayList<Friend> list, String name){
		Friend friendFound = null;
		for(Friend f: list){
			if(f.getUserName().equals(name)){
				friendFound = f;
			}
		}
		list.remove(friendFound);
	}
	
	
	/**
	 * Registers friend as deleted by adding them to deletedFriends list
	 * and removes them from all other lists
	 * @param friend to be deleted
	 */
	public void registerDeletedFriend(Friend friend){
		String name = friend.getUserName();
		if( (!this.isFriendInThisList(this.incomingRequests, name)) &&
			(!this.isFriendInThisList(this.outgoingRequests, name)) &&
			(!this.isFriendInThisList(this.deletedFriends, name)) ){
			this.removeFriendInArrayList(this.newFriends, name);
			this.deletedFriends.add(friend);
		}
	}
	
	/**
	 * Registers potential friend as requesting a friend request 
	 * by adding them to incomingRequests list
	 * @param friend making the request
	 */
	public void registerIncomingRequest(Friend friend){
		if(!isFriendInList(friend))
		{
			incomingRequests.add(friend);
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
			outgoingRequests.add(friend);
		}	
	}

	public void registerDeletedPendingRequest(Friend friend){
		String name = friend.getUserName();
		if( (!this.isFriendInThisList(this.deletedPendingRequest, name)) &&
			(!this.isFriendInThisList(this.newFriends, name)) &&
			(!this.isFriendInThisList(this.deletedFriends, name)) ){
			
			this.removeFriendInArrayList(this.outgoingRequests, name);
			this.removeFriendInArrayList(this.incomingRequests, name);
			this.deletedPendingRequest.add(friend);
		}
	}
	
	/**
	 * @param friend to be evaluated
	 * @return true if friend is in a list and false otherwise
	 */
	private boolean isFriendInList(Friend friend){
		if( (!this.isFriendInThisList(this.deletedFriends, friend.getUserName())) &&
			(!this.isFriendInThisList(this.newFriends, friend.getUserName())) &&
			(!this.isFriendInThisList(this.outgoingRequests, friend.getUserName())) &&
			(!this.isFriendInThisList(this.incomingRequests, friend.getUserName())) &&
			(!this.isFriendInThisList(this.deletedPendingRequest, friend.getUserName()))){
			return false;
		}
		return true;
	}
	
	/**
	 * Removes every Friends from every list in UOW
	 */
	public void clearFriendsLists() {
		newFriends.clear();
		deletedFriends.clear();
		incomingRequests.clear();
		outgoingRequests.clear();
		deletedPendingRequest.clear();
	}
	
	public void reloadPersonsList(){
		loadInitialFriends(person.getUserID());
		loadInitialIncomingRequests(person.getUserID());
		loadInitialOutgoingRequests(person.getUserID());
	}
	
	/**
	 * Locates DataMapper for a Person and invoke 
	 * the appropriate mapping method on their friends
	 */
	public void commit(){
		addNew();
		updatePending();
		removeDeletedPending();
		removeDelete();
		clearFriendsLists();
		reloadPersonsList();
		System.out.println("Changes persisted and " + person.getUsername() + "'s lists reloaded"); 
	}
	
	/**
	 * Iterates through newFriends list and for each
	 * friend in list call mapper's insert method
	 * and passes it the persons userId and friends ? 
	 */
	private void addNew() {
		for(Friend f: newFriends)
		{
			System.out.println("Person: " + person.getUsername() + " " + person.getUserID() + " added Friend: " + f.getUserName());
			mapper.addFriend(person.getUserID(), f.getUserName());
		}
	}
	
	/**
	 * Iterates through incomingRequest & outgoingRequest lists
	 * and for each friend in a list call mapper's insert method
	 * and passes it the persons userId and friends ? 
	 */
	private void updatePending() {
		for(Friend f: incomingRequests)
		{
			System.out.println("Person: " + person.getUsername() + " " + person.getUserID() + " has a request from Friend: " + f.getUserName());
			mapper.addIncomingRequest(person.getUserID(), f.getUserName());
		}
		for(Friend f: outgoingRequests)
		{
			System.out.println("Person: " + person.getUsername() + " " + person.getUserID() + " sent a request to Friend: " + f.getUserName());
			mapper.addOutgoingRequest(person.getUserID(), f.getUserName());
		}
	}
	
	private void removeDeletedPending(){
		for(Friend f: deletedPendingRequest){
			System.out.println("Person: " + person.getUsername() + " " + person.getUserID() + " removes request from Friend: " + f.getUserName());
			mapper.deleteRequest(person, f);
		}
	}

	/**
	 * Iterates through deletedFriends list and for each
	 * friend in list call mapper's delete method
	 * and passes it the persons userId and friends ? 
	 */
	private void removeDelete() {
		for(Friend f: deletedFriends)
		{
			System.out.println("Person: " + person.getUsername() + " " + person.getUserID() + " unfriended Friend: " + f.getUserName());
			mapper.deleteFriend(person.getUserID(), f.getUserName());
		}
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
		return incomingRequests;
	}
	
	/**
	 * @return outgoingRequest list
	 */
	public ArrayList<Friend> getOutgoingRequests(){
		return outgoingRequests;
	}
	
	/**
	 * @return deletedPendingRequest list
	 */
	public ArrayList<Friend> getDeletedPendingRequests() {
		return deletedPendingRequest;
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
		Friend f = mapper.makeFriendRequest(person, userIDOfRequester, userNameOfRequestee);
		this.registerOutgoingRequests(f);
	}
	
	public void acceptFriendRequest(int userIDOfRequester, String userNameOfRequestee){
		Friend f = mapper.acceptFriendRequest(person, userIDOfRequester, userNameOfRequestee);
		this.registerNewFriend(f);
	}

	public void deleteFriendInList(int userIDOfRequester, String userNameOfRequestee) {
		Friend f = mapper.deleteFriendInList(person, userIDOfRequester, userNameOfRequestee);
		this.registerDeletedFriend(f);
	}

	public void rejectFriendRequest(int userIDOfRequestee, String userNameOfRequester) {
		Friend f = mapper.rejectFriendRequest(person, userIDOfRequestee, userNameOfRequester);
		this.registerDeletedPendingRequest(f);
	}

	public String printFriendsInLists(ArrayList<Friend> initialFriends, ArrayList<Friend> friends){
		ArrayList<Friend> mergedList = friends;
		for(Friend f : initialFriends){
			mergedList.add(f);
		}
		if( (!this.deletedFriends.isEmpty()) && (!this.deletedPendingRequest.isEmpty()) ){
			mergedList = updateResult(mergedList);
		}
		String result="";
		Boolean first = true;
		for(Friend f : mergedList){
			if(first) {
				result = result + f.getUserName();
				first = false;
			} else {
				result+= "," + f.getUserName();
			}
		}
		return result;
	}

	private ArrayList<Friend> updateResult(ArrayList<Friend> friends) {
		ArrayList<Friend> updatedList = new ArrayList<Friend>();
		for(Friend f: friends){
			String name = f.getUserName();
			if((!this.isFriendInThisList(this.deletedFriends, name)) &&
				(!this.isFriendInThisList(this.deletedPendingRequest, name))){
				updatedList.add(f);
			}
		}
		return updatedList;
	}
}
