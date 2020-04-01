package com.huacai.DAO;

public interface RestDAO {

  // 发送群消息
  void sendGroupMessage(int ServerType, String groupName, String msg);
  
}
