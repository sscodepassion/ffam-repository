package com.ffam.workdistapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.ffam.workdistapi.model.Agent;

public interface AgentRepository extends CrudRepository<Agent, Integer> {

	@Override
	@RestResource(exported = false)
	<S extends Agent> S save(S entity);

	@Override
	@RestResource(exported = false)
	<S extends Agent> Iterable<S> saveAll(Iterable<S> entities);

	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);

	@Override
	@RestResource(exported = false)
	void delete(Agent entity);

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends Agent> entities);

	@Override
	@RestResource(exported = false)
	void deleteAll();
}
