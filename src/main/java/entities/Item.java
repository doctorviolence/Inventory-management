package main.java.entities;

import javafx.beans.property.*;

import javax.persistence.*;

/**
 * Created by joakimlindvall on 2017-10-28.
 */

@Entity
@Table(name = "items")
public class Item {

    public Item(){

    }

    private final IntegerProperty itemId = new SimpleIntegerProperty();

    public IntegerProperty itemIdProperty(){
        return itemId;
    }

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", updatable = false, nullable = false)
    public int getItemId(){
        return itemId.get();
    }

    public void setItemId(int value){
        itemId.set(value);
    }

    private final StringProperty itemName = new SimpleStringProperty();

    public StringProperty itemNameProperty(){
        return itemName;
    }

    @Basic
    @Column(name = "item_name")
    public String getItemName(){
        return itemName.get();
    }

    public void setItemName(String value){
        itemName.set(value);
    }

    private final StringProperty category = new SimpleStringProperty();

    public StringProperty categoryProperty(){
        return category;
    }

    @Basic
    @Column(name = "category")
    public String getCategory(){
        return category.get();
    }

    public void setCategory(String value){
        category.set(value);
    }

    private final IntegerProperty quantity = new SimpleIntegerProperty();

    public IntegerProperty quantityProperty(){
        return quantity;
    }

    @Basic
    @Column(name = "qty")
    public int getQuantity(){
        return quantity.get();
    }

    public void setQuantity(int value){
        quantity.set(value);
    }

    private final IntegerProperty purchasePrice = new SimpleIntegerProperty();

    public IntegerProperty purchasePriceProperty(){
        return purchasePrice;
    }

    @Basic
    @Column(name = "purchase_price")
    public int getPurchasePrice(){
        return purchasePrice.get();
    }

    public void setPurchasePrice(int value){
        purchasePrice.set(value);
    }

    private final ObjectProperty<Order> order = new SimpleObjectProperty<>();

    public ObjectProperty<Order> orderProperty(){
        return order;
    }

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    public Order getOrder(){
        return order.get();
    }

    public void setOrder(Order order){
        this.order.set(order);
    }


}
