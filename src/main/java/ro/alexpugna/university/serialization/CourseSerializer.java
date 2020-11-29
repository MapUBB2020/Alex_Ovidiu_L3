package ro.alexpugna.university.serialization;

import ro.alexpugna.university.model.Course;
import ro.alexpugna.university.model.Teacher;

public class CourseSerializer implements Serializer<Course> {
    @Override
    public String serialize(Course entity) {
        String studentsString = entity.getStudentsEnrolled().toString();
        return String.format("%d,%s,%d,%s,%s,%d,%d,%s",
                entity.getId(),
                entity.getName(),
                entity.getTeacher().getId(),
                entity.getTeacher().getFirstName(),
                entity.getTeacher().getLastName(),
                entity.getMaxEnrollment(),
                entity.getCredits(),
                studentsString.substring(1, studentsString.length() - 1));
    }

    @Override
    public Course deserialize(String entityString) {
        String[] parts = entityString.split(",");
        Course course = new Course(
                Long.parseLong(parts[0]),
                parts[1],
                new Teacher(Long.parseLong(parts[2]), parts[3], parts[4]),
                Integer.parseInt(parts[5]),
                Integer.parseInt(parts[6])
        );
        for (int i = 7; i < parts.length; ++i) {
            course.addStudent(Long.parseLong(parts[i]));
        }
        return course;
    }
}
