package com.assignment3version1.servlet;

import com.assignment3version1.dao.EnrollmentDAO;
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
 * This servlet is responsible for handling requests to view the list of students enrolled in a specific course.
 * It ensures that only authenticated teachers can access the information. The servlet retrieves the course ID
 * from the request parameters, validates it, and uses the EnrollmentDAO to fetch the list of enrolled students.
 * The resulting list is then forwarded to the 'viewStudents.jsp' page for display.
 *
 * @author jiahong lin
 * @date Created in 30/03/2024 00:05
 * @modified By jiahong lin in 30/03/2024 00:05
 */
@WebServlet("/ViewStudentsServlet")
public class ViewStudentsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Processes the GET request to fetch and display the students enrolled in a selected course.
     * It performs checks to ensure that the user making the request is a teacher and that a valid
     * course ID is provided. In case of any issues (such as an invalid course ID or format),
     * it sets appropriate error messages and redirects to the teacher dashboard.
     *
     * @param request  HttpServletRequest object containing the client's request.
     * @param response HttpServletResponse object for the servlet's response.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs during servlet processing.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        // Authentication and role check
        if (currentUser == null || !"teacher".equals(currentUser.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Course ID parsing and validation
        String courseIdParam = request.getParameter("courseId");
        int courseId = parseAndValidateCourseId(courseIdParam, request, response);
        if (courseId == -1) return; // If course ID is invalid, response is already handled

        // Fetch and set enrolled students for display
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        List<User> enrolledStudents = enrollmentDAO.getStudentsEnrolledInCourse(courseId);
        request.setAttribute("enrolledStudents", enrolledStudents);
        request.setAttribute("selectedCourseId", courseId);
        request.getRequestDispatcher("/viewStudents.jsp").forward(request, response);
    }

    /**
     * Parses the course ID from request parameters and validates it.
     * Sets appropriate error messages and forwards to the teacher dashboard in case of invalid IDs.
     *
     * @param courseIdParam The course ID parameter from the request.
     * @param request       HttpServletRequest object for setting error attributes.
     * @param response      HttpServletResponse object for forwarding.
     * @return The parsed course ID, or -1 if validation fails.
     * @throws ServletException If a servlet-specific error occurs during forwarding.
     * @throws IOException      If an I/O error occurs during forwarding.
     */
    private int parseAndValidateCourseId(String courseIdParam, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (courseIdParam == null || courseIdParam.trim().isEmpty()) {
            setAndForwardError("Invalid course ID.", request, response);
            return -1;
        }

        try {
            return Integer.parseInt(courseIdParam);
        } catch (NumberFormatException e) {
            setAndForwardError("Invalid course ID format.", request, response);
            return -1;
        }
    }

    /**
     * Sets an error message on the request and forwards to the teacher dashboard.
     *
     * @param errorMessage The error message to be set.
     * @param request      HttpServletRequest object for setting the error attribute.
     * @param response     HttpServletResponse object for forwarding.
     * @throws ServletException If a servlet-specific error occurs during forwarding.
     * @throws IOException      If an I/O error occurs during forwarding.
     */
    private void setAndForwardError(String errorMessage, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", errorMessage);
        request.getRequestDispatcher("/teacherDashboard.jsp").forward(request, response);
    }
}
