package com.huacai.Config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 陈震 【1525822@qq.com】
 * @Date 2020-04-01 17:11
 */
@Component
public class httpClient {

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
    // 设置超时时间
    RestTemplate restTemplate = restTemplateBuilder
            .setConnectTimeout(Duration.ofSeconds(3))
            .setReadTimeout(Duration.ofSeconds(3))
            .build();

    // 设置转换器
    List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
    FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

    List<MediaType> list = new ArrayList<MediaType>();
    list.add(MediaType.APPLICATION_JSON);
    list.add(MediaType.APPLICATION_FORM_URLENCODED);
    list.add(MediaType.APPLICATION_XML);
    list.add(MediaType.TEXT_PLAIN);
    list.add(MediaType.TEXT_XML);
    list.add(MediaType.TEXT_HTML);

    converter.setSupportedMediaTypes(list);
    converterList.add(0, converter);

    restTemplate.setMessageConverters(converterList);

    // 为日志拦截器，重设httpClient实现类
    restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(restTemplate.getRequestFactory()));

    // 设置日志拦截器
    List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
    if (CollectionUtils.isEmpty(interceptors)) {
      interceptors = new ArrayList<>();
    }
    interceptors.add(new RestTemplateInterceptor());
    restTemplate.setInterceptors(interceptors);

    return restTemplate;
  }
}
