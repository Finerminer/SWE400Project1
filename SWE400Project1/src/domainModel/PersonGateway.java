package domainModel;

import java.util.ArrayList;


/**
 * 
 * @author Nathaniel
 *
 */
public class PersonGateway {
	
	private int userID;
	
	public Person find(int userID) { 
		//TODO SQL With JDBC
		return null;
	}
	
	public ArrayList<Person> findAll() {
		//TODO SQL With JDBC
		return null;
	}

	public void insert(String username,String password,String firstName,String lastName) {
		
	}
	
	public void delete(int userID) {
		
	}
	
	public void setUserID(int userID){
		this.userID = userID;
	}

	public int getUserID() {
		return userID;
	}

}
