import java.util.Date;

public class Goal {
	int goalID;
	int numberOfActivities;
	int numberOfDays;
	int userID;
	Date stateDate;
	Date endDate;
	boolean wasAchieved;
	
	public boolean createGoal(int goalID, int userID, int numberOfActivities, int numberOfDays, Date startDate, Date endDate) {
		return false;
	}

	public Goal readGoal(int goalID) {
		return null;
	}

	public boolean updateGoal(int goalID, int userID, int numberOfActivities, int numberOfDays, Date startDate, Date endDate) {
		return false;
	}

	public boolean deleteGoal(int goalID) {
		return false;
	}
	
	public boolean goalAchieved(){
		return wasAchieved;
	}
}
