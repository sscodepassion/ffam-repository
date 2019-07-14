package com.ffam.workdistapi.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AgentSkillMapId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column (name = "agent_id")
	private Integer agentId;
	
	@Column (name = "skill_id")
	private Integer skillId;
	
	@SuppressWarnings("unused")
	private AgentSkillMapId() { /* To protect anyone from initializing using default constructor */ }

	public AgentSkillMapId(Integer agentId, Integer skillId) {
		this.agentId = agentId;
		this.skillId = skillId;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public Integer getSkillId() {
		return skillId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(agentId, skillId);	
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		 
        if (obj == null || getClass() != obj.getClass())
            return false;
 
        AgentSkillMapId that = (AgentSkillMapId) obj;
        return Objects.equals(agentId, that.agentId) &&
               Objects.equals(skillId, that.skillId);
	}
}