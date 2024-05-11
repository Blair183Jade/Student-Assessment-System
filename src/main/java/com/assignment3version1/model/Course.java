package com.assignment3version1.model;

import java.io.Serializable;

/**
 * Represents a course offered in the system. Includes details such as the course name and the semester it's offered in.
 * Implements Serializable to facilitate easy transfer of Course objects across different parts of the application or for session management.
 *
 * @author jiahong lin
 * @date Created in 24/03/2024 01:50
 * @modified By jiahong lin in 24/03/2024 01:50
 */
public class Course implements Serializable {
    private int id; // Unique identifier for the course
    private String courseName; // Name of the course
    private String semester; // The semester during which the course is offered

    /**
     * Constructs a new Course with the specified name and semester.
     *
     * @param courseName The name of the course.
     * @param semester   The semester the course is offered in.
     */
    public Course(String courseName, String semester) {
        this.courseName = courseName;
        this.semester = semester;
    }

    /**
     * Default no-argument constructor for creating a Course instance without setting initial values.
     */
    public Course() {
    }

    // Getters and setters for each field

    /**
     * Gets the unique identifier of the course.
     *
     * @return The ID of the course.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the course.
     *
     * @param id The ID to set for the course.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the course.
     *
     * @return The name of the course.
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets the name of the course.
     *
     * @param courseName The name to set for the course.
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Gets the semester during which the course is offered.
     *
     * @return The semester of the course.
     */
    public String getSemester() {
        return semester;
    }

    /**
     * Sets the semester during which the course is offered.
     *
     * @param semester The semester to set for the course.
     */
    public void setSemester(String semester) {
        this.semester = semester;
    }
}
