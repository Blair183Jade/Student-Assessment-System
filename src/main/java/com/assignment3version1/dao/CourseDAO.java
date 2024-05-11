package com.assignment3version1.dao;

/**
 * * Facilitates all data access operations related to courses within the system.
 * * This includes operations such as retrieving all courses, saving new courses,
 * * fetching courses that a student is not enrolled in, and managing course assignments for teachers.
 *
 * @author jiahong lin
 * @date Created in 29/03/2024 18:13
 * @modified By  jiahong lin in 29/03/2024 18:13
 */

import com.assignment3version1.model.Course;
import com.assignment3version1.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    /**
     * Retrieves all courses from the database.
     *
     * @return A list of Course objects.
     */

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM courses";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Course course = new Course();
                course.setId(resultSet.getInt("id"));
                course.setCourseName(resultSet.getString("course_name"));
                course.setSemester(resultSet.getString("semester"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Proper error handling should be implemented
        }
        return courses;
    }

    /**
     * Saves a new course to the database.
     *
     * @param course The course object to be saved.
     */
    public void saveCourse(Course course) {
        String query = "INSERT INTO courses (course_name, semester) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.setString(2, course.getSemester());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Proper error handling should be implemented
        }
    }

    /**
     * Retrieves courses that a specific student is not currently enrolled in.
     *
     * @param studentId The ID of the student.
     * @return A list of Course objects the student is not enrolled in.
     */
    // Get all courses that a particular student is NOT enrolled in
    public List<Course> getAvailableCoursesForStudent(int studentId) {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM courses WHERE id NOT IN " +
                "(SELECT course_id FROM user_courses WHERE user_id = ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

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
            e.printStackTrace(); // Proper error handling should be implemented
        }
        return courses;
    }

    /**
     * Fetches a specific course by its ID.
     *
     * @param courseId The ID of the course to retrieve.
     * @return A Course object or null if the course does not exist.
     */
    public Course getCourseById(int courseId) {
        Course course = null;
        String query = "SELECT * FROM courses WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                course = new Course();
                course.setId(resultSet.getInt("id"));
                course.setCourseName(resultSet.getString("course_name"));
                course.setSemester(resultSet.getString("semester"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Proper error handling should be implemented
        }
        return course;
    }

    /**
     * Deletes a course from the database by its ID.
     *
     * @param courseId The ID of the course to be deleted.
     * @return true if the deletion was successful, false otherwise.
     */
    // Potentially a delete course method if needed
    public boolean deleteCourse(int courseId) {
        String query = "DELETE FROM courses WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, courseId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Proper error handling should be implemented
            return false;
        }
    }

    /**
     * Retrieves all courses assigned to a specific teacher.
     *
     * @param teacherId The ID of the teacher.
     * @return A list of Course objects assigned to the teacher.
     */
    public List<Course> getAssignedCoursesByTeacherId(int teacherId) {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT c.id, c.course_name, c.semester FROM courses c JOIN user_courses uc ON c.id = uc.course_id WHERE uc.user_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, teacherId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Course course = new Course();
                course.setId(resultSet.getInt("id"));
                course.setCourseName(resultSet.getString("course_name"));
                course.setSemester(resultSet.getString("semester"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    /**
     * Registers a teacher for a specific course by creating an association in the database.
     *
     * @param teacherId The ID of the teacher.
     * @param courseId The ID of the course.
     * @return true if the registration was successful, false otherwise.
     */
    public boolean registerTeacherForCourse(int teacherId, int courseId) {
        String query = "INSERT INTO user_courses (user_id, course_id) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, teacherId);
            preparedStatement.setInt(2, courseId);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0; // Return true if the insertion was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error during insertion
        }
    }


}

