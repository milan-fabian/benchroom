package sk.mimac.benchroom.backend.persistence.dao.impl;

import java.util.List;
import javax.persistence.*;
import javax.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.mimac.benchroom.backend.persistence.dao.Dao;
import sk.mimac.benchroom.backend.persistence.entity.EntityInterface;
import sk.mimac.benchroom.backend.persistence.query.QueryBuilder;

public class AbstractDao<T extends EntityInterface> implements Dao<T> {

    protected final Logger logger;

    private final Class<T> clazz;

    @PersistenceContext
    protected EntityManager em;

    public AbstractDao(Class<T> clazz) {
        this.clazz = clazz;
        logger = LoggerFactory.getLogger(clazz);
    }

    @Override
    public long insert(T entity) {
        em.persist(entity);
        logger.info("Entity [id=" + entity.getId() + "] of class '" + clazz.getSimpleName() + "' inserted");
        return entity.getId();
    }

    @Override
    public void update(T entity) {
        T temp = em.find(clazz, entity.getId());
        if (temp == null) {
            throw new EntityNotFoundException("Entity [" + clazz.getName() + "] not found");
        }
        em.merge(entity);
        logger.info("Entity [id=" + entity.getId() + "] of class '" + clazz.getSimpleName() + "' updated");
    }

    @Override
    public T find(long id) {
        return em.find(clazz, id);
    }

    @Override
    public List<T> getAll() {
        CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(clazz);
        criteriaQuery.select(criteriaQuery.from(clazz));
        TypedQuery<T> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<T> getForFilter(QueryBuilder<T> queryBuilder) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> c = cb.createQuery(clazz);
        Root<T> root = c.from(clazz);
        c.select(root);
        c.where(queryBuilder.where(root, cb));
        c.orderBy(queryBuilder.orderBy(root, cb));
        TypedQuery<T> query = em.createQuery(c);
        query.setFirstResult(queryBuilder.getPageSize() * (queryBuilder.getPageNumber() - 1));
        query.setMaxResults(queryBuilder.getPageSize());
        return query.getResultList();
    }

    @Override
    public long countForFilter(QueryBuilder<T> queryBuilder) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> c = cb.createQuery(Long.class);
        Root<T> root = c.from(clazz);
        c.select(cb.count(root));
        c.where(queryBuilder.where(root, cb));
        TypedQuery<Long> query = em.createQuery(c);
        return query.getSingleResult();
    }
}
