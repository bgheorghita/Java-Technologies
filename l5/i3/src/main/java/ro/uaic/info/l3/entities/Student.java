package ro.uaic.info.l3.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany
    @JoinTable(
            name = "students_projects",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> preferredProjects;

    public Student(int id, String name, List<Project> preferredProjects) {
        this(name, preferredProjects);
        this.id = id;
    }
    public Student(String name, List<Project> preferredProjects) {
        this.name = name;
        this.preferredProjects = preferredProjects;
    }

    public Student() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Project> getPreferredProjects() {
        return preferredProjects;
    }

    public void setPreferredProjects(List<Project> preferredProjects) {
        this.preferredProjects = preferredProjects;
    }
}
