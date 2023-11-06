package ro.uaic.info.l3.entities;

import ro.uaic.info.l3.models.Teacher;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "projects")
@NamedQueries({
        @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p"),
        @NamedQuery(name = "Project.findById", query = "SELECT p FROM Project p WHERE p.id = :id")
})
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String description;
    private Calendar deadline;
    private transient Teacher supervisedBy;

    public Project(String name, Category category, String description, Calendar deadline) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.deadline = deadline;
    }

    public Project(int id, String name, Category category, String description, Calendar deadline) {
        this(name, category, description, deadline);
        this.id = id;
    }

    public Project() {};

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getDeadline() {
        return deadline;
    }

    public void setDeadline(Calendar deadline) {
        this.deadline = deadline;
    }

    public Teacher getSupervisedBy() {
        return supervisedBy;
    }

    public void setSupervisedBy(Teacher supervisedBy) {
        this.supervisedBy = supervisedBy;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", supervisedBy=" + supervisedBy +
                '}';
    }
}
