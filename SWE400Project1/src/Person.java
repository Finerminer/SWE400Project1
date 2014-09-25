import java.util.ArrayList;
import java.util.Date;

public class Person {
	ArrayList<Activity> activity = new ArrayList<Activity>();
	ArrayList<Goal> goal = new ArrayList<Goal>();
	String userName;
	String password;
	int userID;
	String firstName;
	String lastName;

	public void createPerson() {

	}

	public void readPerson() {

	}

	public boolean updatePerson() {
		return false;
	}

	public boolean deletePerson() {
		return false;
	}

	public boolean addActivity(int userID, Date date, String activityType,
			int caloriesBurned) {
		return false;
	}
	
	public Person getUser(int userID){
		return null;
	}
}
