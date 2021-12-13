package cn.gov.zhejianglab.robot.neo4j.client.repository;

import cn.gov.zhejianglab.robot.neo4j.client.pojo.PersonBean;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends MultiNeo4jRepository<PersonBean, Long> {

}