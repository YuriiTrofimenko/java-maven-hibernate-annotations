package org.tyaa.javaee.hibernate.annotations;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tyaa.javaee.hibernate.annotations.entity.Repository;
import org.tyaa.javaee.hibernate.annotations.entity.Role;
import org.tyaa.javaee.hibernate.annotations.entity.User;
import org.tyaa.javaee.hibernate.annotations.entity.UserDetails;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        SessionFactory sessionFactory =
                HibernateFactory.getSessionFactory();
        Session session =
                sessionFactory.openSession();

        List<Role> roles = null;
        try {
            session.beginTransaction();

            // SQL
            SQLQuery queryInsert =
                    session.createSQLQuery("INSERT INTO Roles (title) VALUES (?)");
            queryInsert.setParameter(1, "admin");
            queryInsert.executeUpdate();

            // JPQL / HQL
            Query query =
                    // session.createQuery("SELECT r FROM Role AS r INNER JOIN FETCH r.users as users");
                    session.createQuery("SELECT r FROM Role AS r LEFT OUTER JOIN FETCH r.users as users");
            roles = query.getResultList();

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }

        System.out.println(roles.size());
        roles.forEach(System.out::println);
    }
}
