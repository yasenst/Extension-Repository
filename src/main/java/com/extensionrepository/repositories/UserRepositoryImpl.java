package com.extensionrepository.repositories;

import com.extensionrepository.entity.User;
import com.extensionrepository.repositories.base.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private SessionFactory factory;

    public UserRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();

        try (Session session = factory.openSession()){
            session.beginTransaction();

            Query query = session.createQuery("FROM User");
            users = query.list();

            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public User getById(int id) {
        User user = null;

        try (Session session = factory.openSession()) {
            session.beginTransaction();
            user = (User) session.get(User.class, id);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }

        return user;
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

    /*
    @Override
    public void changeStatus(int id) {
        User user = null;

        try (Session session = factory.openSession()) {
            session.beginTransaction();

            user = (User) session.get(User.class, id);

            if (user.getEnabled()) {
                user.setEnabled(false);
            } else {
                user.setEnabled(true);
            }

            session.update(user);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
    @Override
    public boolean isExistUsername(String username) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            Query query = session.createQuery("from User where username=:uname");
            query.setParameter("uname",username);
            if (query.uniqueResult() != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void update(User user) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
