package domainModel;


/**
 * A command that is used in the tests of UserThread to make sure that it checks
 * the results of commands when appropriate
 * 
 * @author Merlin
 *
 */
public class MockCommand implements Command
{

	private int userID;
	private String msg;

	/**
	 * 
	 * @param userID
	 *            any old number
	 * @param msg
	 *            a message with no blanks in it
	 */
	public MockCommand(int userID, String msg)
	{
		this.userID = userID;
		this.msg = msg;
	}

	/**
	 * We don't really have to do anything here
	 * 
	 * @see domainLogic.Command#execute()
	 */
	@Override
	public void execute()
	{

	}

	/**
	 * For the result to be a String with the userID, message, and some extra
	 * stuff to be sure it is coming from here
	 * 
	 * @see domainLogic.Command#getResult()
	 */
	@Override
	public Object getResult()
	{
		return userID + " " + msg + " worked";
	}

}
