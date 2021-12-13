package cn.gov.zhejianglab.robot.neo4j.client.service;

import cn.gov.zhejianglab.robot.neo4j.client.pojo.LoveBean;
import cn.gov.zhejianglab.robot.neo4j.client.pojo.PersonBean;

/**
 * @author jinxin
 * @date 2021/12/1
 * @description
 */
public interface PersonService {

    PersonBean addPerson(PersonBean person);

    PersonBean findOnePerson(long id);

    LoveBean loves(LoveBean love);
}
