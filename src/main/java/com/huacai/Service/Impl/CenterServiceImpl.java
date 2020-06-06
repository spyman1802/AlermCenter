package com.huacai.Service.Impl;

import cn.hutool.core.date.DateUtil;
import com.huacai.DAO.MybatisDAO;
import com.huacai.DAO.RestDAO;
import com.huacai.Model.MessageServer;
import com.huacai.Model.ZabbixAlerm;
import com.huacai.Model.NormalMessage;
import com.huacai.Model.ZabbixServer;
import com.huacai.Service.CenterService;
import com.huacai.Service.WeChatMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class CenterServiceImpl implements CenterService {

  @Autowired
  MybatisDAO db;

  @Autowired
  RestDAO rest;

  @Autowired
  WeChatMsg weChatMsg;


  @Override
  public void acceptAlerm(ZabbixAlerm zabbixAlerm) {
    int rtv = 0;

    log.debug("Pharse the alerm infomation ....");

    // 按照上传的告警状态，进行处理
    switch (zabbixAlerm.getAlermStatus()) {
      case 1:
        // 新增告警
        log.debug("New Alerm has been received. zabbix id: {} zabbix host: {} alerm: {}", zabbixAlerm.getZabbixID(), zabbixAlerm.getZabbixHost(), zabbixAlerm.getAlermInfo());
        rtv = db.insertAlerm(zabbixAlerm);
        log.debug("INSERT Result is {}", rtv);

        break;

      case 2:
        // 解除告警
        log.debug("Old Alerm release. zabbix id: {} zabbix host: {} alerm: {}", zabbixAlerm.getZabbixID(), zabbixAlerm.getZabbixHost(), zabbixAlerm.getAlermInfo());
        rtv = db.releaseAlerm(zabbixAlerm);
        log.debug("UPDATE Result is {}", rtv);
        break;

      default:
        // 未知类型，报错
        break;
    }

    // 更新数据库成功，发送消息
    if (rtv > 0) {
      // 获取此服务器对应的消息组列表
      List<MessageServer> msgServerList = db.getMessageServerList(zabbixAlerm.getZid());
      msgServerList.forEach(ms -> {
        // Get current clock
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTimeString = df.format(new Date());

        // 发送消息
        if (zabbixAlerm.getAlermStatus() == 1)
          rest.sendGroupMessage(ms.getType(), ms.getName(), nowTimeString + " 报警发生！[" + db.getZabbixName(zabbixAlerm.getZid()) + "]" + zabbixAlerm.getAlermText());

        if (zabbixAlerm.getAlermStatus() == 2)
          rest.sendGroupMessage(ms.getType(), ms.getName(), nowTimeString + " 报警解除！[" + db.getZabbixName(zabbixAlerm.getZid()) + "]" + zabbixAlerm.getAlermText());

        rest.sendGroupMessage(ms.getType(), ms.getName(), "[" + db.getZabbixName(zabbixAlerm.getZid()) + "] 报警数量为：" + getNowAlermCount(zabbixAlerm.getZid()));
      });
    }

  }

  @Override
  public void daySettle() {
    // 每天晚上12点执行
    // 1、获取前一天的未解除报警数量 pre_count
    // 2、获取当天的发生报警数量 occur_count
    // 3、获取当天的解除报警数量 release_count
    // 4、当天的报警数量为： occur_count + pre_count - release_count

    // 当前时间
    Date nowDate = DateUtil.date();

    // 日结前一天的日期字符串
    String preDayString = DateUtil.formatDate(DateUtil.offsetDay(nowDate, -2));

    // 日结处理的日期字符串
    String nowDayString = DateUtil.formatDate(DateUtil.offsetDay(nowDate, -1));

    // 获取所有的Zabbix服务器列表
    List<ZabbixServer> zabbixs = db.getAllZabbix();

    // 遍历所有的ZABBIX服务器
    zabbixs.forEach(zabbix -> {

      int preAlermCount = 0;

      @SuppressWarnings("rawtypes")
      HashMap map = db.getZabbixHisAlermCount(zabbix.getZid(), preDayString);

      if (map != null && !map.isEmpty()) {
        preAlermCount = (int) map.get("dayCount");
      }

      int nowOccurCount = db.getZabbixDayOccurAlermCount(zabbix.getZid(), nowDayString);
      int nowReleaseCount = db.getZabbixDayReleaseAlermCount(zabbix.getZid(), nowDayString);

      db.deleteDaySettle(zabbix.getZid(), nowDayString);
      db.insertDaySettle(zabbix.getZid(), nowDayString, nowOccurCount, nowReleaseCount, preAlermCount + nowOccurCount - nowReleaseCount);
    });

    // 遍历所有得微信通道，发送日结消息
//    List<MessageServer> msgServerList = db.getAllMessageServerList();
//    msgServerList.forEach(ms -> {
//      // Get current clock
//      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//      String nowTimeString = df.format(new Date());
//
//      rest.sendGroupMessage(ms.getType(), ms.getName(), "今天日结完毕");
//    });

  }

  @Override
  public int getNowAlermCount(int zid) {
    return db.getZabbixNowAlermCount(zid);
  }

  @Override
  public List<ZabbixServer> getZabbixList() {
    // TODO Auto-generated method stub
    return db.getAllZabbix();
  }

  @Override
  public void forwardMessage(NormalMessage msg) {

    // 获取此服务器对应的消息组列表
    MessageServer server = db.getMessageServer(msg.getGroupID());
    if (server == null) return;

    // 发送消息
    rest.sendGroupMessage(server.getType(), server.getName(), msg.getMessage());


  }

}
