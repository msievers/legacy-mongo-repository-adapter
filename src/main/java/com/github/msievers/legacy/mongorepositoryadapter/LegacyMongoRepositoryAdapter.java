package com.github.msievers.legacy.mongorepositoryadapter;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface LegacyMongoRepositoryAdapter<T, ID extends Serializable> extends MongoRepository<T, ID> {

    // org.springframework.data.mongodb.repository.support.SimpleMongoRepository.convertIterableToList
    static <T> List<T> convertIterableToList(Iterable<T> entities) {

        if (entities instanceof List) {
            return (List<T>) entities;
        }

        int capacity = tryDetermineRealSizeOrReturn(entities, 10);

        if (capacity == 0 || entities == null) {
            return Collections.emptyList();
        }

        List<T> list = new ArrayList<>(capacity);
        for (T entity : entities) {
            list.add(entity);
        }

        return list;
    }

    // org.springframework.data.mongodb.repository.support.SimpleMongoRepository.tryDetermineRealSizeOrReturn
    static int tryDetermineRealSizeOrReturn(Iterable<?> iterable, int defaultSize) {
        return iterable == null ? 0 : (iterable instanceof Collection) ? ((Collection<?>) iterable).size() : defaultSize;
    }

    @Deprecated
    default void delete(ID id) {

        deleteByIdIn(Collections.singletonList(id));
    }

    @Deprecated
    default void delete(Iterable<? extends T> entities) {

        for (T entity : entities) {
            delete(entity);
        }
    }

    void deleteByIdIn(Iterable<ID> ids);

    @Deprecated
    default boolean exists(ID id) {

        return existsById(id);
    }

    boolean existsById(ID id);

    @Deprecated
    default List<T> findAll(Iterable<ID> ids) {

        return findByIdIn(ids);
    }

    List<T> findByIdIn(Iterable<ID> ids);

    Optional<T> findById(ID id);

    @Deprecated
    default T findOne(ID id) {

        return findById(id).orElse(null);
    }

    // org.springframework.data.mongodb.repository.support.SimpleMongoRepository.save(java.lang.Iterable<S>)
    @Deprecated
    default <S extends T> List<S> save(Iterable<S> entities) {

        Assert.notNull(entities, "The given Iterable of entities not be null!");

        List<S> result = convertIterableToList(entities);

        for (S entity : result) {
            save(entity);
        }

        return result;
    }
}
