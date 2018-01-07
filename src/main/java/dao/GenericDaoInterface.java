package main.java.dao;

import java.util.List;

/**
 * Created by joakimlindvall on 2017-11-07.
 */
public interface GenericDaoInterface<T> {

    <T> List<T> getEntities(Class<T> entity);

    List<T> query(String hsql);

    void createEntity(final T entity);

    void updateEntity(final T entity);

    void deleteEntity(final T entity);

}
