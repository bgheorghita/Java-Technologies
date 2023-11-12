package ro.uaic.info.l3.services.projects;

import ro.uaic.info.l3.entities.Project;
import ro.uaic.info.l3.repositories.projects.ProjectRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ProjectServiceImpl implements ProjectService {
    @EJB
    private ProjectRepository projectRepository;

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project findById(int id) {
        return projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found."));
    }

    @Override
    public Project update(Project project) {
        return projectRepository.update(project);
    }

    @Override
    public List<Project> searchProjects(boolean usePartialNameFilter, boolean usePartialDescriptionFilter, boolean useCategoryFilter, String partialName, String partialDescription, String partialCategoryName) {
        return projectRepository.searchProjects(usePartialNameFilter, usePartialDescriptionFilter, useCategoryFilter, partialName, partialDescription, partialCategoryName);
    }
}
