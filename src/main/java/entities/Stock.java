package main.java.entities;

import javafx.beans.property.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "stock_levels")
public class Stock implements Serializable {

    public Stock() {

    }

    public Stock(String itemName, int qty) {
        this.setItemName(itemName);
        this.setQuantity(qty);
    }

    public Stock(int id, String itemName, int qty, String date) {
        this.setStockId(id);
        this.setQuantity(qty);
        this.setItemName(itemName);
        this.setExpirationDate(date);
    }

    private final IntegerProperty stockId = new SimpleIntegerProperty();

    public IntegerProperty stockIdProperty() {
        return stockId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id", nullable = false, updatable = false)
    public int getStockId() {
        return stockId.get();
    }

    public void setStockId(int value) {
        stockId.set(value);
    }

    private final IntegerProperty quantity = new SimpleIntegerProperty();

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    @Basic
    @Column(name = "qty")
    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int value) {
        quantity.set(value);
    }

    private final StringProperty itemName = new SimpleStringProperty();

    public StringProperty itemNameProperty() {
        return itemName;
    }

    @Basic
    @Column(name = "item_name")
    public String getItemName() {
        return itemName.get();
    }

    public void setItemName(String value) {
        itemName.set(value);
    }

    private final StringProperty expirationDate = new SimpleStringProperty();

    public StringProperty expirationDateProperty() {
        return expirationDate;
    }

    @Basic
    @Column(name = "expiration_date")
    public String getExpirationDate() {
        return expirationDate.get();
    }

    public void setExpirationDate(String value) {
        expirationDate.set(value);
    }

    private final ObjectProperty<Order> order = new SimpleObjectProperty<>();

    public ObjectProperty<Order> orderProperty() {
        return order;
    }

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    public Order getOrder() {
        return order.get();
    }

    public void setOrder(Order order) {
        this.order.set(order);
    }

    @Override
    public String toString() {
        return "Stock ID: " + stockId + " Qty: " + quantity + " Item Name: " + itemName + " Expiration date: " + expirationDate;
    }

}
