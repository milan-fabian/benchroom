package sk.mimac.benchroom.backend.persistence.query;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import sk.mimac.benchroom.api.filter.BenchmarkMonitorFilter;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkMonitor;

/**
 *
 * @author Milan Fabian
 */
public class BenchmarkMonitorQueryBuilder extends QueryBuilder<BenchmarkMonitor> {

    private final BenchmarkMonitorFilter filter;

    public BenchmarkMonitorQueryBuilder(BenchmarkMonitorFilter filter) {
        super(filter);
        this.filter = filter;
    }

    @Override
    public List<Predicate> getPredicates(Root<BenchmarkMonitor> root, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<>();
        if (filter.getSuiteId()!= null) {
            list.add(cb.equal(root.get("benchmarkSuite").get("id"), filter.getSuiteId()));
        }
        if (filter.getFulltext() != null) {
            list.add(cb.like(root.get("name"), "%" + filter.getFulltext() + "%"));
        }
        return list;
    }
}
