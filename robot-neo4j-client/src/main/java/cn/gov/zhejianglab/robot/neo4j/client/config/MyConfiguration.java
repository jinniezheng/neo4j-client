package cn.gov.zhejianglab.robot.neo4j.client.config;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author jinxin
 * @date 2021/12/2
 * @description
 */
@Configuration
@ComponentScan
@EnableNeo4jRepositories
@EnableTransactionManagement
public class MyConfiguration {

    @Bean(name = "sessionFactory")
    @Primary
//    @Bean
    public SessionFactory sessionFactory(@Qualifier("db1Config") org.neo4j.ogm.config.Configuration configuration) {
        // with domain entity base package(s)
        return new SessionFactory(configuration, "cn.gov.zhejianglab.robot.neo4j.client");
    }

    @Bean("db1Config")
    @Primary
    public org.neo4j.ogm.config.Configuration configuration() {
        org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration.Builder()
                .uri("bolt://10.8.27.50:7687")
                .credentials("neo4j", "graph")
                .build();
        return configuration;
    }

    @Bean("transactionManager")
    @Primary
    public Neo4jTransactionManager transactionManager(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        return new Neo4jTransactionManager(sessionFactory);
    }
}
