package com.ffam.workdistapi;

import static com.ffam.workdistapi.utils.TestUtils.asJsonString;
import static com.ffam.workdistapi.utils.TestUtils.ERR001;
import static com.ffam.workdistapi.utils.TestUtils.ERR_DESCRIPTION_NO_AGENTS_AVLBL_TO_ASSIGN_TASKS;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasToString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.server.ResponseStatusException;

import com.ffam.workdistapi.dto.TaskDTO;
import com.ffam.workdistapi.exceptions.TaskAssignmentException;
import com.ffam.workdistapi.model.Agent;
import com.ffam.workdistapi.model.Priority;
import com.ffam.workdistapi.model.Skill;
import com.ffam.workdistapi.model.Task;
import com.ffam.workdistapi.model.TaskStatus;
import com.ffam.workdistapi.services.TaskService;
import com.ffam.workdistapi.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest (classes = FfamWorkDistributionApiApplication.class)
@AutoConfigureMockMvc
public class TaskControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	TaskService taskService;
	
	TaskDTO inputTask;
	
	Task taskResponse;
	
	List<Task> allTasksResponse;
	
	@Before
	public void setUp() {
		inputTask = new TaskDTO();
		inputTask.setPriority(Priority.LOW);
		inputTask.setSkillsRequired(Arrays.asList(new String[] {"skill1", "skill3"}));
		
		Skill skill1 = new Skill("skill1");
		Skill skill2 = new Skill("skill2");
		Skill skill3 = new Skill("skill3");
		
		List<Skill> agentSkills = new ArrayList<>();
		agentSkills.add(skill1);
		agentSkills.add(skill3);
		Agent agent1 = new Agent("John", "Doe").setId(101).setAgentSkills(agentSkills);
		
		agentSkills = new ArrayList<>();
		agentSkills.add(skill1);
		agentSkills.add(skill2);
		agentSkills.add(skill3);
		Agent agent2 = new Agent("Mike", "Smith").setId(102).setAgentSkills(agentSkills);
		
		agentSkills = new ArrayList<>();
		agentSkills.add(skill1);
		agentSkills.add(skill2);
		agentSkills.add(skill3);
		Agent agent3 = new Agent("Kara", "Stevenson").setId(103).setAgentSkills(agentSkills);

		agentSkills = new ArrayList<>();
		agentSkills.add(skill2);
		agentSkills.add(skill3);
		Agent agent4 = new Agent("Parag", "Shah").setId(104).setAgentSkills(agentSkills);
		agentSkills = new ArrayList<>();

		agentSkills.add(skill2);
		Agent agent5 = new Agent("Su", "Kyi").setId(105).setAgentSkills(agentSkills);
		
		taskResponse = new Task(Priority.LOW, TaskStatus.INPROGRESS)
					.setId(1L)
					.setAgent(agent1)
					.setTaskAssignmentTimestamp(LocalDateTime.now());
		
		allTasksResponse = new ArrayList<>();
		Task task = new Task(Priority.LOW, TaskStatus.COMPLETED).setId(2L).setAgent(agent1).setTaskAssignmentTimestamp(LocalDateTime.now());
		allTasksResponse.add(task);
		task = new Task(Priority.HIGH, TaskStatus.COMPLETED).setId(3L).setAgent(agent1).setTaskAssignmentTimestamp(LocalDateTime.now());
		allTasksResponse.add(task);
		task = new Task(Priority.LOW, TaskStatus.INPROGRESS).setId(4L).setAgent(agent2).setTaskAssignmentTimestamp(LocalDateTime.now());
		allTasksResponse.add(task);
		task = new Task(Priority.LOW, TaskStatus.INPROGRESS).setId(5L).setAgent(agent3).setTaskAssignmentTimestamp(LocalDateTime.now());
		allTasksResponse.add(task);
		task = new Task(Priority.LOW, TaskStatus.INPROGRESS).setId(6L).setAgent(agent4).setTaskAssignmentTimestamp(LocalDateTime.now());
		allTasksResponse.add(task);
		task = new Task(Priority.LOW, TaskStatus.INPROGRESS).setId(7L).setAgent(agent3).setTaskAssignmentTimestamp(LocalDateTime.now());
		allTasksResponse.add(task);
		task = new Task(Priority.HIGH, TaskStatus.COMPLETED).setId(8L).setAgent(agent5).setTaskAssignmentTimestamp(LocalDateTime.now());
		allTasksResponse.add(task);
		task = new Task(Priority.LOW, TaskStatus.INPROGRESS).setId(9L).setAgent(agent5).setTaskAssignmentTimestamp(LocalDateTime.now());
		allTasksResponse.add(task);
		task = new Task(Priority.LOW, TaskStatus.INPROGRESS).setId(10L).setAgent(agent2).setTaskAssignmentTimestamp(LocalDateTime.now());
		allTasksResponse.add(task);
		task = new Task(Priority.LOW, TaskStatus.COMPLETED).setId(11L).setAgent(agent4).setTaskAssignmentTimestamp(LocalDateTime.now());
		allTasksResponse.add(task);
		task = new Task(Priority.LOW, TaskStatus.INPROGRESS).setId(12L).setAgent(agent5).setTaskAssignmentTimestamp(LocalDateTime.now());
		allTasksResponse.add(task);
		task = new Task(Priority.LOW, TaskStatus.INPROGRESS).setId(13L).setAgent(agent3).setTaskAssignmentTimestamp(LocalDateTime.now());
		allTasksResponse.add(task);
		task = new Task(Priority.LOW, TaskStatus.INPROGRESS).setId(14L).setAgent(agent1).setTaskAssignmentTimestamp(LocalDateTime.now());
		allTasksResponse.add(task);
		task = new Task(Priority.LOW, TaskStatus.INPROGRESS).setId(15L).setAgent(agent4).setTaskAssignmentTimestamp(LocalDateTime.now());
		allTasksResponse.add(task);
	}

	@Test
	public void shouldReturnValidResponseCreateTask() throws Exception {
		when(taskService.createTask(any())).
				thenReturn(taskResponse);
		
		callWorkDistributionAPIPost("/agentworkdistapi/tasks", inputTask)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andReturn();
	}

	@Test
	public void shouldReturnAgentNotFoundForTaskAssignmentCreateTask() throws Exception {
		when(taskService.createTask(any())).
				thenThrow(new TaskAssignmentException(ERR001, ERR_DESCRIPTION_NO_AGENTS_AVLBL_TO_ASSIGN_TASKS));
		
		callWorkDistributionAPIPost("/agentworkdistapi/tasks", inputTask)
				.andExpect(status().isNotFound())
				.andReturn();
	}

	@Test
	public void shouldReturnBadRequestForInvalidInputCreateTask() throws Exception {
		when(taskService.createTask(any())).
				thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "An Invalid input was sent"));
		
		callWorkDistributionAPIPost("/agentworkdistapi/tasks", null)
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	public void shouldReturnBadRequestForNoTaskPriorityAndSkillsCreateTask() throws Exception {
		when(taskService.createTask(any())).
				thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "An Invalid input was sent"));
		
		inputTask = new TaskDTO();
		callWorkDistributionAPIPost("/agentworkdistapi/tasks", inputTask)
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	public void shouldReturnBadRequestForNoSkillsForTaskCreateTask() throws Exception {
		when(taskService.createTask(any())).
				thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "An Invalid input was sent"));
		
		inputTask = new TaskDTO();
		inputTask.setPriority(Priority.LOW);
		inputTask.setSkillsRequired(Arrays.asList(new String[] {}));
		
		callWorkDistributionAPIPost("/agentworkdistapi/tasks", inputTask)
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	public void shouldReturnBadRequestForInvalidPriorityCreateTask() throws Exception {
		when(taskService.createTask(any())).
				thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "An Invalid input was sent"));
		
		mockMvc.perform(post("/agentworkdistapi/tasks")
				.content("{\r\n" + 
						"	\"priority\" : \"MEDIUM\",\r\n" + 
						"	\"skillsRequired\" : [\"skill2\", \"skill3\"]\r\n" + 
						"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	public void shouldRetrieveAllTasksSuccessfully() throws Exception {
		when(taskService.retrieveAllTasks()).
		thenReturn(allTasksResponse);

		callWorkDistributionAPIGet("/agentworkdistapi/tasks")
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(14)))
				.andReturn();
	}

	@Test
	public void shouldMarkTaskCompleteSuccessfully() throws Exception {
		when(taskService.markTaskCompleted(any())).
				thenReturn(taskResponse.setStatus(TaskStatus.COMPLETED));
		
		mockMvc.perform(patch("/agentworkdistapi/completeTask?id={id}", 2)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status", hasToString("COMPLETED")))
				.andReturn();
	}

	@Test
	public void shouldMarkTaskCompleteBadRequest() throws Exception {
		when(taskService.markTaskCompleted(any())).
				thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "An Invalid input was sent"));
		
		mockMvc.perform(patch("/agentworkdistapi/completeTask?id={id}", -123)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	private ResultActions callWorkDistributionAPIGet(String uri) throws Exception {
		return mockMvc.perform(get(uri)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}
	
	private ResultActions callWorkDistributionAPIPost(String uri, TaskDTO inputTask) throws Exception {
		return mockMvc.perform(post(uri)
				.content(asJsonString(inputTask))
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}
}
