package test;
import static org.junit.Assert.*;

import java.util.ArrayList;

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
	public void testRegisterIncomingRequests(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().registerIncomingRequest(new Friend("fred",null));
		
		assertTrue(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getIncomingRequests(),"fred"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getOutgoingRequests(),"fred"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getDeletedPendingRequests(),"fred"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getNewFriends(),"fred"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getDeletedFriends(),"fred"));
	}
	
	@Test
	public void testRegisterOutgoingRequests(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().registerOutgoingRequests(new Friend("adam",null));
		
		assertTrue(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getOutgoingRequests(),"adam"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getIncomingRequests(),"adam"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getDeletedPendingRequests(),"adam"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getNewFriends(),"adam"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getDeletedFriends(),"adam"));
	}
	
	@Test
	public void testCanNotRegisterIncomingIfAlreadyInList(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().registerOutgoingRequests(new Friend("testF2",null));
		UnitOfWork.getThread().registerIncomingRequest(new Friend("testF2",null));
		
		assertTrue(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getOutgoingRequests(),"testF2"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getIncomingRequests(),"testF2"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getDeletedPendingRequests(),"testF2"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getNewFriends(),"testF2"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getDeletedFriends(),"testF2"));
	}

	@Test
	public void testCanNotRegisterOutgoingIfAlreadyInList(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().registerIncomingRequest(new Friend("testF3",null));
		UnitOfWork.getThread().registerOutgoingRequests(new Friend("testF3",null));
		
		assertTrue(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getIncomingRequests(),"testF3"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getOutgoingRequests(),"testF3"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getDeletedPendingRequests(),"testF3"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getNewFriends(),"testF3"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getDeletedFriends(),"testF3"));
	}
	
	@Test
	public void testRegisterDeletedPendingRequests(){
		UnitOfWork.setThread(new UnitOfWork());		
		UnitOfWork.getThread().registerIncomingRequest(new Friend("testFriend", null));
		UnitOfWork.getThread().registerOutgoingRequests(new Friend("testFriend", null));
		UnitOfWork.getThread().registerDeletedPendingRequest(new Friend("testFriend", null));
		
		assertTrue(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getDeletedPendingRequests(),"testFriend"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getOutgoingRequests(),"testFriend"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getIncomingRequests(),"testFriend"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getNewFriends(),"testFriend"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getDeletedFriends(),"testFriend"));
	}
	
	@Test
	public void testRegisterNewFriend(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().registerIncomingRequest(new Friend("bob",null));
		UnitOfWork.getThread().registerNewFriend(new Friend("bob",null));
		
		assertTrue(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getNewFriends(),"bob"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getIncomingRequests(),"bob"));
		assertTrue(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getDeletedPendingRequests(),"bob"));
		
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getDeletedFriends(),"bob"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getOutgoingRequests(),"bob"));
	}
	
	@Test
	public void testCanNotRegisterFriendIfNotRequested(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().registerNewFriend(new Friend("testF5",null));
		
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getNewFriends(),"testF5"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getIncomingRequests(),"testF5"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getDeletedFriends(),"testF5"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getDeletedPendingRequests(),"testF5"));	
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getOutgoingRequests(),"testF5"));
	}
	
	@Test
	public void testCanNotRegisterFriendIfInDeletedOrNewLists(){
		UnitOfWork.setThread(new UnitOfWork());
		
		UnitOfWork.getThread().registerIncomingRequest(new Friend("bob",null));
		UnitOfWork.getThread().registerDeletedFriend(new Friend("bob",null));
		UnitOfWork.getThread().registerNewFriend(new Friend("bob",null));
		
		assertTrue(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getIncomingRequests(),"bob"));
		assertTrue(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getDeletedFriends(),"bob"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getNewFriends(),"bob"));
		
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getDeletedPendingRequests(),"bob"));	
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getOutgoingRequests(),"bob"));
	}

	@Test
	public void testRegisterDeleteFriend(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().registerIncomingRequest(new Friend("chloe", null));
		UnitOfWork.getThread().registerNewFriend(new Friend("chloe", null));
		UnitOfWork.getThread().registerDeletedFriend(new Friend("chloe", null));
		
		assertTrue(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getDeletedFriends(),"chloe"));
		assertTrue(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getDeletedPendingRequests(),"chloe"));
		
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getIncomingRequests(),"chloe"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getNewFriends(),"chloe"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getOutgoingRequests(),"chloe"));
	}
	
	@Test
	public void testClearFriendsLists(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().registerIncomingRequest(new Friend("david","davidsPage"));
		UnitOfWork.getThread().registerOutgoingRequests(new Friend("emma","emmasPage"));
		UnitOfWork.getThread().registerNewFriend(new Friend("liz","lizsPage"));
		UnitOfWork.getThread().registerDeletedFriend(new Friend("mike","mikesPage"));
		UnitOfWork.getThread().registerDeletedPendingRequest(new Friend("luke","lukesPage"));
		UnitOfWork.getThread().clearFriendsLists();
		
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getIncomingRequests(),"david"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getOutgoingRequests(),"emma"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getNewFriends(),"liz"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getDeletedFriends(),"mike"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getDeletedPendingRequests(),"luke"));
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
		
		// Call UpdatePending()
		UnitOfWork.getThread().commit();
		
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getIncomingRequests(),"olivia"));
		assertTrue(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialIncomingFriends(),"olivia"));
	
//		assertTrue(UnitOfWork.getThread().getIncomingRequests().isEmpty());
//		assertFalse(UnitOfWork.getThread().getPerson().getInitialIncomingFriends().isEmpty());
		
		// Delete Friend Relationship & Persons from DB
		int neilsID = UnitOfWork.getThread().findPerson("neil", "9012").getUserID();
		int oliviasID = UnitOfWork.getThread().findPerson("olivia", "5678").getUserID();
		FriendGateway fg = new FriendGateway();
		fg.deleteRequest(neilsID, oliviasID);
		//fg.deleteFriend(neilsID, oliviasID);
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
		
		// Call UpdatePending()
		UnitOfWork.getThread().commit();
		
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getOutgoingRequests(),"bob"));
		assertTrue(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialOutgoingFriends(),"bob"));
		
//		assertTrue(UnitOfWork.getThread().getOutgoingRequests().isEmpty());
//		assertFalse(UnitOfWork.getThread().getPerson().getInitialOutgoingFriends().isEmpty());
	
		// Delete Friend Relationship & Persons from DB
		int adamsID = UnitOfWork.getThread().findPerson("adam", "4321").getUserID();
		int bobsID = UnitOfWork.getThread().findPerson("bob", "8765").getUserID();
		FriendGateway fg = new FriendGateway();
		fg.deleteRequest(adamsID, bobsID);
		//fg.deleteFriend(adamsID, bobsID);
		PersonGateway pg = new PersonGateway();
		pg.delete(adamsID);
		pg.delete(bobsID);
	}

	@Test
	public void testAddNew(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("chloe", "4567", "chloesPage");
		UnitOfWork.getThread().createPerson("david", "8912", "davidsPage");
		Person chloe = UnitOfWork.getThread().findPerson("chloe", "4567");
		UnitOfWork.getThread().registerIncomingRequest(new Friend("david","davidsPage"));
		UnitOfWork.getThread().commit();
		UnitOfWork.getThread().registerNewFriend(new Friend("david","davidsPage"));

		// Call addNew()
		UnitOfWork.getThread().commit();	
		
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getNewFriends(),"david"));
		assertTrue(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialFriends(),"david"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialIncomingFriends(),"david"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialOutgoingFriends(),"david"));
		
//		assertTrue(UnitOfWork.getThread().getNewFriends().isEmpty());
//		assertFalse(UnitOfWork.getThread().getPerson().getInitialFriends().isEmpty());
			
		// Delete Friend Relationship & Persons from DB
		Person david = UnitOfWork.getThread().findPerson("david", "8912");
		FriendGateway fg = new FriendGateway();
		fg.deleteFriend(chloe.getUserID(), david.getUserID());
		PersonGateway pg = new PersonGateway();
		pg.delete(chloe.getUserID());
		pg.delete(david.getUserID());
	}
	
	@Test
	public void testRemoveDeletedPending(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("person5", "p5Password", "person5");
		UnitOfWork.getThread().createPerson("person6", "p6Password", "person6");
		Person p5 = UnitOfWork.getThread().findPerson("person5", "p5Password");
		UnitOfWork.getThread().registerOutgoingRequests(new Friend("person6","person6"));
		UnitOfWork.getThread().commit();
		Person p6 = UnitOfWork.getThread().findPerson("person6", "p6Password");
		UnitOfWork.getThread().registerDeletedPendingRequest(new Friend("person5","person5"));
		
		// Call removeDeletedPending()
		UnitOfWork.getThread().commit();	
		
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialFriends(),"person5"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialIncomingFriends(),"person5"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialOutgoingFriends(),"person5"));

//		assertTrue(UnitOfWork.getThread().getIncomingRequests().isEmpty());
//		assertTrue(UnitOfWork.getThread().getPerson().getInitialIncomingFriends().isEmpty());
//		assertTrue(UnitOfWork.getThread().getDeletedPendingRequests().isEmpty());
		
		UnitOfWork.getThread().findPerson("person5", "p5Password");
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialFriends(),"person6"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialIncomingFriends(),"person6"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialOutgoingFriends(),"person6"));
		
		// Delete Persons from DB
		PersonGateway pg = new PersonGateway();
		pg.delete(p5.getUserID());
		pg.delete(p6.getUserID());
	}
			
	@Test
	public void testRemoveDelete(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("emma", "1111", "emmasPage");
		UnitOfWork.getThread().createPerson("henry", "2222", "henrysPage");
		UnitOfWork.getThread().findPerson("emma", "1111");
		UnitOfWork.getThread().registerOutgoingRequests(new Friend("henry","henrysPage"));
		UnitOfWork.getThread().commit();
		
		UnitOfWork.getThread().findPerson("henry", "2222");
		UnitOfWork.getThread().registerNewFriend(new Friend("emma","emmasPage"));
		UnitOfWork.getThread().commit();
		
		// Emma un-friends Henry
		UnitOfWork.getThread().findPerson("emma", "1111");
		UnitOfWork.getThread().registerDeletedFriend(new Friend("henry","henrysPage"));
		
		// Call removeDeleted()
		UnitOfWork.getThread().commit();
		
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialFriends(),"henry"));
		
//		assertTrue(UnitOfWork.getThread().getDeletedFriends().isEmpty());
//		assertTrue(UnitOfWork.getThread().getPerson().getInitialFriends().isEmpty());
		
		int henryID = UnitOfWork.getThread().findPerson("henry", "2222").getUserID();
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialFriends(),"emma"));
		
		// Delete Persons from DB
		int emmaID = UnitOfWork.getThread().findPerson("emma", "1111").getUserID();
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
		UnitOfWork.getThread().makeFriendRequest(kevinID, "liz");
		
		System.out.println("Kevins outgoing requests before commit: ");
		UnitOfWork.getThread().printFriendsInLists(UnitOfWork.getThread().getPerson().getInitialOutgoingFriends(), UnitOfWork.getThread().getOutgoingRequests());
		
		UnitOfWork.getThread().commit();
		assertTrue(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialOutgoingFriends(),"liz"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialFriends(),"liz"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialIncomingFriends(),"liz"));
		
		System.out.println("Kevins outgoing requests after commit: ");
		UnitOfWork.getThread().printFriendsInLists(UnitOfWork.getThread().getPerson().getInitialOutgoingFriends(), UnitOfWork.getThread().getOutgoingRequests());
		
		int lizID = UnitOfWork.getThread().findPerson("liz", "4444").getUserID();
		assertTrue(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialIncomingFriends(),"kevin"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialOutgoingFriends(),"kevin"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialFriends(),"kevin"));
		
		// Delete Friend Relationship & Persons from DB
		FriendGateway fg = new FriendGateway();
		fg.deleteRequest(kevinID, lizID);
		//fg.deleteFriend(kevinID, lizID);
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
		UnitOfWork.getThread().makeFriendRequest(mollyID, "tina");
		UnitOfWork.getThread().commit();
		int tinaID = UnitOfWork.getThread().findPerson("tina", "6666").getUserID();
		UnitOfWork.getThread().acceptFriendRequest(tinaID, "molly");
		
		System.out.println("tinas incoming requests before commit: ");
		UnitOfWork.getThread().printFriendsInLists(UnitOfWork.getThread().getPerson().getInitialIncomingFriends(), UnitOfWork.getThread().getIncomingRequests());
		System.out.println("tinas friends before commit: ");
		UnitOfWork.getThread().printFriendsInLists(UnitOfWork.getThread().getPerson().getInitialFriends(), UnitOfWork.getThread().getNewFriends());
		
		UnitOfWork.getThread().commit();
		
		System.out.println("tinas incoming requests after commit: ");
		UnitOfWork.getThread().printFriendsInLists(UnitOfWork.getThread().getPerson().getInitialIncomingFriends(), UnitOfWork.getThread().getIncomingRequests());
		System.out.println("tinas friends after commit: ");
		UnitOfWork.getThread().printFriendsInLists(UnitOfWork.getThread().getPerson().getInitialFriends(), UnitOfWork.getThread().getNewFriends());
		
		
		assertTrue(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialFriends(),"molly"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialIncomingFriends(),"molly"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialOutgoingFriends(),"molly"));
		
		UnitOfWork.getThread().findPerson("molly", "5555");
		assertTrue(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialFriends(),"tina"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialIncomingFriends(),"tina"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialOutgoingFriends(),"tina"));
		
		// Delete Friend Relationship and Persons from DB
		FriendGateway fg = new FriendGateway();
		fg.deleteFriend(mollyID, tinaID);
		PersonGateway pg = new PersonGateway();
		pg.delete(mollyID);
		pg.delete(tinaID);
	}
	
	@Test
	public void testDeleteFriendInList(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("sam", "7777", "samsPage");
		UnitOfWork.getThread().createPerson("tim", "8888", "timsPage");
		int samID = UnitOfWork.getThread().findPerson("sam", "7777").getUserID();
		UnitOfWork.getThread().makeFriendRequest(samID, "tim");
		UnitOfWork.getThread().commit();
		int timID = UnitOfWork.getThread().findPerson("tim", "8888").getUserID();
		UnitOfWork.getThread().acceptFriendRequest(timID, "sam");
		UnitOfWork.getThread().commit();
		UnitOfWork.getThread().findPerson("sam", "7777");
		UnitOfWork.getThread().deleteFriendInList(samID, "tim");
		
		System.out.println("sams friends before commit: ");
		UnitOfWork.getThread().printFriendsInLists(UnitOfWork.getThread().getPerson().getInitialFriends(), UnitOfWork.getThread().getNewFriends());
		
		UnitOfWork.getThread().commit();
		
		System.out.println("sams friends after commit: ");
		UnitOfWork.getThread().printFriendsInLists(UnitOfWork.getThread().getPerson().getInitialFriends(), UnitOfWork.getThread().getNewFriends());
		
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialFriends(),"tim"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialIncomingFriends(),"tim"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialOutgoingFriends(),"tim"));
		
		UnitOfWork.getThread().findPerson("tim", "8888").getUserID();
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialFriends(),"sam"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialIncomingFriends(),"sam"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialOutgoingFriends(),"sam"));
		
		// Delete Persons from DB 
		PersonGateway pg = new PersonGateway();
		pg.delete(samID);
		pg.delete(timID);
	}
	
	@Test
	public void testRejectFriendRequest(){
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().createPerson("Ga", "9999", "GasPage");
		UnitOfWork.getThread().createPerson("Ryan", "1010", "RyansPage");
		int gaID = UnitOfWork.getThread().findPerson("Ga", "9999").getUserID();
		UnitOfWork.getThread().makeFriendRequest(gaID, "Ryan");
		UnitOfWork.getThread().commit();
		int ryanID = UnitOfWork.getThread().findPerson("Ryan", "1010").getUserID();
		UnitOfWork.getThread().rejectFriendRequest(ryanID, "Ga");
		
		System.out.println("Ryans friends after commit: ");
		UnitOfWork.getThread().printFriendsInLists(UnitOfWork.getThread().getPerson().getInitialIncomingFriends(), UnitOfWork.getThread().getIncomingRequests());
		
		UnitOfWork.getThread().commit();
		
		System.out.println("Ryans friends after commit: ");
		UnitOfWork.getThread().printFriendsInLists(UnitOfWork.getThread().getPerson().getInitialIncomingFriends(), UnitOfWork.getThread().getIncomingRequests());
		
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialFriends(),"Ga"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialIncomingFriends(),"Ga"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialOutgoingFriends(),"Ga"));
		
		UnitOfWork.getThread().findPerson("Ga", "9999").getUserID();
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialFriends(),"Ryan"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialIncomingFriends(),"Ryan"));
		assertFalse(UnitOfWork.getThread().isFriendInThisList(UnitOfWork.getThread().getPerson().getInitialOutgoingFriends(),"Ryan"));
		
		// Delete Persons from DB
		PersonGateway pg = new PersonGateway();
		pg.delete(gaID);
		pg.delete(ryanID);
	}
	
	@Test
	public void testPrintFriendsInLists(){
		ArrayList<Friend> list1 = new ArrayList<Friend>();
		list1.add(new Friend("friend1", "friend1"));
		list1.add(new Friend("friend2", "friend2"));
		list1.add(new Friend("friend3", "friend3"));
		ArrayList<Friend> list2 = new ArrayList<Friend>();
		list2.add(new Friend("f4", "f4"));
		list2.add(new Friend("f5", "f5"));
		UnitOfWork.setThread(new UnitOfWork());
		assertEquals("f4,f5,friend1,friend2,friend3", UnitOfWork.getThread().printFriendsInLists(list1, list2));
	}
	
	@Test
	public void testUpdateResult(){
		ArrayList<Friend> list1 = new ArrayList<Friend>();
		list1.add(new Friend("friend1", "friend1"));
		list1.add(new Friend("friend2", "friend2"));
		list1.add(new Friend("friend3", "friend3"));
		ArrayList<Friend> list2 = new ArrayList<Friend>();
		list2.add(new Friend("f4", "f4"));
		list2.add(new Friend("f5", "f5"));
		UnitOfWork.setThread(new UnitOfWork());
		UnitOfWork.getThread().registerDeletedFriend(new Friend("friend1", "friend1"));
		UnitOfWork.getThread().registerDeletedPendingRequest(new Friend("f4","f4"));
		assertEquals("f5,friend2,friend3", UnitOfWork.getThread().printFriendsInLists(list1, list2));
	}
	
	@Test
	public void testClearDBtables(){
		PersonGateway pg = new PersonGateway();
		pg.deleteEverythingFromTable();
		FriendGateway fg = new FriendGateway();
		fg.deleteEverythingFromTable();
	}
}