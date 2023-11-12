package ro.uaic.info.l3.beans.projects.assign;

import ro.uaic.info.l3.entities.AssignedProject;
import ro.uaic.info.l3.services.projects.assign.AssignProjectService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.*;

@ManagedBean
@SessionScoped
public class AssignedProjectsByCategoryBean {
    @EJB
    private AssignedProjectsSingleton assignedProjectsSingleton;

    private List<AssignedProject> selectedGroup;

    @PostConstruct
    public void init() {
        selectedGroup = assignedProjectsSingleton.getAssignedProjects();
    }

    public void setSelectedGroup(List<AssignedProject> selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public List<AssignedProject> getSelectedGroup() {
        return selectedGroup;
    }

    public void refreshSelectedGroup() {
        init();
    }

    public int getMethodInvocations() {
        return AssignProjectService.methodInvocations;
    }
}
