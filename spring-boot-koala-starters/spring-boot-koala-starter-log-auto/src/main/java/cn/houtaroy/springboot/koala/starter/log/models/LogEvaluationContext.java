package cn.houtaroy.springboot.koala.starter.log.models;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;

/**
 * @author Houtaroy
 */
public class LogEvaluationContext extends MethodBasedEvaluationContext {

    /**
     * 构造方法
     *
     * @param rootObject 数据来源对象
     * @param discoverer 参数解析器
     */
    public LogEvaluationContext(LogRootObject rootObject, ParameterNameDiscoverer discoverer) {
        super(rootObject, rootObject.getMethod(), rootObject.getArgs(), discoverer);
    }

}
