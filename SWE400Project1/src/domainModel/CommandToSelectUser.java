package domainModel;

/**
 * Retrieve a specified user from the database into the domain model
 */
public class CommandToSelectUser implements Command
{
	private String userName;
	private String password;

	/**
	 * @param userName
	 *            the username from the user's credentials
	 * @param password
	 *            the password from the user's credentials
	 */
	public CommandToSelectUser(String userName, String password)
	{
		this.userName = userName;
		this.password = password;
	}

	/**
	 * Create the domain model object for the specified user (retrieve that user
	 * from the database)
	 * 
	 * @see Command#execute()
	 */
	@Override
	public void execute()
	{		
		if(UnitOfWork.getThread() == null){
			UnitOfWork.setThread(new UnitOfWork());
			UnitOfWork.getThread().findPerson(userName, password);
		}else{
			UnitOfWork.getThread().findPerson(userName, password);
		}
	}

	/**
	 * Get the password that was given as part of the user's credentials
	 * 
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * This should return the appropriate Person object from the domain model.
	 * Null if the credentials of the user were invalid
	 * 
	 * @see Command#getResult()
	 */
	@Override
	public Object getResult()
	{
		return UnitOfWork.getThread().getPerson();
	}

	/**
	 * Get the user name that was given as part of the user's credentials
	 * 
	 * @return the user name
	 */
	public String getUserName()
	{
		return userName;
	}

}
