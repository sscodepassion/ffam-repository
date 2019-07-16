package com.ffam.workdistapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.ffam.workdistapi.model.Agent;

public interface AgentRepository extends CrudRepository<Agent, Integer> {
}
