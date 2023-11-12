package ro.uaic.info.l3.services.projects.assign;

import ro.uaic.info.l3.beans.projects.assign.AssignedProjectsSingleton;
import ro.uaic.info.l3.entities.AssignedProject;

import javax.ejb.*;
import java.util.List;

@Stateless
public class AssignProjectRefresher {
    @EJB
    private AssignProjectService groupSelectorService;
    @EJB
    private AssignedProjectsSingleton assignedProjectsSingleton;

    @Schedule(second = "*/5", minute = "*", hour = "*", persistent = false)
    public void refreshAssignedProjects() {
        List<AssignedProject> assignedProjects = groupSelectorService.getMaximumGroupOfAssignedProjects();
        assignedProjectsSingleton.setAssignedProjects(assignedProjects);
    }
}