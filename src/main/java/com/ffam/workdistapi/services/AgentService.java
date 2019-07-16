package com.ffam.workdistapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffam.workdistapi.model.Agent;
import com.ffam.workdistapi.repository.AgentRepository;

@Service
public class AgentService {
	
	private AgentRepository agentRepository;

	@Autowired
	public AgentService(final AgentRepository agentRepository) {
		this.agentRepository = agentRepository;
	}
	
	public Iterable<Agent> lookupAgents() {
		return agentRepository.findAll();
	}
}
