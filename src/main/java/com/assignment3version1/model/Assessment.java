package com.assignment3version1.model;

import java.io.Serializable;

/**
 * Represents an assessment record for a student in a course.
 * This class holds details about the student's marks in quizzes, assignments, and the final exam.
 * It implements Serializable to allow instances of this class to be easily passed between layers
 * and potentially stored in sessions or transferred over the network.
 *
 * @author jiahong lin
 * @date Created in 24/03/2024 01:49
 * @modified By jiahong lin in 24/03/2024 01:49
 */
public class Assessment implements Serializable {
    private int id; // Unique identifier for the assessment record
    private int Userid; // ID of the user (student) to whom this assessment belongs
    private int courseId; // ID of the course to which this assessment is related
    private float quizMarks; // Marks obtained in the quiz
    private float assignmentMarks; // Marks obtained in the assignment
    private float examMarks; // Marks obtained in the final exam

    /**
     * Constructs a new Assessment with no specified details (no-argument constructor).
     */
    public Assessment() {
    }

    // Getters and setters for accessing and updating property values

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return Userid;
    }

    public void setUserid(int userid) {
        Userid = userid;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public float getQuizMarks() {
        return quizMarks;
    }

    public void setQuizMarks(float quizMarks) {
        this.quizMarks = quizMarks;
    }

    public float getAssignmentMarks() {
        return assignmentMarks;
    }

    public void setAssignmentMarks(float assignmentMarks) {
        this.assignmentMarks = assignmentMarks;
    }

    public float getExamMarks() {
        return examMarks;
    }

    public void setExamMarks(float examMarks) {
        this.examMarks = examMarks;
    }
}
