package com.huacai.Service;

import java.util.List;

import com.huacai.Model.ZabbixAlerm;
import com.huacai.Model.NormalMessage;
import com.huacai.Model.ZabbixServer;

public interface CenterService {
  // 接收报警信息
  void acceptAlerm(ZabbixAlerm zabbixAlerm);
  
  // 日结
  void daySettle();
  
  // 获取当前的警报数量
  int getNowAlermCount(int zid);
  
  // 获取Zabbix列表
  List<ZabbixServer> getZabbixList();

  void forwardMessage(NormalMessage msg);
}
