package ro.uaic.info.l3.beans.projects.assign;

import ro.uaic.info.l3.entities.Project;
import ro.uaic.info.l3.entities.Student;
import ro.uaic.info.l3.services.projects.ProjectService;
import ro.uaic.info.l3.services.projects.checkers.ProjectAvailabilityChecker;
import ro.uaic.info.l3.services.students.StudentService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.*;

@ManagedBean
@SessionScoped
public class AssignProjectBean {
    @EJB
    private StudentService studentService;
    @EJB
    private ProjectService projectService;
    @EJB
    private ProjectAssignmentBean projectAssignmentBean;
    @EJB
    private ProjectAvailabilityChecker projectAvailabilityChecker;
    private List<Student> students;
    private List<Project> projects;
    private Student selectedStudent;
    private Project selectedProject;
    private Integer selectedStudentId;
    private Integer selectedProjectId;
    private String currentErrorMsg = "";
    private List<String> errors;

    @PostConstruct
    public void init() {
        students = studentService.findAll();
        projects = projectService.findAll();
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void addProjectToStudent() {
        if(selectedStudent == null || selectedProject == null) {
            return;
        }
        if(projectAvailabilityChecker.isProjectAvailable(selectedProject.getId())) {
            projectAssignmentBean.addProjectToStudent(selectedStudent, selectedProject);
        } else {
            currentErrorMsg = "The project is not available.";
            System.out.println(currentErrorMsg);
        }
    }

    public void assignProjects() {
        projectAssignmentBean.assignProjects();
    }

    public Integer getSelectedStudentId() {
        return selectedStudentId;
    }

    public void setSelectedStudentId(Integer selectedStudentId) {
        this.selectedStudentId = selectedStudentId;
        selectedStudent = studentService.findById(selectedStudentId);
    }

    public Integer getSelectedProjectId() {
        return selectedProjectId;
    }

    public void setSelectedProjectId(Integer selectedProjectId) {
        this.selectedProjectId = selectedProjectId;
        selectedProject = projectService.findById(selectedProjectId);
    }

    public String getSelectedProjectsSizeForStudent() {
        if(selectedStudent == null) {
            return "";
        }
        int selectedProjectsSize = projectAssignmentBean.getSelectedProjectsSizeForStudent(selectedStudent);
        return "Selected projects for " + selectedStudent.getName() + ": " + selectedProjectsSize;
    }

    public void resetSelectedProjects() {
        projectAssignmentBean.resetSelectedProjects();
    }

    public List<String> getErrors() {
        List<String> errors = new ArrayList<>();
        if(!currentErrorMsg.isBlank()) {
            errors.add(currentErrorMsg);
        }
        if(!projectAssignmentBean.getCurrentErrorMsg().isBlank()) {
            errors.add(projectAssignmentBean.getCurrentErrorMsg());
        }
        return errors;
    }
}