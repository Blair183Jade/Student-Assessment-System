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
<!DOCTYPE html>
<html>
<head>
    <title>Student Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; }
        .container { width: 80%; margin: auto; padding-top: 20px; }
        table { width: 100%; border-collapse: collapse; }
        table, th, td { border: 1px solid black; }
        th, td { padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .form-section, .section { background-color: #f9f9f9; padding: 20px; margin-top: 20px; }
        input[type="submit"] { width: auto; padding: 5px 10px; margin-left: 10px; background-color: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }
        input[type="submit"]:hover { background-color: #45a049; }
        .error { color: red; }
        .success { color: green; }
        .header { margin-bottom: 20px; }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <h2>Welcome to Your Dashboard</h2>
        <% String enrollmentSuccess = (String) request.getAttribute("enrollmentSuccess");
            String enrollmentError = (String) request.getAttribute("enrollmentError");
            if (enrollmentError != null) { %>
        <div class="error"><%= enrollmentError %></div>
        <% } %>
        <% if (enrollmentSuccess != null) { %>
        <div class="success"><%= enrollmentSuccess %></div>
        <% } %>
    </div>

    <div class="section">
        <h3>Enrolled Courses</h3>
        <table>
            <tr>
                <th>Course Name</th>
                <th>Semester</th>
                <th>View Assessments</th>
            </tr>
            <% List<Course> enrolledCourses = (List<Course>) request.getAttribute("enrolledCourses");
                if (enrolledCourses != null && !enrolledCourses.isEmpty()) {
                    for (Course course : enrolledCourses) { %>
            <tr>
                <td><%= course.getCourseName() %></td>
                <td><%= course.getSemester() %></td>
                <td>
                    <!-- Link to a separate servlet for viewing assessments -->
                    <a href="AssessmentServlet?courseId=<%= course.getId() %>">View Assessments</a>
                </td>
            </tr>
            <%     }
            } else { %>
            <tr><td colspan="3">You are not enrolled in any courses.</td></tr>
            <% } %>
        </table>
    </div>

    <!-- Available Courses Section -->
    <div class="section">
        <h3>Available Courses</h3>
        <table>
            <tr>
                <th>Course Name</th>
                <th>Semester</th>
                <th>Enroll</th>
            </tr>
            <% List<Course> availableCourses = (List<Course>) request.getAttribute("availableCourses");
                for (Course course : availableCourses) { %>
            <tr>
                <td><%= course.getCourseName() %></td>
                <td><%= course.getSemester() %></td>
                <td>
                    <form action="StudentDashboard" method="post">
                        <input type="hidden" name="courseId" value="<%= course.getId() %>" />
                        <input type="submit" value="Enroll">
                    </form>
                </td>
            </tr>
            <% } %>
        </table>
    </div>
</div>

</body>
</html>



