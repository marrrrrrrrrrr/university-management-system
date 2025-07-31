package model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

public class Course {
//    private String courseId;
//    private String courseName;
//    private Lecturer lecturer;
//    private Set<Student> enrolledStudents = new HashSet<>();
//    private LocalDate startDate;
//    private LocalDate endDate;
//    private Set<WeekDay> weekDays;
    private String courseId;
    private String courseName;
    private Lecturer lecturer; // Will be null at first
    private Set<Student> enrolledStudents = new HashSet<>(); // Initialize it here!
    private LocalDate startDate;
    private LocalDate endDate;
    private Set<WeekDay> weekDays;

    // THIS IS THE NEW, CORRECTED CONSTRUCTOR
    public Course(String courseId, String courseName, LocalDate startDate, LocalDate endDate, Set<WeekDay> weekDays) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.weekDays = weekDays;
        // Notice we no longer require lecturer or students.
        // The lecturer will be null and enrolledStudents will be an empty set.
    }

//    public Course(String courseId, String courseName, Lecturer lecturer, Set<Student> enrolledStudents, LocalDate startDate, LocalDate endDate, Set<WeekDay> weekDays) {
//        this.courseId = courseId;
//        this.courseName = courseName;
////        this.lecturer = lecturer;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.weekDays = weekDays;
//    }

    public String getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public Lecturer getLecturer() { return lecturer; }
    public Set<Student> getEnrolledStudents() { return enrolledStudents; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public Set<WeekDay> getWeekDays() { return weekDays; }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }
    public void enrollStudent(Student student) {
        this.enrolledStudents.add(student);
    }
    public void removeStudent(Student student) {
        this.enrolledStudents.remove(student);
    }

    @Override
    public String toString() {
        String lecturerName = (lecturer != null) ? lecturer.getFirstName() + " " + lecturer.getLastName() : "Not Assigned";
        return String.format("Course ID: %s, Name: %s, Lecturer: %s, Students: %d",
                courseId, courseName, lecturerName, enrolledStudents.size());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }
}
