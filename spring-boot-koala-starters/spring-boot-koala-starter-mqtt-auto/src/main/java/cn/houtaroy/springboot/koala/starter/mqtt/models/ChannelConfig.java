package cn.houtaroy.springboot.koala.starter.mqtt.models;

import lombok.Data;

/**
 * @author Houtaroy
 */
@Data
public class ChannelConfig {

  /**
   * 数组tcp://ip:port
   */
  private String[] url;
  /**
   * 主题，和qos一一对应
   */
  private String[] topics;
  /**
   * qos设置，和topic一一对应
   */
  private int[] qos;
  /**
   * 账号
   */
  private String username;
  /**
   * 密码
   */
  private String password;
  /**
   * consumer clientId
   */
  private String clientId;
  /**
   * 是否异步发送消息
   */
  private Boolean async;

}
