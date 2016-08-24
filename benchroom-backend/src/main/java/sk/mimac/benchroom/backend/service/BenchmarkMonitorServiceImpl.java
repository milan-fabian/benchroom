package sk.mimac.benchroom.backend.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.mimac.benchroom.api.dto.Page;
import sk.mimac.benchroom.api.dto.impl.BenchmarkMonitorDto;
import sk.mimac.benchroom.api.filter.BenchmarkMonitorFilter;
import sk.mimac.benchroom.api.service.BenchmarkMonitorService;
import sk.mimac.benchroom.backend.persistence.dao.BenchmarkMonitorDao;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkMonitor;
import sk.mimac.benchroom.backend.persistence.query.BenchmarkMonitorQueryBuilder;
import sk.mimac.benchroom.backend.utils.ConvertUtils;

/**
 *
 * @author Milan Fabian
 */
@Service
@Transactional(readOnly = true)
public class BenchmarkMonitorServiceImpl implements BenchmarkMonitorService {

    @Autowired
    private BenchmarkMonitorDao benchmarkMonitorDao;

    @Override
    public BenchmarkMonitorDto getMonitorById(long id) {
        return ConvertUtils.convert(benchmarkMonitorDao.find(id));
    }

    @Override
    @Transactional(readOnly = false)
    public long insertMonitor(BenchmarkMonitorDto monitor) {
        return benchmarkMonitorDao.insert(ConvertUtils.convert(monitor));
    }

    @Override
    @Transactional(readOnly = false)
    public void updateMonitor(BenchmarkMonitorDto monitor) {
        benchmarkMonitorDao.update(ConvertUtils.convert(monitor));
    }

    @Override
    public Page<BenchmarkMonitorDto> getMonitorPage(BenchmarkMonitorFilter filter) {
        BenchmarkMonitorQueryBuilder queryBuilder = new BenchmarkMonitorQueryBuilder(filter);
        long count = benchmarkMonitorDao.countForFilter(queryBuilder);
        List<BenchmarkMonitorDto> list = new ArrayList<>(filter.getPageSize());
        for (BenchmarkMonitor monitor : benchmarkMonitorDao.getForFilter(queryBuilder)) {
            list.add(ConvertUtils.convert(monitor));
        }
        return new Page(list, filter.getPageNumber(), filter.getPageSize(), count);
    }

    @Override
    public List<BenchmarkMonitorDto> getMonitorsForSuite(long suiteId) {
        List<BenchmarkMonitorDto> result = new ArrayList();
        for (BenchmarkMonitor entity : benchmarkMonitorDao.getBySuite(suiteId)) {
            result.add(ConvertUtils.convert(entity));
        }
        return result;
    }

}
