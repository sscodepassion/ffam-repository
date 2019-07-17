package com.ffam.workdistapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ffam.workdistapi.dto.TaskDTO;
import com.ffam.workdistapi.model.Task;
import com.ffam.workdistapi.services.TaskService;

@RestController
@RequestMapping (path = "/agentworkdistapi")
public class TaskController {
	
	@Autowired
	private TaskService taskService; 
	
	@RequestMapping (value = "/tasks", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO) {
		Task retTask = null;
		if (validateRequest(taskDTO)) {
			retTask = taskService.createTask(taskDTO); 
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An Invalid input was sent");
		}
		return new ResponseEntity<Task>(retTask, HttpStatus.CREATED);
	}

	@RequestMapping (value = "/completeTask", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Task> completeTask(@RequestParam Long id) {
		
		if (id <= 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An Invalid input was sent");
		}
		return new ResponseEntity<Task>(taskService.markTaskCompleted(id), HttpStatus.OK);
	}
	
	@RequestMapping (value = "/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Task>> retrieveAllTasks() {
		return new ResponseEntity<List<Task>>(taskService.retrieveAllTasks(), HttpStatus.OK);
	}
	
	private boolean validateRequest(TaskDTO taskDTO) {
		if (taskDTO == null) return false;
		
		if (taskDTO.getPriority() == null || taskDTO.getSkillsRequired() == null) return false;
		
		if (taskDTO.getSkillsRequired() != null && taskDTO.getSkillsRequired().size() <= 0) return false;
		
		if (taskDTO.getPriority().getPriorityValue() < 0 || taskDTO.getPriority().getPriorityValue() > 1) return false;
		
		return true;
	}
}
