package com.huacai.Model;

import lombok.Data;

@Data
public class ZabbixServer implements java.io.Serializable{

  private static final long serialVersionUID = -8728633094972704580L;
  
  int zid;
  String zname;
}
