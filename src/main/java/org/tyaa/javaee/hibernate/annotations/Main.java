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
        /* EntityManager em =
                sessionFactory.createEntityManager(); */

        User user1 = new User();
        User u1 = null;
        List<Role> roles = null;
        try {
            session.beginTransaction();

            // SQL
            SQLQuery<Role> queryInsert =
                    session.createSQLQuery("INSERT INTO Roles (title) VALUES (?)");
            queryInsert.setParameter(1, "admin");
            queryInsert.executeUpdate();

            // JPQL / HQL
            /* Query query =
                    // session.createQuery("SELECT r FROM Role AS r INNER JOIN FETCH r.users as users");
                    session.createQuery("SELECT r FROM Role AS r LEFT OUTER JOIN FETCH r.users");
            roles = query.getResultList(); */

            // Создание построителя заготовки запроса
            CriteriaBuilder cb = session.getCriteriaBuilder();
            // Порождение пустого типизированного объекта заготовки запроса
            CriteriaQuery<Role> cr = cb.createQuery(Role.class);
            // Создание корня запроса: определение основного истчника данных
            Root<Role> root = cr.from(Role.class);
            // В заготовку запроса включаем выше созданный корень,
            // а также раздел предикатов (фильтров)
            // root.get("id") - значение для сравнения - из поля id
            // 1L - значение для сравнения (литерал большого целого числа 1)
            cr.select(root).where(cb.equal(root.get("id"), 1L));
            // Создаем типизированную оболочку запроса, которая позволяет его выполнить
            TypedQuery<Role> query = session.createQuery(cr);
            /* query.setFirstResult(21);
            query.setMaxResults(10); */
            // Выполняем типизированный запрос с ожиданием обязательного только одного результата
            // (одной строки из таблицы Roles, отображенной в один объект типа Role)
            Role role1 = query.getSingleResult();
            // Считанный обратно из базы объект Роль
            // используем для инициализации поля role объекта Пользователь
            user1.setFirstName("f1");
            user1.setLastName("l1");
            user1.setAge(30);
            user1.setRole(role1);
            // Сохраняем пользователя в базу
            session.persist(user1);
            // Создание нового построителя заготовки запроса
            CriteriaQuery<User> cu = cb.createQuery(User.class);
            Root<User> userRoot = cu.from(User.class);
            // если набор предикатов переменный, то можно собрать их все в список,
            // в итоге добавив в модель запроса
            // List<Predicate> predicates = new ArrayList<>();
            cu.select(userRoot);
            // загружать объект роли пользователя в поле role
            userRoot.fetch("role");
            // условие: ищем пользователя с ИД 1
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
        /* System.out.println(roles.size());
        roles.forEach(System.out::println); */
    }
}
