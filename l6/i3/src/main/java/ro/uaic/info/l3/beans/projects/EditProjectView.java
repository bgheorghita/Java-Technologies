package ro.uaic.info.l3.beans.projects;

import ro.uaic.info.l3.entities.Project;
import ro.uaic.info.l3.services.projects.ProjectService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Date;

@ManagedBean
@SessionScoped
public class EditProjectView {
    @EJB
    private ProjectService projectService;
    private Project selectedProject;
    private Date lastModifiedAt = new Date();
    private String lastModifiedBy = "ADMIN";

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
