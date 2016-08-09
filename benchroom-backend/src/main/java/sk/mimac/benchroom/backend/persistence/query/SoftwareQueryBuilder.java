package sk.mimac.benchroom.backend.persistence.query;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import sk.mimac.benchroom.api.filter.SoftwareFilter;
import sk.mimac.benchroom.backend.persistence.entity.Software;

/**
 *
 * @author Milan Fabian
 */
public class SoftwareQueryBuilder extends QueryBuilder<Software> {

    private final SoftwareFilter filter;

    public SoftwareQueryBuilder(SoftwareFilter filter) {
        super(filter);
        this.filter = filter;
    }

    @Override
    public List<Predicate> getPredicates(Root<Software> root, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<>();
        if (filter.getFulltext() != null) {
            list.add(cb.like(root.get("name"), "%" + filter.getFulltext() + "%"));
        }
        return list;
    }

}
