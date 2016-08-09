package sk.mimac.benchroom.backend.persistence.query;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import sk.mimac.benchroom.api.filter.AbstractFilter;
import sk.mimac.benchroom.backend.persistence.entity.EntityInterface;

/**
 *
 * @author Milan Fabian
 */
public abstract class QueryBuilder<T extends EntityInterface> {

    private final int pageSize;
    private final int pageNumber;

    public QueryBuilder(AbstractFilter filter) {
        this.pageSize = filter.getPageSize();
        this.pageNumber = filter.getPageNumber();
    }

    public Expression<Boolean> where(Root<T> root, CriteriaBuilder cb) {
        return cb.and(getPredicates(root, cb).toArray(new Predicate[0]));
    }

    public abstract List<Predicate> getPredicates(Root<T> root, CriteriaBuilder cb);
    
    public int getPageSize() {
        return pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

}
