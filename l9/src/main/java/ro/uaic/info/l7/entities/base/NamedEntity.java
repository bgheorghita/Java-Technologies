package ro.uaic.info.l7.entities.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class NamedEntity extends IdentifiableEntity {
    @Column(name = "name", nullable = false)
    protected String name;

    public NamedEntity(String name) {
        this.name = name;
    }

    public NamedEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
