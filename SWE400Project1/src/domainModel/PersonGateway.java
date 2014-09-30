package domainModel;


/**
 * 
 * @author Nathaniel
 *
 */


public class PersonGateway {
	//Do we want a separate class for the purposes of Finding people in the DB? "PersonFinder" RYANWEAVER
	// Do we need all these instance variables here AND in person? or should person contain a PersonGateway?
	String username;
	String password;
	int userID;
	String firstName;
	String lastName;
	
	public PersonGateway find(int userID) { 
		//TODO SQL With JDBC
		return null;
	}
	
	public PersonGateway findAll() {
		//TODO SQL With JDBC
		return null;
	}

	public void insert() {
		
	}
	
	public void update() {
		
	}
	
	public void delete() {
		
	}
	
	//get statements for each variable
}
