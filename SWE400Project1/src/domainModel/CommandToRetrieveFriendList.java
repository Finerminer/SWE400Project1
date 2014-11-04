package domainModel;
import java.util.ArrayList;

/**
 * Cause a user's friend list to be fetched from the domain model (may or may
 * not cause reading from the DB depending on the state of the domain model
 * 
 * @author merlin
 *
 */
public class CommandToRetrieveFriendList implements Command
{

	@SuppressWarnings("unused")
	private int userID;

	/**
	 * The userID of the current user
	 * @param userID unique
	 */
	public CommandToRetrieveFriendList(int userID)
	{
		this.userID = userID;
	}

	/**
	 * 
	 * @see Command#execute()
	 */
	@Override
	public void execute()
	{
		// TODO Auto-generated method stub

	}

	/**
	 * A list of the friends associated with the given user
	 * @see Command#getResult()
	 */
	@Override
	public String getResult()
	{
		ArrayList<Friend> initialfriends = UnitOfWork.getThread().getPerson().getInitialFriends();
		ArrayList<Friend> newFriends = UnitOfWork.getThread().getNewFriends();
		ArrayList<Friend> deletedFriends = UnitOfWork.getThread().getDeletedFriends();
		
		for(Friend f : deletedFriends) {
			initialfriends.remove(f);
		}
		for(Friend f : newFriends) {
			initialfriends.add(f);
		}
		
		String result = "";
		Boolean first = true;
		for(Friend f : initialfriends)
		{
			if(first) {
				result = result + f.getUserName();
				first = false;
			} else {
				result+= "," + f.getUserName();
			}
		}
		return result;
		//return UnitOfWork.getThread().printFriendsInLists(initialFriends, friends);
	}

}
