package model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Student extends Person {
    private Set<Course> enrolledCourses = new HashSet<>();

    public Student() {
        super();
    }

    public Student(String id, String firstName, String lastName, String email) {
        super(id, firstName, lastName, email);
    }

    public Set<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void enrollInCourse(Course course) {
        this.enrolledCourses.add(course);
    }

    public void unenrollFromCourse(Course course) {
        this.enrolledCourses.remove(course);
    }

    @Override
    public String toString() {
        String courseNames = enrolledCourses.stream()
                .map(Course::getCourseName)
                .collect(Collectors.joining(", "));
        return String.format("Student -> %s, Enrolled Courses: [%s]", super.toString(), courseNames);
    }
}