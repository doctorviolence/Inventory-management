package main.java.dao;

import main.java.entities.Catalog;
import main.java.entities.Item;
import main.java.entities.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ItemDao extends BaseDao<Item> implements ItemDaoInterface {

    /**
     * Calculate total cost of items per order
     */
    @SuppressWarnings("unchecked")
    public int getTotalCost(int orderId) {
        int totalCost = 0;
        for (Item i : findItemsInOrder(orderId)) {
            totalCost += i.getPurchasePrice();
        }
        return totalCost;
    }

    /**
     * Find items pertaining to a particular order
     */
    @SuppressWarnings("unchecked")
    public List<Item> findItemsInOrder(int orderId) {
        List<Item> queryList = new ArrayList<>();
        Session sess = sessionFactory.openSession();
        try {
            String hql = "FROM Item i, OrderDetails o WHERE o.order = :orderId " +
                    "AND i.orderDetails = o.items ";
            Order order = new Order(orderId);
            queryList = sess.createQuery(hql)
                    .setParameter("orderId", order)
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

    /**
     * Find items pertaining to a particular catalog
     */
    @SuppressWarnings("unchecked")
    public List<Item> findItemsInCatalog(int catalogId) {
        List<Item> queryList = new ArrayList<>();
        Session sess = sessionFactory.openSession();
        try {
            String hql = "FROM Item item WHERE item.catalog = :catalogId ";
            Catalog catalog = new Catalog(catalogId);
            queryList = sess.createQuery(hql)
                    .setParameter("catalogId", catalog)
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

    /**
     * Get top five item names, by spending
     */
    @SuppressWarnings("unchecked")
    public List<Item> getTopFiveItemsBySpending() {
        Session sess = sessionFactory.openSession();
        List<Item> topItems = new ArrayList();
        List<Object[]> rows = new ArrayList<>();
        Transaction tx = null;
        try {
            tx = sess.beginTransaction();
            rows = sess.createNativeQuery("SELECT i.item_name, SUM(o.total_cost) AS cost " +
                    "FROM items i, orders o " +
                    "WHERE i.item_id = o.item_id " +
                    "GROUP BY i.item_name " +
                    "ORDER BY cost DESC LIMIT 5;")
                    .list();
            for (Object[] row : rows) {
                String name = row[0].toString();
                Item item = new Item(name);
                topItems.add(item);
            }
            return topItems;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    /**
     * Calculate cost of items as percentage
     */
    /*
    @SuppressWarnings("unchecked")
    public XYChart.Series costPercentageChart() {
        List<Item> queryList = new ArrayList<>();
        Session sess = sessionFactory.openSession();
        XYChart.Series series = new XYChart.Series();
        try {
            String hql = "FROM Item item WHERE item.order = :orderId ";
            queryList = sess.createQuery(hql)
                    .setParameter("orderId", order)
                    .list();
            for (Item i : queryList) {
                String itemName = i.getItemName();
                int cost = i.getPurchasePrice() * 100 / getTotalCost(orderId);
                series.getData().add(new XYChart.Data<>(itemName, cost));
            }
            return series;
        } catch (Exception e) {
            throw e;
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }*/

}
