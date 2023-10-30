package ro.uaic.info.l3.repositories.students;

import ro.uaic.info.l3.models.StudentProject;

import java.util.List;
import java.util.Optional;

public interface StudentProjectRepository {
    StudentProject save(StudentProject studentProject);
    Optional<StudentProject> findById(int id);
    List<StudentProject> findByStudentId(int studentId);
    List<StudentProject> findByProjectId(int projectId);
}
