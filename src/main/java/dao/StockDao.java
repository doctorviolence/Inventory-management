package main.java.dao;

import javafx.scene.chart.XYChart;
import main.java.entities.Stock;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class StockDao extends BaseDao<Stock> implements StockDaoInterface {

    /**
     * Native SQL Query to find items in stock
     */
    @SuppressWarnings("unchecked")
    public List<Stock> getItemsInStock() {
        List<Object[]> rows = new ArrayList<>();
        List<Stock> stockList = new ArrayList<>();
        Session sess = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = sess.beginTransaction();
            rows = sess.createNativeQuery("SELECT s.stock_id, i.item_name, sum(s.qty) as qty, s.expiration_date " +
                    "FROM stock_levels s, items i, orders o " +
                    "WHERE o.order_id = s.order_id " +
                    "AND i.item_id = o.item_id " +
                    "GROUP BY i.item_name, s.stock_id; ")
                    .list();
            for (Object[] row : rows) {
                int id = Integer.parseInt(row[0].toString());
                String name = row[1].toString();
                int qty = Integer.parseInt(row[2].toString());
                String date = row[3].toString();
                Stock stock = new Stock(id, name, qty, date);
                stockList.add(stock);
            }
            tx.commit();
            return stockList;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public List<Stock> getItemsShortInStock() {
        List<Stock> itemsLowInStock = new ArrayList();
        List<Object[]> rows = new ArrayList<>();
        Session sess = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = sess.beginTransaction();
            rows = sess.createNativeQuery("SELECT i.item_name, SUM(s.qty) AS qty " +
                    "FROM stock_levels s, items i, orders o " +
                    "WHERE i.item_id = o.item_id " +
                    "AND o.order_id = s.order_id " +
                    "GROUP BY i.item_name " +
                    "HAVING qty < 3;")
                    .list();
            for (Object[] row : rows) {
                String name = row[0].toString();
                int qty = Integer.parseInt(row[1].toString());
                Stock stock = new Stock(name, qty);
                itemsLowInStock.add(stock);
            }
            return itemsLowInStock;
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
    @SuppressWarnings("unchecked")
    public XYChart.Series getItemsInStockChart() {
        List<Object[]> rows = new ArrayList<>();
        XYChart.Series series = new XYChart.Series();
        Session sess = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = sess.beginTransaction();
            rows = sess.createNativeQuery("SELECT sum(s.qty) as qty, i.item_name " +
                    "FROM stock_levels s, items i, orders o " +
                    "WHERE o.order_id = s.order_id " +
                    "AND i.item_id = o.item_id " +
                    "GROUP BY i.item_name")
                    .list();
            for (Object[] row : rows) {
                int qty = Integer.parseInt(row[0].toString());
                String name = row[1].toString();
                series.getData().add(new XYChart.Data<>(name, qty));
            }
            tx.commit();
            return series;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void removeItemFromStock(int id) {
        Session sess = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = sess.beginTransaction();
            Stock stock = getEntityById(Stock.class, id);
            int qty = stock.getQuantity();
            int removeStock = qty - 1;
            stock.setQuantity(removeStock);
            if (qty <= 1) {
                sess.delete(stock);
            } else {
                sess.update(stock);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

}
