package ro.uaic.info.l3.beans.projects;

import ro.uaic.info.l3.entities.Category;
import ro.uaic.info.l3.entities.Project;
import ro.uaic.info.l3.services.categories.CategoryService;
import ro.uaic.info.l3.services.projects.ProjectService;

import javax.ejb.EJB;
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
    @EJB
    private ProjectService projectService;
    @EJB
    private CategoryService categoryService;
    private String projectName;
    private int categoryId;
    private String projectDescription;
    private Date projectDeadline;

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
