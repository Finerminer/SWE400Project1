package domainModel;

import java.util.ArrayList;

public class UnitOfWork {
	
	private ArrayList<Person> newObjects = new ArrayList<Person>();
	private ArrayList<Person> dirtyObjects = new ArrayList<Person>();
	private ArrayList<Person> removedObjects = new ArrayList<Person>();
	PersonMapper mapper = new PersonMapper();
	private static ThreadLocal<UnitOfWork> thread = new ThreadLocal<UnitOfWork>();
	
	/**
	 * Registers person as new by adding them to newObjects array list.
	 * Only if the person does not exist in any other list. 
	 * @param person the domain object
	 */
	public void registerNew(Person person){
		if((!dirtyObjects.contains(person)) || (!removedObjects.contains(person)) || (!newObjects.contains(person)))
		{
			newObjects.add(person);
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
			removedObjects.add(person);
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
	 * Iterates through newObjects list and for each
	 * person in list call mapper's insert method
	 * and passes it the person user name, password, 
	 * and display name
	 */
	private void insertNew() {
		for(Person p: newObjects)
		{
			mapper.insertPerson(p.getUsername(), p.getPassword(), p.getDisplayName());
		}
	}
	
	/**
	 * Iterates through dirtyObjects list and for each
	 * person in list call mapper's update method and 
	 * passes it the person ?
	 */
	private void updateDirty() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Iterates through removedObjects list and for each
	 * person in list call mapper's delete method and 
	 * passes it the person's id
	 */
	private void deleteRemoved() {
		for(Person p: removedObjects)
		{
			mapper.deletePerson(p.getUserID());
		}
	}
	
	

	/**
	 * @return thread the current thread for a UnitOfWork
	 */
	public static UnitOfWork getThread() {
		return (UnitOfWork) thread.get();
	}

	/**
	 * Sets current thread for specific UnitOfWork
	 * @param thread the current thread
	 */
	public static void setThread(UnitOfWork t) {
		thread.set(t);
	}
	/**
	 * @return newObjects array list
	 */
	public ArrayList<Person> getNewObjects() {
		return newObjects;
	}

	/**
	 * @return dirtyObjects array list
	 */
	public ArrayList<Person> getDirtyObjects() {
		return dirtyObjects;
	}

	/**
	 * @return removedObjects array list
	 */
	public ArrayList<Person> getRemovedObjects() {
		return removedObjects;
	}
}
