package ro.uaic.info.l3.beans;

import ro.uaic.info.l3.databases.AbstractDatabase;
import ro.uaic.info.l3.databases.MYSQLDatabase;
import ro.uaic.info.l3.models.Student;
import ro.uaic.info.l3.repositories.categories.CategoryRepository;
import ro.uaic.info.l3.repositories.categories.CategoryRepositoryImpl;
import ro.uaic.info.l3.repositories.projects.ProjectRepository;
import ro.uaic.info.l3.repositories.projects.ProjectRepositoryImpl;
import ro.uaic.info.l3.repositories.students.StudentRepository;
import ro.uaic.info.l3.repositories.students.StudentRepositoryImpl;
import ro.uaic.info.l3.services.projects.ProjectService;
import ro.uaic.info.l3.services.projects.ProjectServiceImpl;
import ro.uaic.info.l3.services.students.StudentService;
import ro.uaic.info.l3.services.students.StudentServiceImpl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class ViewStudentsManagedBean {
    private List<Student> students = new ArrayList<>();

    private final StudentService studentService;
    private final ProjectService projectService;

    public ViewStudentsManagedBean() {
        AbstractDatabase abstractDatabase = new MYSQLDatabase();
        StudentRepository studentRepository = new StudentRepositoryImpl(abstractDatabase);
        studentService = new StudentServiceImpl(studentRepository);

        CategoryRepository categoryRepository = new CategoryRepositoryImpl(abstractDatabase);
        ProjectRepository projectRepository = new ProjectRepositoryImpl(abstractDatabase, categoryRepository);
        projectService = new ProjectServiceImpl(projectRepository);

        loadStudents();
    }

    private void loadStudents() {
        students = studentService.findAll();
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
