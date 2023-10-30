package ro.uaic.info.l3.repositories.projects;

import ro.uaic.info.l3.databases.AbstractDatabase;
import ro.uaic.info.l3.models.Category;
import ro.uaic.info.l3.models.Project;
import ro.uaic.info.l3.repositories.categories.CategoryRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class ProjectRepositoryImpl implements ProjectRepository {
    private final AbstractDatabase abstractDatabase;
    private final CategoryRepository categoryRepository;

    public ProjectRepositoryImpl(AbstractDatabase abstractDatabase, CategoryRepository categoryRepository) {
        this.abstractDatabase = abstractDatabase;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Project save(Project project) {
        int affectedRows = 0;

        try (Connection connection = abstractDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO projects (name, category_id, description, deadline) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, project.getName());
            preparedStatement.setInt(2, project.getCategory().getId());
            preparedStatement.setString(3, project.getDescription());
            preparedStatement.setDate(4, new Date(project.getDeadline().getTimeInMillis()));

            affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        project.setId(generatedKeys.getInt(1));
                        return project;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (affectedRows > 0) {
            throw new RuntimeException("Project saved, but could not be retrieved from the database.");
        }

        throw new RuntimeException("Project could not be saved");
    }

    @Override
    public Optional<Project> findById(int id) {
        try (Connection connection = abstractDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, category_id, description, deadline FROM projects WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Project> projects = getProjects(resultSet);
                if (projects.size() > 0) {
                    return Optional.of(projects.get(0));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<Project> findAll() {
        try (Connection connection = abstractDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, category_id, description, deadline FROM projects");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            return getProjects(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("A problem has occurred.");
    }

    @Override
    public Project update(Project project) {
        String updateQuery = "UPDATE projects SET name = ?, category_id = ?, description = ?, deadline = ? WHERE id = ?";

        try (Connection connection = abstractDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, project.getName());
            preparedStatement.setInt(2, project.getCategory().getId());
            preparedStatement.setString(3, project.getDescription());
            preparedStatement.setDate(4, new Date(project.getDeadline().getTimeInMillis()));
            preparedStatement.setInt(5, project.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                return project;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Project could not be updated");
    }

    private List<Project> getProjects(ResultSet resultSet) throws SQLException {
        List<Project> projects = new ArrayList<>();

        while (resultSet.next()) {
            int projectId = resultSet.getInt("id");
            String projectName = resultSet.getString("name");
            int categoryId = resultSet.getInt("category_id");
            Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
            Category projectCategory = categoryOptional
                    .orElseThrow(() -> new RuntimeException("The project should have a category, but it does not."));
            String projectDescription = resultSet.getString("description");
            Calendar projectDeadline = Calendar.getInstance();
            projectDeadline.setTime(resultSet.getDate("deadline"));

            Project project = new Project(projectId, projectName, projectCategory, projectDescription, projectDeadline);
            projects.add(project);
        }

        return projects;
    }
}
