package sk.mimac.benchroom.backend.persistence.query;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import sk.mimac.benchroom.api.filter.SoftwareVersionFilter;
import sk.mimac.benchroom.backend.persistence.entity.SoftwareVersion;

/**
 *
 * @author Milan Fabian
 */
public class SoftwareVersionQueryBuilder extends QueryBuilder<SoftwareVersion> {

    private final SoftwareVersionFilter filter;

    public SoftwareVersionQueryBuilder(SoftwareVersionFilter filter) {
        super(filter);
        this.filter = filter;
    }

    @Override
    public List<Predicate> getPredicates(Root<SoftwareVersion> root, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<>();
        if (filter.getSoftwareId() != null) {
            list.add(cb.equal(root.get("software"), filter.getSoftwareId()));
        }
        return list;
    }

}
