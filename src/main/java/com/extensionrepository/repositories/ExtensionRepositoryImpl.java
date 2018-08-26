package com.extensionrepository.repositories;

import com.extensionrepository.entity.Extension;
import com.extensionrepository.repositories.base.ExtensionRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ExtensionRepositoryImpl implements ExtensionRepository {

    private SessionFactory factory;

    public ExtensionRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Extension> getAll() {
        List<Extension> predictions = new ArrayList<>();

        try(Session session = factory.openSession()) {
            session.beginTransaction();
            predictions = session.createQuery("From Extension").list();
            session.getTransaction().commit();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        /*
        if (predictions != null) {
            Collections.sort(predictions, new Comparator<Prediction>() {
                @Override
                public int compare(Prediction o1, Prediction o2) {
                    return o2.getDate().compareTo(o1.getDate());
                }
            });
        }
        */
        return predictions;
    }

    @Override
    public boolean saveExtension(Extension extension) {
        try (Session session = factory.openSession()) {

            session.beginTransaction();
            session.save(extension);
            session.getTransaction().commit();

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
