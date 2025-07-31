package util;

import model.WeekDay;
import service.UniversityManagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class DataLoader {

    public static void loadDataFromFile(String filePath, UniversityManagement university) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String currentSection = "";

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                if (line.contains("Students")) {
                    currentSection = "Students";
                    continue;
                } else if (line.contains("Lecturers")) {
                    currentSection = "Lecturers";
                    continue;
                } else if (line.contains("Courses")) {
                    currentSection = "Courses";
                    continue;
                } else if (line.contains("Enrollments")) {
                    currentSection = "Enrollments";
                    continue;
                }

                try {
                    processLine(line, currentSection, university);
                } catch (Exception e) {
                    System.err.println("Could not process data at line: '" + line + "'. Reason: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to read the data file. " + e.getMessage());
        }
    }

    private static void processLine(String line, String section, UniversityManagement university) {
        String[] parts = line.split(",");

        switch (section) {
            case "Students":
                String studentId = parts[0].trim();
                String firstName = parts[1].trim();
                String lastName = parts[2].trim();
                String email = parts[3].trim();
                university.addStudent(studentId, firstName, lastName, email);
                break;

            case "Lecturers":
                String lecturerId = parts[0].trim();
                String lecturerFirstName = parts[1].trim();
                String lecturerLastName = parts[2].trim();
                String lecturerEmail = parts[3].trim();
                university.addLecturer(lecturerId, lecturerFirstName, lecturerLastName, lecturerEmail);
                break;

            case "Courses":
                String courseId = parts[0].trim();
                String courseName = parts[1].trim();
                String courseLecturerId = parts[2].trim();
                LocalDate startDate = LocalDate.parse(parts[3].trim());
                LocalDate endDate = LocalDate.parse(parts[4].trim());

                Set<WeekDay> days = new HashSet<>();
                for (int i = 5; i < parts.length; i++) {
                    String day = parts[i].trim().toUpperCase();
                    days.add(WeekDay.valueOf(day));
                }

                university.addCourse(courseId, courseName, startDate, endDate, days);
                university.assignLecturerToCourse(courseLecturerId, courseId);
                break;

            case "Enrollments":
                String courseCode = parts[0].trim();
                String studentCode = parts[1].trim();
                university.enrollStudentInCourse(studentCode, courseCode);
                break;

            default:
                break;
        }
    }
}