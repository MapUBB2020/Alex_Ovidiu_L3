package ro.alexpugna.university.model;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private Integer totalCredits = 0;
    private List<Long> enrolledCourses = new ArrayList<>();

    public Student(Long id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    public Integer getTotalCredits() {
        return totalCredits;
    }

    public void removeCourse(Course course) {
        Long course1 = enrolledCourses.stream()
                .filter(c -> c.equals(course.getId()))
                .findFirst()
                .orElse(null);
        if (course1 != null) {
            totalCredits -= course.getCredits();
            enrolledCourses.remove(course1);
        }
    }

    public void setTotalCredits(Integer totalCredits) {
        this.totalCredits = totalCredits;
    }

    public List<Long> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void addCourseToEnroll(Course course) {
        enrolledCourses.add(course.getId());
        totalCredits += course.getCredits();
    }

    public void addCourseToEnroll(Long courseId) {
        enrolledCourses.add(courseId);
    }

    @Override
    public String toString() {
        return "Student{" +
                super.toString() +
                ", totalCredits=" + totalCredits +
                ", enrolledCourses=" + enrolledCourses +
                "} ";
    }
}
