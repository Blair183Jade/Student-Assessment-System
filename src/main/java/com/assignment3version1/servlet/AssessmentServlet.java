package com.assignment3version1.servlet;

/**
 * Servlet responsible for handling requests related to viewing assessments for a course.
 * It retrieves assessments based on the current user (student) and the selected course,
 * then forwards the information to a JSP for display.
 *
 * @author jiahong lin
 * @date Created in 29/03/2024 23:38
 * @modified By jiahong lin in 29/03/2024 23:38
 */

import com.assignment3version1.dao.AssessmentDAO;
import com.assignment3version1.model.Assessment;
import com.assignment3version1.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/AssessmentServlet")
public class AssessmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * Initializes the servlet and creates an instance of the AssessmentDAO to interact with the database.
     */
    private AssessmentDAO assessmentDAO;

    public AssessmentServlet() {
        this.assessmentDAO = new AssessmentDAO();
    }

    /**
     * Processes GET requests to display assessments for a course. Validates the user role and
     * the provided course ID before fetching and displaying assessment data.
     *
     * @param request  HttpServletRequest object that contains the request the client has made of the servlet.
     * @param response HttpServletResponse object that contains the response the servlet sends to the client.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs during servlet processing.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        // Redirect to login if not logged in or not a student.
        if (currentUser == null || !currentUser.getRole().equals("student")) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Extract the course ID from the request and validate it.
        String courseIdParam = request.getParameter("courseId");
        if (courseIdParam == null || courseIdParam.isEmpty()) {
            response.sendRedirect("studentDashboard.jsp");
            return;
        }
// Parse the course ID and fetch the assessments for the course and user.
        int courseId;
        try {
            courseId = Integer.parseInt(courseIdParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("studentDashboard.jsp");
            return;
        }

        int userId = currentUser.getId();
        List<Assessment> assessments = assessmentDAO.getAssessmentsByStudentIdAndCourseId(userId, courseId);
// Set attributes for the JSP page and forward the request.
        request.setAttribute("assessments", assessments);
        request.setAttribute("courseId", courseId); // In case you need to display the course name in the JSP.
        request.getRequestDispatcher("/assessmentDetails.jsp").forward(request, response);
    }

}

