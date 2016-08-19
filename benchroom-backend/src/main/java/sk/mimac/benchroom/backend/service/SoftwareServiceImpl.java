package sk.mimac.benchroom.backend.service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.mimac.benchroom.api.dto.Page;
import sk.mimac.benchroom.api.dto.impl.SoftwareDto;
import sk.mimac.benchroom.api.dto.impl.SoftwareVersionDto;
import sk.mimac.benchroom.api.filter.SoftwareFilter;
import sk.mimac.benchroom.api.filter.SoftwareVersionFilter;
import sk.mimac.benchroom.api.service.SoftwareService;
import sk.mimac.benchroom.backend.persistence.dao.SoftwareDao;
import sk.mimac.benchroom.backend.persistence.dao.SoftwareVersionDao;
import sk.mimac.benchroom.backend.persistence.entity.Software;
import sk.mimac.benchroom.backend.persistence.entity.SoftwareVersion;
import sk.mimac.benchroom.backend.persistence.query.SoftwareQueryBuilder;
import sk.mimac.benchroom.backend.persistence.query.SoftwareVersionQueryBuilder;
import sk.mimac.benchroom.backend.utils.ConvertUtils;

/**
 *
 * @author Milan Fabian
 */
@Service
@Transactional(readOnly = true)
public class SoftwareServiceImpl implements SoftwareService {

    private static final Logger logger = LoggerFactory.getLogger(SoftwareServiceImpl.class);

    @Autowired
    private SoftwareDao softwareDao;

    @Autowired
    private SoftwareVersionDao softwareVersionDao;

    @Override
    public SoftwareDto getSoftwareById(long id) {
        return ConvertUtils.convert(softwareDao.find(id));
    }

    @Override
    @Transactional(readOnly = false)
    public long insertSoftware(SoftwareDto software) {
        return softwareDao.insert(ConvertUtils.convert(software));
    }

    @Override
    @Transactional(readOnly = false)
    public void updateSoftware(SoftwareDto software) {
        softwareDao.update(ConvertUtils.convert(software));
    }

    @Override
    public Page<SoftwareDto> getSoftwarePage(SoftwareFilter filter) {
        SoftwareQueryBuilder queryBuilder = new SoftwareQueryBuilder(filter);
        long count = softwareDao.countForFilter(queryBuilder);
        List<SoftwareDto> list = new ArrayList<>(filter.getPageSize());
        for (Software sofware : softwareDao.getForFilter(queryBuilder)) {
            list.add(ConvertUtils.convert(sofware));
        }
        return new Page(list, filter.getPageNumber(), filter.getPageSize(), count);
    }

    @Override
    public SoftwareVersionDto getVersionById(long id) {
        return ConvertUtils.convert(softwareVersionDao.find(id));
    }

    @Override
    @Transactional(readOnly = false)
    public long insertVersion(SoftwareVersionDto version) {
        return softwareVersionDao.insert(ConvertUtils.convert(version));
    }

    @Override
    @Transactional(readOnly = false)
    public void updateVersion(SoftwareVersionDto version) {
        softwareVersionDao.update(ConvertUtils.convert(version));
    }

    @Override
    public Page<SoftwareVersionDto> getVersionPage(SoftwareVersionFilter filter) {
        SoftwareVersionQueryBuilder queryBuilder = new SoftwareVersionQueryBuilder(filter);
        long count = softwareVersionDao.countForFilter(queryBuilder);
        List<SoftwareVersionDto> list = new ArrayList<>(filter.getPageSize());
        for (SoftwareVersion version : softwareVersionDao.getForFilter(queryBuilder)) {
            list.add(ConvertUtils.convert(version));
        }
        return new Page(list, filter.getPageNumber(), filter.getPageSize(), count);
    }

}
