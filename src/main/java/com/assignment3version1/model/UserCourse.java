package com.assignment3version1.model;

import java.io.Serializable;

/**
 * Represents the association between a user and a course within the system.
 * This class is typically used to model which users are enrolled in which courses,
 * facilitating many-to-many relationships between users (students/teachers) and courses.
 * Implements Serializable to support session storage and easy transfer across the application.
 *
 * @author jiahong lin
 * @date Created in 24/03/2024 01:51
 * @modified By jiahong lin in 24/03/2024 01:51
 */
public class UserCourse implements Serializable {
    private int id; // Unique identifier for the user-course association
    private int userId; // ID of the user associated with the course
    private int courseId; // ID of the course associated with the user

    /**
     * Constructs a new UserCourse instance without setting initial values.
     */
    public UserCourse() {
    }

    // Accessor and Mutator methods

    /**
     * Gets the unique identifier of the user-course association.
     *
     * @return The ID of the association.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user-course association.
     *
     * @param id The ID to be set for the association.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the ID of the user associated with the course.
     *
     * @return The user's ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user associated with the course.
     *
     * @param userId The user's ID to be set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the ID of the course associated with the user.
     *
     * @return The course's ID.
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * Sets the ID of the course associated with the user.
     *
     * @param courseId The course's ID to be set.
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}



