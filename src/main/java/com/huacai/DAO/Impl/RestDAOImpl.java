package com.huacai.DAO.Impl;

import com.huacai.Service.WeChatMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSONObject;
import com.huacai.Config.SysConstants;
import com.huacai.DAO.RestDAO;
import com.huacai.Model.SendResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RestDAOImpl implements RestDAO {

//  @Autowired
//  RestTemplate rest;

  @Autowired
  WeChatMsg weChatMsg;

//  private String qqSendURI = "/openqq/send_group_message";
//  private String wxSendURI = "/openwx/send_group_message";
//
//  @Value("${groupServer.QQ.ip}")
//  private String qqIP;
//
//  @Value("${groupServer.QQ.port}")
//  private String qqPort;
//
//  @Value("${groupServer.WX.ip}")
//  private String wxIP;
//
//  @Value("${groupServer.WX.port}")
//  private String wxPort;

  @Override
  public void sendGroupMessage(int ServerType, String groupName, String msg) {

    log.debug("Prepaer send group message. Type: {} Group name: {} Message: {}", ServerType, groupName, msg.replace("\n", ""));

    weChatMsg.send(msg, groupName);

    // 拼接请求URL（RestTemplete会自动URL化，所以这里不用转码）
//    String url = "http://";
//    switch (ServerType) {
//    case SysConstants.QQ_SERVER:
//      url += qqIP + ":" + qqPort + qqSendURI + "?" + "displayname=" + groupName + "&content=" + msg + "&async=1";
//
//    case SysConstants.WX_SERVER:
//      url += wxIP + ":" + wxPort + wxSendURI + "?" + "displayname=" + groupName + "&content=" + msg + "&async=1";
//
//    }
//
//    // 发出日志
//    log.debug("Sending group message --> {}", url.replace("\n", ""));

    // SendResult sr = rest.getForObject(url, SendResult.class);


    // 返回日志
    // log.debug("Send group message complete. result: {}", JSONObject.toJSONString(sr));

  }

}
