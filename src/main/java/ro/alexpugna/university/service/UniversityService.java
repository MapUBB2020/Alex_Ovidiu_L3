package ro.alexpugna.university.service;

import ro.alexpugna.university.model.Course;
import ro.alexpugna.university.model.Student;
import ro.alexpugna.university.repository.ICrudRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UniversityService {
    private static final int MAX_CREDITS_FOR_STUDENT = 30;
    private ICrudRepository<Course> courseRepository;
    private ICrudRepository<Student> studentRepository;

    public UniversityService(ICrudRepository<Course> courseRepository, ICrudRepository<Student> studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public boolean register(Long courseId, Long studentId) {
        Course course = courseRepository.findOne(courseId);
        Student student = studentRepository.findOne(studentId);
        if (course == null || student == null) {
            return false;
        }
        if (course.isStudentEnrolled(student)) {
            return false;
        }
        if (student.getTotalCredits() + course.getCredits() > MAX_CREDITS_FOR_STUDENT) {
            return false;
        }
        if (!course.hasFreePlaces()) {
            return false;
        }
        course.addStudent(student);
        student.addCourseToEnroll(course);
        courseRepository.update(course);
        studentRepository.update(student);
        return true;
    }

    public List<Course> retrieveCoursesWithFreePlaces() {
        return StreamSupport.stream(courseRepository.findAll().spliterator(), false)
                .filter(Course::hasFreePlaces)
                .collect(Collectors.toList());
    }

    public List<Student> retrieveStudentsEnrolledForACourse(Long courseId) {
        Course course = courseRepository.findOne(courseId);
        if (course == null) {
            return Collections.emptyList();
        }

        return course.getStudentsEnrolled();
    }

    public List<Course> getAllCourses() {
        return StreamSupport.stream(courseRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public boolean updateCourseCredits(Long courseId, Integer newCredits) {
        Course course = courseRepository.findOne(courseId);
        if (course == null) {
            return false;
        }
        Integer oldCredits = course.getCredits();
        course.setCredits(newCredits);
        course.getStudentsEnrolled().forEach(student -> {
            Integer studentCredits = student.getTotalCredits();
            student.setTotalCredits(studentCredits - oldCredits + newCredits);
        });
        return true;
    }

    public boolean deleteCourse(Long courseId) {
        Course course = courseRepository.findOne(courseId);
        if (course == null) {
            return false;
        }
        course.getStudentsEnrolled().forEach(student -> {
            student.removeCourse(courseId);
            studentRepository.update(student);
        });
        courseRepository.delete(courseId);
        return true;
    }

    public List<Student> getAllStudents() {
        return StreamSupport.stream(studentRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
