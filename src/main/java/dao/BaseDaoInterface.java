package main.java.dao;

import java.util.List;

public interface BaseDaoInterface<T> {

    <T> List<T> getEntities(Class<T> entity);

    T getEntityById(Class<T> entity, int id);

    List<T> query(String hsql);

    void createEntity(final T entity);

    void updateEntity(final T entity);

    void deleteEntity(final T entity);

}
