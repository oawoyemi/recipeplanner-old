package com.bulbview.recipeplanner.persistence;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.Query;

public class JdoDao<T> {

    protected final Logger   logger;
    private final Class<T>   entityClass;

    @Autowired
    private ObjectifyFactory objectifyFactory;

    public JdoDao(final Class<T> entityClass) {
        this.entityClass = entityClass;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public T get(final String name) {
        final Objectify objectify = beginObjectify();
        return objectify.get(entityClass, name);
    }

    public Collection<T> getAll() {
        final Objectify objectify = beginObjectify();
        final Query<T> query = objectify.query(entityClass);
        return query.list();
    }

    @PostConstruct
    public void registerEntityType() {
        logger.info("Registering datastore entity type: {}", entityClass.getName());
        objectifyFactory.register(entityClass);

    }

    public void save(final T entity) {
        logger.debug("Saving recipe: {}...", entity);
        final Objectify objectify = beginObjectify();
        objectify.put(entity);

    }

    private Objectify beginObjectify() {
        return objectifyFactory.begin();
    }

}