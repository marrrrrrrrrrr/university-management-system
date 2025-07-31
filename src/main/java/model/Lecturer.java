package model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Lecturer extends Person {
    private Set<Course> assignedCourses = new HashSet<>();

    public Lecturer(String id, String firstName, String lastName, String email) {
        super(id, firstName, lastName, email);
    }

    public void assignCourse(Course course) {
        this.assignedCourses.add(course);
    }

    public Set<Course> getAssignedCourses() {
        return assignedCourses;
    }

    @Override
    public String toString() {
        String courseNames = assignedCourses.stream()
                .map(Course::getCourseName)
                .collect(Collectors.joining(", "));
        return String.format("Lecturer -> %s, Assigned Courses: [%s]", super.toString(), courseNames);
    }
}