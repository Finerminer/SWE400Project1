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
		UnitOfWork.getThread().createPerson("neil", "9012", "neilsPage");
		UnitOfWork.getThread().createPerson("olivia", "5678", "oliviasPage");
		UnitOfWork.getThread().findPerson("neil", "9012");
		
		UnitOfWork.getThread().registerIncomingRequest(new Friend("olivia","oliviasPage"));
		UnitOfWork.getThread().commit();
		
		Person neil = UnitOfWork.getThread().findPerson("neil", "9012");
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
		UnitOfWork.getThread().createPerson("adam", "4321", "adamsPage");
		UnitOfWork.getThread().createPerson("bob", "8765", "bobsPage");
		UnitOfWork.getThread().findPerson("adam", "4321");
		
		UnitOfWork.getThread().registerOutgoingRequests(new Friend("bob","bobsPage"));
		UnitOfWork.getThread().commit();
		
		Person adam = UnitOfWork.getThread().findPerson("adam", "4321");
		assertFalse(adam.getOutgoingFriends().isEmpty());
		
		// Delete Friend Relationship from DB
		FriendGateway fg = new FriendGateway();
		int adamsID = adam.getUserID();
		int bobsID = UnitOfWork.getThread().findPerson("bob", "8765").getUserID();
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
		Friend david = new Friend("david","davidsPage");
		UnitOfWork.getThread().registerIncomingRequest(david);
		UnitOfWork.getThread().registerNewFriend(david);
		//Call addNew()
		UnitOfWork.getThread().commit();	
		
		// Get Davids friends
		FriendGateway fg = new FriendGateway();
		Person f = UnitOfWork.getThread().findPerson("david", "8912");
		assertTrue(fg.getFriends(chloe.getUserID()).contains(f.getUserID()));
		
		// Delete Friend Relationship from DB
		PersonGateway pg = new PersonGateway();
		fg.deleteFriend(chloe.getUserID(), f.getUserID());
		// Delete Persons from DB
		pg.delete(chloe.getUserID());
		pg.delete(f.getUserID());
	}
	
	@Test
	public void testDeleteRemovedFriend(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("emma", "1111", "emmasPage");
		UnitOfWork.getThread().createPerson("henry", "2222", "henrysPage");
		
		// Emma & Henry are friends
		Person emma = UnitOfWork.getThread().findPerson("emma", "1111");
		Friend friend = new Friend("henry","henrysPage");
		UnitOfWork.getThread().registerNewFriend(friend);
		UnitOfWork.getThread().commit();
		
		// Emma un-friends Henry
		UnitOfWork.getThread().registerDeletedFriend(friend);
		// Call deleteRemoved()
		UnitOfWork.getThread().commit();
		
		// Get Bobs friends
		FriendGateway fg = new FriendGateway();
		Person henry = UnitOfWork.getThread().findPerson("henry", "2222");
		assertFalse(fg.getFriends(emma.getUserID()).contains(henry.getUserID()));
		
		// Delete Persons from DB
		PersonGateway pg = new PersonGateway();
		pg.delete(henry.getUserID());
		pg.delete(emma.getUserID());
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
		
		Person kevin = UnitOfWork.getThread().findPerson("kevin", "3333");
		// Sally sends request to liz
		UnitOfWork.getThread().makeFriendRequest(kevin.getUserID(), "liz");
		UnitOfWork.getThread().commit();
		
		Person liz = UnitOfWork.getThread().findPerson("liz", "4444");
		FriendGateway fg = new FriendGateway();
		assertTrue(fg.getOutgoingRequests(kevin.getUserID()).contains(liz.getUserID()));
		assertTrue(fg.getIncomingRequests(liz.getUserID()).contains(kevin.getUserID()));
		
		// Delete Friend Relationship from DB
		fg.deleteFriend(kevin.getUserID(), liz.getUserID());
		// Delete Persons from DB
		PersonGateway pg = new PersonGateway();
		pg.delete(kevin.getUserID());
		pg.delete(liz.getUserID());
	}
	
	@Test
	public void testAcceptFriendRequest(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("molly", "5555", "mollysPage");
		UnitOfWork.getThread().createPerson("liz", "6666", "lizsPage");
		
		Person molly = UnitOfWork.getThread().findPerson("molly", "5555");
		// Molly sends request to liz
		UnitOfWork.getThread().makeFriendRequest(molly.getUserID(), "liz");
		UnitOfWork.getThread().commit();
		
		// Liz accepts Sally's request
		Person liz = UnitOfWork.getThread().findPerson("liz", "6666");
		UnitOfWork.getThread().acceptFriendRequest(liz.getUserID(), "molly");
		UnitOfWork.getThread().commit();

		FriendGateway fg = new FriendGateway();
		assertTrue(fg.getFriends(molly.getUserID()).contains(liz.getUserID()));
		assertTrue(fg.getFriends(liz.getUserID()).contains(molly.getUserID()));
		
		// Delete Friend Relationship from DB
		fg.deleteFriend(molly.getUserID(), liz.getUserID());
		// Delete Persons from DB
		PersonGateway pg = new PersonGateway();
		pg.delete(molly.getUserID());
		pg.delete(liz.getUserID());
	}
	
	@Test
	public void testDeleteFriend(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("sam", "7777", "samsPage");
		UnitOfWork.getThread().createPerson("tim", "8888", "timsPage");
		Person sam = UnitOfWork.getThread().findPerson("sam", "7777");
		Person tim = UnitOfWork.getThread().findPerson("tim", "8888");
		// Sam sends request to Tim
		UnitOfWork.getThread().makeFriendRequest(sam.getUserID(), "tim");
		UnitOfWork.getThread().commit();
		// Tim accepts Sams request
		UnitOfWork.getThread().acceptFriendRequest(tim.getUserID(), "sam");
		UnitOfWork.getThread().commit();
		// Sam changes his mind & deletes Tim
		UnitOfWork.getThread().deleteFriendInList(sam.getUserID(), "tim");
		UnitOfWork.getThread().commit();
		
		assertFalse(sam.getFriends().contains(tim));
		assertFalse(tim.getFriends().contains(sam));
		
		// Delete Persons from DB
		PersonGateway pg = new PersonGateway();
		pg.delete(sam.getUserID());
		pg.delete(tim.getUserID());
	}
	
	@Test
	public void testRejectRequest(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("Ga", "9999", "GasPage");
		UnitOfWork.getThread().createPerson("Ryan", "1010", "RyansPage");
		Person Ga = UnitOfWork.getThread().findPerson("Ga", "9999");
		Person Ryan = UnitOfWork.getThread().findPerson("Ryan", "1010");
		// Ga sends request to Ryan
		UnitOfWork.getThread().makeFriendRequest(Ga.getUserID(), "Ryan");
		UnitOfWork.getThread().commit();
		// Ryan rejects Ga's request
		UnitOfWork.getThread().rejectRequest(Ryan.getUserID(), "Ga");
		UnitOfWork.getThread().commit();
		
		assertFalse(Ga.getOutgoingFriends().contains(Ryan));
		assertFalse(Ryan.getIncomingFriends().contains(Ga));
		assertFalse(Ga.getFriends().contains(Ryan));
		assertFalse(Ryan.getFriends().contains(Ga));
		
		// Delete Persons from DB
		PersonGateway pg = new PersonGateway();
		pg.delete(Ga.getUserID());
		pg.delete(Ryan.getUserID());
	}
}