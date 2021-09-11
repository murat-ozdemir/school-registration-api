# Simple School Registration System
## Pre-requirements
 - Docker Engine should be installed
 - Git command-line client should be installed
 - Postman (preferred), curl or any other rest client and rest service test tool
 - At least 2 GB System memory (RAM)
 - At least 800 Mbytes of storage (for docker images)

## Installation
 - Start docker engine
 - Create a virtual network
	 - `docker network create network-school`
 - Pull Mysql:8
	 - `docker pull mysql:8`
 - Run mysql container
	 - `docker container run --name mysqldb-school --network network-school -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=school -d mysql:8`
 - Check if container is started without problems;
	 - `docker container logs -f mysqldb-school`
 - Connect to container to check if db is already created;
	 - `docker container exec -it mysqldb-school bash`
	 - `mysql -uroot -proot`
	 - `show databases;`
	 - databases will be listed and you need to see **school** database
	 - exit mysql command line: `exit`
	 - exit docker container: `exit`
 - Clone project from Github
	 - `git clone https://github.com/murat-ozdemir/school-registration-api.git`
	 - `cd school-registration-api`
 - Build school registration service docker image
	 - `docker image build -t school-registration .`
 - Run school registration service docker image
	 - `docker container run --network network-school --name school-registration-container -p 8080:8080 -d school-registration`
 - Check if container is started without problems and database connection is establlished
	 - `docker container logs -f school-registration-container`

## Usage
### Api docs (json) for Swagger
 - Open internet browser
 - Paste `http://localhost:8080/api/v2/api-docs` in address bar and go
 - Copy Json content
 - Open `https://editor.swagger.io/` and paste Json content
 - Editor will ask you to convert Json to Yaml format, click OK button
 - You will see the Api documentation in human readable format

### Api docs GUI (Human Readable)
 - Open internet browser
 - Paste `http://localhost:8080/api/swagger-ui.html` in address bar and go
 - You can also test Api services using over api docs gui

### Student CRUD Api
 - Add Student Service
	 - WARNING! Student.schoolClass must contain both 1 char class number and 1 char branch, Eg. ‘3B’ or ‘2A’
	 - Service URL: `http://localhost:8080/student/add`
	 - Method: `POST`
	 - Request Body Example
		 - `{ "name": "FirstName", "surname": "LastName", "schoolClass": "3A", "age": "10", "sex": "Female"}`
	 - Possible Responses
		 - [Http 400] ErrorDataResult model with Students list in ‘data’ description
		 - [Http 500] ErrorDataResult model with Students list in ‘data’ description
		 - [Http 201] SuccessDataResult model with Student model in ‘data’ field
 - Update Student Service
	 - Service URL: `http://localhost:8080/student/update`
	 - Method: `PUT`
	 - Request Body Example
		 - `{ "studentId": 1, "age": 15 }`
		 - Partial property update is allowed!
 - Delete User Service
	 - [Click here to browse documentation](http://localhost:8080/api/swagger-ui.html)
 - Get User Service
	 - [Click here to browse documentation](http://localhost:8080/api/swagger-ui.html)
 - List Users Service
	 - [Click here to browse documentation](http://localhost:8080/api/swagger-ui.html)

### Course CRUD Api
 - Add Course Service
	 - Service URL: `http://localhost:8080/course/add`
	 - Method: `POST`
	 - Request Body Example
		 - `{ "category": "Engineering", "courseName": "Circuit Theory", "credits": 4, "classNumber": 8 }`
	 - Possible Responses
		 - [Http 400] ErrorDataResult model with Course model in ‘data’ description
		 - [Http 500] ErrorResult model with description
		 - [Http 201] SuccessDataResult model with Course model in ‘data’ field
 - Update CourseService
	 - Service URL: `http://localhost:8080/course/update`
	 - Method: `PUT`
	 - Request Body Example
		 - `{ "courseId": 1, "credits": 6 }`
		 - Partial property update is allowed!
 - Delete CourseService
	 - [Click here to browse documentation](http://localhost:8080/api/swagger-ui.html)
 - Get Course Service
	 - [Click here to browse documentation](http://localhost:8080/api/swagger-ui.html)
 - List Course Service
	 - [Click here to browse documentation](http://localhost:8080/api/swagger-ui.html)

### Student Course Registration Api 
 - Add Registration Service
	 - Service URL: `http://localhost:8080/registration/add`
	 - Method: `POST`
	 - Request Body Example
		 - `{ "studentId": 1, "courseId": 3 }`
	 - Possible Responses
		 - [Http 400] ErrorDataResult model with Student or Course model in ‘data’ field
		 - [Http 404] ErrorResult model with description
		 - [Http 201] SuccessDataResult model with Course Registration model in ‘data’ field
 - Get Students Registered To A Course
	 - [Click here to browse documentation](http://localhost:8080/api/swagger-ui.html)
 - Get Courses Which A Student Registered
	 - [Click here to browse documentation](http://localhost:8080/api/swagger-ui.html)
 - Get Courses Without Any Students
	 - [Click here to browse documentation](http://localhost:8080/api/swagger-ui.html)
 - Get Students Without Any Courses
	 - [Click here to browse documentation](http://localhost:8080/api/swagger-ui.html)