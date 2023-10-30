package ro.uaic.info.l3.beans;

import ro.uaic.info.l3.databases.MYSQLDatabase;
import ro.uaic.info.l3.models.Category;
import ro.uaic.info.l3.models.Project;
import ro.uaic.info.l3.repositories.categories.CategoryRepository;
import ro.uaic.info.l3.repositories.categories.CategoryRepositoryImpl;
import ro.uaic.info.l3.repositories.projects.ProjectRepository;
import ro.uaic.info.l3.repositories.projects.ProjectRepositoryImpl;
import ro.uaic.info.l3.services.categories.CategoryService;
import ro.uaic.info.l3.services.categories.CategoryServiceImpl;
import ro.uaic.info.l3.services.projects.ProjectService;
import ro.uaic.info.l3.services.projects.ProjectServiceImpl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class ViewProjectsManagedBean {
    private List<Project> projects = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    private Project newProject = new Project();

    private final ProjectService projectService;
    private final CategoryService categoryService;

    public ViewProjectsManagedBean() {
        MYSQLDatabase mysqlDatabase = new MYSQLDatabase();
        CategoryRepository categoryRepository = new CategoryRepositoryImpl(mysqlDatabase);
        ProjectRepository projectRepository = new ProjectRepositoryImpl(mysqlDatabase, categoryRepository);
        projectService = new ProjectServiceImpl(projectRepository);
        categoryService = new CategoryServiceImpl(categoryRepository);

        loadProjects();
        loadCategories();
    }

    private void loadCategories() {
        categories = categoryService.findAll();
    }

    public void loadProjects() {
        projects = projectService.findAll();
    }

    public List<Project> getProjects() {
        return projects;
    }

    public Project getNewProject() {
        return newProject;
    }

    public void setNewProject(Project newProject) {
        this.newProject = newProject;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
