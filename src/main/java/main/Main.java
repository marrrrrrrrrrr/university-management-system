package main;

import exception.*;
import model.Course;
import model.Student;
import service.University;
import service.UniversityManagement;
import util.DataLoader;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        UniversityManagement university = new University();

        System.out.println("= 1. Loading data from university_data.txt =");
        DataLoader.loadDataFromFile("university_data.txt", university);

        System.out.println("\n= 2. Reporting current state =");
        printAll(university);

        System.out.println("\n= 3. Demonstration of student management =");
        String newStudentJson = """
                {
                  "id": "S006",
                  "firstName": "Tato",
                  "lastName": "Oniani",
                  "email": "Tato@example.com"
                }
                """;
        university.addStudentFromJson(newStudentJson);

        try {
            Student tato = university.getStudentById("S006");
            System.out.println("Found student: " + tato);
            university.updateStudent("S006", "Tato", "Oniani-Updated", "Tato@example.com");
        } catch (StudentNotFoundException e) {
            System.err.println(e.getMessage());
        }

        try {
            university.enrollStudentInCourse("S006", "C001");
            university.enrollStudentInCourse("S001", "C002");
        } catch (StudentNotFoundException | CourseNotFoundException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("\nTrying to enroll Nika Kapanadze (S001) in OOP (C002) again");
        try {
            university.enrollStudentInCourse("S001", "C002");
        } catch (StudentEnrollmentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nRemove student S003 from course...");
        university.removeStudentFromCourse("S003", "C002");

        try {
            System.out.println("\nDeleting student S003...");
            university.deleteStudent("S003");
        } catch (StudentNotFoundException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("\n= 4. Demonstration of Lecturer and Course Reports =");
        try {
            System.out.println("\nCourses for Tako Beridze (S002):");
            Set<Course> takoCourses = university.getCoursesForStudent("S002");
            takoCourses.forEach(System.out::println);
        } catch (StudentNotFoundException e) {
            System.err.println(e.getMessage());
        }

        try {
            System.out.println("\nCourses for Irakli Gogoladze (L001):");
            Set<Course> irakliCourses = university.getCoursesForLecturer("L001");
            irakliCourses.forEach(System.out::println);
        } catch (LecturerNotFoundException e) {
            System.err.println(e.getMessage());
        }

        try {
            System.out.println("\nDeleting Lecturer L002...");
            university.deleteLecturer("L002");
        } catch (LecturerNotFoundException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("\n= 5. Course Schedule =");
        university.printCourseSchedules();

        try {
            System.out.println("\nFinding course by name: 'Machine Learning'");
            Course mlCourse = university.getCourseByName("Machine Learning");
            System.out.println("Found: " + mlCourse);

            university.deleteCourse(mlCourse.getCourseId());
        } catch (CourseNotFoundException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("\n= 6. Final State of the System =");
        printAll(university);

        System.out.println("\n= 7. Demonstration of Error Handling =");
        try {
            university.getStudentByName("Mariam Pipia");
        } catch (StudentNotFoundException e) {
            System.err.println("Caught expected exception: " + e.getMessage());
        }
        try {
            university.getLecturerByName("David Mania");
        } catch (LecturerNotFoundException e) {
            System.err.println("Caught expected exception: " + e.getMessage());
        }
        try {
            university.getCourseByCode("C999");
        } catch (CourseNotFoundException e) {
            System.err.println("Caught expected exception: " + e.getMessage());
        }
    }

    private static void printAll(UniversityManagement university) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n= All Students =\n");
        university.getAllStudents().forEach(s -> sb.append(s).append("\n"));

        sb.append("\n= All Lecturers =\n");
        university.getAllLecturers().forEach(l -> sb.append(l).append("\n"));

        sb.append("\n= All Courses =\n");
        university.getAllCourses().forEach(c -> sb.append(c).append("\n"));

        System.out.println(sb.toString());
    }
}