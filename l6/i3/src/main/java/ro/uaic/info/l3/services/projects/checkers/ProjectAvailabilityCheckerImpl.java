package ro.uaic.info.l3.services.projects.checkers;

import ro.uaic.info.l3.entities.AssignedProject;
import ro.uaic.info.l3.repositories.students.AssignedProjectRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class ProjectAvailabilityCheckerImpl implements ProjectAvailabilityChecker {
    @EJB
    private AssignedProjectRepository assignedProjectRepository;

    @Override
    public boolean isProjectAvailable(int projectId) {
        Optional<AssignedProject> assignedProject = assignedProjectRepository.findByProjectId(projectId);
        return assignedProject.isEmpty();
    }
}
