package ro.uaic.info.l3.repositories.projects;

import ro.uaic.info.l3.models.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    Project save(Project project);
    Optional<Project> findById(int id);
    List<Project> findAll();
}
