package main.java.dao;

import javafx.scene.chart.XYChart;
import main.java.entities.Stock;

import java.util.List;

public interface StockDaoInterface extends BaseDaoInterface<Stock> {

    List<Stock> getItemsInStock();

    List<Stock> getItemsShortInStock();

    XYChart.Series getItemsInStockChart();

    void removeItemFromStock(int id);

}
