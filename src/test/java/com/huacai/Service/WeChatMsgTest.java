package com.huacai.Service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author 陈震 【1525822@qq.com】
 * @Date 2020-04-01 16:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WeChatMsgTest {

  @Autowired
  WeChatMsg weChatMsg;

  @Test
  public void send() {
    // System.out.println("sdfsdfsdf");
    // assertEquals(1, 1);
    weChatMsg.send("告警消息测试", "1");
  }
}