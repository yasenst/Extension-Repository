package com.extensionrepository.repositories;

import com.extensionrepository.entity.User;
import com.extensionrepository.repositories.base.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private SessionFactory factory;

    public UserRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public User findByUsername(String username) {

        User user = null;
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            Query query = session.createQuery("from User where username=:uname");
            query.setParameter("uname", username);
            user = (User) query.uniqueResult();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public boolean registerUser(User user) {

        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();

            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}

