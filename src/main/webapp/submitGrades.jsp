<%--
  Created by IntelliJ IDEA.
  User: jememalum
  Date: 30/03/2024
  Time: 00:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.assignment3version1.model.User" %>
<%@ page import="com.assignment3version1.dao.UserDAO" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Submit Grades</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f4f7;
            margin: 0;
            padding: 20px;
            color: #333;
        }
        .container {
            max-width: 600px;
            margin: 20px auto;
            padding: 20px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            color: #4CAF50;
            text-align: center;
        }
        form {
            display: flex;
            flex-direction: column;
            gap: 10px;
        }
        label {
            margin-bottom: 5px;
        }
        input[type="number"] {
            padding: 8px;
            border-radius: 4px;
            border: 1px solid #ccc;
            width: 100%;
        }
        button {
            cursor: pointer;
            padding: 10px 15px;
            color: white;
            background-color: #4CAF50;
            border: none;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Submit Grades</h2>
    <%
        String userId = request.getParameter("userId");
        String courseId = request.getParameter("courseId");
        // Optional: Fetch student details if needed
    %>
    <form action="GradeSubmissionServlet" method="post">
        <input type="hidden" name="userId" value="<%= userId %>" />
        <input type="hidden" name="courseId" value="<%= courseId %>" />
        <label>Quiz: <input type="number" name="quizMarks" min="0" max="100" required /></label><br/>
        <label>Assignment: <input type="number" name="assignmentMarks" min="0" max="100" required /></label><br/>
        <label>Exam: <input type="number" name="examMarks" min="0" max="100" required /></label><br/>
        <button type="submit">Submit Grades</button>
    </form>
</div>

</body>
</html>

