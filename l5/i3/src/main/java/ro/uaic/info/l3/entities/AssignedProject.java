package ro.uaic.info.l3.entities;

import javax.persistence.*;

@Entity
@Table(name = "assigned_projects")
public class AssignedProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public AssignedProject(Project project, Student student) {
        this.project = project;
        this.student = student;
    }

    public AssignedProject() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "AssignedProject{" +
                "id=" + id +
                ", project=" + project +
                ", student=" + student +
                '}';
    }

}
