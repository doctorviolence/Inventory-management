package main.java.dao;

import javafx.scene.chart.XYChart;
import main.java.entities.Order;

public interface OrderDaoInterface extends BaseDaoInterface<Order> {

    XYChart.Series populateLineChart();

}
