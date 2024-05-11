<%--
  Created by IntelliJ IDEA.
  User: jememalum
  Date: 29/03/2024
  Time: 18:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.assignment3version1.model.Course" %>
<%@ page import="com.assignment3version1.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>Teacher Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f5f5f5; }
        .container { width: 80%; margin: auto; padding: 20px; }
        h2, h3 { color: #2e8b57; } /* Green theme */
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 8px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #2e8b57; color: white; }
        tr:hover { background-color: #f5f5f5; }
        .message { margin-top: 20px; padding: 10px; background-color: #dbf0e4; border-left: 6px solid #2e8b57; }
        .error { background-color: #f8d7da; border-left: 6px solid #d93025; }
        .success { background-color: #dbf0e4; border-left: 6px solid #2e8b57; }
        .registrationForm { margin-top: 20px; }
        .registrationForm input[type="submit"] { background-color: #4CAF50; color: white; padding: 10px 15px; border: none; border-radius: 4px; cursor: pointer; }
        .registrationForm input[type="submit"]:hover { background-color: #45a049; }
    </style>
</head>
<body>

<div class="container">
    <h2>Welcome, Teacher</h2>

    <% if(request.getAttribute("registrationMessage") != null) { %>
    <div class="message success"><%= request.getAttribute("registrationMessage") %></div>
    <% } %>
    <% if(request.getAttribute("registrationError") != null) { %>
    <div class="message error"><%= request.getAttribute("registrationError") %></div>
    <% } %>

    <div>
        <h3>Assigned Courses</h3>
        <table>
            <tr>
                <th>Course Name</th>
                <th>Semester</th>
                <th>Action</th>
            </tr>
            <%
                List<Course> assignedCourses = (List<Course>) request.getAttribute("assignedCourses");
                if(assignedCourses != null && !assignedCourses.isEmpty()) {
                    for(Course course : assignedCourses) {
            %>
            <tr>
                <td><%= course.getCourseName() %></td>
                <td><%= course.getSemester() %></td>
                <td><a href="ViewStudentsServlet?courseId=<%= course.getId() %>">View Enrolled Students</a></td>
            </tr>
            <%
                }
            } else {
            %>
            <tr><td colspan="3">No courses assigned. Check available courses to register.</td></tr>
            <%
                }
            %>
        </table>
    </div>

    <div class="registrationForm">
        <h3>Register to Teach a Course</h3>
        <form action="TeacherDashboard" method="post">
            <input type="hidden" name="action" value="registerForCourse"> <!-- Added hidden field for action command -->
            <select name="registerCourseId">
                <%
                    List<Course> availableCourses = (List<Course>) request.getAttribute("availableCourses");
                    if(availableCourses != null && !availableCourses.isEmpty()) {
                        for(Course course : availableCourses) {
                %>
                <option value="<%= course.getId() %>"><%= course.getCourseName() %></option>
                <%
                        }
                    }
                %>
            </select>
            <input type="submit" value="Register">
        </form>
    </div>

</div>

</body>
</html>
