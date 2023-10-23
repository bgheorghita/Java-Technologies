package ro.uaic.info.l3.repositories.students;

import ro.uaic.info.l3.models.AssignedProject;

import java.util.List;
import java.util.Optional;

public interface AssignedProjectRepository {
    AssignedProject save(AssignedProject assignedProject);
    Optional<AssignedProject> findById(int id);
    Optional<AssignedProject> findByProjectId(int projectId);
    Optional<AssignedProject> findByStudentId(int studentId);
    List<AssignedProject> findAll();
    void deleteById(int id);
}
