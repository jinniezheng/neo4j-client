package cn.gov.zhejianglab.robot.neo4j.client.support;

//import cn.gov.zhejianglab.robot.neo4j.client.repository.MultiNeo4jRepositoryImpl;
import org.neo4j.ogm.session.Neo4jSession;
import org.neo4j.ogm.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.neo4j.mapping.Neo4jMappingContext;
import org.springframework.data.neo4j.mapping.Neo4jPersistentEntity;
import org.springframework.data.neo4j.mapping.Neo4jPersistentProperty;
import org.springframework.data.neo4j.repository.query.GraphQueryLookupStrategy;
import org.springframework.data.neo4j.repository.support.GraphEntityInformation;
        import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * @author jinxin
 * @date 2021/12/3
 * @description
 */
public class MultiNeo4jRepositoryFactory extends RepositoryFactorySupport {
    private static final Logger logger = LoggerFactory.getLogger(org.springframework.data.neo4j.repository.support.Neo4jRepositoryFactory.class);
    private final List<Session> sessions;
    @Nullable
    private final MappingContext<Neo4jPersistentEntity<?>, Neo4jPersistentProperty> mappingContext;

    /** @deprecated */
    @Deprecated
    public MultiNeo4jRepositoryFactory(List<Session> sessions) {
        this(sessions, (MappingContext)null);
    }

    public MultiNeo4jRepositoryFactory(List<Session> sessions, MappingContext<Neo4jPersistentEntity<?>, Neo4jPersistentProperty> mappingContext) {
        Assert.notNull(sessions, "Session must not be null!");
        this.sessions = sessions;
        if (mappingContext != null) {
            this.mappingContext = mappingContext;
        } else if (sessions.get(0) instanceof Neo4jSession) {
            logger.debug("Creating a new mapping context");
            this.mappingContext = new Neo4jMappingContext(((Neo4jSession)sessions.get(0)).metaData());
            ((Neo4jMappingContext)this.mappingContext).initialize();
        } else {
            logger.warn("No mapping context present, some operations won't support persistence constructors");
            this.mappingContext = null;
        }

    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        super.setBeanClassLoader(classLoader);
    }

    public <T, ID> EntityInformation<T, ID> getEntityInformation(Class<T> type) {
        Assert.notNull(type, "Domain class must not be null!");
        return new GraphEntityInformation(((Neo4jSession)this.sessions.get(0)).metaData(), type);
    }

    protected Object getTargetRepository(RepositoryInformation information) {
        return this.getTargetRepositoryViaReflection(information, new Object[]{information.getDomainType(), this.sessions});
    }

    protected Class<?> getRepositoryBaseClass(RepositoryMetadata repositoryMetadata) {
        return MultiNeo4jRepositoryImpl.class;
    }

    protected Optional<QueryLookupStrategy> getQueryLookupStrategy(QueryLookupStrategy.Key key, QueryMethodEvaluationContextProvider evaluationContextProvider) {
        return Optional.of(new GraphQueryLookupStrategy(this.sessions.get(0), evaluationContextProvider, this.mappingContext));
    }
}

