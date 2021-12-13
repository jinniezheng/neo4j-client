package cn.gov.zhejianglab.robot.neo4j.client.repository;

import cn.gov.zhejianglab.robot.neo4j.client.pojo.PersonBean;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.io.Serializable;

/**
 * @author jinxin
 * @date 2021/12/2
 * @description
 */
public interface MultiNeo4jRepository<T, ID extends Serializable> extends Neo4jRepository<T, ID> {
}
