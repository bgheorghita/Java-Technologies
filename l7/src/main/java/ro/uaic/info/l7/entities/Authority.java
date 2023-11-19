package ro.uaic.info.l7.entities;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "authorities")
public class Authority extends IdentifiableEntity {
    @NotNull(message = "Authority name must not be null.")
    private String name;

    public Authority(String name) {
        this.name = name;
    }

    public Authority() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
