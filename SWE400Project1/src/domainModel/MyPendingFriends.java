package domainModel;

public class MyPendingFriends {
	
	protected int toUserName;
	protected int fromUserId;
	
	/**
	 * Gets the user id of the sender
	 * @return the user ID
	 */
	public int getFromUserId() {
		return fromUserId;
	}
	
	/**
	 * Sets the user id of the sender
	 * @param userID
	 */
	public void setFromUserId(int userID) {
		this.fromUserId = userID;
	}
	
	/**
	 * Gets the user name of the receiver
	 * @return the user name
	 */
	public int getToUserName() {
		return toUserName;
	}
	
	/**
	 * Sets the user name of the receiver
	 * @param pendingFriend
	 */
	public void setToUserName(int pendingFriend) {
		this.toUserName = pendingFriend;
	}

}
