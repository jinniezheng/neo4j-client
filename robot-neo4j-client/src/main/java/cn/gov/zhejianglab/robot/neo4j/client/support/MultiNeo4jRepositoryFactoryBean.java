package cn.gov.zhejianglab.robot.neo4j.client.support;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.neo4j.mapping.Neo4jMappingContext;
import org.springframework.data.neo4j.repository.support.Neo4jRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.core.support.TransactionalRepositoryFactoryBeanSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jinxin
 * @date 2021/12/3
 * @description
 */
public class MultiNeo4jRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable> extends TransactionalRepositoryFactoryBeanSupport<T, S, ID> {
    private List<Session> sessions = new ArrayList<>();
    private Neo4jMappingContext mappingContext;

    public MultiNeo4jRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Autowired
    public void setSession(Session session) {
        this.sessions.add(session);
    }

    @Autowired
    public void setSessions(@Qualifier("db2Session")SessionFactory sessionFactory) {
        this.sessions.add(sessionFactory.openSession());
    }

    public void setMappingContext(MappingContext<?, ?> mappingContext) {
        super.setMappingContext(mappingContext);
        if (mappingContext instanceof Neo4jMappingContext) {
            this.mappingContext = (Neo4jMappingContext)mappingContext;
        }

    }

    public void afterPropertiesSet() {
        Assert.notNull(this.sessions, "Session must not be null!");
        super.afterPropertiesSet();
    }

    protected RepositoryFactorySupport doCreateRepositoryFactory() {
        return this.createRepositoryFactory(this.sessions);
    }

    /** @deprecated */
    @Deprecated
    protected RepositoryFactorySupport createRepositoryFactory(List<Session> sessions) {
        return new MultiNeo4jRepositoryFactory(sessions, this.mappingContext);
    }
}

