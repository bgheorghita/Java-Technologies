package ro.uaic.info.l3.services.categories;

import ro.uaic.info.l3.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();
    Optional<Category> findByName(String name);
    Category findById(int id);
}
