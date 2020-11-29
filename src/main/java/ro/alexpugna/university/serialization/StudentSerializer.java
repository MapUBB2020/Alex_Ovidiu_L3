package ro.alexpugna.university.serialization;

import ro.alexpugna.university.model.Student;

public class StudentSerializer implements Serializer<Student> {
    @Override
    public String serialize(Student entity) {
        String coursesString = entity.getEnrolledCourses().toString();
        return String.format("%d,%s,%s,%d,%s",
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getTotalCredits(),
                coursesString.substring(1, coursesString.length() - 1));
    }

    @Override
    public Student deserialize(String entityString) {
        String[] parts = entityString.split(",");
        Student student = new Student(
                Long.parseLong(parts[0]),
                parts[1],
                parts[2]
        );
        student.setTotalCredits(Integer.parseInt(parts[3]));
        for (int i = 4; i < parts.length; ++i) {
            student.addCourseToEnroll(Long.parseLong(parts[i]));
        }
        return student;
    }
}
