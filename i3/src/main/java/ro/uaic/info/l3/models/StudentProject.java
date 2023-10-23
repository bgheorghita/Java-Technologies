package ro.uaic.info.l3.models;

public class StudentProject {
    private int id;
    private Student student;
    private Project project;

    public StudentProject(Student student, Project project) {
        this.student = student;
        this.project = project;
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
