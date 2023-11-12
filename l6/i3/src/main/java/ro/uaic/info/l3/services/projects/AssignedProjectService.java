package ro.uaic.info.l3.services.projects;

import ro.uaic.info.l3.entities.AssignedProject;

import java.util.List;

public interface AssignedProjectService {
    AssignedProject save(AssignedProject assignedProject);
    AssignedProject findById(int id);
    AssignedProject findByProjectId(int projectId);
    AssignedProject findByStudentId(int studentId);
    List<AssignedProject> findAll();
    void deleteById(int id);
}
