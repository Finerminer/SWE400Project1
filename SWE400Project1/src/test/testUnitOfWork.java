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
		assertFalse(UnitOfWork.getThread().getOutgoingRequests().contains(f));
		assertFalse(UnitOfWork.getThread().getNewFriends().contains(f));
		assertFalse(UnitOfWork.getThread().getDeletedFriends().contains(f));
	}
	
	@Test
	public void testRegisterOutgoingRequest(){
		UnitOfWork.setThread(new UnitOfWork());
		Friend f = new Friend("adam",null);
		UnitOfWork.getThread().registerOutgoingRequests(f);
		assertTrue(UnitOfWork.getThread().getOutgoingRequests().contains(f));
		assertFalse(UnitOfWork.getThread().getIncomingRequests().contains(f));
		assertFalse(UnitOfWork.getThread().getNewFriends().contains(f));
		assertFalse(UnitOfWork.getThread().getDeletedFriends().contains(f));
	}
	
	@Test
	public void testRegisterNewRequest(){
		UnitOfWork.setThread(new UnitOfWork());
		Friend f = new Friend("bob",null);
		UnitOfWork.getThread().registerIncomingRequest(f);
		UnitOfWork.getThread().registerOutgoingRequests(f);
		UnitOfWork.getThread().registerNewFriend(f);
		assertTrue(UnitOfWork.getThread().getNewFriends().contains(f));
		assertFalse(UnitOfWork.getThread().getIncomingRequests().contains(f));
		assertFalse(UnitOfWork.getThread().getOutgoingRequests().contains(f));
		assertFalse(UnitOfWork.getThread().getDeletedFriends().contains(f));
	}
	
	@Test
	public void testRegisterDeleteRequest(){
		UnitOfWork.setThread(new UnitOfWork());
		Friend f = new Friend("chloe", null);
		UnitOfWork.getThread().registerNewFriend(f);
		UnitOfWork.getThread().registerIncomingRequest(f);
		UnitOfWork.getThread().registerOutgoingRequests(f);
		UnitOfWork.getThread().registerDeletedFriend(f);
		
		assertTrue(UnitOfWork.getThread().getDeletedFriends().contains(f));
		assertFalse(UnitOfWork.getThread().getNewFriends().contains(f));
		assertFalse(UnitOfWork.getThread().getIncomingRequests().contains(f));
		assertFalse(UnitOfWork.getThread().getOutgoingRequests().contains(f));
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
		UnitOfWork.getThread().createPerson("neil", "9012", "neilsPage");
		UnitOfWork.getThread().createPerson("olivia", "5678", "oliviasPage");
		UnitOfWork.getThread().findPerson("neil", "9012");
		
		UnitOfWork.getThread().registerIncomingRequest(new Friend("olivia","oliviasPage"));
		UnitOfWork.getThread().commit();
		
		Person neil = UnitOfWork.getThread().findPerson("neil", "9012");
		assertFalse(neil.getInitialIncomingFriends().isEmpty());
		
		// Delete Friend Relationship from DB
		int neilsID = neil.getUserID();
		int oliviasID = UnitOfWork.getThread().findPerson("olivia", "5678").getUserID();
		FriendGateway fg = new FriendGateway();
		fg.deleteFriend(neilsID, oliviasID);
		// Delete Persons from DB
		PersonGateway pg = new PersonGateway();
		pg.delete(neilsID);
		pg.delete(oliviasID);
	}
	
	@Test
	public void testUpdatePendingOutgoing(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("adam", "4321", "adamsPage");
		UnitOfWork.getThread().createPerson("bob", "8765", "bobsPage");
		UnitOfWork.getThread().findPerson("adam", "4321");
		
		UnitOfWork.getThread().registerOutgoingRequests(new Friend("bob","bobsPage"));
		UnitOfWork.getThread().commit();
		
		Person adam = UnitOfWork.getThread().findPerson("adam", "4321");
		assertFalse(adam.getInitialOutgoingFriends().isEmpty());
		
		// Delete Friend Relationship from DB
		int adamsID = adam.getUserID();
		int bobsID = UnitOfWork.getThread().findPerson("bob", "8765").getUserID();
		FriendGateway fg = new FriendGateway();
		fg.deleteFriend(adamsID, bobsID);
		// Delete Persons from DB
		PersonGateway pg = new PersonGateway();
		pg.delete(adamsID);
		pg.delete(bobsID);
	}
	
	@Test
	public void testAddNewFriend(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("chloe", "4567", "chloesPage");
		UnitOfWork.getThread().createPerson("david", "8912", "davidsPage");
		
		Person chloe = UnitOfWork.getThread().findPerson("chloe", "4567");
		UnitOfWork.getThread().registerNewFriend(new Friend("david","davidsPage"));
		//Call addNew()
		UnitOfWork.getThread().commit();	
		
		// Get Davids friends
		Person david = UnitOfWork.getThread().findPerson("david", "8912");
		FriendGateway fg = new FriendGateway();
		assertTrue(fg.getFriends(chloe.getUserID()).contains(david.getUserID()));
		
		// Delete Friend Relationship & Persons from DB
		PersonGateway pg = new PersonGateway();
		fg.deleteFriend(chloe.getUserID(), david.getUserID());
		pg.delete(chloe.getUserID());
		pg.delete(david.getUserID());
	}
	
	@Test
	public void testDeleteRemovedFriend(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("emma", "1111", "emmasPage");
		UnitOfWork.getThread().createPerson("henry", "2222", "henrysPage");
		
		// Emma & Henry are friends
		UnitOfWork.getThread().findPerson("emma", "1111");
		Friend friend = new Friend("henry","henrysPage");
		UnitOfWork.getThread().registerNewFriend(friend);
		UnitOfWork.getThread().commit();
		
		// Emma un-friends Henry
		UnitOfWork.getThread().registerDeletedFriend(friend);
		// Call deleteRemoved()
		UnitOfWork.getThread().commit();
		
		// Emma and Henry are no longer friends
		int henryID = UnitOfWork.getThread().findPerson("henry", "2222").getUserID();
		int emmaID = UnitOfWork.getThread().findPerson("emma", "1111").getUserID();
		FriendGateway fg = new FriendGateway();
		assertFalse(fg.getFriends(emmaID).contains(henryID));
		assertFalse(fg.getFriends(henryID).contains(emmaID));
		
		// Delete Persons from DB
		PersonGateway pg = new PersonGateway();
		pg.delete(henryID);
		pg.delete(emmaID);
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
		UnitOfWork.getThread().createPerson("kevin", "3333", "kevinsPage");
		UnitOfWork.getThread().createPerson("liz", "4444", "lizsPage");
		
		int kevinID = UnitOfWork.getThread().findPerson("kevin", "3333").getUserID();
		// Kevin sends request to liz
		UnitOfWork.getThread().makeFriendRequest(kevinID, "liz");
		UnitOfWork.getThread().commit();
		
		int lizID = UnitOfWork.getThread().findPerson("liz", "4444").getUserID();
		FriendGateway fg = new FriendGateway();
		assertTrue(fg.getOutgoingRequests(kevinID).contains(lizID));
		assertTrue(fg.getIncomingRequests(lizID).contains(kevinID));
		
		// Delete Friend Relationship & Persons from DB
		fg.deleteFriend(kevinID, lizID);
		PersonGateway pg = new PersonGateway();
		pg.delete(kevinID);
		pg.delete(lizID);
	}
	
	@Test
	public void testAcceptFriendRequest(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("molly", "5555", "mollysPage");
		UnitOfWork.getThread().createPerson("tina", "6666", "tinasPage");
		
		int mollyID = UnitOfWork.getThread().findPerson("molly", "5555").getUserID();
		// Molly sends request to Tina
		UnitOfWork.getThread().makeFriendRequest(mollyID, "tina");
		UnitOfWork.getThread().commit();
		
		// Tina accepts Molly's request
		int tinaID = UnitOfWork.getThread().findPerson("tina", "6666").getUserID();
		UnitOfWork.getThread().acceptFriendRequest(tinaID, "molly");
		UnitOfWork.getThread().commit();
		
		FriendGateway fg = new FriendGateway();
		assertTrue(fg.getFriends(mollyID).contains(tinaID));
		assertTrue(fg.getFriends(tinaID).contains(mollyID));
		
		// Delete Friend Relationship and Persons from DB
		fg.deleteFriend(mollyID, tinaID);
		PersonGateway pg = new PersonGateway();
		pg.delete(mollyID);
		pg.delete(tinaID);
	}
	
	@Test
	public void testDeleteFriend(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("sam", "7777", "samsPage");
		UnitOfWork.getThread().createPerson("tim", "8888", "timsPage");
		
		// Sam sends request to Tim & Tim accepts
		int samID = UnitOfWork.getThread().findPerson("sam", "7777").getUserID();
		UnitOfWork.getThread().makeFriendRequest(samID, "tim");
		UnitOfWork.getThread().commit();
		int timID = UnitOfWork.getThread().findPerson("tim", "8888").getUserID();
		UnitOfWork.getThread().acceptFriendRequest(timID, "sam");
		UnitOfWork.getThread().commit();
		
		// Sam changes his mind & deletes Tim
		UnitOfWork.getThread().findPerson("sam", "7777");
		UnitOfWork.getThread().deleteFriendInList(samID, "tim");
		UnitOfWork.getThread().commit();
		
		FriendGateway fg = new FriendGateway();
		assertFalse(fg.getFriends(samID).contains(timID));
		assertFalse(fg.getFriends(timID).contains(samID));
		
		// Delete Persons from DB 
		PersonGateway pg = new PersonGateway();
		pg.delete(samID);
		pg.delete(timID);
	}
	
	@Test
	public void testRejectRequest(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("Ga", "9999", "GasPage");
		UnitOfWork.getThread().createPerson("Ryan", "1010", "RyansPage");
		
		int gaID = UnitOfWork.getThread().findPerson("Ga", "9999").getUserID();
		int ryanID = UnitOfWork.getThread().findPerson("Ryan", "1010").getUserID();
		
		// Ga sends request to Ryan & Ryan rejects request
		UnitOfWork.getThread().makeFriendRequest(gaID, "Ryan");
		UnitOfWork.getThread().commit();
		UnitOfWork.getThread().rejectRequest(ryanID, "Ga");
		UnitOfWork.getThread().commit();
		
		FriendGateway fg = new FriendGateway();
		assertFalse(fg.getFriends(gaID).contains(ryanID));
		assertFalse(fg.getFriends(ryanID).contains(gaID));
		assertFalse(fg.getOutgoingRequests(gaID).contains(ryanID));
		assertFalse(fg.getIncomingRequests(ryanID).contains(gaID));
		
		// Delete Persons from DB
		PersonGateway pg = new PersonGateway();
		pg.delete(gaID);
		pg.delete(ryanID);
	}
}