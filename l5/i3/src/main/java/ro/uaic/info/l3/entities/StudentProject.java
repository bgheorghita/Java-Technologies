package ro.uaic.info.l3.entities;

import javax.persistence.*;

@Entity
@Table(name = "students_projects")
public class StudentProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public StudentProject(Student student, Project project) {
        this.student = student;
        this.project = project;
    }

    public StudentProject() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}