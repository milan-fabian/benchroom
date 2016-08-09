package sk.mimac.benchroom.backend.persistence.query;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import sk.mimac.benchroom.api.filter.BenchmarkParameterFilter;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkParameter;

/**
 *
 * @author Milan Fabian
 */
public class BenchmarkParameterQueryBuilder extends QueryBuilder<BenchmarkParameter> {

    private final BenchmarkParameterFilter filter;

    public BenchmarkParameterQueryBuilder(BenchmarkParameterFilter filter) {
        super(filter);
        this.filter = filter;
    }

    @Override
    public List<Predicate> getPredicates(Root<BenchmarkParameter> root, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<>();
        if (filter.getSuiteId() != null) {
            list.add(cb.equal(root.get("benchmarkSuite").get("id"), filter.getSuiteId()));
        }
        return list;
    }

}
