package com.ffam.workdistapi.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffam.workdistapi.dto.TaskDTO;
import com.ffam.workdistapi.model.Agent;
import com.ffam.workdistapi.model.Skill;
import com.ffam.workdistapi.model.Task;
import com.ffam.workdistapi.repository.AgentRepository;
import com.ffam.workdistapi.repository.TaskRepository;

@Service
public class TaskService {
	
	private AgentRepository agentRepository;
	private TaskRepository taskRepository;
	
	@Autowired
	public TaskService(AgentRepository agentRepository, TaskRepository taskRepository) {
		this.agentRepository = agentRepository;
		this.taskRepository = taskRepository;
	}
	
	public List<Task> retrieveAllTasks() {
		return StreamSupport.stream(taskRepository.findAll().spliterator(), false)
							.collect(Collectors.toList());
	}
	
	public Task createTask(final TaskDTO taskDTO) {
		Task task = new Task(taskDTO.getPriority(), taskDTO.getStatus());
		
		Iterable<Agent> allAgents = agentRepository.findAll();
		
		List<Agent> availableAgentsForTaskAssignment = agentsEligibleToOwnTask(taskDTO, allAgents);
		if (availableAgentsForTaskAssignment.size() == 0) {
			//TODO No Agents found with skills matching the skills required for the task so return ERROR
			
		} else {
			Optional<Agent> agent = retrieveAnAgentWithNoTaskAssignment(availableAgentsForTaskAssignment);
			if (agent.isPresent()) {
				// Assign the Task to the Agent, set TimeStamp of Task Assignment and Save to repository
				task.setAgent(agent.get());
				task.setTaskAssignmentTimestamp(LocalDateTime.now());
				return taskRepository.save(task);
			} else {
				List<Agent> agentsWorkingOnLowerPriorityTasks = retrieveAgentWorkingOnLowerPriorityTask(task, availableAgentsForTaskAssignment);
				if (agentsWorkingOnLowerPriorityTasks.size() == 0) {
					//TODO - No Agents available to pick up the task so return ERROR per requirement
				} else {
					Optional<Agent> pickedAgent = pickAgentWithMostRecentTaskAssignment(availableAgentsForTaskAssignment); 
					if (pickedAgent.isPresent()) {
						task.setAgent(pickedAgent.get());
						task.setTaskAssignmentTimestamp(LocalDateTime.now());
						return taskRepository.save(task);
					}
				}
			}
		}
		
		return task;
	}
	
	private Optional<Agent> pickAgentWithMostRecentTaskAssignment(List<Agent> availableAgents) {
		LocalDateTime currTaskDateTime = null;
		LocalDateTime prevTaskDateTime = null;
		Agent agentPicked = null;
		for (Agent agent: availableAgents) {
			Optional<Task> task = taskRepository.findByAgentId(agent.getId());
			if (task.isPresent()) {
				currTaskDateTime = task.get().getTaskAssignmentTimestamp();
				if (prevTaskDateTime != null && currTaskDateTime.isBefore(prevTaskDateTime)) {
					agentPicked = agent;
				} 
				prevTaskDateTime = currTaskDateTime;
			} 
		}
		return Optional.of(agentPicked);
	}
	
	private List<Agent> retrieveAgentWorkingOnLowerPriorityTask(Task newTask, List<Agent> availableAgents) {
		List<Agent> agentList = new ArrayList<>(); 
		for (Agent agent: availableAgents) {
			Optional<Task> task = taskRepository.findByAgentId(agent.getId());
			if (task.isPresent() && (task.get().getPriority().getPriorityValue() < newTask.getPriority().getPriorityValue()) ) {
				agentList.add(agent);
			} 
		}
		return agentList;
	}
	
	private List<Agent> agentsEligibleToOwnTask(TaskDTO taskDTO, Iterable<Agent> allAgents) {
		List<Agent> availableAgentsList = new ArrayList<>();
		List<String> requiredSkillsForTaskOwnership = taskDTO.getSkillsRequired();
		
		for (Agent agent : allAgents) {
			if (skillMatch(agent.getAgentSkills(), requiredSkillsForTaskOwnership)) {
				availableAgentsList.add(agent);
			}
		}
		
		return availableAgentsList;
	}
	
	private Optional<Agent> retrieveAnAgentWithNoTaskAssignment(List<Agent> availableAgents) {
		
		for (Agent agent: availableAgents) {
			Optional<Task> task = taskRepository.findByAgentId(agent.getId());
			if (!task.isPresent()) return Optional.of(agent);
		}
		
		return Optional.empty();
	}
	
	private boolean skillMatch(List<Skill> agentSkills, List<String> requiredSkills) {
		
		if (requiredSkills == null || agentSkills == null) return false;
		
		if (agentSkills.size() != requiredSkills.size()) return false;
		else {
			List<String> agentSkillsList = agentSkills.stream().sorted().map(s -> s.getName()).collect(Collectors.toList());
			Collections.sort(requiredSkills);
			return requiredSkills.equals(agentSkillsList);
		}
	}
}
