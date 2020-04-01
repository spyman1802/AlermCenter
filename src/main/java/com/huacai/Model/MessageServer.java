package com.huacai.Model;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MessageServer implements Serializable {
  
  private static final long serialVersionUID = 5689056414653653661L;

  // 服务器类型
  int type;
  
  // 组名称
  String name;

  // 主键
  int r_id;

}
