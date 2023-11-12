    package ro.uaic.info.l3.beans.projects;

    import ro.uaic.info.l3.entities.Category;
    import ro.uaic.info.l3.entities.Project;
    import ro.uaic.info.l3.services.categories.CategoryService;
    import ro.uaic.info.l3.services.projects.ProjectService;

    import javax.annotation.PostConstruct;
    import javax.ejb.EJB;
    import javax.faces.bean.ManagedBean;
    import javax.faces.bean.SessionScoped;
    import java.io.Serializable;
    import java.util.ArrayList;
    import java.util.List;

    @ManagedBean
    @SessionScoped
    public class ViewProjectsManagedBean implements Serializable {
        private List<Project> projects = new ArrayList<>();
        private List<Category> categories = new ArrayList<>();
        private Project newProject = new Project();
        @EJB
        private ProjectService projectService;
        @EJB
        private CategoryService categoryService;

        @PostConstruct
        void init() {
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
