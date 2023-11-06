package ro.uaic.info.l3.beans;

import ro.uaic.info.l3.entities.Project;
import ro.uaic.info.l3.repositories.categories.CategoryRepository;
import ro.uaic.info.l3.repositories.categories.CategoryRepositoryImpl;
import ro.uaic.info.l3.repositories.projects.ProjectRepository;
import ro.uaic.info.l3.repositories.projects.ProjectRepositoryImpl;
import ro.uaic.info.l3.services.projects.ProjectService;
import ro.uaic.info.l3.services.projects.ProjectServiceImpl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

@ManagedBean
@SessionScoped
public class EditProjectView {
    private ProjectService projectService;
    private Project selectedProject;
    private Date lastModifiedAt = new Date();
    private String lastModifiedBy = "ADMIN";

    public EditProjectView() {
//        selectedProject = new Project();
//        MYSQLDatabase mysqlDatabase = new MYSQLDatabase();
//        CategoryRepository categoryRepository = new CategoryRepositoryImpl(mysqlDatabase);
//        ProjectRepository projectRepository = new ProjectRepositoryImpl(mysqlDatabase, categoryRepository);
//        projectService = new ProjectServiceImpl(projectRepository);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("abcdef");;
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        ProjectRepository projectRepository = new ProjectRepositoryImpl(entityManager);
        projectService = new ProjectServiceImpl(projectRepository);
    }

    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Project getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;
    }

    public void editProject(Project project) {
        selectedProject = project;
    }

    public void updateProject() {
        projectService.update(selectedProject);
        lastModifiedAt = new Date();
        selectedProject = null;
    }
}
