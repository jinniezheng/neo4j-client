package cn.gov.zhejianglab.robot.neo4j.client.repository;

import cn.gov.zhejianglab.robot.neo4j.client.pojo.LoveBean;
import org.springframework.stereotype.Repository;

/**
 * @author jinxin
 * @date 2021/12/1
 * @description
 */
@Repository
public interface LoveRepository extends MultiNeo4jRepository<LoveBean, Long> {
}
