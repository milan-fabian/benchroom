package sk.mimac.benchroom.backend.persistence.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.persistence.Query;
import org.springframework.stereotype.Component;
import sk.mimac.benchroom.api.system.SystemParameter;
import sk.mimac.benchroom.backend.persistence.dao.SystemInfoDao;
import sk.mimac.benchroom.backend.persistence.entity.SystemInfo;

/**
 *
 * @author Milan Fabian
 */
@Component
public class SystemInfoDaoImpl extends AbstractDao<SystemInfo> implements SystemInfoDao {

    private static final Random random = new Random();

    public SystemInfoDaoImpl() {
        super(SystemInfo.class);
    }

    @Override
    public Long getWithSameParameters(Map<SystemParameter, String> parameters) {
        // Relational division FTW
        String tempTableName = "system_info_" + random.nextInt(100_000_000);
        em.createNativeQuery("CREATE TEMPORARY TABLE " + tempTableName + " (system_key varchar(255), system_value varchar(255))").executeUpdate();
        Query query = em.createNativeQuery("INSERT INTO " + tempTableName + " VALUES (?, ?)");
        for (Map.Entry<SystemParameter, String> parameter : parameters.entrySet()) {
            query.setParameter(1, parameter.getKey().toString());
            query.setParameter(2, parameter.getValue());
            query.executeUpdate();
        }
        query = em.createNativeQuery("SELECT params.system_id "
                + "FROM " + tempTableName + " AS temp, system_parameters AS params "
                + "WHERE temp.system_key = params.system_key AND temp.system_value = params.system_value "
                + "GROUP BY params.system_id "
                + "HAVING COUNT(params.system_key) = " + parameters.size());
        List<BigInteger> list = query.getResultList();
        em.createNativeQuery("DROP TABLE " + tempTableName).executeUpdate();
        if (list.size() > 0) {
            return list.get(0).longValueExact();
        }
        return null;
    }
}
