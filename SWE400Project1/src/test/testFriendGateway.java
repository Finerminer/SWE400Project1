package test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
import domainModel.FriendGateway;

public class testFriendGateway {
	
	@Test
	public void testCreateFriend() {
		FriendGateway fg = new FriendGateway();
		ArrayList<Integer> friends = new ArrayList<Integer>();
		fg.addFriend(1, 2);
		fg.addFriend(2, 3);
		friends = fg.getFriends(2);
		assertEquals(friends.get(0), new Integer(1));
		assertEquals(friends.get(1), new Integer(3));
		fg.deleteFriend(1, 2);
		fg.deleteFriend(2, 3);
	}
	
	@Test
	public void testDeleteFriend() {
		FriendGateway fg = new FriendGateway();
		ArrayList<Integer> friends = new ArrayList<Integer>();
		fg.addFriend(1, 2);
		fg.addFriend(2, 3);
		fg.deleteFriend(2, 1);
		friends = fg.getFriends(2);
		assertEquals(friends.get(0), new Integer(3));
		fg.deleteFriend(2, 3);
	}
	
	@Test
	public void testGetFriends() {
		FriendGateway fg = new FriendGateway();
		ArrayList<Integer> friends = new ArrayList<Integer>();
		fg.addFriend(1, 2);
		fg.addFriend(2, 3);
		fg.addFriend(3, 4);
		fg.addFriend(2, 4);
		fg.addFriend(10, 2);
		friends = fg.getFriends(2); // 2 is friends with 1, 3, 4, 10
		assertEquals(friends.get(0), new Integer(1));
		assertEquals(friends.get(1), new Integer(3));
		assertEquals(friends.get(2), new Integer(4));
		assertEquals(friends.get(3), new Integer(10));
		fg.deleteFriend(1, 2);
		fg.deleteFriend(2, 3);
		fg.deleteFriend(2, 4);
		fg.deleteFriend(2, 10);
		fg.deleteFriend(3, 4);
	}
	
	@Test
	public void testIncomingRequest() {
		FriendGateway fg = new FriendGateway();
		ArrayList<Integer> requesters = new ArrayList<Integer>();
		fg.addRequest(4, 1);
		fg.addRequest(3, 1);
		fg.addRequest(2, 1);
		requesters = fg.getIncomingRequests(1);
		assertEquals(new Integer(2), requesters.get(0));
		assertEquals(new Integer(3), requesters.get(1));
		assertEquals(new Integer(4), requesters.get(2));
		fg.deleteRequest(2, 1);
		fg.deleteRequest(3, 1);
		fg.deleteRequest(4, 1);
	}
	
	@Test
	public void testOutgoingRequest() {
		FriendGateway fg = new FriendGateway();
		ArrayList<Integer> requestees = new ArrayList<Integer>();
		fg.addRequest(1, 2);
		fg.addRequest(1, 3);
		fg.addRequest(1, 4);
		requestees = fg.getOutgoingRequests(1);
		assertEquals(new Integer(2), requestees.get(0));
		assertEquals(new Integer(3), requestees.get(1));
		assertEquals(new Integer(4), requestees.get(2));
		fg.deleteRequest(1, 2);
		fg.deleteRequest(1, 3);
		fg.deleteRequest(1, 4);
	}
	
	@Test
	public void testAddRequests() {
		FriendGateway fg = new FriendGateway();
		ArrayList<Integer> requesters = new ArrayList<Integer>();
		fg.addRequest(1, 2);
		requesters = fg.getIncomingRequests(2);
		assertEquals(new Integer(1), requesters.get(0));
		fg.deleteRequest(1, 2);
	}
	
	@Test
	public void testDeleteFriendRequests() {
		FriendGateway fg = new FriendGateway();
		ArrayList<Integer> requesters = new ArrayList<Integer>();
		fg.addRequest(3, 1);
		fg.addRequest(2, 1);
		requesters = fg.getIncomingRequests(1);
		assertEquals(2, requesters.size());
		fg.deleteRequest(2, 1);
		fg.deleteRequest(3, 1);
		requesters = fg.getIncomingRequests(1);
		assertEquals(0, requesters.size());
	}
}
