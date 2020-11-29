package ro.alexpugna.university.ui;

import ro.alexpugna.university.exception.ItemNotFoundException;
import ro.alexpugna.university.exception.ProgramException;
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
        System.out.println("8. Show all students sorted by last name");
        System.out.println("9. Show all courses having the number of credits greater than or equal to a given value");
        System.out.println("10. Exit");
    }

    private void showAllCourses() {
        service.getAllCourses().forEach(System.out::println);
    }

    private void showAllStudents() {
        service.getAllStudents().forEach(System.out::println);
    }

    private void register(Scanner scanner) throws ProgramException {
        System.out.println("Enter the id of the course and id of the student");
        long courseId = scanner.nextLong();
        long studentId = scanner.nextLong();
        service.register(courseId, studentId);
        System.out.println("Register successful!");
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

    private void updateCreditsForCourse(Scanner scanner) throws ItemNotFoundException {
        System.out.println("Enter the course id and the new number of credits");
        long courseId = scanner.nextLong();
        int newCredits = scanner.nextInt();
        service.updateCourseCredits(courseId, newCredits);
        System.out.println("Update successful!");
    }

    private void deleteCourse(Scanner scanner) throws ItemNotFoundException {
        System.out.println("Enter the course id");
        long courseId = scanner.nextLong();
        service.deleteCourse(courseId);
        System.out.println("Delete successful!");
    }

    private void showAllStudentsSortedByLastName() {
        service.getStudentsSortedByLastName().forEach(System.out::println);
    }

    private void filterCoursesByCredits(Scanner scanner) {
        System.out.println("Enter the course credits threshold");
        int threshold = scanner.nextInt();
        service.getCoursesHavingCreditsAtLeast(threshold).forEach(System.out::println);
    }

    public void run() {
        int choice = 0;

        Scanner scanner = new Scanner(System.in);
        while (choice != 10) {
            printMenuOptions();
            choice = scanner.nextInt();
            try {
                switch (choice) {
                    case 1 -> register(scanner);
                    case 2 -> showCoursesAndPlaces();
                    case 3 -> showStudentsEnrolledToCourse(scanner);
                    case 4 -> showAllCourses();
                    case 5 -> updateCreditsForCourse(scanner);
                    case 6 -> deleteCourse(scanner);
                    case 7 -> showAllStudents();
                    case 8 -> showAllStudentsSortedByLastName();
                    case 9 -> filterCoursesByCredits(scanner);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
    }
}
