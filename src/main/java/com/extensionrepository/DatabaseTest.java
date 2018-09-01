package com.extensionrepository;

import com.extensionrepository.entity.Extension;
import com.extensionrepository.entity.Role;
import com.extensionrepository.entity.Tag;
import com.extensionrepository.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DatabaseTest {
    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Extension.class)
                .addAnnotatedClass(Tag.class)
                .addAnnotatedClass(Role.class)
                .buildSessionFactory();

        Session session = factory.openSession();
        session.beginTransaction();

        Extension t = session.get(Extension.class, 37);
        for (Tag tagg : t.getTags()) {
            System.out.println(tagg.getName() + " ");
        }

        session.getTransaction().commit();
        session.close();
    }
}