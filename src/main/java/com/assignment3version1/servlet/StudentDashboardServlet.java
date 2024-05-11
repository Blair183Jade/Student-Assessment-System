package com.assignment3version1.servlet;

import com.assignment3version1.dao.CourseDAO;
import com.assignment3version1.dao.EnrollmentDAO;
import com.assignment3version1.dao.AssessmentDAO;
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
import java.util.stream.Collectors;

/**
 * Servlet that handles the student dashboard functionality, including displaying enrolled courses,
 * available courses for enrollment, and processing course enrollment requests.
 * It interacts with the CourseDAO and EnrollmentDAO to fetch and update course enrollment information.
 *
 * @author jiahong lin
 * @date Created in 29/03/2024 21:53
 * @modified By jiahong lin in 29/03/2024 21:53
 */
@WebServlet("/StudentDashboard")
public class StudentDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CourseDAO courseDAO;
    private EnrollmentDAO enrollmentDAO;

    /**
     * Initializes DAOs necessary for the servlet's operation. This includes DAOs for courses,
     * enrollment, and assessments.
     */
    public StudentDashboardServlet() {
        this.courseDAO = new CourseDAO();
        this.enrollmentDAO = new EnrollmentDAO();
    }

    /**
     * Handles the GET request by fetching and displaying courses the student is enrolled in
     * and all available courses if the student isn't enrolled in any. It ensures only authenticated
     * students can access their dashboard.
     *
     * @param request HttpServletRequest object that contains the request the client has made of the servlet.
     * @param response HttpServletResponse object that contains the response the servlet sends to the client.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs during servlet processing.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        // Redirect to login page if user is not authenticated or not a student
        if (currentUser == null || !currentUser.getRole().equals("student")) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = currentUser.getId();
        // Fetch the courses the student is enrolled in
        List<Course> enrolledCourses = enrollmentDAO.getEnrolledCoursesByStudentId(userId);
        request.setAttribute("enrolledCourses", enrolledCourses);

        // Fetch all available courses and remove the ones student is already enrolled in
        List<Course> allCourses = courseDAO.getAllCourses();
        List<Course> availableCourses = allCourses.stream()
                .filter(course -> enrolledCourses.stream()
                        .noneMatch(enrolledCourse -> enrolledCourse.getId() == course.getId()))
                .collect(Collectors.toList());
        request.setAttribute("availableCourses", availableCourses);

        request.getRequestDispatcher("/studentDashboard.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for course enrollment. It enrolls the current user (student) into a selected
     * course and provides feedback on the success or failure of enrollment.
     *
     * @param request HttpServletRequest object that contains the request the client has made of the servlet.
     * @param response HttpServletResponse object that contains the response the servlet sends to the client.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs during servlet processing.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        // Redirect to login page if user is not authenticated or not a student
        if (currentUser == null || !currentUser.getRole().equals("student")) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Enrollment logic
        String courseIdParam = request.getParameter("courseId");
        if (courseIdParam == null || courseIdParam.isEmpty()) {
            request.setAttribute("enrollmentError", "No course selected.");
            doGet(request, response);
            return;
        }

        int courseId = Integer.parseInt(courseIdParam);
        boolean enrollmentSuccess = enrollmentDAO.enrollStudentInCourse(currentUser.getId(), courseId);

        // Feedback based on enrollment success
        if (enrollmentSuccess) {
            request.setAttribute("enrollmentSuccess", "Successfully enrolled in the course.");
        } else {
            request.setAttribute("enrollmentError", "Enrollment failed. Possibly already enrolled.");
        }

        doGet(request, response);
    }
}
