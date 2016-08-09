package sk.mimac.benchroom.backend.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.mimac.benchroom.api.dto.Page;
import sk.mimac.benchroom.api.dto.impl.BenchmarkSuiteDto;
import sk.mimac.benchroom.api.filter.BenchmarkSuiteFilter;
import sk.mimac.benchroom.api.service.BenchmarkSuiteService;
import sk.mimac.benchroom.backend.persistence.dao.BenchmarkSuiteDao;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkSuite;
import sk.mimac.benchroom.backend.persistence.query.BenchmarkSuiteQueryBuilder;
import sk.mimac.benchroom.backend.utils.ConvertUtils;

/**
 *
 * @author Milan Fabian
 */
@Service
@Transactional(readOnly = true)
public class BenchmarkSuiteServiceImpl implements BenchmarkSuiteService {

    @Autowired
    private BenchmarkSuiteDao benchmarkSuiteDao;

    @Override
    public BenchmarkSuiteDto getSuiteById(long id) {
        return ConvertUtils.convert(benchmarkSuiteDao.find(id));
    }

    @Override
    @Transactional(readOnly = false)
    public long insertSuite(BenchmarkSuiteDto suite) {
        return benchmarkSuiteDao.insert(ConvertUtils.convert(suite));
    }

    @Override
    @Transactional(readOnly = false)
    public void updateSuite(BenchmarkSuiteDto suite) {
        benchmarkSuiteDao.update(ConvertUtils.convert(suite));
    }

    @Override
    public Page<BenchmarkSuiteDto> getSuitePage(BenchmarkSuiteFilter filter) {
        BenchmarkSuiteQueryBuilder queryBuilder = new BenchmarkSuiteQueryBuilder(filter);
        long count = benchmarkSuiteDao.countForFilter(queryBuilder);
        List<BenchmarkSuiteDto> list = new ArrayList<>(filter.getPageSize());
        for (BenchmarkSuite suite : benchmarkSuiteDao.getForFilter(queryBuilder)) {
            list.add(ConvertUtils.convert(suite));
        }
        return new Page(list, filter.getPageNumber(), filter.getPageSize(), count);
    }
}
