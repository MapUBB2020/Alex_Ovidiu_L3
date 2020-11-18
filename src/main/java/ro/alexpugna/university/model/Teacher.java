package ro.alexpugna.university.model;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {
    private List<Course> courses = new ArrayList<>();

    public Teacher(Long id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourseToTeach(Course course) {
        courses.add(course);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
