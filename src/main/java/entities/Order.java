package main.java.entities;

import javafx.beans.property.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joakimlindvall on 2017-10-28.
 */
@Entity
@Table(name = "orders")
public class Order {

    public Order(){

    }

    public Order(int id){
        setOrderId(id);
    }

    private final IntegerProperty orderId = new SimpleIntegerProperty();

    public IntegerProperty orderIdProperty(){
        return orderId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", updatable = false, nullable = false)
    public int getOrderId(){
        return orderId.get();
    }

    public void setOrderId(int value){
        orderId.set(value);
    }

    private final StringProperty orderDate = new SimpleStringProperty();

    public StringProperty orderDateProperty(){
        return orderDate;
    }

    @Basic
    @Column(name = "order_date")
    public String getOrderDate(){
        return orderDate.get();
    }

    public void setOrderDate(String value){
        orderDate.set(value);
    }

    private List<Item> items = new ArrayList<>();

    @OneToMany(targetEntity=Item.class, cascade = CascadeType.ALL, mappedBy = "order")
    public List<Item> getItems(){
        return items;
    }

    public void setItems(List<Item> items){
        this.items.addAll(items);
    }

    private final ObjectProperty<Vendor> vendor = new SimpleObjectProperty<>();

    public ObjectProperty<Vendor> vendorProperty(){
        return vendor;
    }

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    public Vendor getVendor(){
        return vendor.get();
    }

    public void setVendor(Vendor vendor){
        this.vendor.set(vendor);
    }

}
