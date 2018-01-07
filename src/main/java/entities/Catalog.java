package main.java.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "catalogs")
public class Catalog {

    public Catalog() {

    }

    public Catalog(int catalogId) {
        this.setCatalogId(catalogId);
    }

    private final IntegerProperty catalogId = new SimpleIntegerProperty();

    public IntegerProperty catalogIdProperty() {
        return catalogId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catalog_id", updatable = false, nullable = false)
    public int getCatalogId() {
        return catalogId.get();
    }

    public void setCatalogId(int value) {
        catalogId.set(value);
    }

    private final ObjectProperty<Vendor> vendor = new SimpleObjectProperty<>();

    public ObjectProperty<Vendor> vendorProperty() {
        return vendor;
    }

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    public Vendor getVendor() {
        return vendor.get();
    }

    public void setVendor(Vendor vendor) {
        this.vendor.set(vendor);
    }

    private List<Item> items = new ArrayList<>();

    @OneToMany(targetEntity = Item.class, cascade = CascadeType.ALL, mappedBy = "catalog")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items.addAll(items);
    }

}
