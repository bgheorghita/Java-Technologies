package ro.uaic.info.l3.utils;

import com.github.javafaker.Faker;
import ro.uaic.info.l3.entities.Category;
import ro.uaic.info.l3.entities.Project;
import ro.uaic.info.l3.repositories.categories.CategoryRepositoryImpl;
import ro.uaic.info.l3.repositories.projects.ProjectRepositoryImpl;
import ro.uaic.info.l3.services.categories.CategoryService;
import ro.uaic.info.l3.services.categories.CategoryServiceImpl;
import ro.uaic.info.l3.services.projects.ProjectService;
import ro.uaic.info.l3.services.projects.ProjectServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ProjectGenerator {
    public static final int FIXED_DESCRIPTION_SIZE = 50;
    private final ProjectService projectService;
    private final List<Category> categories;
    private final Faker faker;

    public ProjectGenerator(EntityManager entityManager){
        projectService = new ProjectServiceImpl(new ProjectRepositoryImpl(entityManager));
        CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl(entityManager));
        categories = categoryService.findAll();
        faker = new Faker();
    }

    public void populateDatabaseWithProjects(int numberOfProjects) {
        for (int i = 0; i < numberOfProjects; i++) {
            Project project = new Project();
            project.setCategory(getRandomCategory());
            project.setName(faker.color().name());
            project.setDeadline(generateRandomDeadline(faker));
            project.setDescription(faker.lorem().fixedString(FIXED_DESCRIPTION_SIZE));
            projectService.save(project);
        }
    }

    private Category getRandomCategory() {
        if (categories.isEmpty()) {
            throw new IllegalStateException("Category list is empty");
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(categories.size());
        return categories.get(randomIndex);
    }

    private Calendar generateRandomDeadline(Faker faker) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, faker.number().numberBetween(1, 365));
        return calendar;
    }

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("abcdef");;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ProjectGenerator projectGenerator = new ProjectGenerator(entityManager);

        entityManager.getTransaction().begin();
        projectGenerator.populateDatabaseWithProjects(100);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}