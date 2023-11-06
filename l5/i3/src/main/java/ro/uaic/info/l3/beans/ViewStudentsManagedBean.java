package ro.uaic.info.l3.beans;

import ro.uaic.info.l3.entities.Student;
import ro.uaic.info.l3.repositories.students.StudentRepository;
import ro.uaic.info.l3.repositories.students.StudentRepositoryImpl;
import ro.uaic.info.l3.services.students.StudentService;
import ro.uaic.info.l3.services.students.StudentServiceImpl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class ViewStudentsManagedBean {
    private List<Student> students = new ArrayList<>();
    private final StudentService studentService;

    public ViewStudentsManagedBean() {
//        AbstractDatabase abstractDatabase = new MYSQLDatabase();
//        StudentRepository studentRepository = new StudentRepositoryImpl(abstractDatabase);
//        studentService = new StudentServiceImpl(studentRepository);
//
//        CategoryRepository categoryRepository = new CategoryRepositoryImpl(abstractDatabase);
//        ProjectRepository projectRepository = new ProjectRepositoryImpl(abstractDatabase, categoryRepository);
//        projectService = new ProjectServiceImpl(projectRepository);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("abcdef");;
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        StudentRepository studentRepository = new StudentRepositoryImpl(entityManager);
        studentService = new StudentServiceImpl(studentRepository);

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
