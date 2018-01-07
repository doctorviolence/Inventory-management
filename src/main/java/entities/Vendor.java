package main.java.entities;

import javafx.beans.property.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendors")
public class Vendor {

    public Vendor(){
    }

    public Vendor(int id){
        setVendorId(id);
    }

    public Vendor(int id, String name, String address, String phone, String email, String website){
        setVendorId(id);
        setVendorName(name);
        setAddress(address);
        setPhone(phone);
        setEmail(email);
        setWebsite(website);
    }

    private final IntegerProperty vendorId = new SimpleIntegerProperty(this, "vendorId");

    public IntegerProperty vendorIdProperty(){
        return vendorId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id", updatable = false, nullable = false)
    public int getVendorId(){
        return vendorId.get();
    }

    public void setVendorId(int value){
        vendorId.set(value);
    }

    private final StringProperty vendorName = new SimpleStringProperty(this, "vendorName");

    public StringProperty vendorNameProperty(){
        return vendorName;
    }

    @Basic
    @Column(name = "vendor_name")
    public String getVendorName(){
        return vendorName.get();
    }

    public void setVendorName(String value){
        vendorName.set(value);
    }

    private final StringProperty address = new SimpleStringProperty();

    public StringProperty addressProperty(){
        return address;
    }

    @Basic
    @Column(name = "address")
    public String getAddress(){
        return address.get();
    }

    public void setAddress(String value){
        address.set(value);
    }

    private final StringProperty phone = new SimpleStringProperty();

    public StringProperty phoneProperty(){
        return phone;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone(){
        return phone.get();
    }

    public void setPhone(String value){
        phone.set(value);
    }

    private final StringProperty website = new SimpleStringProperty();

    public StringProperty websiteProperty(){
        return website;
    }

    @Basic
    @Column(name = "website")
    public String getWebsite(){
        return website.get();
    }

    public void setWebsite(String value){
        website.set(value);
    }

    private final StringProperty email = new SimpleStringProperty();

    public StringProperty emailProperty(){
        return email;
    }

    @Basic
    @Column(name = "email")
    public String getEmail(){
        return email.get();
    }

    public void setEmail(String value){
        email.set(value);
    }

    private List<Catalog> catalogs = new ArrayList<>();

    @OneToMany(targetEntity=Catalog.class, cascade = CascadeType.ALL, mappedBy = "vendor")
    public List<Catalog> getCatalogs(){
        return catalogs;
    }

    public void setCatalogs(List<Catalog> catalogs){
        this.catalogs.addAll(catalogs);
    }

}
