package domainModel;

public class PersonMapper {
	protected PersonGateway pGate = new PersonGateway();
	protected FriendGateway fGate = new FriendGateway(); 
	
	public Person find(int userID){
		if(pGate.find(userID) == null)
			return null;
		return pGate.find(userID);
	}
	
	public boolean insertPerson(){
		pGate.insert();
		return false;
	}
	
	public boolean deletePerson(int userID){
		pGate.delete(userID);
		return false;
	}
	
	public void addFriend(int userID, int friendID){
		fGate.addFriend(userID, friendID);
	}
}
