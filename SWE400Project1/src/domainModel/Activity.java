package domainModel;
import java.util.Date;
import java.util.ArrayList;


public class Activity {
	private int userID;
	private int activityID;
	private Date date;
	private String activityType;
	private int caloriesBurned;
	private ActivityGateway act = new ActivityGateway();
	
	public void createActivity() {
		act.createActivity(activityID, userID, date, activityType, caloriesBurned);
	}

	public ArrayList<Activity> readActivity() {
		ArrayList<Activity> activities = act.readActivity(userID);
		return activities;
	}

	public void updateActivity() {
		act.updateActivity(activityID, userID, date, activityType, caloriesBurned);
	}

	public void deleteActivity() {
		act.deleteActivity(activityID);
	}
	
	
}
