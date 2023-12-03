package ro.uaic.info.l7.entities.base;


import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class IdentifiableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdentifiableEntity identifiableEntity = (IdentifiableEntity) o;

        return id.equals(identifiableEntity.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Transient
    public boolean isNew() {
        return this.id == null;
    }
}
