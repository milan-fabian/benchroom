package sk.mimac.benchroom.backend.persistence.query;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import sk.mimac.benchroom.api.filter.BenchmarkRunFilter;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkRun;

/**
 *
 * @author Milan Fabian
 */
public class BenchmarkRunQueryBuilder extends QueryBuilder<BenchmarkRun> {

    private final BenchmarkRunFilter filter;

    public BenchmarkRunQueryBuilder(BenchmarkRunFilter filter) {
        super(filter);
        this.filter = filter;
    }

    @Override
    public List<Predicate> getPredicates(Root<BenchmarkRun> root, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<>();
        if (filter.getSoftwareVersionId() != null) {
            list.add(cb.equal(root.get("softwareVersion").get("id"), filter.getSoftwareVersionId()));
        }
        if (filter.getSoftwareId() != null) {
            list.add(cb.equal(root.get("softwareVersion").get("software").get("id"), filter.getSoftwareId()));
        }
        if (filter.getBenchmarkParameterId() != null) {
            list.add(cb.equal(root.get("benchmarkParameter").get("id"), filter.getBenchmarkParameterId()));
        }
        if (filter.getBenchmarkSuiteId() != null) {
            list.add(cb.equal(root.get("benchmarkParameter").get("benchmarkSuite").get("id"), filter.getBenchmarkSuiteId()));
        }
        return list;
    }

}
