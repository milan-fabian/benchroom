package sk.mimac.benchroom.backend.persistence.query;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
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
    private final String orderBy;
    private final AbstractFilter.OrderDirection orderDir;

    public QueryBuilder(AbstractFilter filter) {
        this.pageSize = filter.getPageSize();
        this.pageNumber = filter.getPageNumber();
        if (filter.getOrderBy() != null && !filter.getOrderBy().isEmpty()) {
            this.orderBy = filter.getOrderBy();
        } else {
            this.orderBy = "id";
        }
        if (filter.getOrderDir() != null) {
            this.orderDir = filter.getOrderDir();
        } else {
            this.orderDir = AbstractFilter.OrderDirection.ASC;
        }
    }

    public Expression<Boolean> where(Root<T> root, CriteriaBuilder cb) {
        return cb.and(getPredicates(root, cb).toArray(new Predicate[0]));
    }

    public Order orderBy(Root<T> root, CriteriaBuilder cb) {
        if (orderDir == AbstractFilter.OrderDirection.ASC) {
            return cb.asc(root.get(orderBy));
        } else {
            return cb.desc(root.get(orderBy));
        }
    }

    public abstract List<Predicate> getPredicates(Root<T> root, CriteriaBuilder cb);

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

}
