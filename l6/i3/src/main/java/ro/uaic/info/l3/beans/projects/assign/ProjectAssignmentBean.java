package ro.uaic.info.l3.beans.projects.assign;

import ro.uaic.info.l3.entities.AssignedProject;
import ro.uaic.info.l3.entities.Project;
import ro.uaic.info.l3.entities.Student;
import ro.uaic.info.l3.services.projects.AssignedProjectService;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Stateful
public class ProjectAssignmentBean {
    @EJB
    private AssignedProjectService assignedProjectService;
    private Map<Student, Set<Project>> addedProjectsToStudents;
    private String currentErrorMsg = "";

    @PostConstruct
    public void init() {
        addedProjectsToStudents = new HashMap<>();
    }

    public void addProjectToStudent(Student student, Project project) {
        if(projectAlreadySelected(project)) {
            currentErrorMsg = "Project already selected.";
            System.out.println(currentErrorMsg);
            return;
        }
        Set<Project> selectedProjects = addedProjectsToStudents.get(student);
        if(selectedProjects == null) {
            selectedProjects = new HashSet<>();
        }
        selectedProjects.add(project);
        addedProjectsToStudents.put(student, selectedProjects);
    }

    private boolean projectAlreadySelected(Project project) {
        return addedProjectsToStudents.values()
                .stream()
                .flatMap(Set::stream)
                .anyMatch(p -> p.equals(project));
    }

    public void assignProjects() {
        addedProjectsToStudents.forEach((student, setOfProjects) -> setOfProjects.forEach(project -> {
            AssignedProject assignedProject = new AssignedProject(project, student);
            assignedProjectService.save(assignedProject);
        }));
        resetSelectedProjects();
    }

    public int getSelectedProjectsSizeForStudent(Student student) {
        Set<Project> selectedProjects = addedProjectsToStudents.get(student);
        if(selectedProjects == null) {
            return 0;
        }
        return selectedProjects.size();
    }

    public void resetSelectedProjects() {
        addedProjectsToStudents.clear();
    }

    public String getCurrentErrorMsg() {
        return currentErrorMsg;
    }
}