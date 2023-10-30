package ro.uaic.info.l3.services.projects;

import ro.uaic.info.l3.models.Project;

import java.util.List;

public interface ProjectService {
    List<Project> findAll();
    Project save(Project project);
    Project findById(int id);
    Project update(Project project);
}
