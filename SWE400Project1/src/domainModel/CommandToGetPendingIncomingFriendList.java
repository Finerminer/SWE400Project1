package domainModel;

import java.util.ArrayList;

/**
 * Cause the list of friend requests from other user to this user to be fetched
 * from the domain model (may or may not cause reading from the DB depending on
 * the state of the domain model)
 * 
 * @author merlin
 *
 */
public class CommandToGetPendingIncomingFriendList implements Command
{

	private int userID;

	/**
	 * The userID of the current user
	 * 
	 * @param userID
	 *            unique
	 */
	public CommandToGetPendingIncomingFriendList(int userID)
	{
		this.userID = userID;
	}

	/**
	 * Does nothing. Results are returned in getResults
	 * @see Command#execute()
	 */
	@Override
	public void execute()
	{
		
	}

	/**
	 * A list of the friends associated with the given user
	 * 
	 * @see Command#getResult()
	 */
	@Override
	public String getResult()
	{
		ArrayList<Friend> friends = UnitOfWork.getThread().getIncomingRequests();
		String result="";
		for(Friend f : friends)
		{
			result = result + f.getUserName() + " ";
		}
		return result;
	}

	/**
	 * For testing purposes - to check that the constructor correctly remembered
	 * the userID of the requestor
	 * 
	 * @return the userID that was given to the constructor
	 */
	public int getUserID()
	{
		return userID;
	}

}
