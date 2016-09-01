package sk.mimac.benchroom.backend.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.mimac.benchroom.api.dto.Page;
import sk.mimac.benchroom.api.dto.impl.BenchmarkParameterDto;
import sk.mimac.benchroom.api.filter.BenchmarkParameterFilter;
import sk.mimac.benchroom.api.service.BenchmarkParameterService;
import sk.mimac.benchroom.backend.persistence.dao.BenchmarkParameterDao;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkParameter;
import sk.mimac.benchroom.backend.persistence.query.BenchmarkParameterQueryBuilder;
import sk.mimac.benchroom.backend.utils.ConvertUtils;

/**
 *
 * @author Milan Fabian
 */
@Service
@Transactional(readOnly = true)
public class BenchmarkParameterServiceImpl implements BenchmarkParameterService {

    @Autowired
    private BenchmarkParameterDao benchmarkParameterDao;

    @Override
    public BenchmarkParameterDto getParameterById(long id) {
        return ConvertUtils.convert(benchmarkParameterDao.find(id));
    }

    @Override
    @Transactional(readOnly = false)
    public long insertParameter(BenchmarkParameterDto parameter) {
        return benchmarkParameterDao.insert(ConvertUtils.convert(parameter));
    }

    @Override
    @Transactional(readOnly = false)
    public void updateParameter(BenchmarkParameterDto parameter) {
        benchmarkParameterDao.update(ConvertUtils.convert(parameter));
    }

    @Override
    public Page<BenchmarkParameterDto> getParameterPage(BenchmarkParameterFilter filter) {
        BenchmarkParameterQueryBuilder queryBuilder = new BenchmarkParameterQueryBuilder(filter);
        long count = benchmarkParameterDao.countForFilter(queryBuilder);
        List<BenchmarkParameterDto> list = new ArrayList<>(filter.getPageSize());
        for (BenchmarkParameter parameter : benchmarkParameterDao.getForFilter(queryBuilder)) {
            list.add(ConvertUtils.convert(parameter));
        }
        return new Page(list, filter.getPageNumber(), filter.getPageSize(), count);
    }

    @Override
    public List<BenchmarkParameterDto> getParametersForSuitePositionPriority(long suiteId, short position, short minPriority) {
        List<BenchmarkParameterDto> result = new ArrayList();
        for (BenchmarkParameter entity : benchmarkParameterDao.getBySuitePositionPriority(suiteId, position, minPriority)) {
            result.add(ConvertUtils.convert(entity));
        }
        return result;
    }

    @Override
    public long getParametersCountForSuitePriority(long suiteId, short minPriority) {
        long result = 1;
        for (long count : benchmarkParameterDao.countBySuitePositionPriority(suiteId, minPriority)) {
            result *= count;
        }
        return result;
    }
}
