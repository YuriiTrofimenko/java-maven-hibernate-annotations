package org.tyaa.javaee.hibernate.annotations;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tyaa.javaee.hibernate.annotations.entity.Repository;
import org.tyaa.javaee.hibernate.annotations.entity.Role;
import org.tyaa.javaee.hibernate.annotations.entity.User;
import org.tyaa.javaee.hibernate.annotations.entity.UserDetails;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        SessionFactory sessionFactory =
                HibernateFactory.getSessionFactory();
        Session session =
                sessionFactory.openSession();

        User user1 = new User();
        User u1 = null;
        List<Role> roles = null;
        try {
            session.beginTransaction();

            // SQL
            SQLQuery queryInsert =
                    session.createSQLQuery("INSERT INTO Roles (title) VALUES (?)");
            queryInsert.setParameter(1, "admin");
            queryInsert.executeUpdate();

            // JPQL / HQL
            /* Query query =
                    // session.createQuery("SELECT r FROM Role AS r INNER JOIN FETCH r.users as users");
                    session.createQuery("SELECT r FROM Role AS r LEFT OUTER JOIN FETCH r.users as users");
            roles = query.getResultList(); */

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Role> cr = cb.createQuery(Role.class);
            Root<Role> root = cr.from(Role.class);
            cr.select(root).where(cb.equal(root.get("id"), 1L));
            TypedQuery<Role> query = session.createQuery(cr);
            Role role1 = query.getSingleResult();

            user1.setFirstName("f1");
            user1.setLastName("l1");
            user1.setAge(30);
            user1.setRole(role1);
            session.persist(user1);

            CriteriaQuery<User> cu = cb.createQuery(User.class);
            Root<User> userRoot = cu.from(User.class);
            List<Predicate> predicates = new ArrayList<>();
            cu.select(userRoot);
            userRoot.fetch("role");
            cu.where(cb.equal(root.get("id"), 1L));
            TypedQuery<User> userTypedQuery = session.createQuery(cu);
            u1 = userTypedQuery.getSingleResult();

            session.getTransaction().commit();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }

        System.out.println(u1);
        // System.out.println(roles.size());
        // roles.forEach(System.out::println);
    }
}
