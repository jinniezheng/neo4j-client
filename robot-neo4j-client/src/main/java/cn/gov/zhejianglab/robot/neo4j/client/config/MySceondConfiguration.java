package cn.gov.zhejianglab.robot.neo4j.client.config;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
public class MySceondConfiguration {

    @Bean("db2Session")
    public SessionFactory sessionFactory(@Qualifier("db2Config") org.neo4j.ogm.config.Configuration configuration) {
        // with domain entity base package(s)
        SessionFactory sessionFactory = new SessionFactory(configuration, "cn.gov.zhejianglab.robot.neo4j.client");
        return sessionFactory;
    }

    @Bean("db2Config")
    public org.neo4j.ogm.config.Configuration configuration() {
        org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration.Builder()
                .uri("bolt://10.8.24.29:7687")
                .credentials("neo4j", "graph")
                .build();
        return configuration;
    }

    @Bean("db2Tm")
    public Neo4jTransactionManager transactionManager(@Qualifier("db2Session") SessionFactory sessionFactory) {
        return new Neo4jTransactionManager(sessionFactory);
    }
}
