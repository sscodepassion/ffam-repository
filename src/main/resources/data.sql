DROP TABLE IF EXISTS agents;

DROP TABLE IF EXISTS skills;

DROP TABLE IF EXISTS agentskillmap;

DROP TABLE IF EXISTS tasks;

DROP TABLE IF EXISTS task_assignment;

DROP TABLE IF EXISTS taskskillmap;

CREATE TABLE agents (
  agent_id INT PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL
);

CREATE TABLE skills (
  skill_id INT PRIMARY KEY,
  skill_name VARCHAR(10) NOT NULL
);

CREATE TABLE agentskillmap (
  agent_id INT,
  skill_id INT,	
  foreign key (agent_id) references agents(agent_id),
  foreign key (skill_id) references skills(skill_id)
);

CREATE TABLE tasks (
  task_id BIGINT PRIMARY KEY,
  priority VARCHAR(10) NOT NULL,
  status VARCHAR(50) DEFAULT 'NEW' NOT NULL,
  task_assignment_timestamp DATETIME DEFAULT NULL,
  agent_id INT DEFAULT NULL,
  foreign key (agent_id) references agents(agent_id),
);

INSERT INTO agents (agent_id, first_name, last_name) VALUES
  (1001, 'Kyler', 'Murray'),
  (1002, 'David', 'Johnson'),
  (1003, 'Larry', 'Fitzgerald'),
  (1004, 'Patrick', 'Peterson'),
  (1005, 'Terrell', 'Suggs'),
  (1006, 'Kevin', 'White'),
  (1007, 'Christian', 'Kirk'),
  (1008, 'Chandler', 'Jones'),
  (1009, 'Jordan', 'Hicks'),
  (1010, 'Mark', 'Smith'),
  (1011, 'Philipp', 'Schule'),
  (1012, 'Brett', 'Hundley');

INSERT INTO skills (skill_id, skill_name) VALUES
  (101, 'skill1'),
  (102, 'skill2'),
  (103, 'skill3');

INSERT INTO agentskillmap (agent_id, skill_id) VALUES
  (1001, 101),
  (1002, 101),
  (1002, 103),
  (1003, 102),
  (1003, 103),
  (1001, 102),
  (1004, 102),
  (1004, 103),
  (1005, 103),
  (1006, 101),
  (1006, 103),
  (1007, 101),
  (1007, 102),
  (1008, 103),
  (1009, 101),
  (1009, 102),
  (1009, 103),
  (1010, 102),
  (1010, 103),
  (1011, 101),
  (1011, 102),
  (1012, 102);