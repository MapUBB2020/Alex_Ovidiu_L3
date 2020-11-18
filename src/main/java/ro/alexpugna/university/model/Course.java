package ro.alexpugna.university.model;

import java.util.ArrayList;
import java.util.List;

public class Course extends Entity {
    private String name; // the name of the course
    private Person teacher; // the teacher conducting the course
    private Integer maxEnrollment; // students limit for enrollment
    private List<Student> studentsEnrolled; // students enrolled at the course
    private Integer credits;

    public Course(Long id, String name, Person teacher, Integer maxEnrollment, Integer credits) {
        super(id);
        this.name = name;
        this.teacher = teacher;
        this.maxEnrollment = maxEnrollment;
        this.credits = credits;
        this.studentsEnrolled = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person getTeacher() {
        return teacher;
    }

    public void setTeacher(Person teacher) {
        this.teacher = teacher;
    }

    public Integer getMaxEnrollment() {
        return maxEnrollment;
    }

    public void setMaxEnrollment(Integer maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }

    public List<Student> getStudentsEnrolled() {
        return studentsEnrolled;
    }

    public void addStudent(Student student) {
        studentsEnrolled.add(student);
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public boolean isStudentEnrolled(Student student) {
        return studentsEnrolled.stream().anyMatch(s -> s.equals(student));
    }

    public boolean hasFreePlaces() {
        return studentsEnrolled.size() < maxEnrollment;
    }

    @Override
    public String toString() {
        return "Course{" + super.toString() +
                ", name='" + name + '\'' +
                ", teacher=" + teacher +
                ", maxEnrollment=" + maxEnrollment +
                ", studentsEnrolled=" + studentsEnrolled +
                ", credits=" + credits +
                "} ";
    }
}
