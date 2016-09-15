package sk.mimac.benchroom.backend.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.mimac.benchroom.api.dto.Page;
import sk.mimac.benchroom.api.dto.impl.BenchmarkRunDto;
import sk.mimac.benchroom.api.dto.impl.BenchmarkRunSimpleDto;
import sk.mimac.benchroom.api.filter.BenchmarkRunFilter;
import sk.mimac.benchroom.api.service.BenchmarkRunService;
import sk.mimac.benchroom.backend.persistence.dao.BenchmarkRunDao;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkMonitor;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkParameter;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkRun;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkRunResult;
import sk.mimac.benchroom.backend.persistence.query.BenchmarkRunQueryBuilder;
import sk.mimac.benchroom.backend.utils.ConvertUtils;

/**
 *
 * @author Milan Fabian
 */
@Service
@Transactional(readOnly = true)
public class BenchmarkRunServiceImpl implements BenchmarkRunService {

    @Autowired
    private BenchmarkRunDao benchmarkRunDao;

    @Override
    public BenchmarkRunDto getRunById(long id) {
        return ConvertUtils.convert(benchmarkRunDao.find(id));
    }

    @Override
    @Transactional(readOnly = false)
    public void insertRun(BenchmarkRunDto dto, Map<Long, Double> monitorResults) {
        BenchmarkRun entity = ConvertUtils.convert(dto);
        Set<BenchmarkRunResult> results = new HashSet<>();
        for (Map.Entry<Long, Double> monitorResult : monitorResults.entrySet()) {
            BenchmarkRunResult result = new BenchmarkRunResult();
            result.setMonitor(new BenchmarkMonitor(monitorResult.getKey()));
            result.setResult(monitorResult.getValue());
            result.setRun(entity);
            results.add(result);
        }
        entity.setResults(results);
        benchmarkRunDao.insert(entity);
    }

    @Override
    public Page<BenchmarkRunSimpleDto> getRunPageSimple(BenchmarkRunFilter filter) {
        BenchmarkRunQueryBuilder queryBuilder = new BenchmarkRunQueryBuilder(filter);
        long count = benchmarkRunDao.countForFilter(queryBuilder);
        List<BenchmarkRunSimpleDto> list = new ArrayList<>(filter.getPageSize());
        for (BenchmarkRun run : benchmarkRunDao.getForFilter(queryBuilder)) {
            list.add(ConvertUtils.convertToSimple(run));
        }
        return new Page(list, filter.getPageNumber(), filter.getPageSize(), count);
    }

    @Override
    public Page<BenchmarkRunDto> getRunPage(BenchmarkRunFilter filter) {
        BenchmarkRunQueryBuilder queryBuilder = new BenchmarkRunQueryBuilder(filter);
        long count = benchmarkRunDao.countForFilter(queryBuilder);
        List<BenchmarkRunDto> list = new ArrayList<>();
        for (BenchmarkRun run : benchmarkRunDao.getForFilter(queryBuilder)) {
            if (filter.getBenchmarkParameterIds() == null || checkParams(run.getBenchmarkParameters(), filter)) {
                list.add(ConvertUtils.convert(run));
            }
        }
        return new Page(list, filter.getPageNumber(), filter.getPageSize(), count);
    }

    @Override
    public List<BenchmarkRunDto> getRunsByIds(List<Long> ids) {
        List<BenchmarkRunDto> list = new ArrayList<>(ids.size());
        for (BenchmarkRun run : benchmarkRunDao.getByIds(ids)) {
            list.add(ConvertUtils.convert(run));
        }
        return list;
    }

    private boolean checkParams(Set<BenchmarkParameter> params, BenchmarkRunFilter filter) {
        for (BenchmarkParameter param : params) {
            if (!filter.getBenchmarkParameterIds().contains(param.getId())) {
                return false;
            }
        }
        return true;
    }
}
