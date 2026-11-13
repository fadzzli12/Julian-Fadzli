package projek;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="students")
public class Student {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int studentId;
    @Column(name="name", nullable=false, length=100)
    private String name;
    @Column(name="className", nullable=false, length=50)
    private String className;

    public Student() {
    }

    public Student(String name, String className) {
        this.name = name;
        this.className = className;
    }

    public Student(int studentId, String name, String className) {
        this.studentId = studentId;
        this.name = name;
        this.className = className;
    }

    public int getStudentId() {
        return this.studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String toString() {
        return "Student{studentId=" + this.studentId + ", name='" + this.name + "', className='" + this.className + "'}";
    }
}

