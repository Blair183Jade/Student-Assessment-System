package com.assignment3version1.servlet;

import com.assignment3version1.dao.UserDAO;
import com.assignment3version1.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet responsible for handling login requests. It authenticates users by their username and password,
 * then redirects them to the appropriate dashboard based on their role within the system.
 * This servlet ensures secure access to admin, student, and teacher dashboards.
 *
 * @author jiahong lin
 * @date Created in 29/03/2024 17:42
 * @modified By jiahong lin in 29/03/2024 17:42
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Processes the HTTP POST request for user login. Validates the user credentials against the database,
     * establishes a session, and redirects the user to the corresponding dashboard based on the role.
     *
     * @param request HttpServletRequest object that contains the request the client has made of the servlet.
     * @param response HttpServletResponse object that contains the response the servlet sends to the client.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs during servlet processing.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO(); // Initializes UserDAO to interact with the database for user authentication.

        // Extract username and password from the request.
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Attempt to retrieve the user from the database.
        User user = userDAO.getUserByUsername(username);
        if (user != null && password.equals(user.getPassword())) {
            // User found and password matches, proceed to login.
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", user);

            // Redirect user to appropriate dashboard based on role.
            switch(user.getRole()) {
                case "admin":
                    response.sendRedirect("AdminDashboard");
                    break;
                case "student":
                    response.sendRedirect("StudentDashboard");
                    break;
                case "teacher":
                    response.sendRedirect("TeacherDashboard");
                    break;
                default:
                    response.sendRedirect("login.jsp?error=Invalid user role");
            }
        } else {
            // User not found or password does not match, redirect back to login page with an error message.
            response.sendRedirect("login.jsp?error=User not found or incorrect password");
        }
    }

    /**
     * Handles HTTP GET requests. Redirects any GET request to the login page,
     * ensuring that the login form is accessed properly.
     *
     * @param request HttpServletRequest object that contains the request the client has made of the servlet.
     * @param response HttpServletResponse object that contains the response the servlet sends to the client.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs during servlet processing.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Direct any GET request to the login page.
        response.sendRedirect("login.jsp");
    }
}
