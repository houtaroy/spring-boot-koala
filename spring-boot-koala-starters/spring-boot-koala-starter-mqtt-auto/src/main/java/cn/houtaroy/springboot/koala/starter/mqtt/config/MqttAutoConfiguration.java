package cn.houtaroy.springboot.koala.starter.mqtt.config;


import cn.houtaroy.springboot.koala.starter.mqtt.models.ChannelConfig;
import cn.houtaroy.springboot.koala.starter.mqtt.utils.MqttUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;

/**
 * @author Houtaroy
 */
@Slf4j
@Data
@AllArgsConstructor
@Configuration
@EnableConfigurationProperties(MqttProperties.class)
public class MqttAutoConfiguration implements ApplicationContextAware, BeanPostProcessor {

    public static final String ADAPTER_SUFFIX = "-adapter";
    public static final String HANDLER_SUFFIX = "-handler";
    private ConfigurableApplicationContext applicationContext;

    private final MqttProperties mqttProperties;

    /**
     * 设置应用上下文
     *
     * @param applicationContext 应用上下文
     * @throws BeansException bean异常
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
        mqttProperties.getProducers().forEach(this::initProducer);
        mqttProperties.getConsumers().forEach(this::initConsumer);
    }

    /**
     * 初始化生产者通道
     *
     * @param name     生产者通道名称
     * @param producer 生产者配置
     */
    private void initProducer(String name, ChannelConfig producer) {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();
        beanFactory.registerBeanDefinition(name, mqttChannel());
        String handlerName = name + HANDLER_SUFFIX;
        beanFactory.registerBeanDefinition(handlerName, mqttOutbound(producer));
        MqttUtil.HANDLER_MAP.put(name, beanFactory.getBean(handlerName, MqttPahoMessageHandler.class));
        log.info(String.format("MQTT: 生产者[%s]已创建", name));
    }

    /**
     * 初始化消费者
     *
     * @param name     消费者通道名称
     * @param consumer 消费者配置
     */
    private void initConsumer(String name, ChannelConfig consumer) {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();
        beanFactory.registerBeanDefinition(name, mqttChannel());
        MessageChannel mqttChannel = beanFactory.getBean(name, MessageChannel.class);
        beanFactory.registerBeanDefinition(name + ADAPTER_SUFFIX, channelAdapter(consumer, mqttChannel));
        log.info(String.format("MQTT: 消费者[%s]已创建", name));
    }

    /**
     * 初始化mqtt客户端工厂
     *
     * @param channel 通道配置
     * @return 客户端工厂
     */
    private MqttPahoClientFactory mqttClientFactory(ChannelConfig channel) {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(channel.getUrl());
        options.setCleanSession(true);
        if (channel.getPassword() != null) {
            options.setPassword(channel.getPassword().toCharArray());
        }
        options.setUserName(channel.getUsername());
        factory.setConnectionOptions(options);
        return factory;
    }

    /**
     * 初始化通道
     *
     * @return 通道定义
     */
    private AbstractBeanDefinition mqttChannel() {
        BeanDefinitionBuilder messageChannelBuilder = BeanDefinitionBuilder.genericBeanDefinition(DirectChannel.class);
        messageChannelBuilder.setScope(BeanDefinition.SCOPE_SINGLETON);
        return messageChannelBuilder.getBeanDefinition();
    }

    /**
     * 初始化通道适配器
     *
     * @param channel     通道配置
     * @param mqttChannel 通道定义
     * @return 通道
     */
    private AbstractBeanDefinition channelAdapter(ChannelConfig channel, MessageChannel mqttChannel) {
        BeanDefinitionBuilder messageProducerBuilder = BeanDefinitionBuilder
                .genericBeanDefinition(MqttPahoMessageDrivenChannelAdapter.class);
        messageProducerBuilder.setScope(BeanDefinition.SCOPE_SINGLETON);
        messageProducerBuilder.addConstructorArgValue(channel.getClientId());
        messageProducerBuilder.addConstructorArgValue(mqttClientFactory(channel));
        messageProducerBuilder.addConstructorArgValue(channel.getTopics());
        messageProducerBuilder.addPropertyValue("converter", new DefaultPahoMessageConverter());
        messageProducerBuilder.addPropertyValue("qos", channel.getQos());
        messageProducerBuilder.addPropertyValue("outputChannel", mqttChannel);
        return messageProducerBuilder.getBeanDefinition();
    }

    /**
     * 初始化生产者出站通道
     *
     * @param channel 通道配置
     * @return 生产者出站通道
     */
    private AbstractBeanDefinition mqttOutbound(ChannelConfig channel) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MqttPahoMessageHandler.class);
        builder.addConstructorArgValue(channel.getClientId());
        builder.addConstructorArgValue(mqttClientFactory(channel));
        builder.addPropertyValue("async", channel.getAsync());
        return builder.getBeanDefinition();
    }

}
