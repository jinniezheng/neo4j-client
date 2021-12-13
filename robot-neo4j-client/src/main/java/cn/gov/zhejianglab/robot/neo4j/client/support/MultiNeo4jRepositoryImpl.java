package cn.gov.zhejianglab.robot.neo4j.client.support;

import cn.gov.zhejianglab.robot.neo4j.client.repository.MultiNeo4jRepository;
import org.neo4j.ogm.cypher.query.Pagination;
import org.neo4j.ogm.session.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.util.PagingAndSortingUtils;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * @author jinxin
 * @date 2021/12/2
 * @description
 */
//@Repository
@Transactional(
        readOnly = true
)
public class MultiNeo4jRepositoryImpl<T, ID extends Serializable> implements MultiNeo4jRepository<T, ID> {
    private final List<Session> sessionList;
    private final Class<T> clazz;

    public MultiNeo4jRepositoryImpl(Class<T> domainClass, List<Session> sessions) {
        this.sessionList = sessions;
        this.clazz = domainClass;
    }

    @Transactional
    public <S extends T> S save(S entity) {
        for (Session session : this.sessionList) {
            session.save(entity);
        }
        return entity;
    }

    @Transactional
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        for (Session session : this.sessionList) {
            session.save(entities);
        }
        return entities;
    }

    public Optional<T> findById(ID id) {
        Assert.notNull(id, "The given id must not be null!");

        return Optional.ofNullable(this.sessionList.get(0).load(this.clazz, id));
    }

    public boolean existsById(ID id) {
        return this.findById(id).isPresent();
    }

    public long count() {
        return this.sessionList.get(0).countEntitiesOfType(this.clazz);
    }

    @Transactional
    public void deleteById(ID id) {
        Optional var10000 = this.findById(id);
        for (Session session : this.sessionList) {
            var10000.ifPresent(session::delete);
        }
    }

    @Transactional
    public void delete(T t) {
        for (Session session : this.sessionList) {
            session.delete(t);
        }
    }

    @Transactional
    public void deleteAll(Iterable<? extends T> ts) {
        Iterator var2 = ts.iterator();

        while(var2.hasNext()) {
            T t = (T) var2.next();
            for (Session session : this.sessionList) {
                session.delete(t);
            }
        }

    }

    @Transactional
    public void deleteAll() {
        for (Session session : this.sessionList) {
            session.deleteAll(this.clazz);
        }
    }

    @Transactional
    public <S extends T> S save(S s, int depth) {
        for (Session session : this.sessionList) {
            session.save(s, depth);
        }
        return s;
    }

    @Transactional
    public <S extends T> Iterable<S> save(Iterable<S> ses, int depth) {
        for (Session session : this.sessionList) {
            session.save(ses, depth);
        }
        return ses;
    }

    public Optional<T> findById(ID id, int depth) {
        return Optional.ofNullable(this.sessionList.get(0).load(this.clazz, id, depth));
    }

    public Iterable<T> findAll() {
        return this.findAll(1);
    }

    public Iterable<T> findAll(int depth) {
        return this.sessionList.get(0).loadAll(this.clazz, depth);
    }

    public Iterable<T> findAllById(Iterable<ID> longs) {
        return this.findAllById(longs, 1);
    }

    public Iterable<T> findAllById(Iterable<ID> ids, int depth) {
        return this.sessionList.get(0).loadAll(this.clazz, (Collection)ids, depth);
    }

    public Iterable<T> findAll(Sort sort) {
        return this.findAll((Sort)sort, 1);
    }

    public Iterable<T> findAll(Sort sort, int depth) {
        return this.sessionList.get(0).loadAll(this.clazz, PagingAndSortingUtils.convert(sort), depth);
    }

    public Iterable<T> findAllById(Iterable<ID> ids, Sort sort) {
        return this.findAllById(ids, sort, 1);
    }

    public Iterable<T> findAllById(Iterable<ID> ids, Sort sort, int depth) {
        return this.sessionList.get(0).loadAll(this.clazz, (Collection)ids, PagingAndSortingUtils.convert(sort), depth);
    }

    public Page<T> findAll(Pageable pageable) {
        return this.findAll((Pageable)pageable, 1);
    }

    public Page<T> findAll(Pageable pageable, int depth) {
        Pagination pagination = new Pagination(pageable.getPageNumber(), pageable.getPageSize());
        Collection<T> data = this.sessionList.get(0).loadAll(this.clazz, PagingAndSortingUtils.convert(pageable.getSort()), pagination, depth);
        return PageableExecutionUtils.getPage(new ArrayList(data), pageable, () -> {
            return this.sessionList.get(0).countEntitiesOfType(this.clazz);
        });
    }
}

