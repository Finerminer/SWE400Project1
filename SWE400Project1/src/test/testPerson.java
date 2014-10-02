package test;

import static org.junit.Assert.*;

import org.junit.Test;

import domainModel.Person;
import domainModel.PersonMapper;


public class testPerson {

	@Test
	public void testCreatePerson() {
		MockPerson p = new MockPerson();
		p.setFirstName("G");
		p.setLastName("A");
		p.setUsername("qwe");
		p.setPassword("123");
		p.addPerson();
		p.getMockDataMapper().find(p.getUserID());
		assertNotNull(p);
		
	}

}

class MockDataMapper extends PersonMapper{
	
}

class MockPerson extends Person{
	
	public MockPerson(){
		pg = new MockDataMapper();
	}
	
	public PersonMapper getMockDataMapper(){
		return pg;
	}
}
