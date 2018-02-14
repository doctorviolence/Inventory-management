package main.java.entities;

import javafx.beans.property.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "items")
public class Item implements Serializable {

    public Item() {

    }

    public Item(int id) {
        this.setItemId(id);
    }

    public Item(String name) {
        this.setItemName(name);
    }

    private final IntegerProperty itemId = new SimpleIntegerProperty();

    public IntegerProperty itemIdProperty() {
        return itemId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", updatable = false, nullable = false)
    public int getItemId() {
        return itemId.get();
    }

    public void setItemId(int value) {
        itemId.set(value);
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

    private final IntegerProperty purchasePrice = new SimpleIntegerProperty();

    public IntegerProperty purchasePriceProperty() {
        return purchasePrice;
    }

    @Basic
    @Column(name = "cost")
    public int getPurchasePrice() {
        return purchasePrice.get();
    }

    public void setPurchasePrice(int value) {
        purchasePrice.set(value);
    }

    private final ObjectProperty<Catalog> catalog = new SimpleObjectProperty<>();

    public ObjectProperty<Catalog> catalogProperty() {
        return catalog;
    }

    @ManyToOne
    @JoinColumn(name = "catalog_id", nullable = false)
    public Catalog getCatalog() {
        return catalog.get();
    }

    public void setCatalog(Catalog catalog) {
        this.catalog.set(catalog);
    }

    private List<Order> orders = new ArrayList<>();

    @OneToMany(targetEntity = Order.class, cascade = CascadeType.ALL, mappedBy = "item", fetch=FetchType.LAZY)
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders.addAll(orders);
    }

    @Override
    public String toString() {
        return "Item ID: " + itemId + " Item Name: " + itemName + " Cost: " + purchasePrice;
    }

}
