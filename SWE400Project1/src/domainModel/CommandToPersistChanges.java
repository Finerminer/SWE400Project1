package domainModel;
/**
 * Tells the system to save any pending changes
 * 
 * @author merlin
 *
 */
public class CommandToPersistChanges implements Command
{

	/**
	 * 
	 * @see Command#execute()
	 */
	@Override
	public void execute()
	{
		if(UnitOfWork.getThread() != null) {
			UnitOfWork.getThread().commit();
		}
	}

	/**
	 * Nothing needs to be returned here (null). The tests will retrieve
	 * anything they want to check by re-finding appropriate records
	 * 
	 * @see Command#getResult()
	 */
	@Override
	public Object getResult()
	{
		return null;
	}

}
