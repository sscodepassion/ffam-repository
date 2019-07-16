package com.ffam.workdistapi.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name = "tasks")
public class Task implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE)
	@Column (name = "task_id")
	private Long id;
	
	@Enumerated (EnumType.STRING)
	@Column (name = "priority")
	private Priority priority;
	
	@Enumerated (EnumType.STRING)
	@Column (name = "status")
	private TaskStatus status;
	
	@Column (name = "task_assignment_timestamp")
	private LocalDateTime taskAssignmentTimestamp;
	
	@OneToOne (fetch = FetchType.LAZY, optional = false)
	@JoinColumn (name = "agent_id", nullable = true)
	private Agent agent;
	
	@SuppressWarnings("unused")
	private Task() {/* To protect anyone from initializing using default constructor */}

	public Task(Priority priority, TaskStatus status) {
		this.priority = priority;
		this.status = status;
	}
	
	public Long getId() {
		return id;
	}

	public Priority getPriority() {
		return priority;
	}

	public TaskStatus getStatus() {
		return status;
	}
	
	public LocalDateTime getTaskAssignmentTimestamp() {
		return taskAssignmentTimestamp;
	}

	public void setTaskAssignmentTimestamp(LocalDateTime taskAssignmentTimestamp) {
		this.taskAssignmentTimestamp = taskAssignmentTimestamp;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return Objects.equals(id, task.id);	
	}
}