package domainModel;

public class MyPendingFriends {
	
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
}
