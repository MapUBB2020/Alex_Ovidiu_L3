package ro.alexpugna.university.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Student extends Person {
    private Integer totalCredits = 0;
    private List<Course> enrolledCourses = new ArrayList<>();

    public Student(Long id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    public Integer getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(Integer totalCredits) {
        this.totalCredits = totalCredits;
    }

    public void removeCourse(Long courseId) {
        Course course1 = enrolledCourses.stream()
                .filter(course -> course.getId().equals(courseId))
                .findFirst()
                .orElse(null);
        if (course1 != null) {
            totalCredits -= course1.getCredits();
            enrolledCourses.remove(course1);
        }
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void addCourseToEnroll(Course course) {
        enrolledCourses.add(course);
        totalCredits += course.getCredits();
    }

    @Override
    public String toString() {
        String coursesString = enrolledCourses.stream().map(course -> course.getId().toString()).collect(Collectors.joining(","));
        return "Student{" + super.toString() +
                ", totalCredits=" + totalCredits +
                ", enrolledCourses=[" + coursesString +
                "]} ";
    }
}
