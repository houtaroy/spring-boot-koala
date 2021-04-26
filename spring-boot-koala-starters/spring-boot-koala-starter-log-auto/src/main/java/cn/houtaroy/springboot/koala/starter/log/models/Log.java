package cn.houtaroy.springboot.koala.starter.log.models;

import cn.houtaroy.springboot.koala.domain.models.BaseAudit;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Houtaroy
 */
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Log extends BaseAudit<Long> {

    /**
     * 功能模块
     */
    private String type;

    /**
     * 业务描述
     */
    private String content;

    /**
     * 请求ip
     */
    private String ip;

    /**
     * 请求url
     */
    private String requestUrl;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String arguments;

    /**
     * 返回值
     */
    private String returnValue;

}
