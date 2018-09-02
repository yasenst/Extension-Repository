package com.extensionrepository.repositories;

import com.extensionrepository.entity.Tag;
import com.extensionrepository.repositories.base.TagRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class TagRepositoryImpl implements TagRepository {

    private SessionFactory factory;

    public TagRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Tag findByName(String name) {
        Tag tag = null;
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            Query query= session.createQuery("from Tag where name=:rname");
            query.setParameter("rname",name);
            tag = (Tag)query.uniqueResult();

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }

        return tag;
    }

    @Override
    public void save(Tag tag) {
        try (Session session = factory.openSession()) {

            session.beginTransaction();
            session.save(tag);
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
