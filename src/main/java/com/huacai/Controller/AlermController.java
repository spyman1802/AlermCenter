package com.huacai.Controller;

import java.util.List;

import com.huacai.Model.NormalMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.huacai.Model.ZabbixAlerm;
import com.huacai.Model.ZabbixServer;
import com.huacai.Service.CenterService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api") // 通过这里配置使下面的映射都在/api下
public class AlermController {

  @Autowired
  CenterService centerService;
  
  @ApiOperation(value = "接收告警信息", notes = "")
  @RequestMapping(value = "/reportAlerm", method = RequestMethod.POST)
  public void reportAlerm(@RequestBody ZabbixAlerm za) {
    log.info("receive a alerm info. ID:{} HOST:{} LEVEL:{} INFO:{} STATUS:{}", za.getZabbixID(), za.getZabbixHost(), za.getAlermLevel(), za.getAlermInfo(), za.getAlermStatus());
    centerService.acceptAlerm(za);
  }

//  @ApiOperation(value = "接收微信信息", notes = "")
//  @RequestMapping(value = "/receiveWXInfo", method = RequestMethod.POST)
//  public WXRes receiveWXInfo(@RequestBody String info) {
//    log.info("receive a message from 微信. content --> {}", info);
//    WXRes r = new WXRes();
//    r.setCode(0);
//    r.setMedia("http://www.bjrbj.gov.cn/images/sdzc_logo.png");
//    r.setReply("回复一个图片");
//    return null;
//  }

  @ApiOperation(value = "获取ZABBIX服务器列表", notes = "")
  @RequestMapping(value = "/getZabbixServer", method = RequestMethod.POST)
  public List<ZabbixServer> getZabbixServer() {
    log.info("getZabbixServer ...");
    return centerService.getZabbixList();
  }

  @ApiOperation(value = "转发消息", notes = "")
  @RequestMapping(value = "/forwardMessage", method = RequestMethod.POST)
  public void forwardMessage(@RequestBody NormalMessage message) {
    log.info("forwardMessage ... {}", message.toString());
    centerService.forwardMessage(message);
  }

}
