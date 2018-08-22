package com.extensionrepository;

import com.extensionrepository.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DatabaseTest {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)

                .buildSessionFactory();

        Session session = factory.openSession();
        session.beginTransaction();

        User u = session.get(User.class, 1);
        System.out.println(u.getUsername());

        session.getTransaction().commit();
        session.close();
    }
}
