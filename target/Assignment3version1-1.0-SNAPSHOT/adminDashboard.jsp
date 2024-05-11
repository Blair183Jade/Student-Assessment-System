<%--
  Created by IntelliJ IDEA.
  User: jememalum
  Date: 29/03/2024
  Time: 18:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.assignment3version1.dao.CourseDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.assignment3version1.model.User" %>
<%@ page import="com.assignment3version1.model.Course" %>

<html>
<head>
    <title>Admin Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; }
        .container { width: 80%; margin: auto; }
        table { width: 100%; border-collapse: collapse; }
        table, th, td { border: 1px solid black; }
        th, td { padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .form-section { margin-top: 20px; }
        input[type="text"], input[type="password"], select { width: 100%; padding: 12px 20px; margin: 8px 0; display: inline-block; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        input[type="submit"] { width: 100%; background-color: #4CAF50; color: white; padding: 14px 20px; margin: 8px 0; border: none; border-radius: 4px; cursor: pointer; }
        input[type="submit"]:hover { background-color: #45a049; }
        .section { background-color: #f9f9f9; padding: 20px; margin-top: 20px; }
    </style>
</head>
<body>

<div class="container">
    <h1>Admin Dashboard</h1>

    <!-- User Creation Form -->
    <div class="section">
        <h2>Create User</h2>
        <form action="AdminDashboard" method="post">
            <input type="hidden" name="action" value="createUser">
            <input type="text" name="username" placeholder="Username" required>
            <input type="password" name="password" placeholder="Password" required>
            <input type="text" name="firstName" placeholder="First Name" required>
            <input type="text" name="lastName" placeholder="Last Name" required>
            <input type="text" name="phone" placeholder="Phone" required>
            <select name="role" required>
                <option value="Student">Student</option>
                <option value="Teacher">Teacher</option>
                <option value="Admin">Admin</option>
            </select>
            <input type="submit" value="Create User">
        </form>
    </div>

    <!-- Course Creation Form -->
    <div class="section">
        <h2>Create Course</h2>
        <form action="AdminDashboard" method="post">
            <input type="hidden" name="action" value="createCourse">
            <input type="text" name="courseName" placeholder="Course Name" required>
            <input type="text" name="semester" placeholder="Semester" required>
            <input type="submit" value="Create Course">
        </form>
    </div>

    <!-- Users Display Section -->
    <div class="section">
        <h2>Existing Users</h2>
        <table>
            <tr>
                <th>Username</th>
                <th>Name</th>
                <th>Role</th>
            </tr>
            <% List<User> allUsers = (List<User>) request.getAttribute("allUsers");
                if (allUsers != null) {
                    for (User user : allUsers) { %>
            <tr>
                <td><%= user.getUsername() %></td>
                <td><%= user.getFirstName() + " " + user.getLastName() %></td>
                <td><%= user.getRole() %></td>
            </tr>
            <%      }
            } %>
        </table>
    </div>

    <!-- Courses Display Section -->
    <div class="section">
        <h2>Existing Courses</h2>
        <table>
            <tr>
                <th>Course Name</th>
                <th>Semester</th>
            </tr>
            <% List<Course> allCourses = (List<Course>) request.getAttribute("allCourses");
                if (allCourses != null) {
                    for (Course course : allCourses) { %>
            <tr>
                <td><%= course.getCourseName() %></td>
                <td><%= course.getSemester() %></td>
            </tr>
            <%      }
            } %>
        </table>
    </div>
</div>

</body>
</html>

