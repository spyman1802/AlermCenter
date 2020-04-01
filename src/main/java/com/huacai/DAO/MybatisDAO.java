package com.huacai.DAO;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.huacai.Model.MessageServer;
import com.huacai.Model.ZabbixAlerm;
import com.huacai.Model.ZabbixServer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Mapper
public interface MybatisDAO {
 
  // 插入报警信息
  @Insert("insert into zone_alerm (zid, zabbix_id, zabbix_host, alerm_info, alerm_level) values (#{zid}, #{zabbixID}, #{zabbixHost}, #{alermInfo}, #{alermLevel})")
  int insertAlerm(ZabbixAlerm zabbixAlerm);
  
  // 解除报警
  @Update("update zone_alerm set alerm_status = 2, release_time = now() where zid = #{zid} and zabbix_id = #{zabbixID}")
  int releaseAlerm(ZabbixAlerm zabbixAlerm);
  
  // 获取消息服务器列表
  @Select("select group_type as type, group_name as name from zabbix_message_group where zid = #{zid}")
  List<MessageServer> getMessageServerList(int zid);

  // 获取消息服务器具体信息
  @Select("select group_type as type, group_name as name from normal_message_group where gid = #{gid}")
  MessageServer getMessageServer(int gid);

  // 获取所有消息服务器列表
  @Select("select distinct group_type as type, group_name as name from zabbix_message_group")
  List<MessageServer> getAllMessageServerList();

  // 获取zabbix服务器名称
  @Select("select zname from zabbix where zid = #{zid}")
  String getZabbixName(int zid);

  // 获取全部的zabbix服务器名称
  @Select("select zid, zname from zabbix")
  List<ZabbixServer> getAllZabbix();

  // 获取服务器的历史告警数量
  @Select("select day_count as dayCount, release_count as releaseCount, occur_count as occurCount from zabbix_day_alerm_count where zid = #{zid} and calc_date = #{cdate}")
  HashMap getZabbixHisAlermCount(@Param("zid") int zid, @Param("cdate") String cdate);
 
  // 查询指定某一天发生报警的数量
  @Select("select count(*) as cnt "
      + "  from zone_alerm "
      + " where zid = #{zid} "
      + "   and occur_time >= str_to_date(#{cdate}, '%Y-%m-%d') "
      + "   and occur_time < DATE_ADD(str_to_date(#{cdate}, '%Y-%m-%d'), INTERVAL 1 DAY)")
  int getZabbixDayOccurAlermCount(@Param("zid") int zid, @Param("cdate") String cdate);

  // 查询指定某一天解除报警的数量
  @Select("select count(*) as cnt "
      + "  from zone_alerm "
      + " where zid = #{zid} "
      + "   and release_time >= str_to_date(#{cdate}, '%Y-%m-%d') "
      + "   and release_time < DATE_ADD(str_to_date(#{cdate}, '%Y-%m-%d'), INTERVAL 1 DAY)"
      + "   and alerm_status = 2")
  int getZabbixDayReleaseAlermCount(@Param("zid") int zid, @Param("cdate") String cdate);
  
  // 插入日结表
  @Insert("insert into zabbix_day_alerm_count(calc_date, zid, occur_count, release_count, day_count) values (#{calc_date}, #{zid}, #{occur_count}, #{release_count}, #{day_count})")
  int insertDaySettle(@Param("zid") int zid, @Param("calc_date") String cdate, @Param("occur_count") int occurCount, @Param("release_count") int releaseCount, @Param("day_count") int dayCount);

  // 删除日结表
  @Delete("delete from zabbix_day_alerm_count where calc_date = #{calc_date} and zid = #{zid}")
  int deleteDaySettle(@Param("zid") int zid, @Param("calc_date") String cdate);

}