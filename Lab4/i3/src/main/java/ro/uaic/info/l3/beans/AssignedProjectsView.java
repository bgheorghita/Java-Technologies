package ro.uaic.info.l3.beans;

import ro.uaic.info.l3.databases.AbstractDatabase;
import ro.uaic.info.l3.databases.MYSQLDatabase;
import ro.uaic.info.l3.models.AssignedProject;
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
import ro.uaic.info.l3.utils.StudentProjectMatcher;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean
@SessionScoped
public class AssignedProjectsView {
    private List<AssignedProject> assignedProjects;

    public AssignedProjectsView() {
        AbstractDatabase abstractDatabase = new MYSQLDatabase();
        StudentRepository studentRepository = new StudentRepositoryImpl(abstractDatabase);
        StudentService studentService = new StudentServiceImpl(studentRepository);

        CategoryRepository categoryRepository = new CategoryRepositoryImpl(abstractDatabase);
        ProjectRepository projectRepository = new ProjectRepositoryImpl(abstractDatabase, categoryRepository);
        ProjectService projectService = new ProjectServiceImpl(projectRepository);

        List<Student> students = studentService.findAll();
        StudentProjectMatcher studentProjectMatcher = new StudentProjectMatcher(projectService, studentService);
        assignedProjects = studentProjectMatcher.getAssignedProjects(students);
    }

    public List<AssignedProject> getAssignedProjects() {
        return assignedProjects;
    }

    public void setAssignedProjects(List<AssignedProject> assignedProjects) {
        this.assignedProjects = assignedProjects;
    }
}
