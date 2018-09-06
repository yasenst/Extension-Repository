package com.extensionrepository.repository;

import com.extensionrepository.entity.Extension;
import com.extensionrepository.repository.base.ExtensionRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ExtensionRepositoryImpl implements ExtensionRepository {

    private SessionFactory factory;

    public ExtensionRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Extension> getAll() {
        List<Extension> extensions = new ArrayList<>();

        try (Session session = factory.openSession()) {
            session.beginTransaction();
            extensions = session.createQuery("From Extension where pending = false").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return extensions;
    }

    @Override
    public boolean save(Extension extension) {
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

    @Override
    public Extension getById(int id) {
        Extension extension = null;

        try (Session session = factory.openSession()) {
            session.beginTransaction();
            extension = (Extension) session.get(Extension.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return extension;
    }

    @Override
    public Extension getByName(String name) {
        Extension extension = null;
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            Query query= session.createQuery("from Extension where name=:rname");
            query.setParameter("rname",name);
            extension = (Extension) query.uniqueResult();

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }

        return extension;
    }

    @Override
    public Extension update(Extension extension) {
        try (Session session = factory.openSession()) {

            session.beginTransaction();
            session.update(extension);
            session.getTransaction().commit();
            return extension;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return extension;
        }

    }

    @Override
    public Extension delete(Extension extension) {
        try (Session session = factory.openSession()) {

            session.beginTransaction();
            session.delete(extension);
            session.getTransaction().commit();

            return extension;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public List<Extension> getPending() {
        List<Extension> extensions = new ArrayList<>();

        try (Session session = factory.openSession()) {
            session.beginTransaction();
            extensions = session.createQuery("From Extension WHERE pending = true").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extensions;

    }


    @Override
    public List<Extension> filterByName(String name) {
        List<Extension> extensions = new ArrayList<>();

        try (Session session = factory.openSession()) {
            session.beginTransaction();

            if (name == null) {
                extensions = session.
                        createQuery("FROM Extension WHERE pending = false ORDER BY name ASC")
                        .list();
            } else {
                extensions = session.createQuery(
                        "FROM Extension " +
                                "WHERE pending = false " +
                                "AND name LIKE:searchName " +
                                "ORDER BY name ASC")
                        .setParameter("searchName", "%" + name.trim() + "%")
                        .list();
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return extensions;
    }

    @Override
    public List<Extension> filterByDate(String name) {
        List<Extension> extensions = new ArrayList<>();

        try (Session session = factory.openSession()) {
            session.beginTransaction();

            if (name == null) {
                extensions = session.
                        createQuery("FROM Extension WHERE pending = false ORDER BY date DESC")
                        .list();
            } else {
                extensions = session.createQuery(
                        "FROM Extension " +
                                "WHERE pending = false " +
                                "AND name LIKE:searchName " +
                                "ORDER BY date DESC")
                        .setParameter("searchName", "%" + name.trim() + "%")
                        .list();
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return extensions;
    }

    @Override
    public List<Extension> filterByDownloads(String name) {
        List<Extension> extensions = new ArrayList<>();

        try (Session session = factory.openSession()) {
            session.beginTransaction();

            if (name == null) {
                extensions = session.
                        createQuery("FROM Extension WHERE pending = false ORDER BY numberOfDownloads DESC")
                        .list();
            } else {
                extensions = session.createQuery(
                        "FROM Extension " +
                                "WHERE pending = false " +
                                "AND name LIKE:searchName " +
                                "ORDER BY numberOfDownloads DESC")
                        .setParameter("searchName", "%" + name.trim() + "%")
                        .list();
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return extensions;
    }

    @Override
    public List<Extension> filterByLastCommit(String name) {
        List<Extension> extensions = new ArrayList<>();

        try (Session session = factory.openSession()) {
            session.beginTransaction();

            if (name == null) {
                extensions = session.
                        createQuery("FROM Extension WHERE pending = false ORDER BY lastCommit DESC")
                        .list();
            } else {
                extensions = session.createQuery(
                        "FROM Extension " +
                                "WHERE pending = false " +
                                "AND name LIKE:searchName " +
                                "ORDER BY lastCommit DESC")
                        .setParameter("searchName", "%" + name.trim() + "%")
                        .list();
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return extensions;
    }

    @Override
    public List<Extension> getNewest() {
        List<Extension> extensions = new ArrayList<>();

        try (Session session = factory.openSession()) {
            session.beginTransaction();

            extensions = session.
                    createQuery("FROM Extension WHERE pending = false ORDER BY date DESC")
                    .list();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return extensions;
    }

    @Override
    public List<Extension> getPopular() {
        List<Extension> extensions = new ArrayList<>();

        try (Session session = factory.openSession()) {
            session.beginTransaction();

            extensions = session.
                    createQuery("FROM Extension WHERE pending = false ORDER BY numberOfDownloads DESC")
                    .list();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return extensions;
    }

    @Override
    public List<Extension> getFeatured() {
        List<Extension> extensions = new ArrayList<>();

        try (Session session = factory.openSession()) {
            session.beginTransaction();

            extensions = session.
                    createQuery("FROM Extension WHERE featured = true ORDER BY numberOfDownloads DESC")
                    .list();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return extensions;
    }

}