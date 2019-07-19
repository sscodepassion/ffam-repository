## FFAM Work Distribution API

This API has been created for a Distribution Service that can distribute tasks to Agents based on certain business rules

## Instructions to build, run and test the Work Distribution API 

### Pre-requisites
* JDK 1.8 should be installed with the PATH set
* Latest Maven version should be installed with the PATH set

### Technology Stack used
* Development - ***Java 8, Spring boot, Spring Data JPA, Spring Data Rest, Junit 4, Mockito***
* Database - ***In-memory H2 Database*** 

### Instructions to clone, build, and unit test the code

* ***Clone the code from GitHub using the command below***

$ git clone https://github.com/sscodepassion/ffam-repository.git

* ***cd to the folder where the repository is cloned (There should be a pom.xml file in this location)***

* ***Run the below-mentioned Maven command to build the service deployable and run JUnits***
***JUnit test results will be visible in the bash / cmd window where this command is run*** 

$ mvn clean install

* ***cd to the "target" directory and run the below-mentioned command to start the Spring boot app***

$ java -jar work-distribution-api-1.0.0-SNAPSHOT.jar

* This will start the Spring boot Work Distribution Rest API app on an embedded Tomcat server  - (Wait until you see a message similar to this - Started FfamWorkDistributionApiApplication in 12.342 seconds (JVM running for 13.105))

* The Agents and Skills data will be pre-loaded into the in-memory H2 database on spring boot app startup.


## Testing the Work Distribution API

#### ***Use a tool like Postman or soapUI to CURL cli to test / run the following end points*** 

***INFO:*** 
* The port information can be obtained from the spring boot app start up logs (A message similar to this - Tomcat started on port(s): 8080 (http) with context path '')

* Endpoint to view / run queries on H2 Database - ***http://localhost:8080/h2-console*** (User Name & Password is "admin", once connected you can run SQL queries to tables to validate data)

* The response JSON will contain the HATEOAS links to guide in the navigation of application state

* ***Rest API end point to view all Agents with HATEOAS links to Tasks assigned to them (if any):*** 

***GET*** http://localhost:8080/agentworkdistapi/agents ***(Only GET is allowed, POST/PATCH/PUT/DELETE will return 405 - Method Not Allowed)***

* ***Rest API end point to view all Skills (Allowed Values for Skills):*** 

***GET*** http://localhost:8080/agentworkdistapi/skills ***(Only GET is allowed, POST/PATCH/PUT/DELETE will return 405 - Method Not Allowed)***

* ***Rest API end point to view all Tasks:***

***GET*** http://localhost:8080/agentworkdistapi/tasks

* ***Rest API end point to create a New Task and assign to an available Agent (based on the business rules mentioned in the requirement) and return the Task details with assigned agent:*** 

***POST*** http://localhost:8080/agentworkdistapi/tasks

* Sample Request Body:

{
	"priority" : "LOW",
	"skillsRequired" : ["skill2", "skill3"]
}

* Sample Response:

{
    "id": 1,
    "priority": "LOW",
    "status": "INPROGRESS",
    "taskAssignmentTimestamp": "2019-07-18T21:44:47.648",
    "agent": {
        "id": 1003,
        "firstName": "Larry",
        "lastName": "Fitzgerald",
        "agentSkills": [
            {
                "id": 102,
                "name": "skill2"
            },
            {
                "id": 103,
                "name": "skill3"
            }
        ]
    }
}

* Status ***201 Created*** 

* If no agent is available to take the task based on the business rules mentioned in the requirement document, below would be the Error response - 

{
    "errorTimeStamp": "07-18-2019 09:47:34",
    "message": "No Agents available to work on the Task",
    "debugMessage": "No Agents available to work on the Task"
}

* Status ***405 Method Not Allowed***  

* ***Rest API end point to create a New Task and assign to an available Agent (based on the business rules mentioned in the requirement) and return the Task details with assigned agent:*** 

***PATCH*** http://localhost:8080/agentworkdistapi/completeTask?id={id}  ***(Replace {id} with the ID of the task to be completed. This ID can be obtained by doing a GET on http://localhost:8080/agentworkdistapi/tasks to view all Tasks)***

* Sample Response: 

{
    "id": 4,
    "priority": "HIGH",
    "status": "COMPLETED",
    "taskAssignmentTimestamp": "2019-07-18T19:44:33.818",
    "agent": {
        "id": 1010,
        "firstName": "Mark",
        "lastName": "Smith",
        "agentSkills": [
            {
                "id": 102,
                "name": "skill2"
            },
            {
                "id": 103,
                "name": "skill3"
            }
        ]
    }
}

* Status ***200 OK***
