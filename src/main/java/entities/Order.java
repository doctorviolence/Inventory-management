package main.java.entities;

import javafx.beans.property.*;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    public Order() {

    }

    public Order(int id) {
        setOrderId(id);
    }

    private final IntegerProperty orderId = new SimpleIntegerProperty();

    public IntegerProperty orderIdProperty() {
        return orderId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", updatable = false, nullable = false)
    public int getOrderId() {
        return orderId.get();
    }

    public void setOrderId(int value) {
        orderId.set(value);
    }

    private final StringProperty orderDate = new SimpleStringProperty();

    public StringProperty orderDateProperty() {
        return orderDate;
    }

    @Basic
    @Column(name = "order_date")
    public String getOrderDate() {
        return orderDate.get();
    }

    public void setOrderDate(String value) {
        orderDate.set(value);
    }

    private final IntegerProperty totalCost = new SimpleIntegerProperty();

    public IntegerProperty totalCostProperty() {
        return totalCost;
    }

    @Basic
    @Column(name = "total_cost")
    public int getTotalCost() {
        return totalCost.get();
    }

    public void setTotalCost(int value) {
        totalCost.set(value);
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

    private final ObjectProperty<Item> item = new SimpleObjectProperty<>();

    public ObjectProperty<Item> itemProperty() {
        return item;
    }

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    public Item getItem() {
        return item.get();
    }

    public void setItem(Item item) {
        this.item.set(item);
    }

    private final ObjectProperty<Stock> stock = new SimpleObjectProperty<>();

    public ObjectProperty<Stock> stockProperty() {
        return stock;
    }

    @OneToOne(targetEntity = Stock.class, cascade = CascadeType.ALL, mappedBy = "order")
    public Stock getStock() {
        return stock.get();
    }

    public void setStock(Stock stock) {
        this.stock.set(stock);
    }


}
