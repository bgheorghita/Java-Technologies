package ro.uaic.info.l3.beans;

import ro.uaic.info.l3.entities.Project;
import ro.uaic.info.l3.repositories.projects.ProjectRepositoryImpl;
import ro.uaic.info.l3.services.projects.ProjectService;
import ro.uaic.info.l3.services.projects.ProjectServiceImpl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ManagedBean
@SessionScoped
public class ProjectSearchBean  {
    private String partialName;
    private String partialDescription;
    private String partialCategoryName;
    private Calendar deadline;
    private Date date;
    private boolean usePartialNameFilter;
    private boolean usePartialDescriptionFilter;
    private boolean useCategoryFilter;
    private boolean useDeadlineFilter;
    private List<Project> projects;
    private final ProjectService projectService;

    public ProjectSearchBean() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("abcdef");;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        projectService = new ProjectServiceImpl(new ProjectRepositoryImpl(entityManager));
    }

    public void searchProjects() {
        projects = projectService.searchProjects(usePartialNameFilter, usePartialDescriptionFilter, useCategoryFilter, partialName, partialDescription, partialCategoryName);
    }

    public String getPartialName() {
        return partialName;
    }

    public void setPartialName(String partialName) {
        this.partialName = partialName;
    }

    public String getPartialDescription() {
        return partialDescription;
    }

    public void setPartialDescription(String partialDescription) {
        this.partialDescription = partialDescription;
    }

    public String getPartialCategoryName() {
        return partialCategoryName;
    }

    public void setPartialCategoryName(String partialCategoryName) {
        this.partialCategoryName = partialCategoryName;
    }

    public Calendar getDeadline() {
        return deadline;
    }

    public void setDeadline(Calendar deadline) {
        this.deadline = deadline;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public boolean isUsePartialNameFilter() {
        return usePartialNameFilter;
    }

    public void setUsePartialNameFilter(boolean usePartialNameFilter) {
        this.usePartialNameFilter = usePartialNameFilter;
    }

    public boolean isUsePartialDescriptionFilter() {
        return usePartialDescriptionFilter;
    }

    public void setUsePartialDescriptionFilter(boolean usePartialDescriptionFilter) {
        this.usePartialDescriptionFilter = usePartialDescriptionFilter;
    }

    public boolean isUseCategoryFilter() {
        return useCategoryFilter;
    }

    public void setUseCategoryFilter(boolean useCategoryFilter) {
        this.useCategoryFilter = useCategoryFilter;
    }

    public boolean isUseDeadlineFilter() {
        return useDeadlineFilter;
    }

    public void setUseDeadlineFilter(boolean useDeadlineFilter) {
        this.useDeadlineFilter = useDeadlineFilter;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
