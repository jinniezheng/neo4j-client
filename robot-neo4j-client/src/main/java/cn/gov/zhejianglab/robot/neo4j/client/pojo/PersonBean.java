package cn.gov.zhejianglab.robot.neo4j.client.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * @author jinxin
 * @date 2021/12/1
 * @description
 */
@NodeEntity(label = "Person")
@Data
@NoArgsConstructor
public class PersonBean {

    @Id
    @GeneratedValue
    Long id;

    @Property(name = "name")
    private String name;
}
