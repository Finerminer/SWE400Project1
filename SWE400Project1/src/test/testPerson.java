package test;

import static org.junit.Assert.*;

import org.junit.Test;

import domainModel.Person;
import domainModel.PersonMapper;


public class testPerson {

	@Test
	public void test() {
		MockPerson p = new MockPerson();
		p.setUsername("G");
		p.setPassword("123");
		p.setDisplayName("A");
		p.addPerson();
		
		assertNotNull(p);
		assertEquals(MockDatabase.username,"G");
		assertEquals(MockDatabase.password,"123");
		assertEquals(MockDatabase.displayName,"A");
	}

}

class MockDatabase{
	public static String username;
	public static String password;
	public static String displayName;
}

class MockDataMapper extends PersonMapper{
	public boolean insertPerson(String username, String password,String displayName){
		MockDatabase.username = (username);
		MockDatabase.password = (password);
		MockDatabase.displayName = (displayName);
		return false;
	}
}

class MockPerson extends Person{
	
//	public MockPerson(){
//		//dm = new MockDataMapper();
//	}
//	
//	public PersonMapper getMockDataMapper(){
//		return dm;
//	}
}
