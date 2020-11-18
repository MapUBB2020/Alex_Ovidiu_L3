package ro.alexpugna.university.ui;

import ro.alexpugna.university.model.Course;
import ro.alexpugna.university.service.UniversityService;

import java.util.List;
import java.util.Scanner;

public class Console {
    private UniversityService service;

    public Console(UniversityService service) {
        this.service = service;
    }

    private void printMenuOptions() {
        System.out.println("1. Register a student to a course");
        System.out.println("2. Show courses and number of places");
        System.out.println("3. Show all students enrolled to a course");
        System.out.println("4. Show all courses");
        System.out.println("5. Update number of credits of a course");
        System.out.println("6. Delete a course");
        System.out.println("7. Show all students");
        System.out.println("8. Exit");
    }

    private void showAllCourses() {
        service.getAllCourses().forEach(System.out::println);
    }

    private void showAllStudents() {
        service.getAllStudents().forEach(System.out::println);
    }

    private void register(Scanner scanner) {
        System.out.println("Enter the id of the course and id of the student");
        long courseId = scanner.nextLong();
        long studentId = scanner.nextLong();
        boolean outcome = service.register(courseId, studentId);
        if (outcome) {
            System.out.println("Register successful!");
        } else {
            System.out.println("Register failed!");
        }
    }

    private void showCoursesAndPlaces() {
        List<Course> courses = service.getAllCourses();
        courses.forEach(course -> {
            int freePlaces = course.getMaxEnrollment() - course.getStudentsEnrolled().size();
            System.out.printf("Name: %s Total places: %d Free places %d%n", course.getName(), course.getMaxEnrollment(), freePlaces);
        });
    }

    private void showStudentsEnrolledToCourse(Scanner scanner) {
        System.out.println("Enter the course id");
        long courseId = scanner.nextLong();
        service.retrieveStudentsEnrolledForACourse(courseId).forEach(System.out::println);
    }

    private void updateCreditsForCourse(Scanner scanner) {
        System.out.println("Enter the course id and the new number of credits");
        long courseId = scanner.nextLong();
        int newCredits = scanner.nextInt();
        if (service.updateCourseCredits(courseId, newCredits)) {
            System.out.println("Update successful!");
        } else {
            System.out.println("Update failed!");
        }
    }

    private void deleteCourse(Scanner scanner) {
        System.out.println("Enter the course id");
        long courseId = scanner.nextLong();
        if (service.deleteCourse(courseId)) {
            System.out.println("Delete successful!");
        } else {
            System.out.println("Delete failed!");
        }
    }

    public void run() {
        int choice = 0;

        Scanner scanner = new Scanner(System.in);
        while (choice != 8) {
            printMenuOptions();
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    register(scanner);
                    break;
                case 2:
                    showCoursesAndPlaces();
                    break;
                case 3:
                    showStudentsEnrolledToCourse(scanner);
                    break;
                case 4:
                    showAllCourses();
                    break;
                case 5:
                    updateCreditsForCourse(scanner);
                    break;
                case 6:
                    deleteCourse(scanner);
                    break;
                case 7:
                    showAllStudents();
                    break;
            }
        }
        scanner.close();
    }
}
