package cn.gov.zhejianglab.robot.neo4j.client.service.impl;

//import cn.gov.zhejianglab.robot.neo4j.client.repository.LoveRepository;
//import cn.gov.zhejianglab.robot.neo4j.client.repository.PersonRepository;
import cn.gov.zhejianglab.robot.neo4j.client.pojo.LoveBean;
import cn.gov.zhejianglab.robot.neo4j.client.pojo.PersonBean;
import cn.gov.zhejianglab.robot.neo4j.client.repository.LoveRepository;
import cn.gov.zhejianglab.robot.neo4j.client.repository.PersonRepository;
import cn.gov.zhejianglab.robot.neo4j.client.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jinxin
 * @date 2021/12/1
 * @description
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private LoveRepository loveRepository;

    @Override
    public PersonBean addPerson(PersonBean person) {
//        return null;
        return personRepository.save(person);
    }

    @Override
    public PersonBean findOnePerson(long id) {
        return personRepository.findById(id).get();
//        return null;
    }

    @Override
    public LoveBean loves(LoveBean love) {
        return loveRepository.save(love);
//        return null;
    }
}
