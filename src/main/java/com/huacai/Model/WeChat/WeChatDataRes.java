package com.huacai.Model.WeChat;

import lombok.Data;

/**
 * @Author 陈震 【1525822@qq.com】
 * @Date 2020-04-01 12:42
 */
@Data
public class WeChatDataRes {

  int errcode;
  String errmsg;
  String invaliduser;
  String invalidparty;
  String invalidtag;

}
