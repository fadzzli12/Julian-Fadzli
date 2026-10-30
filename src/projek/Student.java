/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package projek;

// 1. CLASS MODEL - Student
public class Student {
    private int studentId;
    private String name;
    private String className;
    
    public Student(int studentId, String name, String className) {
        this.studentId = studentId;
        this.name = name;
        this.className = className;
    }
    
    // Getters & Setters
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
