package ro.uaic.info.l3.converters;

import ro.uaic.info.l3.entities.Category;
import ro.uaic.info.l3.services.categories.CategoryService;


import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "categoryConverter")
public class CategoryConverter implements Converter {

    @EJB
    private CategoryService categoryService;

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
