package com.extensionrepository.repositories;

import com.extensionrepository.entity.Role;
import com.extensionrepository.repositories.base.RoleRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private SessionFactory factory;

    public RoleRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Role findByName(String name) {
        Role role = null;
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            Query query= session.createQuery("from Role where name=:rname");
            query.setParameter("rname",name);
            role = (Role)query.uniqueResult();

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }

        return role;
    }
}