# University Management System

---

## 📌 Description

A Java-based university management system designed for administrators to efficiently manage students, lecturers, and courses. The system supports core operations like adding, updating, deleting, and associating entities, as well as generating custom reports.

It emphasizes clean architecture and user-friendly error handling to prevent invalid operations such as duplicate enrollments.

---

## Key Features

- **Student Management:** Add students (via JSON), find, update, delete, and enroll/unenroll in courses.  
- **Lecturer Management:** Add, find, update, delete, and assign lecturers to courses.  
- **Course Management:** Add, find, update, delete courses, and manage associations with students and lecturers.  
- **Custom Reports:** Generate formatted schedules and summaries for courses, students, and lecturers.  
- **Robust Error Handling:** Custom exceptions prevent invalid actions and provide informative feedback.

---

## Technologies & Tools

- **Language:** Java  
- **Design:** Object-Oriented Programming (OOP) with abstract classes and interfaces  
- **JSON Parsing:** Jackson library  
- **Build Tool:** Maven  
- **Data Handling:** Collections (List, HashMap, HashSet), Stream API with Lambdas  
- **Error Handling:** Custom exceptions (`StudentNotFoundException`, etc.)  
- **Data Source:** Initial data loaded from `university_data.txt`  

---

## How to Run

1. Ensure you have **Java JDK** and **Maven** installed.  
2. Open the project in your favorite IDE (e.g., IntelliJ IDEA, Eclipse).  
3. The IDE will detect the `pom.xml` and automatically download required dependencies.  
4. Run the `main.Main` class to launch the application and see a full demonstration of its features.
