package ro.uaic.info.l3.beans.projects.assign;

import ro.uaic.info.l3.entities.AssignedProject;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.Lock;
import javax.ejb.Singleton;

import java.util.List;

import static javax.ejb.ConcurrencyManagementType.CONTAINER;
import static javax.ejb.LockType.READ;
import static javax.ejb.LockType.WRITE;

@Singleton
@ConcurrencyManagement(CONTAINER)
public class AssignedProjectsSingleton {
    private List<AssignedProject> assignedProjects;

    @Lock(READ)
    public List<AssignedProject> getAssignedProjects() {
        return assignedProjects;
    }

    @Lock(WRITE)
    public void setAssignedProjects(List<AssignedProject> assignedProjects) {
        this.assignedProjects = assignedProjects;
    }
}