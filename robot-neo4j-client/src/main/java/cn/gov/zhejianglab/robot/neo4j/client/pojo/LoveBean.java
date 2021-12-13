package cn.gov.zhejianglab.robot.neo4j.client.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

/**
 * @author jinxin
 * @date 2021/12/1
 * @description
 */
@RelationshipEntity(type = "LOVES")
@Data
@NoArgsConstructor
public class LoveBean {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private PersonBean startNode;

    @EndNode
    private PersonBean endNode;
}
