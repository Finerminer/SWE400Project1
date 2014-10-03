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
		p.setDisplayName("display name");
		p.setPassword("password");
		p.setUsername("username");
		UnitOfWork uow = new UnitOfWork();
		uow.registerNew(p);
		assertTrue(uow.getNewObjects().contains(p));
	}
	
	@Test
	public void testRegisterDirty() 
	{
		Person p = new Person();
		p.setDisplayName("display name");
		p.setPassword("password");
		p.setUsername("username");
		p.setUserId(12);
		UnitOfWork uow = new UnitOfWork();
		uow.registerDirty(p);
		assertTrue(uow.getDirtyObjects().contains(p));
	}

	@Test
	public void testRegisterRemoved() 
	{
		Person p = new Person();
		p.setDisplayName("display name");
		p.setPassword("password");
		p.setUsername("username");
		p.setUserId(12);
		UnitOfWork uow = new UnitOfWork();
		uow.registerDirty(p);
		uow.registerRemoved(p);
		assertTrue(uow.getRemovedObjects().contains(p));
		assertFalse(uow.getDirtyObjects().contains(p));
	}
}
