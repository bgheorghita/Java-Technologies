package ro.uaic.info.l7.entities;

import ro.uaic.info.l7.entities.base.IdentifiableEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "preferences")
@NamedQueries({
        @NamedQuery(name = "Preference.findByUserId", query = "SELECT p FROM Preference p WHERE p.user.id = ?1"),
        @NamedQuery(name = "Preference.findByUsername", query = "SELECT p FROM Preference p WHERE p.user.username = ?1"),
        @NamedQuery(name = "Preference.findAll", query = "SELECT p FROM Preference p"),
        @NamedQuery(name = "Preference.findById", query = "SELECT p FROM Preference p where p.id = ?1"),
        @NamedQuery(name = "Preference.deleteById", query = "DELETE FROM Preference p where p.id = ?1"),
        @NamedQuery(name = "Preference.findByRegistrationNumber", query = "SELECT p FROM Preference p where p.registrationNumber = ?1")
})
public class Preference extends IdentifiableEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @NotNull(message = "User can not be null.")
    private User user;
    @Column(name = "content", nullable = false)
    @Size(min = 10, max = 100, message = "Size of content should be between 10-100 characters.")
    private String content;
    @Column(name = "registration_number", nullable = false)
    @NotNull(message = "Registration number can not be null.")
    private String registrationNumber;

    public Preference(User user, String content, String registrationNumber) {
        this.user = user;
        this.content = content;
        this.registrationNumber = registrationNumber;
    }

    public Preference() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Override
    public String toString() {
        return "Preference{" +
                "user=" + user +
                ", content='" + content + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                '}';
    }
}
