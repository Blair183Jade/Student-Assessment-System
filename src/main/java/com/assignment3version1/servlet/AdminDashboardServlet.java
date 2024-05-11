package com.assignment3version1.servlet;

/**
 * Handles all operations related to the admin dashboard, including displaying users and courses,
 * and processing requests to create new users and courses.
 *
 * @author jiahong lin
 * @date Created in 29/03/2024 18:12
 * @modified By jiahong lin in 29/03/2024 18:12
 */

import com.assignment3version1.dao.CourseDAO;
import com.assignment3version1.dao.UserDAO;
import com.assignment3version1.model.Course;
import com.assignment3version1.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/AdminDashboard")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;
    private CourseDAO courseDAO;

    @Override
    public void init() {
        userDAO = new UserDAO(); // Assume this exists for managing user-related operations
        courseDAO = new CourseDAO(); // Assume this exists for managing course-related operations
    }

    /**
     * Processes GET requests to the admin dashboard. Fetches all users and courses from the database
     * and forwards them to the adminDashboard.jsp for display.
     *
     * @param request  The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Assume session check for admin role is done here

        // Fetching all users and courses to display on the admin dashboard
        List<User> allUsers = userDAO.getAllUsers();
        List<Course> allCourses = courseDAO.getAllCourses();

        // Setting attributes to be accessed in the JSP
        request.setAttribute("allUsers", allUsers);
        request.setAttribute("allCourses", allCourses);

        // Forwarding the request to the adminDashboard.jsp
        request.getRequestDispatcher("/adminDashboard.jsp").forward(request, response);
    }

    /**
     * Processes POST requests for creating new users or courses based on the provided request parameters.
     *
     * @param request  The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handling creation of new users or courses based on request parameters
        String action = request.getParameter("action");

        if ("createUser".equals(action)) {
            // Extract user information from request
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String phone = request.getParameter("phone");
            String role = request.getParameter("role");

            // Create and save the new user
            User newUser = new User(username, password, firstName, lastName, phone, role);
            userDAO.saveUser(newUser);

        } else if ("createCourse".equals(action)) {
            // Extract course information from request
            String courseName = request.getParameter("courseName");
            String semester = request.getParameter("semester");

            // Create and save the new course
            Course newCourse = new Course(courseName, semester);
            courseDAO.saveCourse(newCourse);
        }

        // Redirect back to the admin dashboard
        response.sendRedirect("AdminDashboard");
    }
}

