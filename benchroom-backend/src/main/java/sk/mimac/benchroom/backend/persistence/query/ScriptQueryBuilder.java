package sk.mimac.benchroom.backend.persistence.query;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import sk.mimac.benchroom.api.filter.ScriptFilter;
import sk.mimac.benchroom.backend.persistence.entity.Script;

/**
 *
 * @author Milan Fabian
 */
public class ScriptQueryBuilder extends QueryBuilder<Script> {

    private final ScriptFilter filter;

    public ScriptQueryBuilder(ScriptFilter filter) {
        super(filter);
        this.filter = filter;
    }

    @Override
    public List<Predicate> getPredicates(Root<Script> root, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<>();
        if (filter.getSoftwareVersionId() != null) {
            list.add(cb.equal(root.get("softwareVersion").get("id"), filter.getSoftwareVersionId()));
        }
        return list;
    }

}
