package service;

import model.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface UniversityManagement {
    // სტუდენტების მართვა
    void addStudent(String id, String firstName, String lastName, String email);
    void addStudent(Student student);
    void addStudentFromJson(String json);
    Student getStudentById(String id);
    Student getStudentByName(String name);
    void updateStudent(String id, String newFirstName, String newLastName, String newEmail);
    void deleteStudent(String id);
    void enrollStudentInCourse(String studentId, String courseId);
    void removeStudentFromCourse(String studentId, String courseId);

    // ლექტორების მართვა
    void addLecturer(String id, String firstName, String lastName, String email);
    Lecturer getLecturerById(String id);
    Lecturer getLecturerByName(String name);
    void deleteLecturer(String id);
    void assignLecturerToCourse(String lecturerId, String courseId);

    // კურსების მართვა
    void addCourse(String courseId, String courseName, LocalDate startDate, LocalDate endDate, Set<WeekDay> weekDays);
    Course getCourseByCode(String code);
    Course getCourseByName(String name);
    void deleteCourse(String courseId);
    void printCourseSchedules();

    // რეპორტინგი
    List<Student> getAllStudents();
    List<Lecturer> getAllLecturers();
    List<Course> getAllCourses();
    Set<Course> getCoursesForStudent(String studentId);
    Set<Course> getCoursesForLecturer(String lecturerId);
}