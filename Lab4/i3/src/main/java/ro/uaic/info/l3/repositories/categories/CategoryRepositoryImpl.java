package ro.uaic.info.l3.repositories.categories;

import ro.uaic.info.l3.databases.AbstractDatabase;
import ro.uaic.info.l3.models.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryRepositoryImpl implements CategoryRepository {

    private final AbstractDatabase database;

    public CategoryRepositoryImpl(AbstractDatabase database) {
        this.database = database;
    }

    @Override
    public Category saveCategory(Category category) {
        int affectedRows = 0;
        try (Connection connection = database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO categories (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, category.getName());
            affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        category.setId(generatedKeys.getInt(1));
                        return category;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (affectedRows > 0) {
            throw new RuntimeException("Category saved, but could not be retrieved from the database.");
        }
        throw new RuntimeException("Category should not be saved.");
    }

    @Override
    public Optional<Category> findById(int id) {
        try (Connection connection = database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name FROM categories WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int categoryId = resultSet.getInt("id");
                    String categoryName = resultSet.getString("name");
                    return Optional.of(new Category(categoryId, categoryName));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM categories")) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int categoryId = resultSet.getInt("id");
                    String categoryName = resultSet.getString("name");
                    categories.add(new Category(categoryId, categoryName));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    @Override
    public Optional<Category> findByName(String name) {
        try (Connection connection = database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name FROM categories WHERE name = ?")) {

            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int categoryId = resultSet.getInt("id");
                    String categoryName = resultSet.getString("name");
                    return Optional.of(new Category(categoryId, categoryName));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
