package com.ffam.workdistapi.model;

import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity 
@Table (name = "agentskillmap")
public class AgentSkills {
	
	@EmbeddedId
	private AgentSkillMapId id;
	
	@ManyToOne (fetch = FetchType.LAZY)
	@MapsId("agentId")
	private Agent agent;
	
	@ManyToOne (fetch = FetchType.LAZY)
	@MapsId("skillId")
	private Skill skill;
	
	@SuppressWarnings("unused")
	private AgentSkills() {/* To protect anyone from initializing using default constructor */}

	public AgentSkills(Agent agent, Skill skill) {
		this.id = new AgentSkillMapId(agent.getId(), skill.getId());
		this.agent = agent;
		this.skill = skill;
	}

	public AgentSkillMapId getId() {
		return id;
	}

	public Agent getAgent() {
		return agent;
	}

	public Skill getSkill() {
		return skill;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(agent, skill);	
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		 
        if (obj == null || getClass() != obj.getClass())
            return false;
 
        AgentSkills that = (AgentSkills) obj;
        return Objects.equals(agent, that.agent) &&
               Objects.equals(skill, that.skill);
	}
}
