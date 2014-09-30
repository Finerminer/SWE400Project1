import java.util.Date;
import java.util.List;


public class Activity {
	int userID;
	int activityID;
	Date date;
	String activityType;
	int caloriesBurned;
	ActivityGateway act = new ActivityGateway();
	
	public void createActivity() {
		act.createActivity(activityID, userID, date, activityType, caloriesBurned);
	}

	public List<Activity> readActivity() {
		List<Activity> activities = act.readActivity(userID);
		return activities;
	}

	public void updateActivity() {
		act.updateActivity(activityID, userID, date, activityType, caloriesBurned);
	}

	public void deleteActivity() {
		act.deleteActivity(activityID);
	}
	
	
}
