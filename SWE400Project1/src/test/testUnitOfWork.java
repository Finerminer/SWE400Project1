package test;
import static org.junit.Assert.*;
import org.junit.Test;
import domainModel.Friend;
import domainModel.FriendGateway;
import domainModel.Person;
import domainModel.PersonGateway;
import domainModel.UnitOfWork;

public class testUnitOfWork {

	@Test
	public void testSetAndGetThread(){
		UnitOfWork.setThread(new UnitOfWork());
		assertTrue(UnitOfWork.getThread() != null);
	}
	
	@Test
	public void testCreateAndFindPerson(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("jake", "1234", "jakesPage");
		UnitOfWork.getThread().findPerson("jake", "1234");
		assertTrue(UnitOfWork.getThread().getPerson().getUserID() > 0);
		assertEquals("jakesPage", UnitOfWork.getThread().getPerson().getDisplayName());
		assertEquals("1234", UnitOfWork.getThread().getPerson().getPassword());
		assertEquals("jake", UnitOfWork.getThread().getPerson().getUsername());
		
		PersonGateway pg = new PersonGateway();
		pg.delete(UnitOfWork.getThread().getPerson().getUserID());
	}
	
	@Test
	public void testRegisterIncomingRequest(){
		UnitOfWork.setThread(new UnitOfWork());
		
		Friend f = new Friend("fred",null);
		UnitOfWork.getThread().registerIncomingRequest(f);
		assertTrue(UnitOfWork.getThread().getIncomingRequests().contains(f));
	}
	
	@Test
	public void testRegisterOutgoingRequest(){
		UnitOfWork.setThread(new UnitOfWork());
		Friend f = new Friend("adam",null);
		UnitOfWork.getThread().registerOutgoingRequests(f);
		assertTrue(UnitOfWork.getThread().getOutgoingRequests().contains(f));
	}
	
	@Test
	public void testRegisterNewRequest(){
		UnitOfWork.setThread(new UnitOfWork());
		Friend f = new Friend("bob",null);
		UnitOfWork.getThread().registerIncomingRequest(f);
		UnitOfWork.getThread().registerNewFriend(f);
		assertTrue(UnitOfWork.getThread().getNewFriends().contains(f));
		assertFalse(UnitOfWork.getThread().getIncomingRequests().contains(f));
	}
	
	@Test
	public void testRegisterDeleteRequest(){
		UnitOfWork.setThread(new UnitOfWork());
		Friend f = new Friend("chloe","fredsPage");
		UnitOfWork.getThread().registerDeletedFriend(f);
		assertTrue(UnitOfWork.getThread().getDeletedFriends().contains(f));
	}
	
	@Test
	public void testClearFriendsLists(){
		UnitOfWork.setThread(new UnitOfWork());
		
		Friend david = new Friend("david","davidsPage");
		Friend emma = new Friend("emma","emmasPage");
		Friend liz = new Friend("liz","lizsPage");
		Friend mike = new Friend("mike","mikesPage");
		UnitOfWork.getThread().registerIncomingRequest(david);
		UnitOfWork.getThread().registerOutgoingRequests(emma);
		UnitOfWork.getThread().registerNewFriend(liz);
		UnitOfWork.getThread().registerDeletedFriend(mike);
		UnitOfWork.getThread().clearFriendsLists();
		
		assertFalse(UnitOfWork.getThread().getIncomingRequests().contains(david));
		assertFalse(UnitOfWork.getThread().getOutgoingRequests().contains(emma));
		assertFalse(UnitOfWork.getThread().getNewFriends().contains(liz));
		assertFalse(UnitOfWork.getThread().getDeletedFriends().contains(mike));
	}
	
	//*************************************
	// Test the methods called by Commit()
	//*************************************
	
	@Test
	public void testUpdatePendingIncoming(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("neil", "1234", "neilsPage");
		UnitOfWork.getThread().createPerson("olivia", "5678", "oliviasPage");
		UnitOfWork.getThread().findPerson("neil", "1234");
		
		UnitOfWork.getThread().registerIncomingRequest(new Friend("olivia","oliviasPage"));
		UnitOfWork.getThread().commit();
		
		Person neil = UnitOfWork.getThread().findPerson("neil", "1234");
		assertFalse(neil.getIncomingFriends().isEmpty());
		
		// Delete Friend Relationship from DB
		FriendGateway fg = new FriendGateway();
		int neilsID = neil.getUserID();
		int oliviasID = UnitOfWork.getThread().findPerson("olivia", "5678").getUserID();
		fg.deleteFriend(neilsID, oliviasID);
		// Delete Persons from DB
		PersonGateway pg = new PersonGateway();
		pg.delete(neilsID);
		pg.delete(oliviasID);
	}
	
	@Test
	public void testUpdatePendingOutgoing(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("bob", "1234", "bobsPage");
		UnitOfWork.getThread().createPerson("fred", "5678", "fredsPage");
		UnitOfWork.getThread().findPerson("bob", "1234");
		
		UnitOfWork.getThread().registerOutgoingRequests(new Friend("fred","fredsPage"));
		UnitOfWork.getThread().commit();
		
		Person bob = UnitOfWork.getThread().findPerson("bob", "1234");
		assertFalse(bob.getOutgoingFriends().isEmpty());
		
		// Delete Friend Relationship from DB
		FriendGateway fg = new FriendGateway();
		int bobsID = bob.getUserID();
		int fredsID = UnitOfWork.getThread().findPerson("fred", "5678").getUserID();
		fg.deleteFriend(bobsID, fredsID);
		// Delete Persons from DB
		PersonGateway pg = new PersonGateway();
		pg.delete(bobsID);
		pg.delete(fredsID);
	}
	
	@Test
	public void testAddNewFriend(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("bob", "1234", "bobsPage");
		UnitOfWork.getThread().createPerson("fred", "5678", "fredsPage");
		
		Person bob = UnitOfWork.getThread().findPerson("bob", "1234");
		Friend fred = new Friend("fred","fredsPage");
		UnitOfWork.getThread().registerIncomingRequest(fred);
		UnitOfWork.getThread().registerNewFriend(fred);
		//Call addNew()
		UnitOfWork.getThread().commit();	
		
		// Get Bobs friends
		FriendGateway fg = new FriendGateway();
		Person f = UnitOfWork.getThread().findPerson("fred", "5678");
		assertTrue(fg.getFriends(bob.getUserID()).contains(f.getUserID()));
		
		// Delete Friend Relationship from DB
		PersonGateway pg = new PersonGateway();
		fg.deleteFriend(bob.getUserID(), f.getUserID());
		// Delete Persons from DB
		pg.delete(bob.getUserID());
		pg.delete(f.getUserID());
	}
	
	@Test
	public void testDeleteRemovedFriend(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("bob", "1234", "bobsPage");
		UnitOfWork.getThread().createPerson("fred", "5678", "fredsPage");
		
		// Bob & Fred are friends
		Person bob = UnitOfWork.getThread().findPerson("bob", "1234");
		Friend friend = new Friend("fred","fredsPage");
		UnitOfWork.getThread().registerNewFriend(friend);
		UnitOfWork.getThread().commit();
		
		// Bob un-friends Fred
		UnitOfWork.getThread().registerDeletedFriend(friend);
		// Call deleteRemoved()
		UnitOfWork.getThread().commit();
		
		// Get Bobs friends
		FriendGateway fg = new FriendGateway();
		Person fred = UnitOfWork.getThread().findPerson("fred", "5678");
		assertFalse(fg.getFriends(bob.getUserID()).contains(fred.getUserID()));
		
		// Delete Persons from DB
		PersonGateway pg = new PersonGateway();
		pg.delete(fred.getUserID());
		pg.delete(bob.getUserID());
	}
	
	//*************************************
	// Test methods called by commands
	//*************************************
	
	@Test
	public void testModifyName(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("sally", "9090", "sallysPage");
		UnitOfWork.getThread().findPerson("sally", "9090");
		int sallysID = UnitOfWork.getThread().getPerson().getUserID();
		UnitOfWork.getThread().modifyName(sallysID, "sallysNewPage");
		assertEquals("sallysNewPage",UnitOfWork.getThread().findPerson("sally", "9090").getDisplayName());
		
		// Delete Persons from DB
		PersonGateway pg = new PersonGateway();
		pg.delete(sallysID);
	}
	
	@Test
	public void testMakeFriendRequest(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("sally", "9090", "sallysPage");
		UnitOfWork.getThread().createPerson("liz", "1234", "lizsPage");
		
		Person sally = UnitOfWork.getThread().findPerson("sally", "9090");
		// Sally sends request to liz
		UnitOfWork.getThread().makeFriendRequest(sally.getUserID(), "liz");
		UnitOfWork.getThread().commit();
		
		Person liz = UnitOfWork.getThread().findPerson("liz", "1234");
		FriendGateway fg = new FriendGateway();
		assertTrue(fg.getOutgoingRequests(sally.getUserID()).contains(liz.getUserID()));
		assertTrue(fg.getIncomingRequests(liz.getUserID()).contains(sally.getUserID()));
		
		// Delete Friend Relationship from DB
		fg.deleteFriend(sally.getUserID(), liz.getUserID());
		// Delete Persons from DB
		PersonGateway pg = new PersonGateway();
		pg.delete(sally.getUserID());
		pg.delete(liz.getUserID());
	}
	
	@Test
	public void testAcceptFriendRequest(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("sally", "9090", "sallysPage");
		UnitOfWork.getThread().createPerson("liz", "1234", "lizsPage");
		
		Person sally = UnitOfWork.getThread().findPerson("sally", "9090");
		// Sally sends request to liz
		UnitOfWork.getThread().makeFriendRequest(sally.getUserID(), "liz");
		UnitOfWork.getThread().commit();
		
		// Liz accepts Sally's request
		Person liz = UnitOfWork.getThread().findPerson("liz", "1234");
		UnitOfWork.getThread().acceptFriendRequest(liz.getUserID(), "sally");
		UnitOfWork.getThread().commit();

		FriendGateway fg = new FriendGateway();
		assertTrue(fg.getFriends(sally.getUserID()).contains(liz.getUserID()));
		assertTrue(fg.getFriends(liz.getUserID()).contains(sally.getUserID()));
		
		// Delete Friend Relationship from DB
		fg.deleteFriend(sally.getUserID(), liz.getUserID());
		// Delete Persons from DB
		PersonGateway pg = new PersonGateway();
		pg.delete(sally.getUserID());
		pg.delete(liz.getUserID());
	}
	
	@Test
	public void testDeleteFriend(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("sally", "9090", "sallysPage");
		UnitOfWork.getThread().createPerson("liz", "1234", "lizsPage");
		Person sally = UnitOfWork.getThread().findPerson("sally", "9090");
		Person liz = UnitOfWork.getThread().findPerson("liz", "1234");
		// Sally sends request to Liz
		UnitOfWork.getThread().makeFriendRequest(sally.getUserID(), "liz");
		UnitOfWork.getThread().commit();
		// Liz accepts Sally's request
		UnitOfWork.getThread().acceptFriendRequest(liz.getUserID(), "sally");
		UnitOfWork.getThread().commit();
		// Sally changes her mind & deletes Liz
		UnitOfWork.getThread().deleteFriendInList(sally.getUserID(), "liz");
		UnitOfWork.getThread().commit();
		
		assertFalse(sally.getFriends().contains(liz));
		assertFalse(liz.getFriends().contains(sally));
		
		// Delete Persons from DB
		PersonGateway pg = new PersonGateway();
		pg.delete(sally.getUserID());
		pg.delete(liz.getUserID());
	}
	
	@Test
	public void testRejectRequest(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("sally", "9090", "sallysPage");
		UnitOfWork.getThread().createPerson("liz", "1234", "lizsPage");
		Person sally = UnitOfWork.getThread().findPerson("sally", "9090");
		Person liz = UnitOfWork.getThread().findPerson("liz", "1234");
		// Sally sends request to Liz
		UnitOfWork.getThread().makeFriendRequest(sally.getUserID(), "liz");
		UnitOfWork.getThread().commit();
		// Liz rejects Sally's request
		UnitOfWork.getThread().rejectRequest(liz.getUserID(), "sally");
		UnitOfWork.getThread().commit();
		
		assertFalse(sally.getOutgoingFriends().contains(liz));
		assertFalse(liz.getIncomingFriends().contains(sally));
		assertFalse(sally.getFriends().contains(liz));
		assertFalse(liz.getFriends().contains(sally));
		
		// Delete Persons from DB
		PersonGateway pg = new PersonGateway();
		pg.delete(sally.getUserID());
		pg.delete(liz.getUserID());
	}
}
