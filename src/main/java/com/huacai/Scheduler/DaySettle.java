package com.huacai.Scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.huacai.Service.CenterService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DaySettle {
  
  @Autowired
  CenterService centerService;
  
  @Scheduled(cron = "0 1 0 * * *")
  private void doTask()
  {
    log.info("Start day settle ...");
    centerService.daySettle();
    log.info("do day settle done");
  }
}
