package ro.uaic.info.l3.services.projects;

import ro.uaic.info.l3.entities.AssignedProject;
import ro.uaic.info.l3.repositories.students.AssignedProjectRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;

@Stateless
public class AssignedProjectServiceImpl implements AssignedProjectService {
    @EJB
    private AssignedProjectRepository projectRepository;

    @Override
    // This annotation specifies that the method must be executed within a transaction. If no transaction exists, a new one will be started.
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AssignedProject save(AssignedProject assignedProject) {
        return projectRepository.save(assignedProject);
    }

    @Override
    public AssignedProject findById(int id) {
        return projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found."));
    }

    @Override
    public AssignedProject findByProjectId(int projectId) {
        return projectRepository.findByProjectId(projectId).orElseThrow(() -> new RuntimeException("Project not found."));
    }

    @Override
    public AssignedProject findByStudentId(int studentId) {
        return projectRepository.findByStudentId(studentId).orElseThrow(() -> new RuntimeException("Project not found."));
    }

    @Override
    public List<AssignedProject> findAll() {
        return projectRepository.findAll();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteById(int id) {
        projectRepository.deleteById(id);
    }
}
