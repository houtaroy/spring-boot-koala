package cn.houtaroy.springboot.koala.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Houtaroy
 */
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class BusinessLog extends BaseAudit<Long> {

    private Integer type;

    private String content;

    private String ip;

    private String mappingMethod;

    private String mappingUrl;

    private String requestUrl;

    private String requestMethod;

    private String arguments;

    private String returnValue;

}
