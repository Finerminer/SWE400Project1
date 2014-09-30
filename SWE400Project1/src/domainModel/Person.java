package domainModel;
import java.util.ArrayList;
import java.util.Date;

public class Person {
	ArrayList<Activity> activity = new ArrayList<Activity>();
	ArrayList<Goal> goal = new ArrayList<Goal>();
	FriendList friends = new FriendList();
	
	String username;
	String password;
	int userID;
	String firstName;
	String lastName;

	public boolean addPerson(int userID, String firstName, String lastName, String username, String password) {
		return false;
	}

	public Person getPerson(int userID) {
		return null;

	}

	public boolean updatePerson(int userID, String firstName, String lastName, String username, String password) {
		return false;
	}

	public boolean deletePerson(int userID) {
		return false;
	}

	public boolean addActivity(int userID, Date date, String activityType,
			int caloriesBurned) {
		return false;
	}
	
	public boolean addGoal(int goalID, int userID, int numberOfActivities, int numberOfDays, Date startDate, Date endDate){
		return false;
	}
	
	public boolean addFriend(int userID, int friendID){
		return false;
	}
}
