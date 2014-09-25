import java.util.ArrayList;
import java.util.Date;

public class Person {
	ArrayList<Activity> activity = new ArrayList<Activity>();
	ArrayList<Goal> goal = new ArrayList<Goal>();
	int userID;
	String firstName;
	String lastName;

	public void create() {

	}

	public void read() {

	}

	public boolean update() {
		return false;
	}

	public boolean delete() {
		return false;
	}

	public boolean addActivity(int userID, Date date, String activityType,
			int caloriesBurned) {
		return false;
	}
}
