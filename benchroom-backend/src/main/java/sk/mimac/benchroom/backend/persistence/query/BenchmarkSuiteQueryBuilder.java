package sk.mimac.benchroom.backend.persistence.query;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import sk.mimac.benchroom.api.filter.BenchmarkSuiteFilter;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkSuite;

/**
 *
 * @author Milan Fabian
 */
public class BenchmarkSuiteQueryBuilder extends QueryBuilder<BenchmarkSuite> {

    private final BenchmarkSuiteFilter filter;

    public BenchmarkSuiteQueryBuilder(BenchmarkSuiteFilter filter) {
        super(filter);
        this.filter = filter;
    }

    @Override
    public List<Predicate> getPredicates(Root<BenchmarkSuite> root, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<>();
        if (filter.getSoftwareId() != null) {
            list.add(cb.equal(root.get("software").get("id"), filter.getSoftwareId()));
        }
        if (filter.getFulltext() != null) {
            list.add(cb.like(root.get("name"), "%" + filter.getFulltext() + "%"));
        }
        return list;
    }
}
