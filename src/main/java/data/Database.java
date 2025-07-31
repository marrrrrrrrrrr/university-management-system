package data;

import model.Course;
import model.Lecturer;
import model.Student;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private static final Map<String, Student> students = new HashMap<>();
    private static final Map<String, Lecturer> lecturers = new HashMap<>();
    private static final Map<String, Course> courses = new HashMap<>();

    private Database() {
    }

    public static Map<String, Student> getStudents() {
        return students;
    }

    public static Map<String, Lecturer> getLecturers() {
        return lecturers;
    }

    public static Map<String, Course> getCourses() {
        return courses;
    }
}