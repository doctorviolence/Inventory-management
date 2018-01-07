package main.java.dao;

import main.java.entities.Item;

import java.util.List;

public interface ItemDaoInterface extends BaseDaoInterface<Item> {

    int getTotalCost(int orderId);

    List<Item> findItemsInOrder(int orderId);

    List<Item> findItemsInCatalog(int catalogId);

    List<Item> getTopFiveItemsBySpending();

    //XYChart.Series costPercentageChart();

}
