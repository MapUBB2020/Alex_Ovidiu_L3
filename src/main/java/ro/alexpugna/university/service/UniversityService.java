package ro.alexpugna.university.service;

import ro.alexpugna.university.exception.*;
import ro.alexpugna.university.model.Course;
import ro.alexpugna.university.model.Person;
import ro.alexpugna.university.model.Student;
import ro.alexpugna.university.repository.ICrudRepository;

import java.util.Collections;
import java.util.Comparator;
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

    public void register(Long courseId, Long studentId) throws ProgramException {
        Course course = courseRepository.findOne(courseId);
        Student student = studentRepository.findOne(studentId);
        if (course == null || student == null) {
            throw new ItemNotFoundException("No course or student found");
        }
        if (course.isStudentEnrolled(student)) {
            throw new StudentAlreadyEnrolledException(String.format("Student %s is already enrolled to course %s", student, course));
        }
        if (student.getTotalCredits() + course.getCredits() > MAX_CREDITS_FOR_STUDENT) {
            throw new StudentCreditsOverflowException(String.format("Credits overflow for student %s", student));
        }
        if (!course.hasFreePlaces()) {
            throw new FullCourseException(String.format("Course %s is already full", course));
        }
        course.addStudent(student);
        student.addCourseToEnroll(course);
        courseRepository.update(course);
        studentRepository.update(student);
    }

    public List<Course> retrieveCoursesWithFreePlaces() throws ItemNotFoundException {
        List<Course> courses = StreamSupport.stream(courseRepository.findAll().spliterator(), false)
                .filter(Course::hasFreePlaces)
                .collect(Collectors.toList());
        if (courses.isEmpty()) {
            throw new ItemNotFoundException("No courses with free places found");
        }
        return courses;
    }

    public List<Student> retrieveStudentsEnrolledForACourse(Long courseId) {
        Course course = courseRepository.findOne(courseId);
        if (course == null) {
            return Collections.emptyList();
        }

        return course.getStudentsEnrolled().stream().map(id -> studentRepository.findOne(id)).collect(Collectors.toList());
    }

    public List<Course> getAllCourses() {
        return StreamSupport.stream(courseRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public void updateCourseCredits(Long courseId, Integer newCredits) throws ItemNotFoundException {
        Course course = courseRepository.findOne(courseId);
        if (course == null) {
            throw new ItemNotFoundException(String.format("Course %d not found", courseId));
        }
        Integer oldCredits = course.getCredits();
        course.setCredits(newCredits);
        course.getStudentsEnrolled().forEach(studentId -> {
            Student student = studentRepository.findOne(studentId);
            Integer studentCredits = student.getTotalCredits();
            student.setTotalCredits(studentCredits - oldCredits + newCredits);
            studentRepository.update(student);
        });
    }

    public void deleteCourse(Long courseId) throws ItemNotFoundException {
        Course course = courseRepository.findOne(courseId);
        if (course == null) {
            throw new ItemNotFoundException(String.format("Course %d not found", courseId));
        }
        course.getStudentsEnrolled().forEach(studentId -> {
            Student student = studentRepository.findOne(studentId);
            student.removeCourse(course);
            studentRepository.update(student);
        });
        courseRepository.delete(courseId);
    }

    public List<Student> getAllStudents() {
        return StreamSupport.stream(studentRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public List<Student> getStudentsSortedByLastName() {
        List<Student> students = getAllStudents();
        students.sort(Comparator.comparing(Person::getLastName));
        return students;
    }

    public List<Course> getCoursesHavingCreditsAtLeast(Integer threshold) {
        List<Course> courses = getAllCourses();
        return courses.stream().filter(course -> course.getCredits() >= threshold).collect(Collectors.toList());
    }
}
