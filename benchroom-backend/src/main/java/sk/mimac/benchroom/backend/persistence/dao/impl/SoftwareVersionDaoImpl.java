package sk.mimac.benchroom.backend.persistence.dao.impl;

import org.springframework.stereotype.Component;
import sk.mimac.benchroom.backend.persistence.dao.SoftwareVersionDao;
import sk.mimac.benchroom.backend.persistence.entity.SoftwareVersion;

/**
 *
 * @author Milan Fabian
 */
@Component
public class SoftwareVersionDaoImpl extends AbstractDao<SoftwareVersion> implements SoftwareVersionDao {

    public SoftwareVersionDaoImpl() {
        super(SoftwareVersion.class);
    }

}
