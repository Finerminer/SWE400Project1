package test;

import static org.junit.Assert.*;

import org.junit.Test;

import domainModel.Friend;
import domainModel.Person;
import domainModel.UnitOfWork;

public class testUnitOfWork {

	@Test
	public void testFindPerson(){
		Person p = new Person();
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().findPerson("jake", "password");
		//TODO: Will fail because DB is not set up yet
		assertEquals(p, UnitOfWork.getThread().getPerson());
	}
	
	@Test
	public void testCreatePerson(){
		Person p = new Person();
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("jake", "1234", "jakesPage");
		//TODO: Will fail because DB is not set up yet
		assertEquals(p, UnitOfWork.getThread().findPerson("jake", "1234"));
	}
	
	@Test
	public void testAcceptAFriendRequest() 
	{
		Person p = new Person();
		p.setDisplayName("bobsPage");
		p.setPassword("5678");
		p.setUsername("bob");
		
		UnitOfWork.setThread(new UnitOfWork());
		Friend f = new Friend("fred",null);
		p.acceptFriendRequest(p.getUserID(), f.getUserName());
		assertTrue(UnitOfWork.getThread().getNewFriends().contains(f));
	}
	
	@Test
	public void testMakeAFriendRequest() 
	{
		Person p = new Person();
		p.setDisplayName("bobsPage");
		p.setPassword("5678");
		p.setUsername("bob");
		
		UnitOfWork.setThread(new UnitOfWork());
		Friend f = new Friend("fred","fredsPage");
		p.addFriend(f);
		assertTrue(UnitOfWork.getThread().getOutgoingRequests().contains(f));
	}
	
	@Test
	public void testGetAFriendRequest() 
	{
		Person p = new Person();
		p.setDisplayName("bobsPage");
		p.setPassword("5678");
		p.setUsername("bob");
		
		UnitOfWork.setThread(new UnitOfWork());
		Friend f = new Friend("fred","fredsPage");
		//TODO: test after database is working
		assertTrue(UnitOfWork.getThread().getIncomingRequests().contains(f));
	}
	
	@Test
	public void testRemoveAFriend() 
	{
		Person p = new Person();
		p.setDisplayName("bobsPage");
		p.setPassword("5678");
		p.setUsername("bob");
		
		UnitOfWork.setThread(new UnitOfWork());
		Friend f = new Friend("fred","fredsPage");
		p.deleteFriend(f);
		assertTrue(UnitOfWork.getThread().getDeletedFriends().contains(f));
	}
}