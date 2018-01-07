package main.java.dao;

import main.java.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class BaseDao<T> implements BaseDaoInterface<T> {

    protected SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @SuppressWarnings("unchecked")
    public T getEntityById(Class<T> entity, int id) {
        Session sess = sessionFactory.openSession();
        try {
            return (T) sess.get(entity.getName(), id);
        } catch (Exception e) {
            throw e;
        } finally {
            sess.close();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getEntities(Class<T> entity) {
        List<T> entities = new ArrayList();
        Session sess = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = sess.beginTransaction();
            entities.addAll(sess.createQuery("FROM " + entity.getName()).list());
            return entities;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            sess.close();
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> query(String hsql) {
        List<T> queryList = new ArrayList();
        Session sess = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = sess.beginTransaction();
            queryList = sess.createQuery(hsql).list();
            return queryList;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            sess.close();
        }

    }

    @SuppressWarnings("unchecked")
    public void createEntity(T entity) {
        Session sess = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = sess.beginTransaction();
            sess.save(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            sess.close();
        }
    }

    @SuppressWarnings("unchecked")
    public void updateEntity(T entity) {
        Session sess = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = sess.beginTransaction();
            sess.update(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            sess.close();
        }
    }

    @SuppressWarnings("unchecked")
    public void deleteEntity(T entity) {
        Session sess = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = sess.beginTransaction();
            sess.delete(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            sess.close();
        }
    }

}
