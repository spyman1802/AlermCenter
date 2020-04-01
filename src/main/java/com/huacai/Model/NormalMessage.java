package com.huacai.Model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class NormalMessage implements Serializable {

  private static final long serialVersionUID = 8361835304377104485L;

  // ZABBIX服务器推送群组ID
  int groupID;

  // 消息内容
  String message;

}
