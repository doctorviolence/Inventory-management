package main.java.dao;

import javafx.scene.chart.XYChart;
import main.java.entities.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class OrderDao extends BaseDao<Order> implements OrderDaoInterface {


    /**
     * Native SQL Query to populate line chart
     * x-axis: order_date
     * y-axis: getTotalCost (ItemDao)
     * NOTE: Fix this so it includes CalendarTable
     */
    @SuppressWarnings("unchecked")
    public XYChart.Series populateLineChart() {
        List<Object[]> rows = new ArrayList<>();
        XYChart.Series series = new XYChart.Series();
        Session sess = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = sess.beginTransaction();
            rows = sess.createNativeQuery("SELECT SUM(total_cost) AS total_cost, order_date As date " +
                    "FROM items, orders " +
                    "WHERE items.item_id = orders.item_id " +
                    "GROUP BY date")
                    .list();
            for (Object[] row : rows) {
                int cost = Integer.parseInt(row[0].toString());
                String date = row[1].toString();
                series.getData().add(new XYChart.Data<>(date, cost));
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

    /**
     * Get top three months, by spending
     */

    /**
     *  Stock algorithm:
     *  Trend analysis
     *  (https://www.cliffsnotes.com/study-guides/accounting/accounting-principles-ii/financial-statement-analysis/trend-analysis)
     */
}
