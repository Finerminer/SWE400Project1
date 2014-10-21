package test;

import static org.junit.Assert.*;

import org.junit.Test;

import domainModel.Person;
import domainModel.PersonGateway;

public class testPersonGateway {

	@Test
	public void testFindPersonByUserID() {
		// username nk3668 password dreamteam display NK3668
		PersonGateway pg = new PersonGateway();
		Person nate = pg.find(1);
		assertEquals(nate.getUsername(), "nk3668");
		assertEquals(nate.getPassword(), "dreamteam");
		assertEquals(nate.getDisplayName(), "NK3668");
	}
	
	@Test
	public void testFindPersonByUsernameAndPassword() {
		// username nk3668 password dreamteam display NK3668
		PersonGateway pg = new PersonGateway();
		Person nate = pg.find("nk3668", "dreamteam");
		assertEquals(nate.getUserID(), 1);
		assertEquals(nate.getUsername(), "nk3668");
		assertEquals(nate.getPassword(), "dreamteam");
		assertEquals(nate.getDisplayName(), "NK3668");
	}
	
	@Test
	public void testFindPersonByUsername() {
		// username:nk3668 password:dreamteam display:NK3668
		PersonGateway pg = new PersonGateway();
		int nateID = pg.find("nk3668");
		assertEquals(nateID, 1);
	}
	
	@Test
	public void insertPerson() {
		PersonGateway pg = new PersonGateway();
		pg.insert("ryanw", "goats", "circlethrower");
		Person ryan = pg.find("ryanw", "goats");
		assertEquals(ryan.getUsername(), "ryanw");
		assertEquals(ryan.getPassword(), "goats");
		assertEquals(ryan.getDisplayName(), "circlethrower");
		assertNotNull(ryan.getUserID());
		pg.delete(ryan.getUserID());
	}
	
	@Test
	public void deletePerson() {
		PersonGateway pg = new PersonGateway();
		pg.insert("ryanw", "goats", "circlethrower");
		Person ryan = pg.find("ryanw", "goats"); 
		assertEquals(ryan.getUsername(), "ryanw");
		pg.delete(ryan.getUserID());
		assertEquals(-1, pg.find(ryan.getUsername()));
	}
	
	@Test
	public void duplicateUsernameError() {
		PersonGateway pg = new PersonGateway();
		pg.insert("test1", "password", "display1");
		// This will cause a catch in turn which will print out the stack trace to the exception in PersonGateway
		pg.insert("test1", "password", "display2");
		Person test = pg.find("test1", "password");
		assertEquals(test.getUsername(), "test1");
		// Insert same username twice with different display names, first person inserted will, but second won't sincet they're equal
		assertEquals(test.getDisplayName(), "display1"); 
		pg.delete(test.getUserID());
	}
}
