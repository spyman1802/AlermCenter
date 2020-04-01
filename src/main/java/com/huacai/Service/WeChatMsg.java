package com.huacai.Service;

import com.alibaba.fastjson.JSONObject;
import com.huacai.Config.WeChatConfig;
import com.huacai.Config.httpClient;
import com.huacai.Model.WeChat.WeChatAuthRes;
import com.huacai.Model.WeChat.WeChatDataRes;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author 陈震 【1525822@qq.com】
 * @Date 2020-04-01 12:13
 */

@Service
@Slf4j
public class WeChatMsg {

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private WeChatConfig weChatConfig;

  public void send(@NonNull String message, @NonNull String tag) {

    String token = this.getToken();

    log.info("微信发送消息: {}", message);

    this.postMessage(message, tag, token);
  }

  /**
   * corpid应用组织编号   corpsecret应用秘钥
   * 获取toAuth(String Get_Token_Url)返回结果中键值对中access_token键的值
   *
   * @param
   */
  private String getToken() {

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(weChatConfig.getTokenUrl())
            .queryParam("corpid", weChatConfig.getCorpid())
            .queryParam("corpsecret", weChatConfig.getCorpsecret());

    WeChatAuthRes res = restTemplate.getForObject(builder.toUriString(), WeChatAuthRes.class);

    String token = res.getAccess_token();

    log.debug("微信登录成功。Token：{}", token);

    return token;

  }

  /**
   * @return String
   * @Title 创建微信发送请求post实体
   * charset消息编码    ，contentType消息体内容类型，
   * url微信消息发送请求地址，data为post数据，token鉴权token
   */

  private void postMessage(@NonNull String message, @NonNull String tag, @NonNull String token) {
    JSONObject jsonText = new JSONObject();
    jsonText.put("content", weChatConfig.getPreMessage() + message);

    JSONObject json = new JSONObject();
    json.put("totag", tag);
    json.put("msgtype", "text");
    json.put("agentid", weChatConfig.getAgentID());
    json.put("text", jsonText);

    log.debug("请求的JSON为：{}", json.toJSONString());

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(weChatConfig.getMessageUrl()).queryParam("access_token", token);

    WeChatDataRes res = restTemplate.postForObject(builder.toUriString(), json, WeChatDataRes.class);

    if (res.getErrcode() != 0) {
      log.error("发送微信消息失败：{}", res.getErrmsg());
    }

  }

}