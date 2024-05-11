# Student Assessment System

## Overview
The Student Assessment System is a web-based application designed to simulate a University Management System. This system allows different user roles—Students, Teachers, and Admins—to interact with various educational and administrative functionalities through dedicated dashboards. Built using Java Servlets, JSP (JavaServer Pages), and relational databases, this application demonstrates how a multi-role application can manage complex interactions in an educational context.

## Features

### Admin Dashboard
- **User Management**: Admins can create and manage users (students and teachers), setting up access credentials and personal information.
- **Course Management**: Admins have the capability to create and modify course listings, including details such as course ID, course name, and semester.

### Student Dashboard
- **Course Enrollment**: Upon successful login, students can view courses they are enrolled in and enroll in new courses from a list of available offerings.
- **Assessment Overview**: Students can access their grades for various assessments such as quizzes, assignments, and final exams for each enrolled course.

### Teacher Dashboard
- **Course Assignment**: Teachers can view courses they are assigned to teach and register to teach additional courses.
- **Student Management**: For each course, teachers can view and manage enrolled students and enter grades for their assessments.

## Getting Started

### Prerequisites
- Java JDK 8 or newer
- Apache Tomcat 9.0 or newer
- MySQL Server 5.7 or newer

### Setup and Installation
1. **Database Setup**: Import the provided SQL script to set up the database schema and initial data.
2. **Server Setup**: Deploy the application on Apache Tomcat by placing the WAR file in the `webapps` directory.
3. **Configuration**: Update the `db.properties` file with your database connection details.

### Accessing the Application
- Navigate to `http://localhost:8080/StudentAssessmentSystem/` to access the login screen.
- Use the provided credentials for admin, student, or teacher to log in and interact with the system as per the assigned roles.

## Development
This project is developed with:
- **Backend**: Java Servlets and JSP
- **Frontend**: HTML, CSS, and JavaScript
- **Database**: MySQL

## Contributing
Contributions are welcome, and any contributions you make are **greatly appreciated**. If you have a suggestion that would improve this, please fork the repository and create a pull request. You can also simply open an issue with the tag "enhancement".

## License
Distributed under the MIT License. See `LICENSE` for more information.

## Acknowledgments
- This project is an educational tool intended to demonstrate the capabilities of Java Servlets and JSP in managing data-driven web applications.
