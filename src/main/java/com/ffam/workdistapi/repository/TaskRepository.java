package com.ffam.workdistapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ffam.workdistapi.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
	
	List<Task> findByAgentId(Integer agentId);
}
