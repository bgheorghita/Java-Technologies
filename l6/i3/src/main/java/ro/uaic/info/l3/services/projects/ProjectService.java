package ro.uaic.info.l3.services.projects;

import ro.uaic.info.l3.entities.Project;

import javax.persistence.EntityManager;
import java.util.Calendar;
import java.util.List;

public interface ProjectService {
    List<Project> findAll();
    Project save(Project project);
    Project findById(int id);
    Project update(Project project);
    List<Project> searchProjects(boolean usePartialNameFilter, boolean usePartialDescriptionFilter, boolean useCategoryFilter, String partialName, String partialDescription, String partialCategoryName);
}
