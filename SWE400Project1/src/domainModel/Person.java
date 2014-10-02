package domainModel;
import java.util.ArrayList;

public class Person {
	private ArrayList<Friend> friends = new ArrayList<Friend>();
	
	protected PersonMapper dm = new PersonMapper();
	
	private String username;
	private String password;
	private int userID;
	private String displayName;

	public boolean addPerson(String username, String password,String displayName){
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
}
