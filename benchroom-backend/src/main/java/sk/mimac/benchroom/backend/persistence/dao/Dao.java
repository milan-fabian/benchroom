package sk.mimac.benchroom.backend.persistence.dao;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import sk.mimac.benchroom.backend.persistence.entity.EntityInterface;
import sk.mimac.benchroom.backend.persistence.query.QueryBuilder;

public interface Dao<T extends EntityInterface> {

    long insert(T entity);

    void update(T entity) throws EntityNotFoundException;

    T find(long id);

    List<T> getAll();
    
    List<T> getForFilter(QueryBuilder<T> queryBuilder);
    
    long countForFilter(QueryBuilder<T> queryBuilder);

}
