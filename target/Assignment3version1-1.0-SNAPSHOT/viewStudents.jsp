<%--
  Created by IntelliJ IDEA.
  User: jememalum
  Date: 30/03/2024
  Time: 00:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.assignment3version1.model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>View Enrolled Students</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            color: #333;
        }
        .container {
            max-width: 90%;
            margin: 20px auto;
            overflow: hidden;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px 0 rgba(0,0,0,0.1);
        }
        h2 {
            color: #28a745; /* A shade of green */
            text-align: center;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background-color: #28a745; /* A shade of green */
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        a {
            text-decoration: none;
            background-color: #28a745;
            color: white;
            padding: 8px 12px;
            border-radius: 5px;
            font-size: 14px;
        }
        a:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Enrolled Students</h2>
    <%
        List<User> enrolledStudents = (List<User>) request.getAttribute("enrolledStudents");
        // Use String.valueOf() to handle both null and non-null cases gracefully
        String selectedCourseId = String.valueOf(request.getAttribute("selectedCourseId"));
        if (enrolledStudents == null || enrolledStudents.isEmpty()) { %>
    <p>No students are enrolled in this course.</p>
    <% } else { %>
    <table>
        <tr>
            <th>Student Name</th>
            <th>Email (Username)</th>
            <th>Phone</th>
            <th>Grades</th>
        </tr>
        <% for (User student : enrolledStudents) { %>
        <tr>
            <td><%= student.getFirstName() + " " + student.getLastName() %></td>
            <td><%= student.getUsername() %></td>
            <td><%= student.getPhone() %></td>
            <td>
                <a href="submitGrades.jsp?userId=<%= student.getId() %>&courseId=<%= selectedCourseId %>">Submit Grades</a>
            </td>
        </tr>
        <% } %>
    </table>
    <% } %>
</div>

</body>
</html>


