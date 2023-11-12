package ro.uaic.info.l3.repositories.projects;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.uaic.info.l3.entities.Category;
import ro.uaic.info.l3.entities.Project;
import ro.uaic.info.l3.repositories.categories.CategoryRepository;
import ro.uaic.info.l3.repositories.categories.CategoryRepositoryImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProjectRepositoryImplTest {
    EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private ProjectRepository projectRepository;
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("abcdef");
        entityManager = entityManagerFactory.createEntityManager();
        projectRepository = new ProjectRepositoryImpl();
        categoryRepository = new CategoryRepositoryImpl();
    }

    @AfterEach
    void tearDown() {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    public void testCRUDOperations() {
        // Create a new Category
        Category category = new Category("Category");
        entityManager.getTransaction().begin();
        categoryRepository.saveCategory(category);
        entityManager.getTransaction().commit();

        // Create a new Project
        Project project = new Project("Test Sample Project", category, "Test Sample Description", Calendar.getInstance());

        // Save the project
        entityManager.getTransaction().begin();
        projectRepository.save(project);
        entityManager.getTransaction().commit();

        // Read the project
        Optional<Project> retrievedProject = projectRepository.findById(project.getId());
        assertTrue(retrievedProject.isPresent());
        assertEquals(project.getName(), retrievedProject.get().getName());

        // Update the project
        entityManager.getTransaction().begin();
        project.setName("Test Updated Project Name");
        projectRepository.update(project);
        entityManager.getTransaction().commit();

        // Verify the update of the project
        retrievedProject = projectRepository.findById(project.getId());
        assertTrue(retrievedProject.isPresent());
        assertEquals("Test Updated Project Name", retrievedProject.get().getName());

        // Delete the project and the category
        entityManager.getTransaction().begin();
        projectRepository.delete(project);
        categoryRepository.delete(category);
        entityManager.getTransaction().commit();

        // Verify the deletion of the project
        retrievedProject = projectRepository.findById(project.getId());
        assertFalse(retrievedProject.isPresent());
    }

    @Test
    public void testFindAllProjects() {
        // assume that there is at least one project in the database
        List<Project> projects = projectRepository.findAll();
        assertNotNull(projects);
        assertTrue(projects.size() > 0);
    }

    @Test
    public void testFindNonExistentProject() {
        // assume that there is not a project in the database with id -1
        Optional<Project> retrievedProject = projectRepository.findById(-1);
        assertFalse(retrievedProject.isPresent());
    }
}