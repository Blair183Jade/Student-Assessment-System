package com.assignment3version1.dao;

/**
 * @author jiahong lin
 * @date Created in 29/03/2024 21:55
 * @modified By  jiahong lin in 29/03/2024 21:55
 * @description AddDescriptionHere
 */

import com.assignment3version1.model.Course;
import com.assignment3version1.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.assignment3version1.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Facilitates the management of course enrollments within the system.
 * Supports enrolling students in courses, retrieving courses a student is enrolled in,
 * and fetching all students enrolled in a specific course.
 *
 * @author jiahong lin
 * @date Created in 29/03/2024 21:55
 * @modified By jiahong lin in 29/03/2024 21:55
 */
public class EnrollmentDAO {

    /**
     * Retrieves all courses that a specified student is enrolled in.
     *
     * @param studentId The ID of the student.
     * @return A list of Course objects representing the courses the student is enrolled in.
     */
    public List<Course> getEnrolledCoursesByStudentId(int studentId) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.id, c.course_name, c.semester FROM courses c " +
                "JOIN user_courses uc ON c.id = uc.course_id " +
                "WHERE uc.user_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Course course = new Course();
                course.setId(resultSet.getInt("id"));
                course.setCourseName(resultSet.getString("course_name"));
                course.setSemester(resultSet.getString("semester"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Proper exception handling should be implemented
        }
        return courses;
    }

    /**
     * Enrolls a student in a specified course by creating a record in the user_courses table.
     *
     * @param studentId The ID of the student to enroll.
     * @param courseId  The ID of the course to enroll the student in.
     * @return true if the enrollment was successful, false otherwise.
     */
    public boolean enrollStudentInCourse(int studentId, int courseId) {
        String sql = "INSERT INTO user_courses (user_id, course_id) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Proper exception handling should be implemented
            return false;
        }
    }

    // Fetches all students enrolled in a specific course

    /**
     * Fetches a list of all students enrolled in a specified course.
     *
     * @param courseId The ID of the course.
     * @return A list of User objects representing the students enrolled in the course.
     */

    public List<User> getStudentsEnrolledInCourse(int courseId) {
        List<User> students = new ArrayList<>();
        String query = "SELECT u.* FROM users u JOIN user_courses uc ON u.id = uc.user_id WHERE uc.course_id = ? AND u.role = 'student'";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User student = new User();
                student.setId(resultSet.getInt("id"));
                student.setUsername(resultSet.getString("username"));
                student.setPassword(resultSet.getString("password")); // Consider security implications
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setPhone(resultSet.getString("phone"));
                student.setRole(resultSet.getString("role"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}
