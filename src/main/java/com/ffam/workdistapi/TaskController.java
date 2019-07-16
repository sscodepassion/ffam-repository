package com.ffam.workdistapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
		
		if (validateRequest()) {
			
			retTask = taskService.createTask(taskDTO); 
		}
		
		return new ResponseEntity<Task>(retTask, HttpStatus.OK);
	}
	
	@RequestMapping (value = "/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Task>> retrieveAllTasks() {
		return new ResponseEntity<List<Task>>(taskService.retrieveAllTasks(), HttpStatus.OK);
	}
	
	private boolean validateRequest() {
		return true;
	}
}
