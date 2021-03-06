package ro.alexpugna.university.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.alexpugna.university.exception.*;
import ro.alexpugna.university.model.Course;
import ro.alexpugna.university.model.Student;
import ro.alexpugna.university.model.Teacher;
import ro.alexpugna.university.repository.ICrudRepository;
import ro.alexpugna.university.repository.InMemoryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UniversityServiceTest {
    private UniversityService universityService;

    @BeforeEach
    public void setUp() {
        Teacher t1 = new Teacher(1L, "Teacher", "Teacherescu");
        Course c1 = new Course(1L, "c1", t1, 1, 5);
        Course c2 = new Course(2L, "c2", t1, 1, 5);
        Course c3 = new Course(3L, "c3", t1, 1, 31);
        Student s1 = new Student(1L, "s1", "");
        Student s2 = new Student(2L, "s2", "");
        Student s3 = new Student(3L, "s3", "");
        ICrudRepository<Course> courseRepository = new InMemoryRepository<>();
        ICrudRepository<Student> studentRepository = new InMemoryRepository<>();

        courseRepository.save(c1);
        courseRepository.save(c2);
        courseRepository.save(c3);
        studentRepository.save(s1);
        studentRepository.save(s2);
        studentRepository.save(s3);
        universityService = new UniversityService(courseRepository, studentRepository);
    }

    @Test
    public void testRegister() {
        assertThrows(ItemNotFoundException.class, () -> universityService.register(10L, 1L));
        assertThrows(ItemNotFoundException.class, () -> universityService.register(1L, 10L));
        assertDoesNotThrow(() -> universityService.register(1L, 1L));
        assertThrows(StudentAlreadyEnrolledException.class, () -> universityService.register(1L, 1L));
        assertThrows(StudentCreditsOverflowException.class, () -> universityService.register(3L, 2L));
        assertThrows(FullCourseException.class, () -> universityService.register(1L, 2L));

        List<Course> courses = universityService.getAllCourses();
        courses.forEach(course -> {
            int size = course.getId().equals(1L) ? 1 : 0;
            assertEquals(size, course.getStudentsEnrolled().size());
        });
    }

    @Test
    public void testRetrieveCoursesWithFreePlaces() throws ProgramException {
        assertEquals(3, universityService.retrieveCoursesWithFreePlaces().size());
        universityService.register(1L, 1L);
        assertEquals(2, universityService.retrieveCoursesWithFreePlaces().size());
    }

    @Test
    public void testRetrieveCoursesEnrolledForACourse() throws ProgramException {
        assertEquals(0, universityService.retrieveStudentsEnrolledForACourse(1L).size());
        universityService.register(1L, 1L);
        assertEquals(1, universityService.retrieveStudentsEnrolledForACourse(1L).size());
    }

    @Test
    public void testGetAllCourses() {
        assertThrows(ItemNotFoundException.class, () -> universityService.register(10L, 1L));
        assertThrows(ItemNotFoundException.class, () -> universityService.register(1L, 10L));
        assertDoesNotThrow(() -> universityService.register(1L, 1L));
        assertThrows(StudentAlreadyEnrolledException.class, () -> universityService.register(1L, 1L));
        assertThrows(StudentCreditsOverflowException.class, () -> universityService.register(3L, 2L));
        assertThrows(FullCourseException.class, () -> universityService.register(1L, 2L));

        List<Course> courses = universityService.getAllCourses();
        courses.forEach(course -> {
            int size = course.getId().equals(1L) ? 1 : 0;
            assertEquals(size, course.getStudentsEnrolled().size());
        });
    }

    @Test
    public void testUpdateCourseCredits() {
        assertThrows(ItemNotFoundException.class, () -> universityService.updateCourseCredits(10L, 30));
        assertDoesNotThrow(() -> universityService.register(1L, 1L));
        Student student = universityService.retrieveStudentsEnrolledForACourse(1L).get(0);
        assertEquals(Integer.valueOf(5), student.getTotalCredits());
        assertDoesNotThrow(() -> universityService.updateCourseCredits(1L, 10));
        student = universityService.retrieveStudentsEnrolledForACourse(1L).get(0);
        assertEquals(Integer.valueOf(10), student.getTotalCredits());
    }

    @Test
    public void deleteCourse() {
        assertThrows(ItemNotFoundException.class, () -> universityService.deleteCourse(10L));
        assertDoesNotThrow(() -> universityService.register(1L, 1L));
        assertDoesNotThrow(() -> universityService.deleteCourse(1L));
        Student student1 = universityService.getAllStudents().stream().filter(student -> student.getId().equals(1L)).findFirst().get();
        assertEquals(0, student1.getEnrolledCourses().size());
        assertEquals(Integer.valueOf(0), student1.getTotalCredits());
    }
}
