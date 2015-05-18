package by.academy.it.domain.peopleentity;

import java.io.Serializable;

/**
 * Created by alexanderleonovich on 17.05.15.
 */
public class Student extends People implements Serializable{

    private String faculty;
    private Double mark;

    public Student() {
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }


    @Override
    public int hashCode() {
        int result = faculty != null ? faculty.hashCode() : 0;
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Student)) return false;

        Student student = (Student) obj;

        if (faculty != null ? !faculty.equals(student.faculty) : student.faculty != null) return false;
        if (mark != null ? !mark.equals(student.mark) : student.mark != null) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Student{" +
                "faculty='" + faculty + '\'' +
                ", mark=" + mark +
                '}';
    }
}
