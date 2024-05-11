<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Login</title>
  <style>
    body {
      font-family: Arial, sans-serif;
    }
    .container {
      width: 300px;
      margin: 100px auto;
      padding: 20px;
      border: 1px solid #ccc;
      border-radius: 5px;
    }
    input[type="text"], input[type="password"] {
      width: calc(100% - 22px);
      padding: 10px;
      margin-bottom: 10px;
      border: 1px solid #ccc;
      border-radius: 3px;
    }
    button[type="submit"] {
      width: 100%;
      padding: 10px;
      border: none;
      background-color: lightseagreen;
      color: white;
      cursor: pointer;
      border-radius: 3px;
    }
    .error {
      color: red;
      margin-bottom: 10px;
    }
  </style>
</head>
<body>
<div class="container">
  <h2>Login</h2>
  <% String error = request.getParameter("error"); %>
  <% if (error != null && !error.isEmpty()) { %>
  <div class="error"><%= error %></div>
  <% } %>
  <form action="<%= request.getContextPath() %>/Login" method="post">
    <div>
      <label for="username">Username:</label>
      <input type="text" id="username" name="username" required>
    </div>
    <div>
      <label for="password">Password:</label>
      <input type="password" id="password" name="password" required>
    </div>
    <button type="submit">Login</button>
  </form>
</div>
</body>
</html>
