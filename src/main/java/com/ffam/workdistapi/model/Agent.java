package com.ffam.workdistapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "agents")
public class Agent implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column (name = "agent_id")
	private Integer id;
	
	@Column (name = "first_name")
	private String firstName;
	
	@Column (name = "last_name")
	private String lastName;
	
	@OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable (name = "agentskillmap", 
		joinColumns = {@JoinColumn (name = "agent_id")},
		inverseJoinColumns = {@JoinColumn (name = "skill_id")})
	private List<Skill> agentSkills = new ArrayList<>();
	
	@OneToMany (mappedBy = "agent")
	private List<Task> tasks = new ArrayList<>();
	
	public Agent() {
	}
	
	public Agent(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Integer getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setAgentSkills(List<Skill> agentSkills) {
		this.agentSkills = agentSkills;
	}

	public List<Skill> getAgentSkills() {
		return agentSkills;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName);	
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		 
        if (obj == null || getClass() != obj.getClass())
            return false;
 
        Agent that = (Agent) obj;
        return Objects.equals(firstName, that.firstName) &&
               Objects.equals(lastName, that.lastName);
	}
}