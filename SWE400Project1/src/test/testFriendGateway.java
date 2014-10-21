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
}
