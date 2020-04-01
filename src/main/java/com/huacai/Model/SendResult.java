package com.huacai.Model;

import java.io.Serializable;

import lombok.Data;

@Data
public class SendResult implements Serializable {

  private static final long serialVersionUID = -9098038837477921902L;

  String status;
  String id;
  int code;
}
