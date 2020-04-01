package com.huacai.Config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 陈震 【1525822@qq.com】
 * @Date 2020-04-01 12:15
 */

@Configuration
@Data
public class WeChatConfig {

  /**
   * 企业Id
   */
  @Value("${wechat.corpid}")
  private String corpid;

  /**
   * secret管理组的凭证密钥
   */
  @Value("${wechat.corpsecret}")
  private String corpsecret;

  @Value("${wechat.agentid}")
  int agentID;

  /**
   * 获取ToKen的请求URL
   */
  @Value("${wechat.token-url}")
  private String TokenUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";

  /**
   * 发送消息的请求URL
   */
  @Value("${wechat.message-url}")
  private String MessageUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send";

  @Value("${wechat.pre-message}")
  private String PreMessage;

}
