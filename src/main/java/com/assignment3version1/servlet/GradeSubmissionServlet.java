package com.assignment3version1.servlet;

/**
 * Servlet for handling the submission of grades by teachers. This includes receiving grades for quizzes,
 * assignments, and final exams, and updating the database with these grades for a specific student in a specific course.
 *
 * @author jiahong lin
 * @date Created in 30/03/2024 00:19
 * @modified By jiahong lin in 30/03/2024 00:19
 */

import com.assignment3version1.model.User;
import com.assignment3version1.dao.AssessmentDAO;
import com.assignment3version1.model.Assessment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/GradeSubmissionServlet")
public class GradeSubmissionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * Handles the HTTP POST request. This method processes the grade submission form data,
     * creates an Assessment object with the submitted grades, and attempts to update the database
     * with the new grades. On success or failure, it redirects to the ViewStudentsServlet while passing along
     * a message indicating the outcome of the grade submission process.
     *
     * @param request HttpServletRequest object that contains the request the client has made of the servlet.
     * @param response HttpServletResponse object that contains the response the servlet sends to the client.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs during servlet processing.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null || !"teacher".equals(currentUser.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Extracting form data
        int userId = Integer.parseInt(request.getParameter("userId"));
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        float quizMarks = Float.parseFloat(request.getParameter("quizMarks"));
        float assignmentMarks = Float.parseFloat(request.getParameter("assignmentMarks"));
        float examMarks = Float.parseFloat(request.getParameter("examMarks"));

        // Submit grades to the database
        Assessment assessment = new Assessment();
        assessment.setUserid(userId);
        assessment.setCourseId(courseId);
        assessment.setQuizMarks(quizMarks);
        assessment.setAssignmentMarks(assignmentMarks);
        assessment.setExamMarks(examMarks);

        // Use DAO to update or insert grades
        AssessmentDAO assessmentDAO = new AssessmentDAO();
        boolean success = assessmentDAO.updateAssessment(assessment);

        // Feedback to the user based on the success of the grade submission
        if(success) {
            session.setAttribute("message", "Grades submitted successfully.");
        } else {
            session.setAttribute("error", "Failed to submit grades.");
        }

        // Redirect to the student view servlet with courseId to refresh the list of students
        response.sendRedirect("ViewStudentsServlet?courseId=" + courseId);
    }
}

