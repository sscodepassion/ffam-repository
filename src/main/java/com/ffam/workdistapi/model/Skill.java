package com.ffam.workdistapi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "skills")
public class Skill {

	@Id
	@Column (name = "skill_id")
	private Integer id;
	
	@Column (name = "skill_name")
	private String name;
	
	@OneToMany (mappedBy = "skill")
	private List<AgentSkills> agentSkills = new ArrayList<>();

	@OneToMany (mappedBy = "skill")
	private List<TaskSkills> taskSkills = new ArrayList<>();
	
	@SuppressWarnings("unused")
	private Skill() {/* To protect anyone from initializing using default constructor */}
	
	public Skill(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<AgentSkills> getAgentSkills() {
		return agentSkills;
	}

	public void setAgentSkills(List<AgentSkills> agentSkills) {
		this.agentSkills = agentSkills;
	}
	
	public List<TaskSkills> getTaskSkills() {
		return taskSkills;
	}

	public void setTaskSkills(List<TaskSkills> taskSkills) {
		this.taskSkills = taskSkills;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);	
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		 
        if (obj == null || getClass() != obj.getClass())
            return false;
 
        Skill that = (Skill) obj;
        return Objects.equals(name, that.name);
	}
}