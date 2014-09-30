package test;

import static org.junit.Assert.*;
import org.junit.Test;
import domainModel.Person;


public class testPerson {

	@Test
	public void testCreatePerson() {
		Person p = new Person();
		
	}

}

class MockPersonGateway{
	String username;
	String password;
	int userID;
	String firstName;
	String lastName;
	
	public MockPersonGateway find(int userID) { 
		//TODO SQL With JDBC
		return null;
	}
	
	public MockPersonGateway findAll() {
		//TODO SQL With JDBC
		return null;
	}

	public void insert() {
		
	}
	
	public void update() {
		
	}
	
	public void delete() {
		
	}
}
