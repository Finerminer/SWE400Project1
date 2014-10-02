package domainModel;

public class PersonMapper {
	PersonGateway gate = new PersonGateway();
	
	public Person find(int userID){
		if(gate.find(userID) == null)
			return null;
		return gate.find(userID);
	}
	
	
}
