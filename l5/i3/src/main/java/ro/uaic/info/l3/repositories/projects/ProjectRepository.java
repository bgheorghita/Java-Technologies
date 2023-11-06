package ro.uaic.info.l3.repositories.projects;

import ro.uaic.info.l3.entities.Project;

import javax.persistence.EntityManager;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    Project save(Project project);
    Optional<Project> findById(int id);
    List<Project> findAll();
    Project update(Project project);
    void delete(Project project);
    List<Project> searchProjects(boolean usePartialNameFilter, boolean usePartialDescriptionFilter, boolean useCategoryFilter, String partialName, String partialDescription, String partialCategoryName);
}
