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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ManagedBean
@SessionScoped
public class AddProjectView {
    private List<Category> categories = new ArrayList<>();
    private final ProjectService projectService;
    private final CategoryService categoryService;

    private String projectName;
    private int categoryId;
    private String projectDescription;
    private Date projectDeadline;

    public AddProjectView() {
        MYSQLDatabase mysqlDatabase = new MYSQLDatabase();
        CategoryRepository categoryRepository = new CategoryRepositoryImpl(mysqlDatabase);
        ProjectRepository projectRepository = new ProjectRepositoryImpl(mysqlDatabase, categoryRepository);
        projectService = new ProjectServiceImpl(projectRepository);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    public void save() {
        Category category = categoryService.findById(categoryId);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(projectDeadline);
        Project project = new Project(projectName, category, projectDescription, calendar);
        projectService.save(project);
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public Date getProjectDeadline() {
        return projectDeadline;
    }

    public void setProjectDeadline(Date projectDeadline) {
        this.projectDeadline = projectDeadline;
    }
}
