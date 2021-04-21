package cn.houtaroy.springboot.koala.starter.mqtt.config;



import cn.houtaroy.springboot.koala.starter.mqtt.models.ChannelConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author Houtaroy
 */
@Data
@ConfigurationProperties("mqtt")
public class MqttProperties {

  /**
   * 所有的配置
   */
  private final Map<String, ChannelConfig> producers;

  private final Map<String, ChannelConfig> consumers;

}
