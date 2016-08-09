package sk.mimac.benchroom.backend.persistence.dao.impl;

import org.springframework.stereotype.Component;
import sk.mimac.benchroom.backend.persistence.dao.SoftwareDao;
import sk.mimac.benchroom.backend.persistence.entity.Software;

/**
 *
 * @author Milan Fabian
 */
@Component
public class SoftwareDaoImpl extends AbstractDao<Software> implements SoftwareDao {

    public SoftwareDaoImpl() {
        super(Software.class);
    }

}
