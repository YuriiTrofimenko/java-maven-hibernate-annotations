package org.tyaa.javaee.hibernate.annotations.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Roles")
public class Role extends AbstractEntity {

    @Column(name="title", length=25)
    private String title;
    @OneToMany(mappedBy="role", fetch=FetchType.LAZY)
    private Set<User> users = new HashSet<>(0);

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
                ", users_count=" + ((users != null) ? users.size() : "0") +
                '}';
    }
}
