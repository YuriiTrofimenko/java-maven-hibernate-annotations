package org.tyaa.javaee.hibernate.annotations;

import org.hibernate.SessionFactory;
import org.tyaa.javaee.hibernate.annotations.entity.Role;
import org.tyaa.javaee.hibernate.annotations.entity.User;
import org.tyaa.javaee.hibernate.annotations.entity.UserDetails;

import javax.persistence.EntityManager;

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
    }
}
