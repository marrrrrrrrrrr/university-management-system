package service;

import data.Database;
import exception.CourseNotFoundException;
import exception.LecturerNotFoundException;
import exception.StudentEnrollmentException;
import exception.StudentNotFoundException;
import model.Course;
import model.Lecturer;
import model.Student;
import model.WeekDay;
import util.JsonUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class University implements UniversityManagement {

    // სტუდენტების მართვა
    @Override
    public void addStudent(String id, String firstName, String lastName, String email) {
        addStudent(new Student(id, firstName, lastName, email));
    }

    @Override
    public void addStudent(Student student) {
        Database.getStudents().put(student.getId(), student);
        System.out.println("Student added: " + student.getFirstName() + " " + student.getLastName());
    }

    @Override
    public void addStudentFromJson(String json) {
        Student student = JsonUtil.studentFromJson(json);

        if (student != null) {
            if (student.getId() == null || student.getFirstName() == null || student.getLastName() == null) {
                System.err.println("Missing required student fields (id, firstName, lastName)");
                return;
            }
            addStudent(student);
        }
    }

    @Override
    public Student getStudentById(String id) {
        Student student = Database.getStudents().get(id);
        if (student == null) {
            throw new StudentNotFoundException("Student not found with ID: " + id);
        }
        return student;
    }

    @Override
    public Student getStudentByName(String name) {
        return Database.getStudents().values().stream()
                .filter(s -> (s.getFirstName() + " " + s.getLastName()).equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new StudentNotFoundException("Student not found with name: " + name));
    }

    @Override
    public void updateStudent(String id, String newFirstName, String newLastName, String newEmail) {
        Student student = getStudentById(id);
        student.setFirstName(newFirstName);
        student.setLastName(newLastName);
        student.setEmail(newEmail);
        System.out.println("Student updated: " + student);
    }

    @Override
    public void deleteStudent(String id) {
        Student student = getStudentById(id);
        student.getEnrolledCourses().forEach(course -> course.removeStudent(student));
        Database.getStudents().remove(id);
        System.out.println("Student deleted with ID: " + id);
    }

    @Override
    public void enrollStudentInCourse(String studentId, String courseId) {
        Student student = getStudentById(studentId);
        Course course = getCourseByCode(courseId);

        if (course.getEnrolledStudents().contains(student)) {
            throw new StudentEnrollmentException(
                    "Enrollment failed: Student '" + student.getFirstName() + " " + student.getLastName() +
                            "' is already enrolled in the course '" + course.getCourseName() + "'."
            );
        }

        student.enrollInCourse(course);
        course.enrollStudent(student);
        System.out.printf("Success: Student '%s' enrolled in course '%s'.\n", student.getFirstName(), course.getCourseName());
    }

    @Override
    public void removeStudentFromCourse(String studentId, String courseId) {
        Student student = getStudentById(studentId);
        Course course = getCourseByCode(courseId);
        student.unenrollFromCourse(course);
        course.removeStudent(student);
        System.out.printf("Student '%s' removed from course '%s'.\n", student.getFirstName(), course.getCourseName());
    }

    // ლექტორების მართვა
    @Override
    public void addLecturer(String id, String firstName, String lastName, String email) {
        Lecturer lecturer = new Lecturer(id, firstName, lastName, email);
        Database.getLecturers().put(lecturer.getId(), lecturer);
        System.out.println("Lecturer added: " + lecturer.getFirstName() + " " + lecturer.getLastName());
    }

    @Override
    public Lecturer getLecturerById(String id) {
        Lecturer lecturer = Database.getLecturers().get(id);
        if (lecturer == null) {
            throw new LecturerNotFoundException("Lecturer not found with ID: " + id);
        }
        return lecturer;
    }

    @Override
    public Lecturer getLecturerByName(String name) {
        return Database.getLecturers().values().stream()
                .filter(l -> (l.getFirstName() + " " + l.getLastName()).equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new LecturerNotFoundException("Lecturer not found with name: " + name));
    }

    @Override
    public void deleteLecturer(String id) {
        if (Database.getLecturers().remove(id) == null) {
            throw new LecturerNotFoundException("Lecturer not found with ID: " + id);
        }
        Database.getCourses().values().stream()
                .filter(c -> c.getLecturer() != null && c.getLecturer().getId().equals(id))
                .forEach(c -> c.setLecturer(null));
        System.out.println("Lecturer deleted with ID: " + id);
    }

    @Override
    public void assignLecturerToCourse(String lecturerId, String courseId) {
        Lecturer lecturer = getLecturerById(lecturerId);
        Course course = getCourseByCode(courseId);
        course.setLecturer(lecturer);
        lecturer.assignCourse(course);
        System.out.printf("Lecturer '%s' assigned to course '%s'.\n", lecturer.getFirstName(), course.getCourseName());
    }

    // კურსების მართვა
    @Override
    public void addCourse(String courseId, String courseName, LocalDate startDate, LocalDate endDate, Set<WeekDay> weekDays) {
        Course course = new Course(courseId, courseName, startDate, endDate, weekDays);
        Database.getCourses().put(course.getCourseId(), course);
        System.out.println("Course added: " + course.getCourseName());
    }

    @Override
    public Course getCourseByCode(String code) {
        Course course = Database.getCourses().get(code);
        if (course == null) {
            throw new CourseNotFoundException("Course not found with code: " + code);
        }
        return course;
    }

    @Override
    public Course getCourseByName(String name) {
        return Database.getCourses().values().stream()
                .filter(c -> c.getCourseName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException("Course not found with name: " + name));
    }

    @Override
    public void deleteCourse(String courseId) {
        Course course = getCourseByCode(courseId);
        course.getEnrolledStudents().forEach(student -> student.unenrollFromCourse(course));
        if (course.getLecturer() != null) {
            course.getLecturer().getAssignedCourses().remove(course);
        }
        Database.getCourses().remove(courseId);
        System.out.println("Course deleted with ID: " + courseId);
    }

    // რეპორტინგი
    @Override
    public List<Student> getAllStudents() {
        return List.copyOf(Database.getStudents().values());
    }

    @Override
    public List<Lecturer> getAllLecturers() {
        return List.copyOf(Database.getLecturers().values());
    }

    @Override
    public List<Course> getAllCourses() {
        return List.copyOf(Database.getCourses().values());
    }

    @Override
    public Set<Course> getCoursesForStudent(String studentId) {
        return getStudentById(studentId).getEnrolledCourses();
    }

    @Override
    public Set<Course> getCoursesForLecturer(String lecturerId) {
        return getLecturerById(lecturerId).getAssignedCourses();
    }

    @Override
    public void printCourseSchedules() {
        StringBuilder sb = new StringBuilder();

        Database.getCourses().values().forEach(course -> {
            sb.append("Course: ").append(course.getCourseName()).append("\n");
            sb.append("Duration: ").append(course.getStartDate()).append(" to ").append(course.getEndDate()).append("\n");

            sb.append("Days: ");
            course.getWeekDays().forEach(day -> sb.append(day.name()).append(" "));
            sb.append("\n");

            sb.append("Enrolled Students: ").append(course.getEnrolledStudents().size()).append("\n");
            sb.append("----------------------------------\n");
        });
    }
}