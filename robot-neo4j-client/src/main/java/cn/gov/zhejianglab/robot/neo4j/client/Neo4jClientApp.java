package cn.gov.zhejianglab.robot.neo4j.client;

import cn.gov.zhejianglab.robot.neo4j.client.support.MultiNeo4jRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.support.Neo4jRepositoryFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hello world!
 *
 */
@EnableTransactionManagement
@ConfigurationPropertiesScan
@EnableNeo4jRepositories(repositoryFactoryBeanClass = MultiNeo4jRepositoryFactoryBean.class)
@SpringBootApplication
public class Neo4jClientApp {
    public static void main(String[] args) {
        SpringApplication.run(Neo4jClientApp.class, args);
    }
}