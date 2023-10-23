package ro.uaic.info.l3.repositories.categories;

import ro.uaic.info.l3.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Category saveCategory(Category category);
    Optional<Category> findById(int id);
    List<Category> findAll();
    Optional<Category> findByName(String name);
}
