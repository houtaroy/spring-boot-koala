package cn.houtaroy.springboot.koala.tools;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Houtaroy
 */
public class JacksonUtil {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  static {
    MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
  }

  /**
   * 读取json为指定对象类型
   *
   * @param json        json数据
   * @param objectClass 对象类型
   * @param <T>         对象类型泛型
   * @return 对象
   * @throws JsonProcessingException Json转换错误
   */
  public static <T> T load(String json, Class<T> objectClass) throws JsonProcessingException {
    return MAPPER.readValue(json, objectClass);
  }

  /**
   * 将对象转换为json字符串
   *
   * @param data 对象
   * @param <T>  对象泛型
   * @return json字符串
   * @throws JsonProcessingException Json转换错误
   */
  public static <T> String dump(T data) throws JsonProcessingException {
    return MAPPER.writeValueAsString(data);
  }

}
