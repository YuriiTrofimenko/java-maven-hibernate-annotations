package org.tyaa.javaee.hibernate.annotations.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="Roles")
public class Role extends AbstractEntity {

    @Column(name="title", length=25)
    private String title;
    @OneToMany(mappedBy="role")
    private Set<User> users;

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Role() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Role{" +
                "title='" + title + '\'' +
                ", users=" + users +
                '}';
    }
}
