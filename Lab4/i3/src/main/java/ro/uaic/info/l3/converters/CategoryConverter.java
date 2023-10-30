package ro.uaic.info.l3.converters;

import ro.uaic.info.l3.databases.MYSQLDatabase;
import ro.uaic.info.l3.models.Category;
import ro.uaic.info.l3.repositories.categories.CategoryRepository;
import ro.uaic.info.l3.repositories.categories.CategoryRepositoryImpl;
import ro.uaic.info.l3.services.categories.CategoryService;
import ro.uaic.info.l3.services.categories.CategoryServiceImpl;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "categoryConverter")
public class CategoryConverter implements Converter {
    private final CategoryService categoryService;

    public  CategoryConverter() {
        CategoryRepository categoryRepository = new CategoryRepositoryImpl(new MYSQLDatabase());
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        return categoryService.findById(Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Category) {
            return String.valueOf(((Category) value).getId());
        }
        return "no-value";
    }
}
