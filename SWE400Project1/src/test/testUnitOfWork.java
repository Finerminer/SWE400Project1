package test;

import static org.junit.Assert.*;

import org.junit.Test;

import domainModel.Person;
import domainModel.UnitOfWork;

public class testUnitOfWork {

	@Test
	public void testRegisterNew() 
	{
		Person p = new Person();
		p.setInitialDisplayName("display name");
		p.setInitialPassword("password");
		p.setInitialUsername("username");
		UnitOfWork.setThread(new UnitOfWork());
		p.addPerson();
		assertTrue(UnitOfWork.getThread().getNewObjects().contains(p));
	}
	
	@Test
	public void testRegisterDirty() 
	{
		Person p = new Person();
		p.setInitialDisplayName("display name");
		p.setInitialPassword("password");
		p.setInitialUsername("username");
		UnitOfWork.setThread(new UnitOfWork());
		p.setUserId(12);
		p.setDisplayName("another display");
		assertTrue(UnitOfWork.getThread().getDirtyObjects().contains(p));
	}

	@Test
	public void testRegisterRemoved() 
	{
		Person p = new Person();
		p.setDisplayName("display name");
		p.setPassword("password");
		p.setUsername("username");
		p.setUserId(12);
		UnitOfWork.setThread(new UnitOfWork());
		p.removePerson();
		assertTrue(UnitOfWork.getThread().getRemovedObjects().contains(p));
	}
}
