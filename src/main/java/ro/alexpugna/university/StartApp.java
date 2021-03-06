package ro.alexpugna.university;

import ro.alexpugna.university.model.Course;
import ro.alexpugna.university.model.Student;
import ro.alexpugna.university.repository.FileRepository;
import ro.alexpugna.university.repository.ICrudRepository;
import ro.alexpugna.university.serialization.CourseSerializer;
import ro.alexpugna.university.serialization.StudentSerializer;
import ro.alexpugna.university.service.UniversityService;
import ro.alexpugna.university.ui.Console;

public class StartApp {
    public static void main(String[] args) {
//        ICrudRepository<Course> courseRepository = new InMemoryRepository<>();
//        ICrudRepository<Student> studentRepository = new InMemoryRepository<>();
//        Teacher teacher = new Teacher(1L, "Teacher", "Teacherescu");
//        for (int i = 0; i < 10; ++i) {
//            Course course = new Course((long) i, "course" + i, teacher, 5, 6);
//            Student student = new Student((long) i, "student", String.valueOf(i));
//            courseRepository.save(course);
//            studentRepository.save(student);
//        }

        ICrudRepository<Course> courseRepository = new FileRepository<>(new CourseSerializer(), "files/courses.txt");
        ICrudRepository<Student> studentRepository = new FileRepository<>(new StudentSerializer(), "files/students.txt");
        UniversityService service = new UniversityService(courseRepository, studentRepository);
        Console console = new Console(service);
        console.run();
    }
}
