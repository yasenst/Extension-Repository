package com.extensionrepository.repositories;

import com.extensionrepository.entity.Extension;
import com.extensionrepository.entity.User;
import com.extensionrepository.repositories.base.AdminRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class AdminRepositoryImpl implements AdminRepository {

    private SessionFactory factory;

    public AdminRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void enableUser(String name) {

        try(Session session = factory.openSession()){
            session.beginTransaction();
            User user = session.get(User.class, name);
            user.setEnabled(true);
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void disableUser(String name) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            User user = session.get(User.class, name);
            user.setEnabled(false);
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void approveExtension(int id) {

    }

    @Override
    public void editExtension(int id) {

    }

    @Override
    public void deleteExtension(Extension extension) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(extension);
            System.out.println(extension.getName() + "was deleted!");
            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
