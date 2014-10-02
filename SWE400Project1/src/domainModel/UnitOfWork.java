package domainModel;

import java.util.ArrayList;

public class UnitOfWork {
	
	private ArrayList<Person> newObjects = new ArrayList<Person>();
	private ArrayList<Person> dirtyObjects = new ArrayList<Person>();
	private ArrayList<Person> removedObjects = new ArrayList<Person>();
	
	/**
	 * Registers person as new by adding them to newObjects array list.
	 * Only if the person has an ID and does not exist in any other list. 
	 * @param person the domain object
	 */
	public void registerNew(Person person){
		if(person.getUserID() > 0)
		{
			if((!dirtyObjects.contains(person)) || (!removedObjects.contains(person)) || (!newObjects.contains(person)))
			{
				newObjects.add(person);
			}
		}
	}

	/**
	 * Registers person as dirty by adding them to dirtyObjects array list
	 * Only if the person has an ID and does not exist in any other list.
	 * @param person the domain object
	 */
	public void registerDirty(Person person){
		if(person.getUserID() > 0)
		{
			if((!removedObjects.contains(person)) || (!newObjects.contains(person)) || (!dirtyObjects.contains(person)))
			{
				dirtyObjects.add(person);
			}
		}
	}
	
	/**
	 * Registers person as removed by adding them to removedObjects array List
	 * and removing them from the other lists
	 * Only if the person has an ID
	 * @param person the domain object
	 */
	public void registerRemoved(Person person){
		newObjects.remove(person);
		dirtyObjects.remove(person);
		if(!removedObjects.contains(person))
		{
			removedObjects.remove(person);
		}
	}
	
	/**
	 * Locates DataMapper for each Person and 
	 * invoke the appropriate mapping method
	 */
	public void commit(){
		insertNew();
		updateDirty();
		deleteRemoved();
	}
	
	/**
	 * 
	 */
	private void insertNew() {
		PersonMapper mapper = new PersonMapper();
		for(Person p: newObjects)
		{
			mapper.insertPerson(p.getUsername(), p.getPassword(), p.getDisplayName());
		}
	}
	
	private void updateDirty() {
		// TODO Auto-generated method stub
		
	}
	
	private void deleteRemoved() {
		// TODO Auto-generated method stub
		
	}
	
}
