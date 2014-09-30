package test;

import static org.junit.Assert.*;
import org.junit.Test;
import domainModel.Person;


public class testPerson {

	@Test
	public void testCreatePerson() {
		Person p = new Person();
		p.setFirstName("G");
		p.setLastName("A");
		p.setUsername("qwe");
		p.setPassword("123");
		p.addPerson();
		MockPersonGateway.find(p.getUserID());
		assertNotNull(p);
		
	}

}

class MockPersonGateway{
	String username;
	String password;
	int userID;
	String firstName;
	String lastName;
	
	public static MockPersonGateway find(int userID) {
		
		return null;
	}

	public void insert() {
		
	}
	
	public void update() {
		
	}
	
	public void delete() {
		
	}
}
