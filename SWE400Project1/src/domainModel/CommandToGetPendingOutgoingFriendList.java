package domainModel;
import java.util.ArrayList;

/**
 * Cause the list of pending friend requests from this user to other users to be fetched
 * from the domain model (may or may not cause reading from the DB depending on
 * the state of the domain model)
 * 
 * @author merlin
 *
 */
public class CommandToGetPendingOutgoingFriendList implements Command
{

	private int userID;

	/**
	 * The userID of the current user
	 * 
	 * @param userID
	 *            unique
	 */
	public CommandToGetPendingOutgoingFriendList(int userID)
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
		ArrayList<Friend> intialRequests = UnitOfWork.getThread().getPerson().getInitialOutgoingFriends();
		ArrayList<Friend> currentRequests = new ArrayList<Friend>();
		
		for(Friend f : UnitOfWork.getThread().getOutgoingRequests()) {
			currentRequests.add(f);
		}
		
		for(Friend f : intialRequests){
			currentRequests.add(f);
		}
		
		String result="";
		Boolean first = true;
		for(Friend f : currentRequests)
		{
			if(first) {
				result = result + f.getDisplayName();
				first = false;
			} else {
				result+= "," + f.getDisplayName();
			}
		}
		return result;
		//return UnitOfWork.getThread().printFriendsInLists(initialFriends, friends);
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
