package ro.uaic.info.l3.beans.projects.assign;

import ro.uaic.info.l3.entities.AssignedProject;
import ro.uaic.info.l3.entities.Student;
import ro.uaic.info.l3.services.projects.ProjectService;
import ro.uaic.info.l3.services.students.StudentService;
import ro.uaic.info.l3.utils.StudentProjectMatcher;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean
@SessionScoped
public class AssignedProjectsView {
    private List<AssignedProject> assignedProjects;
    @EJB
    private StudentService studentService;
    @EJB
    private ProjectService projectService;

    @PostConstruct
    public void init() {
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