package domainModel;
import java.util.ArrayList;

public class Person {
	private ArrayList<Friend> friends = new ArrayList<Friend>();
	
	protected PersonMapper dm = new PersonMapper();
	
	private String username;
	private String password;
	private int userID;
	private String firstName;
	private String lastName;

	public boolean addPerson(){
		dm.insertPerson();
		return false;
	}
	
	public boolean removePerson(){
		dm.deletePerson(userID);
		return false;
	}
	
	public int getUserID(){
		return userID;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
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

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
