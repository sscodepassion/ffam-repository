package com.ffam.workdistapi.model;

import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity 
@Table (name = "taskskillmap")
public class TaskSkills {
	
	@EmbeddedId
	private TaskSkillMapId id;
	
	@ManyToOne (fetch = FetchType.LAZY)
	@MapsId("taskId")
	private Task task;
	
	@ManyToOne (fetch = FetchType.LAZY)
	@MapsId("skillId")
	private Skill skill;
	
	@SuppressWarnings("unused")
	private TaskSkills() {/* To protect anyone from initializing using default constructor */}

	public TaskSkills(Task task, Skill skill) {
		this.id = new TaskSkillMapId(task.getId(), skill.getId());
		this.task = task;
		this.skill = skill;
	}

	public TaskSkillMapId getId() {
		return id;
	}

	public Task getTask() {
		return task;
	}

	public Skill getSkill() {
		return skill;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(task, skill);	
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		 
        if (obj == null || getClass() != obj.getClass())
            return false;
 
        TaskSkills that = (TaskSkills) obj;
        return Objects.equals(task, that.task) &&
               Objects.equals(skill, that.skill);
	}
}