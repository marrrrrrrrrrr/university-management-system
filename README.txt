  University Management System
==================================

This project is a simple university management system implemented in Java. It allows an administrator to manage students, lecturers, and courses.

Features
---
- Student Management: Add (from JSON), find, update, delete, and enroll/unenroll students in courses.
- Lecturer Management: Add, find, delete, and assign lecturers to courses.
- Course Management: Add, find, delete, and manage course associations.
- Custom Reports: Generate a clean, formatted schedule summary for all courses.
- Smart Error Handling: Prevents invalid operations like duplicate student enrollments.

Technical Details
---
- Design: Object-Oriented
- Key Libraries: Jackson for JSON parsing
- Build Tool: Maven
- Data: Initial data is loaded from `university_data.txt` at startup.

How to Run
---
1.  Ensure you have Java and Maven installed.
2.  Open the project in your IDE.
3.  The IDE will detect the `pom.xml` and automatically download the required dependencies (Jackson).
4.  Run the `main.Main` class to start the application and see a full demonstration of its features.