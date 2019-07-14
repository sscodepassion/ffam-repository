package com.ffam.workdistapi.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TaskAssignmentId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column (name = "task_id")
	private Integer taskId;
	
	@Column (name = "agent_id")
	private Integer agentId;
	
	@Column (name = "task_assignment_timestamp")
	private LocalDate taskAssignmentTimestamp; 
	
	@SuppressWarnings("unused")
	private TaskAssignmentId() { /* To protect anyone from initializing using default constructor */ }
	
	public TaskAssignmentId(Integer taskId, Integer agentId) {
		this.taskId = taskId;
		this.agentId = agentId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public LocalDate getTaskAssignmentTimestamp() {
		return taskAssignmentTimestamp;
	}
	
	public void setTaskAssignmentTimestamp(LocalDate taskAssignmentTimestamp) {
		this.taskAssignmentTimestamp = taskAssignmentTimestamp;
	}

	@Override
	public int hashCode() {
		return Objects.hash(taskId, agentId);	
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		 
        if (obj == null || getClass() != obj.getClass())
            return false;
 
        TaskAssignmentId that = (TaskAssignmentId) obj;
        return Objects.equals(taskId, that.taskId) &&
               Objects.equals(agentId, that.agentId);
	}	
}