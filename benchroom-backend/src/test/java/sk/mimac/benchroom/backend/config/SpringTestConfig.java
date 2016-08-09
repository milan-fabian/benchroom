package sk.mimac.benchroom.backend.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * @author Milan Fabian
 */
@Configuration
@ComponentScan(basePackages = {"sk.mimac.benchroom.backend"})
@EnableJpaRepositories("sk.mimac.benchroom.backend.persistence.entity")
public class SpringTestConfig extends SpringConfig {

    @Override
    protected Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        return properties;
    }

    @Override
    protected DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
        dataSource.setUrl("jdbc:derby:memory:kastiel;create=true");
        return dataSource;
    }
}
