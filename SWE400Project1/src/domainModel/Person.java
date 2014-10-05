package domainModel;
import java.util.ArrayList;

public class Person {
	private ArrayList<Friend> friends = new ArrayList<Friend>();
	
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
	
	public void setUsername(String username) {
		this.username = username;
		markDirty();
	}

	public void setPassword(String password) {
		this.password = password;
		markDirty();
	}
	
	// Just for testing purposes
	public void setUserId(int userId) {
		this.userID = userId;
		markDirty();
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
		markDirty();
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
	@SuppressWarnings("unused")
	private void markDirty(){
		UnitOfWork.getThread().registerDirty(this);
	}
	
	/**
	 * Marks a person object as removed
	 */
	private void markRemoved(){
		UnitOfWork.getThread().registerRemoved(this);
	}
	
	/**
	 * Sets Persons user name on account creation
	 * @param name a Persons name
	 */
	public void setInitialUsername(String name){
		this.username = name;
	}
	
	/**
	 * Sets Persons display name on account creation
	 * @param display a Persons display name
	 */
	public void setInitialDisplayName(String display){
		this.displayName = display;
	}
	
	/**
	 * Sets Persons password on account creation
	 * @param pw a Persons password
	 */
	public void setInitialPassword(String pw){
		this.password = pw;
	}
}
