package domainModel;
import java.util.ArrayList;

public class Person {
	private ArrayList<Friend> friends = new ArrayList<Friend>();
	
	protected PersonMapper dm = new PersonMapper();
	
	protected UnitOfWork uow = new UnitOfWork();
	
	private String username;
	private String password;
	private int userID;
	private String displayName;

	public boolean addPerson(){
		dm.insertPerson(username, password, displayName);
		return false;
	}
	
	public boolean removePerson(){
		dm.deletePerson(userID);
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
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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
