package main.java.dao;

import main.java.entities.Catalog;
import main.java.entities.Vendor;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class CatalogDao extends BaseDao<Catalog> implements CatalogDaoInterface {

    /**
     * Find catalogs pertaining to a particular vendor
     */
    @SuppressWarnings("unchecked")
    public List<Catalog> findCatalogsByVendor(int vendorId) {
        List<Catalog> queryList = new ArrayList<>();
        Session sess = sessionFactory.openSession();
        try {
            String hql = "FROM Catalog catalog WHERE catalog.vendor = :vendorId ";
            Vendor vendor = new Vendor(vendorId);
            queryList = sess.createQuery(hql)
                    .setParameter("vendorId", vendor)
                    .list();
            return queryList;
        } catch (Exception e) {
            throw e;
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }
}
