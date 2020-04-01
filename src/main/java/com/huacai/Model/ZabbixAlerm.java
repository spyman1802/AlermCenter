package com.huacai.Model;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class ZabbixAlerm implements Serializable {

  private static final long serialVersionUID = 8361835304377104485L;
  
  int alermID;    
  int zid;
  Date occurTime;
  Date releaseTime;
  int alermStatus;
  int zabbixID;
  String zabbixHost; 
  String alermInfo;
  String alermLevel;
  
  public String toJSONString(){
    return JSONObject.toJSONString(this);
  }
  
  public String getAlermText(){
    return "故障ID：[" + zabbixID + "] 级别：[" + alermLevel + "] 服务器：[" + zabbixHost + "] 内容：" + alermInfo;
  }

}
