package com.dbs.sae.training.nerddinner.data.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.function.BiConsumer;

public interface BaseNerdRepository<TModel, ID extends Serializable> extends JpaRepository<TModel, ID> {

    default <TValue> TModel findOneByPropertyValue(
            Class<TModel> modelClass,
            TValue val,
            BiConsumer<TModel, TValue> setter) {
        try {
            TModel model = modelClass.newInstance();
            setter.accept(model, val);
            TModel found = this.findOne(Example.of(model));
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
