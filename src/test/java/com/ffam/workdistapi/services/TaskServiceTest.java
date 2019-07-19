package com.ffam.workdistapi.services;

import static com.ffam.workdistapi.utils.TestUtils.ERR001;
import static com.ffam.workdistapi.utils.TestUtils.ERR_DESCRIPTION_NO_AGENTS_AVLBL_TO_ASSIGN_TASKS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.ffam.workdistapi.FfamWorkDistributionApiApplication;
import com.ffam.workdistapi.dto.TaskDTO;
import com.ffam.workdistapi.exceptions.TaskAssignmentException;
import com.ffam.workdistapi.model.Agent;
import com.ffam.workdistapi.model.Priority;
import com.ffam.workdistapi.model.Skill;
import com.ffam.workdistapi.model.Task;
import com.ffam.workdistapi.model.TaskStatus;
import com.ffam.workdistapi.repository.AgentRepository;
import com.ffam.workdistapi.repository.TaskRepository;

@RunWith(SpringRunner.class)
@SpringBootTest (classes = FfamWorkDistributionApiApplication.class)
public class TaskServiceTest {
	
	@InjectMocks
	TaskService taskService;
	
	@Mock
	AgentRepository agentRepository;

	@Mock
	TaskRepository taskRepository;
	
	TaskDTO inputTask;
	
	Iterable<Agent> allAgents;
	
	Task taskResponse;
	
	List<Task> allTasksResponse;
	
	Agents agents;
	
	@Before
	public void setUp() {
		inputTask = new TaskDTO();
		inputTask.setPriority(Priority.LOW);
		inputTask.setSkillsRequired(Arrays.asList(new String[] {"skill1", "skill3"}));
		
		Skill skill1 = new Skill("skill1");
		Skill skill2 = new Skill("skill2");
		Skill skill3 = new Skill("skill3");
		
		List<Agent> allAgents = new ArrayList<>();
		List<Skill> agentSkills = new ArrayList<>();
		agentSkills.add(skill1);
		agentSkills.add(skill3);
		Agent agent1 = new Agent("John", "Doe").setId(101).setAgentSkills(agentSkills);
		allAgents.add(agent1);
		
		agentSkills = new ArrayList<>();
		agentSkills.add(skill1);
		agentSkills.add(skill2);
		agentSkills.add(skill3);
		Agent agent2 = new Agent("Mike", "Smith").setId(102).setAgentSkills(agentSkills);
		allAgents.add(agent2);
		
		agentSkills = new ArrayList<>();
		agentSkills.add(skill1);
		agentSkills.add(skill2);
		agentSkills.add(skill3);
		Agent agent3 = new Agent("Kara", "Stevenson").setId(103).setAgentSkills(agentSkills);
		allAgents.add(agent3);

		agentSkills = new ArrayList<>();
		agentSkills.add(skill2);
		agentSkills.add(skill3);
		Agent agent4 = new Agent("Parag", "Shah").setId(104).setAgentSkills(agentSkills);
		agentSkills = new ArrayList<>();
		allAgents.add(agent4);

		agentSkills.add(skill2);
		Agent agent5 = new Agent("Su", "Kyi").setId(105).setAgentSkills(agentSkills);
		allAgents.add(agent5);
		
		taskResponse = new Task(Priority.LOW, TaskStatus.INPROGRESS)
					.setId(1L)
					.setAgent(agent1)
					.setTaskAssignmentTimestamp(LocalDateTime.now());
		
		allTasksResponse = new ArrayList<>();
		Task task = new Task(Priority.LOW, TaskStatus.INPROGRESS).setId(2L).setAgent(agent1).setTaskAssignmentTimestamp(LocalDateTime.now());
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
		
		agents = new Agents(allAgents);
	}
	
	class Agents implements Iterable<Agent> {
		private List<Agent> agents;
		
		@Override
		public Iterator<Agent> iterator() {
			return this.agents.iterator();
		}
		
		Agents(List<Agent> agents) {
			this.agents = agents;
		}
	}
	
	@Test
	public void testConstructor() {
		assertNotNull(new TaskService(agentRepository, taskRepository));
	}
	
	@Test
	public void testCreateTaskHappyPath() {
		when(agentRepository.findAll()).
			thenReturn(agents);
		
		when(taskRepository.save(any())).
			thenReturn(taskResponse);

		List<Skill> agentSkills = new ArrayList<>();
		agentSkills.add(new Skill("skill1"));
		agentSkills.add(new Skill("skill3"));
		Agent agent = new Agent("John", "Doe").setId(101).setAgentSkills(agentSkills);
		
		when(taskRepository.findByAgentId(any())).
			thenReturn(Collections.emptyList());
		
		Task taskCreatedExpected = new Task(Priority.LOW, TaskStatus.INPROGRESS)
				.setId(1L)
				.setAgent(agent)
				.setTaskAssignmentTimestamp(LocalDateTime.now());
		
		Task taskCreatedActual = taskService.createTask(inputTask);
		assertThat(taskCreatedActual).isEqualTo(taskCreatedExpected);
	}
	
	@Test
	public void testCreateTaskTaskAssignmentExceptionNoAgentsWithRequiredSkills() {
		when(agentRepository.findAll()).
			thenReturn(Collections.emptyList());
		
		try {
			taskService.createTask(inputTask);
		} catch (TaskAssignmentException ex) {
			assertThat(TaskAssignmentException.class).isEqualTo(ex.getClass());
			assertThat(ERR001).isEqualTo(ex.getErrorCode());
			assertThat(ERR_DESCRIPTION_NO_AGENTS_AVLBL_TO_ASSIGN_TASKS).isEqualTo(ex.getErrorDescription());
		}
	}
	
	@Test
	public void testCreateTaskAgentsAvailableWithSkillsButNotAllAgentsWorkingLowPriorityTasks() {
		when(agentRepository.findAll()).
			thenReturn(agents);
		
		when(taskRepository.save(any())).
			thenReturn(taskResponse);

		List<Skill> agentSkills = new ArrayList<>();
		agentSkills.add(new Skill("skill1"));
		agentSkills.add(new Skill("skill3"));
		Agent agent = new Agent("John", "Doe").setId(101).setAgentSkills(agentSkills);
		List<Task> agentTasks = new ArrayList<>();
		Task task = new Task(Priority.HIGH, TaskStatus.INPROGRESS).setId(1L).setAgent(agent).setTaskAssignmentTimestamp(LocalDateTime.now());
		agentTasks.add(task);

		agentSkills = new ArrayList<>();
		agentSkills.add(new Skill("skill1"));
		agentSkills.add(new Skill("skill3"));
		agent = new Agent("Mike", "Smith").setId(102).setAgentSkills(agentSkills);
		agentTasks = new ArrayList<>();
		task = new Task(Priority.LOW, TaskStatus.INPROGRESS).setId(2L).setAgent(agent).setTaskAssignmentTimestamp(LocalDateTime.now());
		agentTasks.add(task);
		
		when(taskRepository.findByAgentId(any())).
			thenReturn(agentTasks);
		
		try {
			taskService.createTask(inputTask);
		} catch (TaskAssignmentException ex) {
			assertThat(TaskAssignmentException.class).isEqualTo(ex.getClass());
			assertThat(ERR001).isEqualTo(ex.getErrorCode());
			assertThat(ERR_DESCRIPTION_NO_AGENTS_AVLBL_TO_ASSIGN_TASKS).isEqualTo(ex.getErrorDescription());
		}
	}
}
