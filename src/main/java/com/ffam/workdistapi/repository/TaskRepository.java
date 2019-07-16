package com.ffam.workdistapi.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ffam.workdistapi.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
	
	Optional<Task> findByAgentId(Integer agentId);
}
