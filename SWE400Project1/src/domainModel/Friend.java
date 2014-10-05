package domainModel;

public class Friend {

	protected int userID;
	protected int friendID;

	/**
	 * Gets user ID of the active user.
	 * @return the main users ID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * Sets the ID of the active user.
	 * @param userID
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}

	/**
	 * Gets the ID of the friend being added.
	 * @return the friends ID
	 */
	public int getFriendID() {
		return friendID;
	}

	/**
	 * Sets the friends ID who is being added.
	 * @param friendID
	 */
	public void setFriendID(int friendID) {
		this.friendID = friendID;
	}

}
