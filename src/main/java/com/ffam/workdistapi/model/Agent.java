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
@Table (name = "agents")
public class Agent {
	
	@Id
	@Column (name = "agent_id")
	private Integer id;
	
	@Column (name = "first_name")
	private String firstName;
	
	@Column (name = "last_name")
	private String lastName;
	
	@OneToMany (mappedBy = "agent")
	private List<AgentSkills> agentSkills = new ArrayList<>();

	@SuppressWarnings("unused")
	private Agent() {/* To protect anyone from initializing using default constructor */}
	
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

	public List<AgentSkills> getAgentSkills() {
		return agentSkills;
	}

	public void setAgentSkills(List<AgentSkills> agentSkills) {
		this.agentSkills = agentSkills;
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