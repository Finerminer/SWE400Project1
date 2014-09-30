package domainModel;
import java.util.ArrayList;
import java.util.Date;

public class Person {
	private ArrayList<Friend> friends = new ArrayList<Friend>();
	
	private PersonGateway pg = new PersonGateway(); 
	
	private String username;
	private String password;
	private int userID;
	private String firstName;
	private String lastName;

	public boolean addPerson(){
		pg.setUserID(getUserID());
		pg.insert();
		return false;
	}
	
	public boolean removePerson(){
		pg.delete();
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
