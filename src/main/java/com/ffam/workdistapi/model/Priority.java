package com.ffam.workdistapi.model;

public enum Priority {
	LOW (0), 
	HIGH (1);
	
	private int priorityValue;
	
	Priority(final int priorityValue) {
		this.priorityValue = priorityValue;
	}
	
	public int getPriorityValue() {
		return priorityValue;
	}
}
