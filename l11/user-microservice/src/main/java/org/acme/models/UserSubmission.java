package org.acme.models;

import jakarta.persistence.*;

@Entity
@Table(name = "user_submissions")
public class UserSubmission extends IdentifiableEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private Integer counter = 0; // number of submitted preferences for a user

    public UserSubmission(String username, Integer counter) {
        this.username = username;
        this.counter = counter;
    }

    public UserSubmission() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        return "PreferenceCounter{" +
                "username=" + username +
                ", counter=" + counter +
                '}';
    }
}
