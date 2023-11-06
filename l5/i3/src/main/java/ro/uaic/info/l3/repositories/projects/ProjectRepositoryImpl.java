package ro.uaic.info.l3.repositories.projects;

import ro.uaic.info.l3.entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;

public class ProjectRepositoryImpl implements ProjectRepository {
    private final EntityManager entityManager;

    public ProjectRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Project save(Project project) {
        entityManager.persist(project);
        return project;
    }

    @Override
    @Transactional
    public Optional<Project> findById(int id) {
        TypedQuery<Project> query = entityManager.createNamedQuery("Project.findById", Project.class);
        query.setParameter("id", id);
        return query.getResultList().stream().findFirst();
    }

    @Override
    @Transactional
    public List<Project> findAll() {
        TypedQuery<Project> query = entityManager.createNamedQuery("Project.findAll", Project.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Project update(Project project) {
        return entityManager.merge(project);
    }

    @Override
    public void delete(Project project) {
        entityManager.remove(project);
    }

    @Override
    public List<Project> searchProjects(boolean usePartialNameFilter, boolean usePartialDescriptionFilter, boolean useCategoryFilter, String partialName, String partialDescription, String partialCategoryName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> cq = cb.createQuery(Project.class);
        Root<Project> projectRoot = cq.from(Project.class);
        List<Predicate> predicates = new ArrayList<>();
        if (usePartialNameFilter && partialName != null && !partialName.isEmpty()) {
            predicates.add(cb.like(cb.lower(projectRoot.get("name")), "%" + partialName.toLowerCase() + "%"));
        }
        if (usePartialDescriptionFilter && partialDescription != null && !partialDescription.isEmpty()) {
            predicates.add(cb.like(cb.lower(projectRoot.get("description")), "%" + partialDescription.toLowerCase() + "%"));
        }
        if (useCategoryFilter && partialCategoryName != null && !partialCategoryName.isEmpty()) {
            predicates.add(cb.like(cb.lower(projectRoot.get("category").get("name")), "%" + partialCategoryName.toLowerCase() + "%"));
        }
        cq.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Project> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

//    private final AbstractDatabase abstractDatabase;
//    private final CategoryRepository categoryRepository;
//
//    public ProjectRepositoryImpl(AbstractDatabase abstractDatabase, CategoryRepository categoryRepository) {
//        this.abstractDatabase = abstractDatabase;
//        this.categoryRepository = categoryRepository;
//    }
//
//    @Override
//    public Project save(Project project) {
//        int affectedRows = 0;
//
//        try (Connection connection = abstractDatabase.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO projects (name, category_id, description, deadline) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
//
//            preparedStatement.setString(1, project.getName());
//            preparedStatement.setInt(2, project.getCategory().getId());
//            preparedStatement.setString(3, project.getDescription());
//            preparedStatement.setDate(4, new Date(project.getDeadline().getTimeInMillis()));
//
//            affectedRows = preparedStatement.executeUpdate();
//
//            if (affectedRows > 0) {
//                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
//                    if (generatedKeys.next()) {
//                        project.setId(generatedKeys.getInt(1));
//                        return project;
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        if (affectedRows > 0) {
//            throw new RuntimeException("Project saved, but could not be retrieved from the database.");
//        }
//
//        throw new RuntimeException("Project could not be saved");
//    }
//
//    @Override
//    public Optional<Project> findById(int id) {
//        try (Connection connection = abstractDatabase.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, category_id, description, deadline FROM projects WHERE id = ?")) {
//
//            preparedStatement.setInt(1, id);
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                List<Project> projects = getProjects(resultSet);
//                if (projects.size() > 0) {
//                    return Optional.of(projects.get(0));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return Optional.empty();
//    }
//
//    @Override
//    public List<Project> findAll() {
//        try (Connection connection = abstractDatabase.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, category_id, description, deadline FROM projects");
//             ResultSet resultSet = preparedStatement.executeQuery()) {
//
//            return getProjects(resultSet);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        throw new RuntimeException("A problem has occurred.");
//    }
//
//    @Override
//    public Project update(Project project) {
//        String updateQuery = "UPDATE projects SET name = ?, category_id = ?, description = ?, deadline = ? WHERE id = ?";
//
//        try (Connection connection = abstractDatabase.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
//
//            preparedStatement.setString(1, project.getName());
//            preparedStatement.setInt(2, project.getCategory().getId());
//            preparedStatement.setString(3, project.getDescription());
//            preparedStatement.setDate(4, new Date(project.getDeadline().getTimeInMillis()));
//            preparedStatement.setInt(5, project.getId());
//
//            int affectedRows = preparedStatement.executeUpdate();
//
//            if (affectedRows > 0) {
//                return project;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        throw new RuntimeException("Project could not be updated");
//    }
//
//    private List<Project> getProjects(ResultSet resultSet) throws SQLException {
//        List<Project> projects = new ArrayList<>();
//
//        while (resultSet.next()) {
//            int projectId = resultSet.getInt("id");
//            String projectName = resultSet.getString("name");
//            int categoryId = resultSet.getInt("category_id");
//            Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
//            Category projectCategory = categoryOptional
//                    .orElseThrow(() -> new RuntimeException("The project should have a category, but it does not."));
//            String projectDescription = resultSet.getString("description");
//            Calendar projectDeadline = Calendar.getInstance();
//            projectDeadline.setTime(resultSet.getDate("deadline"));
//
//            Project project = new Project(projectId, projectName, projectCategory, projectDescription, projectDeadline);
//            projects.add(project);
//        }
//
//        return projects;
//    }
}
