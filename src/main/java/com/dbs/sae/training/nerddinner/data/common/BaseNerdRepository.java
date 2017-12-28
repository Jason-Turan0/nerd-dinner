package com.dbs.sae.training.nerddinner.data.common;

import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.function.BiConsumer;

public interface BaseNerdRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    <TValue> T findOneByPropertyValue(
            Class<T> modelClass,
            TValue val,
            BiConsumer<T, TValue> setter);

}
