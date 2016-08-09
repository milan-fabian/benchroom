package sk.mimac.benchroom.backend.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.mimac.benchroom.api.dto.Page;
import sk.mimac.benchroom.api.dto.impl.BenchmarkRunDto;
import sk.mimac.benchroom.api.filter.BenchmarkRunFilter;
import sk.mimac.benchroom.api.service.BenchmarkRunService;
import sk.mimac.benchroom.backend.persistence.dao.BenchmarkRunDao;
import sk.mimac.benchroom.backend.persistence.entity.BenchmarkRun;
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
    public Page<BenchmarkRunDto> getRunPage(BenchmarkRunFilter filter) {
        BenchmarkRunQueryBuilder queryBuilder = new BenchmarkRunQueryBuilder(filter);
        long count = benchmarkRunDao.countForFilter(queryBuilder);
        List<BenchmarkRunDto> list = new ArrayList<>(filter.getPageSize());
        for (BenchmarkRun suite : benchmarkRunDao.getForFilter(queryBuilder)) {
            list.add(ConvertUtils.convert(suite));
        }
        return new Page(list, filter.getPageNumber(), filter.getPageSize(), count);
    }

}
