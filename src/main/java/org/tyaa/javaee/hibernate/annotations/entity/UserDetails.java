package org.tyaa.javaee.hibernate.annotations.entity;

import javax.persistence.*;

@Entity
@Table(name="UserDetails")
public class UserDetails extends AbstractEntity {

    @Column(name="text")
    private String text;
    @OneToOne(mappedBy="userDetails")
    private User user;

    public UserDetails() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
