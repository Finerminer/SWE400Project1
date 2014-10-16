package domainModel;

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
	public void addFriend(int userID, int friendID){
		fGate.addFriend(userID, friendID);
	}

	/**
	 * Updates changes to a person
	 * @param userID the Persons ID
	 * @param username the Persons user name
	 * @param password the Persons password
	 * @param displayName the Persons display name
	 * @return if the update is successful
	 */
	public boolean updatePerson(int userID, String username, String password, String displayName) {
		//pGate.update(userID, username, password, displayName);
		return false;
	}
}
