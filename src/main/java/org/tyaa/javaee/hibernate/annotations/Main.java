package org.tyaa.javaee.hibernate.annotations;

import org.hibernate.SessionFactory;
import org.tyaa.javaee.hibernate.annotations.entity.Repository;
import org.tyaa.javaee.hibernate.annotations.entity.Role;
import org.tyaa.javaee.hibernate.annotations.entity.User;
import org.tyaa.javaee.hibernate.annotations.entity.UserDetails;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory =
                HibernateFactory.getSessionFactory();
        EntityManager em = sessionFactory.createEntityManager();

        Role r = new Role();
        r.setTitle("admin");
        em.getTransaction().begin();
        em.persist(r);
        em.getTransaction().commit();

        User u = new User();
        u.setFirstName("f1");
        u.setLastName("l1");
        u.setAge(20);
        u.setRole(r);

        UserDetails userDetails = new UserDetails();
        userDetails.setText("Lorem ipsum dolor");
        userDetails.setUser(u);
        u.setUserDetails(userDetails);

        em.getTransaction().begin();
        em.persist(userDetails);
        em.persist(u);
        em.getTransaction().commit();
        User fromDbUser =
                em.createQuery("SELECT u FROM User u", User.class).getResultList().get(0);
        System.out.printf(
                "User %1$s (Role %2$s), details: %3$s",
                fromDbUser.getFirstName(),
                fromDbUser.getRole().getTitle(),
                fromDbUser.getUserDetails().getText()
        );

        List<User> users = new ArrayList<>();
        List<Repository> repositories = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            users.add(new User());
            User newUser = users.get(i);
            newUser.setFirstName("f1" + i);
            newUser.setLastName("l1" + i);
            newUser.setAge(21 + i);
            newUser.setRole(r);
            UserDetails newUserDetails = new UserDetails();
            newUserDetails.setText("Lorem ipsum dolor text " + i);
            newUserDetails.setUser(newUser);
            newUser.setUserDetails(newUserDetails);
            repositories.add(new Repository());
            Repository newRepository = repositories.get(i);
            newRepository.setData("Lorem ipsum dolor data " + i);
            newRepository.getUsers().add(newUser);
            newUser.getRepositories().add(newRepository);
            em.getTransaction().begin();
            em.persist(newUserDetails);
            em.persist(newRepository);
            em.persist(newUser);
            em.getTransaction().commit();
        }

        users.get(0).getRepositories().add(repositories.get(1));
        repositories.get(1).getUsers().add(users.get(0));
        em.getTransaction().begin();
        em.persist(users.get(0));
        em.getTransaction().commit();

        em.find(User.class, users.get(0).getId())
                .getRepositories().forEach(repository -> {
            System.out.println(repository.getData());
        });

        repositories.get(1).getUsers().forEach(user -> {
            System.out.println(user.getFirstName());
        });
    }
}
