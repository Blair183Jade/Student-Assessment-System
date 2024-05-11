package com.assignment3version1.servlet;

import com.assignment3version1.dao.CourseDAO;
import com.assignment3version1.dao.EnrollmentDAO;
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

/**
 * Servlet responsible for managing the teacher dashboard. It provides functionality for teachers to view courses they are assigned to,
 * enroll in additional courses, and view students enrolled in their courses. This servlet interacts with CourseDAO, EnrollmentDAO,
 * and UserDAO to fetch and manipulate course and enrollment information.
 *
 * @author jiahong lin
 * @date Created in 29/03/2024 23:49
 * @modified By jiahong lin in 29/03/2024 23:49
 */
@WebServlet("/TeacherDashboard")
public class TeacherDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CourseDAO courseDAO = new CourseDAO();
    private EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private UserDAO userDAO = new UserDAO();

    /**
     * Processes GET requests to display the teacher's dashboard. This includes showing assigned courses, allowing for registration into new courses,
     * and viewing students enrolled in a specific course. Only authenticated users with a 'teacher' role have access to this functionality.
     *
     * @param request  HttpServletRequest object containing the client's request.
     * @param response HttpServletResponse object for the servlet's response.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs during processing.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        // Ensure the user is authenticated and authorized to access the teacher dashboard.
        if (currentUser == null || !currentUser.getRole().equals("teacher")) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = currentUser.getId();

        // Fetch and display courses assigned to the teacher
        List<Course> assignedCourses = courseDAO.getAssignedCoursesByTeacherId(userId);
        request.setAttribute("assignedCourses", assignedCourses);

        // Provide an option to register for additional courses if no courses are currently assigned.
        if (assignedCourses.isEmpty()) {
            List<Course> availableCourses = courseDAO.getAllCourses();
            request.setAttribute("availableCourses", availableCourses);
        }

        // Allow the teacher to view students enrolled in their courses.
        handleEnrolledStudentsRequest(request);

        request.getRequestDispatcher("/teacherDashboard.jsp").forward(request, response);
    }

    /**
     * Processes POST requests for teachers to register for additional courses. Updates are reflected on the teacher's dashboard
     * and confirmation messages are displayed accordingly.
     *
     * @param request  HttpServletRequest object containing the client's request.
     * @param response HttpServletResponse object for the servlet's response.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs during processing.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        // Check if the action command is for registering to teach a course
        String action = request.getParameter("action");
        if ("registerForCourse".equals(action)) {
            handleCourseRegistration(request, currentUser);
        }

        doGet(request, response);
    }

    /**
     * Handles logic for registering a teacher to a course. Sets appropriate success or error messages based on the outcome.
     *
     * @param request     HttpServletRequest object containing the client's request.
     * @param currentUser The currently authenticated user.
     */
    private void handleCourseRegistration(HttpServletRequest request, User currentUser) {
        String registerCourseIdParam = request.getParameter("registerCourseId");
        if (registerCourseIdParam != null && !registerCourseIdParam.trim().isEmpty()) {
            try {
                int courseIdToRegister = Integer.parseInt(registerCourseIdParam);
                boolean isRegistered = courseDAO.registerTeacherForCourse(currentUser.getId(), courseIdToRegister);
                if (isRegistered) {
                    request.setAttribute("registrationMessage", "Registration successful.");
                } else {
                    request.setAttribute("registrationError", "Could not register. You might already be registered for this course.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("registrationError", "Invalid course ID format.");
            }
        }
    }

    /**
     * Handles the request to view students enrolled in a specific course, setting the necessary request attributes for display in the JSP.
     *
     * @param request HttpServletRequest object containing the client's request.
     */
    private void handleEnrolledStudentsRequest(HttpServletRequest request) {
        String courseIdParam = request.getParameter("viewStudentsCourseId");
        if (courseIdParam != null && !courseIdParam.trim().isEmpty()) {
            try {
                int courseId = Integer.parseInt(courseIdParam);
                List<User> enrolledStudents = enrollmentDAO.getStudentsEnrolledInCourse(courseId);
                request.setAttribute("enrolledStudents", enrolledStudents);
                request.setAttribute("selectedCourseId", courseId);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid course ID format.");
            }
        }
    }

}
