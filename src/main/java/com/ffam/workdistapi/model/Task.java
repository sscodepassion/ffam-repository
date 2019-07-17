package com.ffam.workdistapi.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	
	@ManyToOne
	@JoinColumn (name = "agent_id")
	private Agent agent;
	
	public Task() {
	}

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
	
	public Task setPriority(Priority priority) {
		this.priority = priority;
		return this;
	}

	public TaskStatus getStatus() {
		return status;
	}
	
	public Task setStatus(TaskStatus status) {
		this.status = status;
		return this;
	}

	public LocalDateTime getTaskAssignmentTimestamp() {
		return taskAssignmentTimestamp;
	}

	public Task setTaskAssignmentTimestamp(LocalDateTime taskAssignmentTimestamp) {
		this.taskAssignmentTimestamp = taskAssignmentTimestamp;
		return this;
	}

	public Agent getAgent() {
		return agent;
	}

	public Task setAgent(Agent agent) {
		this.agent = agent;
		return this;
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