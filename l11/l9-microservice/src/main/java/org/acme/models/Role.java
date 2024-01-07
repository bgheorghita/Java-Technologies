package org.acme.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.acme.models.base.IdentifiableEntity;

@Entity
@Table(name = "roles")
public class Role extends IdentifiableEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
