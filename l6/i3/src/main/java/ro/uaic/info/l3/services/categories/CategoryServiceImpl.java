package ro.uaic.info.l3.services.categories;

import ro.uaic.info.l3.entities.Category;
import ro.uaic.info.l3.repositories.categories.CategoryRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class CategoryServiceImpl implements CategoryService {
    @EJB
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category findById(int id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found."));
    }
}
