package com.huacai.Model.WeChat;

import lombok.Data;
import lombok.ToString;

/**
 * @Author 陈震 【1525822@qq.com】
 * @Date 2020-04-01 12:10
 */

@Data
public class WeChatData {
  //发送微信消息的URLString sendMsgUrl="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";

  /**
   * 消息类型
   */
  private String msgtype;

  /**
   * 企业应用的agentID
   */
  private int agentid;

  /**
   * 实际接收Map类型数据
   */
  @ToString(includeFieldNames = true)
  @Data(staticConstructor = "of")
  private static class text {
    String contents;
  }

}
