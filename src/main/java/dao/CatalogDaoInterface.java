package main.java.dao;

import main.java.entities.Catalog;

import java.util.List;

public interface CatalogDaoInterface extends BaseDaoInterface<Catalog> {

    List<Catalog> findCatalogsByVendor(int vendorId);

}
