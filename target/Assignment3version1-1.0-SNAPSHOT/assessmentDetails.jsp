<%--
  Created by IntelliJ IDEA.
  User: jememalum
  Date: 29/03/2024
  Time: 23:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.assignment3version1.model.Assessment" %>
<!DOCTYPE html>
<html>
<head>
    <title>Assessment Details</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; }
        .container { max-width: 800px; margin: 20px auto; padding: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 8px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #f2f2f2; }
        tr:hover {background-color: #f5f5f5;}
        .error { color: red; }
        .success { color: green; }
        .header { margin-bottom: 20px; }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <h2>Assessment Details</h2>
    </div>

    <%
        // Cast the assessments object to List<Assessment>
        List<Assessment> assessments = (List<Assessment>) request.getAttribute("assessments");
        if (assessments != null && !assessments.isEmpty()) {
    %>
    <table>
        <thead>
        <tr>
            <th>Quiz Marks</th>
            <th>Assignment Marks</th>
            <th>Final Exam Marks</th>
        </tr>
        </thead>
        <tbody>
        <% for (Assessment assessment : assessments) { %>
        <tr>
            <td><%= assessment.getQuizMarks() %></td>
            <td><%= assessment.getAssignmentMarks() %></td>
            <td><%= assessment.getExamMarks() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <%
    } else {
    %>
    <p class="error">No assessment data available.</p>
    <%
        }
    %>
</div>

</body>
</html>

