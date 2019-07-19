package com.ffam.workdistapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.ffam.workdistapi.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
	
	List<Task> findByAgentId(Integer agentId);

	@Override
	@RestResource(exported = false)
	<S extends Task> Iterable<S> saveAll(Iterable<S> entities);

	@Override
	@RestResource(exported = false)
	void deleteById(Long id);

	@Override
	@RestResource(exported = false)
	void delete(Task entity);

	@Override
	@RestResource(exported = false)
	void deleteAll(Iterable<? extends Task> entities);

	@Override
	@RestResource(exported = false)
	void deleteAll();
}
