package org.tyaa.javaee.hibernate.annotations.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="Repositories")
public class Repository  extends AbstractEntity {

    @Column(name="data")
    private String data;
    @ManyToMany(mappedBy="repositories")
    private Set<User> users = new HashSet<>(0);

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Repository() {}

    public String getData() {
        return data;
    }

    public void setData(String title) {
        this.data = title;
    }
}
