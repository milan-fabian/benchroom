package sk.mimac.benchroom.backend.persistence.query;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import sk.mimac.benchroom.api.filter.BenchmarkRunFilter;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkRun;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkRun_;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkSuite_;
import sk.mimac.benchroom.backend.persistence.entity.SoftwareVersion_;
import sk.mimac.benchroom.backend.persistence.entity.Software_;
import sk.mimac.benchroom.backend.persistence.entity.SystemInfo_;

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
            list.add(cb.equal(root.get(BenchmarkRun_.softwareVersion).get(SoftwareVersion_.id), filter.getSoftwareVersionId()));
        }
        if (filter.getSoftwareId() != null) {
            list.add(cb.equal(root.get(BenchmarkRun_.softwareVersion).get(SoftwareVersion_.software).get(Software_.id), filter.getSoftwareId()));
        }
        if (filter.getBenchmarkSuiteId() != null) {
            list.add(cb.equal(root.get(BenchmarkRun_.benchmarkSuite).get(BenchmarkSuite_.id), filter.getBenchmarkSuiteId()));
        }
        if (filter.getSystemInfoId() != null) {
            list.add(cb.equal(root.get(BenchmarkRun_.systemInfo).get(SystemInfo_.id), filter.getSystemInfoId()));
        }
        return list;
    }
}
