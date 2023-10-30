package ro.uaic.info.l3.services.projects;

import ro.uaic.info.l3.models.Project;
import ro.uaic.info.l3.repositories.projects.ProjectRepository;

import java.util.List;

public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

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
}
