package com.huacai.Config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * @Author 陈震 【1525822@qq.com】
 * @Date 2020-04-01 17:01
 */
@Slf4j
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

  static final String DEFAULT_ENCODING = "UTF-8";

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    traceRequest(request, body);
    ClientHttpResponse response = execution.execute(request, body);

    traceResponse(response);
    return response;
  }

  private void traceRequest(HttpRequest request, byte[] body) throws IOException {
    log.info("===========================request begin================================================");
    log.info("URI         : {}", request.getURI());
    log.info("Method      : {}", request.getMethodValue());
    log.info("Headers     : {}", request.getHeaders());
    log.info("Request body: {}", new String(body, "UTF-8"));
    log.info("==========================request end================================================");
  }

  private void traceResponse(ClientHttpResponse response) throws IOException {
    log.info("============================response begin==========================================");
    log.info("Status code  : {}", response.getStatusCode());
    log.info("Status text  : {}", response.getStatusText());
    log.info("Headers      : {}", response.getHeaders());
    log.info("Response body: {}", IOUtils.toString(response.getBody(), DEFAULT_ENCODING));
    log.info("=======================response end=================================================");
  }
}
