package ro.uaic.info.l3.models;

import java.util.List;

public class Student {
    private int id;
    private String name;
    private List<Project> preferredProjects;

    public Student(int id, String name, List<Project> preferredProjects) {
        this(name, preferredProjects);
        this.id = id;
    }
    public Student(String name, List<Project> preferredProjects) {
        this.name = name;
        this.preferredProjects = preferredProjects;
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
