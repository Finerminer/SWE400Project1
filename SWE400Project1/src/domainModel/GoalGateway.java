package domainModel;

import java.sql.ResultSet;
import java.util.Date;

public class GoalGateway {
	int goalID;
	int numberOfActivities;
	int numberOfDays;
	int userID;
	Date stateDate;
	Date endDate;
	boolean wasAchieved;
	
	/**
	 * @return the goalID
	 */
	public int getGoalID() {
		return goalID;
	}

	/**
	 * @param goalID the goalID to set
	 */
	public void setGoalID(int goalID) {
		this.goalID = goalID;
	}

	/**
	 * @return the numberOfActivities
	 */
	public int getNumberOfActivities() {
		return numberOfActivities;
	}

	/**
	 * @param numberOfActivities the numberOfActivities to set
	 */
	public void setNumberOfActivities(int numberOfActivities) {
		this.numberOfActivities = numberOfActivities;
	}

	/**
	 * @return the numberOfDays
	 */
	public int getNumberOfDays() {
		return numberOfDays;
	}

	/**
	 * @param numberOfDays the numberOfDays to set
	 */
	public void setNumberOfDays(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}

	/**
	 * @return the stateDate
	 */
	public Date getStateDate() {
		return stateDate;
	}

	/**
	 * @param stateDate the stateDate to set
	 */
	public void setStateDate(Date stateDate) {
		this.stateDate = stateDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the wasAchieved
	 */
	public boolean isWasAchieved() {
		return wasAchieved;
	}

	/**
	 * @param wasAchieved the wasAchieved to set
	 */
	public void setWasAchieved(boolean wasAchieved) {
		this.wasAchieved = wasAchieved;
	}

	public ResultSet findAll() {
		String sql = "select * from Goal";
		return null;
	}
	
	
	
	public void insert() {
		
	}

	public void update() {

	}

	public void delete() {

	}
}
