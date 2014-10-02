package domainModel;

public class PersonMapper {
	PersonGateway gate = new PersonGateway();
	
	public Person find(int userID){
		if(gate.find(userID) == null)
			return null;
		return gate.find(userID);
	}
	
	public boolean insertPerson(){
		gate.insert();
		return false;
	}
	
	public boolean deletePerson(){
		gate.delete();
		return false;
	}
}
