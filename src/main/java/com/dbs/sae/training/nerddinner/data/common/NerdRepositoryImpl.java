package com.dbs.sae.training.nerddinner.data.common;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.function.BiConsumer;

public class NerdRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID>
        implements BaseNerdRepository<T, ID> {

    private final EntityManager entityManager;
    private final Class<T> modelClass;

    public NerdRepositoryImpl(Class<T> modelClass, EntityManager entityManager) {
        super(modelClass, entityManager);
        this.entityManager = entityManager;
        this.modelClass = modelClass;
    }

    public <TValue> T findOneByPropertyValue(
            Class<T> modelClass,
            TValue val,
            BiConsumer<T, TValue> setter) {
        try {
            T model = modelClass.newInstance();
            setter.accept(model, val);
            T found = this.findOne(Example.of(model));
            return found;
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
