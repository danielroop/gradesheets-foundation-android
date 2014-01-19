package com.roopsays.gradesheet.foundation.model;

public class GradesheetMeta {
	private int numberOfQuestions;
	private int numberOfTimesAccessed;
	private String lastAccessed;
	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}
	public void setNumberOfQuestions(int numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}
	public int getNumberOfTimesAccessed() {
		return numberOfTimesAccessed;
	}
	public void setNumberOfTimesAccessed(int numberOfTimesAccessed) {
		this.numberOfTimesAccessed = numberOfTimesAccessed;
	}
	public String getLastAccessed() {
		return lastAccessed;
	}
	public void setLastAccessed(String lastAccessed) {
		this.lastAccessed = lastAccessed;
	}
	

}
