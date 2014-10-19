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
			if(incomingRequest.contains(friend))
			{
				incomingRequest.remove(friend);
				newFriends.add(friend);
			}else if (outgoingRequest.contains(friend)){
				outgoingRequest.remove(friend);
				newFriends.add(friend);
			}
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
	 * Locates DataMapper for a Person and invoke 
	 * the appropriate mapping method on their friends
	 */
	public void commit(){
		addNew();
		updatePending();
		removeDelete();
	}
	
	/**
	 * Iterates through newFriends list and for each
	 * friend in list call mapper's insert method
	 * and passes it the persons userId and friends ? 
	 */
	private void addNew() {
		for(Friend f: newFriends)
		{
			mapper.addFriend(person.getUserID(), f.friendID);
		}
	}
	
	/**
	 * Iterates through incomingRequest & outgoingRequest lists
	 * and for each friend in a list call mapper's insert method
	 * and passes it the persons userId and friends ? 
	 */
	private void updatePending() {
		for(Friend f: incomingRequest)
		{
			mapper.addIncomingRequest(person.getUserID(), f.friendID);
		}
		for(Friend f: outgoingRequest)
		{
			mapper.addOutgoingRequest(person.getUserID(), f.friendID);
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
			mapper.deleteFriend(person.getUserID(), f.friendID);
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
}
