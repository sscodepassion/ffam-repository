package com.ffam.workdistapi.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TaskSkillMapId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column (name = "task_id")
	private Long taskId;
	
	@Column (name = "skill_id")
	private Integer skillId;
	
	@SuppressWarnings("unused")
	private TaskSkillMapId() { /* To protect anyone from initializing using default constructor */ }
	
	public TaskSkillMapId(Long taskId, Integer skillId) {
		this.taskId = taskId;
		this.skillId = skillId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public Integer getSkillId() {
		return skillId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(taskId, skillId);	
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		 
        if (obj == null || getClass() != obj.getClass())
            return false;
 
        TaskSkillMapId that = (TaskSkillMapId) obj;
        return Objects.equals(taskId, that.taskId) &&
               Objects.equals(skillId, that.skillId);
	}	
}