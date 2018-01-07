package main.java.dao;

import javafx.collections.FXCollections;
import main.java.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by joakimlindvall on 2017-11-07.
 */
public class GenericDao<T> implements GenericDaoInterface<T> {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @SuppressWarnings("unchecked")
    public <T> List<T> getEntities(Class<T> entity) {
        List<T> entities = FXCollections.observableArrayList();
        Session sess = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = sess.beginTransaction();
            entities.addAll(sess.createQuery("FROM " + entity.getName()).list());
            return entities;
        } catch(Exception e){
            if(tx != null){
                tx.rollback();
            }
            throw e;
        } finally{
            sess.close();
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> query(String hsql){
        List<T> queryList = FXCollections.observableArrayList();
        Session sess = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = sess.beginTransaction();
            queryList = sess.createQuery(hsql).list();
            return queryList;
        } catch(Exception e){
            if(tx != null){
                tx.rollback();
            }
            throw e;
        } finally{
            sess.close();
        }

    }

    @SuppressWarnings("unchecked")
    public void createEntity(T entity){
        Session sess = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = sess.beginTransaction();
            sess.save(entity);
            tx.commit();
        } catch(Exception e){
            if(tx != null){
                tx.rollback();
            }
            throw e;
        } finally{
            sess.close();
        }
    }

    @SuppressWarnings("unchecked")
    public void updateEntity(T entity){
        Session sess = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = sess.beginTransaction();
            sess.update(entity);
            tx.commit();
        } catch(Exception e){
            if(tx != null){
                tx.rollback();
            }
            throw e;
        } finally{
            sess.close();
        }
    }

    @SuppressWarnings("unchecked")
    public void deleteEntity(T entity){
        Session sess = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = sess.beginTransaction();
            sess.delete(entity);
            tx.commit();
        } catch(Exception e){
            if (tx != null){
                tx.rollback();
            }
            throw e;
        } finally{
            sess.close();
        }
    }

}
