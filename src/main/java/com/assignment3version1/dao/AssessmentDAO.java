package com.assignment3version1.dao;

/**
 * @author jiahong lin
 * @date Created in 29/03/2024 21:54
 * @modified By  jiahong lin in 29/03/2024 21:54
 * @description /**
 * * Handles database operations related to assessments.
 * * This includes creating, updating, and retrieving assessment records for students.
 * * Each record can include quiz marks, assignment marks, and final exam marks.
 */

import com.assignment3version1.model.Assessment;
import com.assignment3version1.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssessmentDAO {
    /**
     * Retrieves a list of assessment records for a specific student and course.
     *
     * @param studentId The ID of the student.
     * @param courseId The ID of the course.
     * @return A list of Assessment objects for the specified student and course.
     */

    public List<Assessment> getAssessmentsByStudentIdAndCourseId(int studentId, int courseId) {
        List<Assessment> assessments = new ArrayList<>();
        String sql = "SELECT * FROM assessments WHERE user_id = ? AND course_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Assessment assessment = new Assessment();
                assessment.setId(resultSet.getInt("id"));
                assessment.setUserid(resultSet.getInt("user_id"));
                assessment.setCourseId(resultSet.getInt("course_id"));
                assessment.setQuizMarks(resultSet.getFloat("quiz_marks"));
                assessment.setAssignmentMarks(resultSet.getFloat("assignment_marks"));
                assessment.setExamMarks(resultSet.getFloat("exam_marks"));
                assessments.add(assessment);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Consider proper exception handling
        }

        return assessments;
    }

    // Method to update or insert assessment marks for a student in a course

    /**
     * Updates an existing assessment record or inserts a new one if it does not exist.
     *
     * @param assessment The assessment object containing the new data.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean updateAssessment(Assessment assessment) {
        // Check if an assessment record already exists
        String checkQuery = "SELECT id FROM assessments WHERE user_id = ? AND course_id = ?";
        String updateQuery = "UPDATE assessments SET quiz_marks = ?, assignment_marks = ?, exam_marks = ? WHERE id = ?";
        String insertQuery = "INSERT INTO assessments (user_id, course_id, quiz_marks, assignment_marks, exam_marks) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {

            checkStmt.setInt(1, assessment.getUserid());
            checkStmt.setInt(2, assessment.getCourseId());
            ResultSet resultSet = checkStmt.executeQuery();

            if (resultSet.next()) {
                // Record exists, perform an update
                try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                    updateStmt.setFloat(1, assessment.getQuizMarks());
                    updateStmt.setFloat(2, assessment.getAssignmentMarks());
                    updateStmt.setFloat(3, assessment.getExamMarks());
                    updateStmt.setInt(4, resultSet.getInt("id"));
                    updateStmt.executeUpdate();
                }
            } else {
                // Record does not exist, perform an insert
                try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                    insertStmt.setInt(1, assessment.getUserid());
                    insertStmt.setInt(2, assessment.getCourseId());
                    insertStmt.setFloat(3, assessment.getQuizMarks());
                    insertStmt.setFloat(4, assessment.getAssignmentMarks());
                    insertStmt.setFloat(5, assessment.getExamMarks());
                    insertStmt.executeUpdate();
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

