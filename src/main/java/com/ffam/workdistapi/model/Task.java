package com.ffam.workdistapi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "tasks")
public class Task {
	
	@Id
	@GeneratedValue
	@Column (name = "task_id")
	private Long id;
	
	@Enumerated (EnumType.STRING)
	@Column (name = "priority")
	private Priority priority;
	
	@Enumerated (EnumType.STRING)
	@Column (name = "status")
	private TaskStatus status;
	
	@OneToMany (mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TaskSkills> taskSkills = new ArrayList<>();
	
	@SuppressWarnings("unused")
	private Task() {/* To protect anyone from initializing using default constructor */}

	public Task(Priority priority, TaskStatus status) {
		this.priority = priority;
		this.status = status;
	}
	
	public void addSkill(Skill skill) {
		TaskSkills taskSkill = new TaskSkills(this, skill);
		taskSkills.add(taskSkill);
		skill.getTaskSkills().add(taskSkill);
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

	public List<TaskSkills> getTaskSkills() {
		return taskSkills;
	}

	public void setTaskSkills(List<TaskSkills> taskSkills) {
		this.taskSkills = taskSkills;
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