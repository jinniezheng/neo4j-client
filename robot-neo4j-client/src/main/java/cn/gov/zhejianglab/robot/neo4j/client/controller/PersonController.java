package cn.gov.zhejianglab.robot.neo4j.client.controller;

import cn.gov.zhejianglab.robot.common.base.enums.StatusEnum;
import cn.gov.zhejianglab.robot.common.base.model.Result;
import cn.gov.zhejianglab.robot.neo4j.client.pojo.LoveBean;
import cn.gov.zhejianglab.robot.neo4j.client.pojo.PersonBean;
import cn.gov.zhejianglab.robot.neo4j.client.service.PersonService;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author jinxin
 * @date 2021/11/30
 * @description
 */
@Slf4j
@Api(value = "PersonController", tags = "人物模块")
@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    public PersonService personService;

    @ApiOperation(value = "add Person", notes = "add Person")
    @RequestMapping(method = RequestMethod.POST, value = "/addPerson")
    public Result<JSONArray> addPerson(@RequestBody PersonBean person) {
        try {
            personService.addPerson(person);
            return Result.succeed(null, StatusEnum.SUCCESS.getName());
        } catch (Exception e) {
            log.error("addPerson -=- {}", e);
            return Result.failed(null, e.getMessage());
        }
    }

    @ApiOperation(value = "love Person", notes = "love Person")
    @RequestMapping(method = RequestMethod.GET, value = "/loves/{id1}/{id2}")
    public Result<JSONArray> loves(@PathVariable("id1") String id1, @PathVariable("id2") String id2) {
        try {
            PersonBean person1 = personService.findOnePerson(Long.parseLong(id1));
            PersonBean person2 = personService.findOnePerson(Long.parseLong(id2));
            LoveBean love = new LoveBean();
            love.setStartNode(person1);
            love.setEndNode(person2);
            personService.loves(love);
            return Result.succeed(null, StatusEnum.SUCCESS.getName());
        } catch (Exception e) {
            log.error("loves -=- {}", e);
            return Result.failed(null, e.getMessage());
        }
    }


}
