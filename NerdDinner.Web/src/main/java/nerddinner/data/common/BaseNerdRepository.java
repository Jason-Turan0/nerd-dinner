package nerddinner.data.common;

import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface BaseNerdRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    T findOneByExample(
            Class<T> modelClass,
            Consumer<T> setter);

    <TValue> T findOneByPropertyValue(
            Class<T> modelClass,
            TValue val,
            BiConsumer<T, TValue> setter);

    <TValue> List<T> findAllByPropertyValue(
            Class<T> modelClass,
            TValue val,
            BiConsumer<T, TValue> setter);

    List<T> findAllByExample(
            Class<T> modelClass,
            Consumer<T> setter);

}
