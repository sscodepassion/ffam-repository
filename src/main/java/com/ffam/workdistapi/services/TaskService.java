package com.ffam.workdistapi.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ffam.workdistapi.dto.TaskDTO;
import com.ffam.workdistapi.exceptions.TaskAssignmentException;
import com.ffam.workdistapi.model.Agent;
import com.ffam.workdistapi.model.Skill;
import com.ffam.workdistapi.model.Task;
import com.ffam.workdistapi.model.TaskStatus;
import com.ffam.workdistapi.repository.AgentRepository;
import com.ffam.workdistapi.repository.TaskRepository;

@Service
public class TaskService {
	
	private AgentRepository agentRepository;
	private TaskRepository taskRepository;
	
	private static final String ERR001 = "ERR-001";
	private static final String ERR_DESCRIPTION_NO_AGENTS_AVLBL_TO_ASSIGN_TASKS = "No Agents available to work on the Task";
	
	@Autowired
	public TaskService(AgentRepository agentRepository, TaskRepository taskRepository) {
		this.agentRepository = agentRepository;
		this.taskRepository = taskRepository;
	}
	
	public Task markTaskCompleted(final Long taskId) {
		Optional<Task> existingTask = taskRepository.findById(taskId);
		if (existingTask.isPresent()) {
			existingTask.get().setStatus(TaskStatus.COMPLETED);
			taskRepository.save(existingTask.get());
			return existingTask.get();
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task does not exist for marking completion");
		}
	}
	
	public List<Task> retrieveAllTasks() {
		return StreamSupport.stream(taskRepository.findAll().spliterator(), false)
							.collect(Collectors.toList());
	}
	
	public Task createTask(final TaskDTO taskDTO) {
		Task task = new Task(taskDTO.getPriority(), taskDTO.getStatus())
				.setStatus(TaskStatus.NEW);
		
		Iterable<Agent> allAgents = agentRepository.findAll();
		
		// Find Eligible Agents based on skill match in the Task
		List<Agent> availableAgentsForTaskAssignment = agentsEligibleToOwnTask(taskDTO, allAgents);
		if (availableAgentsForTaskAssignment.size() > 0) {
			// Find an Agent from the eligible Agents with No current Active Task Assignment 
			Optional<Agent> agent = retrieveAnAgentWithNoTaskAssignment(availableAgentsForTaskAssignment);
			if (agent.isPresent()) {
				// Assign the Task to the Agent, set TimeStamp of Task Assignment and Save to repository
				task.setAgent(agent.get())
					.setTaskAssignmentTimestamp(LocalDateTime.now())
					.setStatus(TaskStatus.INPROGRESS);
				return taskRepository.save(task);
			} else {
				List<Agent> agentsWorkingOnLowerPriorityTasks = retrieveAgentsWorkingOnLowerPriorityTask(task, availableAgentsForTaskAssignment);
				if (agentsWorkingOnLowerPriorityTasks.size() == 0) {
					// No Agents available to pick up the task so return ERROR per requirement
					throw new TaskAssignmentException(ERR001, ERR_DESCRIPTION_NO_AGENTS_AVLBL_TO_ASSIGN_TASKS);
				} else {
					// Check if all Eligible Agents with right skills have a lower priority task assigned
					if (agentsWorkingOnLowerPriorityTasks.size() == availableAgentsForTaskAssignment.size()) {
						// Pick an agent working on lower priority task with most recent task assignment 
						Optional<Agent> pickedAgent = pickAgentWithMostRecentTaskAssignment(agentsWorkingOnLowerPriorityTasks); 
						if (pickedAgent.isPresent()) {
							task.setAgent(pickedAgent.get())
								.setTaskAssignmentTimestamp(LocalDateTime.now())
								.setStatus(TaskStatus.INPROGRESS);
							return taskRepository.save(task);
						}
					}
					else {
						// No Agents available to pick up the task so return ERROR per requirement
						throw new TaskAssignmentException(ERR001, ERR_DESCRIPTION_NO_AGENTS_AVLBL_TO_ASSIGN_TASKS);
					}
				}
			}
		} else {
			// No Agents available to pick up the task so return ERROR per requirement
			throw new TaskAssignmentException(ERR001, ERR_DESCRIPTION_NO_AGENTS_AVLBL_TO_ASSIGN_TASKS);
		}
		
		return task;
	}
	
	private Optional<Agent> pickAgentWithMostRecentTaskAssignment(List<Agent> availableAgents) {
		LocalDateTime currTaskDateTime = null;
		LocalDateTime prevTaskDateTime = null;
		Agent agentPicked = null;
		for (Agent agent: availableAgents) {
			List<Task> tasks = taskRepository.findByAgentId(agent.getId());
			
			for (Task task: tasks) {
				if (!task.getStatus().equals(TaskStatus.COMPLETED) && task.getPriority().getPriorityValue() < 1) { 
					currTaskDateTime = task.getTaskAssignmentTimestamp();
					if (prevTaskDateTime != null && currTaskDateTime.isAfter(prevTaskDateTime)) {
						agentPicked = agent;
					} 
					prevTaskDateTime = currTaskDateTime;
				}
			}
		}
		return Optional.of(agentPicked);
	}
	
	private List<Agent> retrieveAgentsWorkingOnLowerPriorityTask(Task newTask, List<Agent> availableAgents) {
		List<Agent> agentList = new ArrayList<>();
		boolean agentWorkingHigherPriorityTask = false;
		for (Agent agent: availableAgents) {
			List<Task> tasks = taskRepository.findByAgentId(agent.getId());
			for (Task task: tasks) {
				if (!task.getStatus().equals(TaskStatus.COMPLETED)) {
					if (task.getPriority().getPriorityValue() >= newTask.getPriority().getPriorityValue()) {
						agentWorkingHigherPriorityTask = true;
						break;
					}
				} 
			}
			if (! agentWorkingHigherPriorityTask) {
				agentList.add(agent);
				agentWorkingHigherPriorityTask = false;
			}
		}
		if (agentList.size() > 0) {
			return agentList.stream().distinct().collect(Collectors.toList());
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
			List<Task> tasks = taskRepository.findByAgentId(agent.getId());
			if (tasks == null || (tasks != null && tasks.size() <= 0)) {
				return Optional.of(agent);
			} else {
				int tasksCompleted = 0;
				for (Task task: tasks) {
					if (task.getStatus().equals(TaskStatus.COMPLETED)) {
						tasksCompleted++;
					}
				}
				if (tasksCompleted == tasks.size()) return Optional.of(agent); 
			}
		}
		return Optional.empty();
	}
	
	private boolean skillMatch(List<Skill> agentSkills, List<String> requiredSkills) {
		if (requiredSkills == null || agentSkills == null) return false;
		
		if (agentSkills.size() != requiredSkills.size()) return false;
		else {
			List<String> agentSkillsList = agentSkills.stream().map(s -> s.getName()).sorted().collect(Collectors.toList());
			Collections.sort(requiredSkills);
			return requiredSkills.equals(agentSkillsList);
		}
	}
}
