package cn.houtaroy.springboot.koala.starter.mqtt.utils;


import cn.houtaroy.springboot.koala.starter.mqtt.models.KoalaMqttException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Houtaroy
 */
@Slf4j
public class MqttUtil {

    public static final Map<String, MqttPahoMessageHandler> HANDLER_MAP = new HashMap<>(16);

    public static final int DEFAULT_QOS = 0;

    /**
     * 获取默认生产者的消息处理器
     *
     * @return 消息处理器
     */
    public static MqttPahoMessageHandler getDefaultHandler() throws KoalaMqttException {
        Collection<MqttPahoMessageHandler> handlers = HANDLER_MAP.values();
        Iterator<MqttPahoMessageHandler> iterator = handlers.iterator();
        MqttPahoMessageHandler handler = iterator.next();
        if (handler == null) {
            throw new KoalaMqttException("无可用消息通道, 请保证mqtt中至少有一个producer");
        }
        return handler;
    }

    /**
     * 给主题发送消息
     *
     * @param topic 主题
     * @param data  消息内容
     */
    public static void sendMessage(String topic, Object data) throws KoalaMqttException, JsonProcessingException {
        sendMessage(getDefaultHandler(), topic, data);
    }

    /**
     * 使用指定生产者通道给主题发送消息
     *
     * @param producerName 生产者名称
     * @param topic        主题
     * @param data         消息
     */
    public static void sendMessage(String producerName, String topic, Object data) throws KoalaMqttException,
            JsonProcessingException {
        if (!HANDLER_MAP.containsKey(producerName)) {
            throw new KoalaMqttException(String.format("生产者[%s]不存在", producerName));
        }
        sendMessage(HANDLER_MAP.get(producerName), topic, data);
    }

    /**
     * 使用消息处理器给主题发送消息
     *
     * @param handler 消息处理器
     * @param topic   主题
     * @param data    消息
     */
    private static void sendMessage(MqttPahoMessageHandler handler, String topic, Object data)
            throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);
        Message<String> mqttMessage = MessageBuilder.withPayload(json).setHeader(MqttHeaders.TOPIC, topic)
                .setHeader(MqttHeaders.QOS, DEFAULT_QOS).build();
        handler.handleMessage(mqttMessage);
        log.info(String.format("MQTT: 向主题[%s]发送消息[%s]", topic, json));
    }
}
