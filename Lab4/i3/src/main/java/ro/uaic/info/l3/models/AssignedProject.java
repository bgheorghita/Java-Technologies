package ro.uaic.info.l3.models;

public class AssignedProject {
    private int id;
    private Project project;
    private Student student;

    public AssignedProject(Project project, Student student) {
        this.project = project;
        this.student = student;
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
