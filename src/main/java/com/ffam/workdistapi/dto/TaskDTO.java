package com.ffam.workdistapi.dto;

import java.util.List;

import com.ffam.workdistapi.model.Priority;
import com.ffam.workdistapi.model.TaskStatus;

public class TaskDTO {
	
	private Priority priority;
	
	private TaskStatus status;
	
	private List<String> skillsRequired;

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public List<String> getSkillsRequired() {
		return skillsRequired;
	}

	public void setSkillsRequired(List<String> skillsRequired) {
		this.skillsRequired = skillsRequired;
	}
}