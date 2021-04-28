package cn.houtaroy.springboot.koala.starter.log;


import cn.houtaroy.springboot.koala.starter.log.annotations.KoalaLog;
import cn.houtaroy.springboot.koala.starter.log.models.Log;
import cn.houtaroy.springboot.koala.starter.log.models.LogEvaluationContext;
import cn.houtaroy.springboot.koala.starter.log.models.LogEvaluator;
import cn.houtaroy.springboot.koala.starter.log.models.LogRootObject;
import cn.houtaroy.springboot.koala.tools.IpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author Houtaroy
 */
@Aspect
@Slf4j
public class KoalaLogAspect {

    /**
     * 日志SpEL解析器
     */
    private final LogEvaluator evaluator = new LogEvaluator();

    /**
     * jackson
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 日志切入点
     */
    @Pointcut(value = "@annotation(cn.houtaroy.springboot.koala.starter.log.annotations.KoalaLog)")
    public void logPointCut() {
    }

    /**
     * 方法返回后切入点
     *
     * @param joinPoint   切入点
     * @param returnValue 返回值
     */
    @AfterReturning(value = "logPointCut()", returning = "returnValue")
    public void log(JoinPoint joinPoint, Object returnValue) {
        // 记录正常日志
        Log koalaLog = generateLog(joinPoint);
        try {
            koalaLog.setReturnValue(objectMapper.writeValueAsString(returnValue));
        } catch (JsonProcessingException e) {
            log.error("KOALA-LOG: 序列化返回值失败", e);
        }
    }

    /**
     * 方法抛出异常后切入点
     *
     * @param joinPoint 切入点
     * @param error     异常
     */
    @AfterThrowing(pointcut = "logPointCut()", throwing = "error")
    public void errorLog(JoinPoint joinPoint, Exception error) {
        // 记录异常日志
        Log koalaLog = generateLog(joinPoint);
        koalaLog.setReturnValue(error.getMessage());
    }

    /**
     * 生成日志实体
     *
     * @param joinPoint 切入点
     * @return 日志实体
     */
    private Log generateLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Object[] args = joinPoint.getArgs();
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(joinPoint.getTarget());
        KoalaLog annotation = method.getAnnotation(KoalaLog.class);
        LogRootObject rootObject = new LogRootObject(method, args, targetClass);
        LogEvaluationContext context = new LogEvaluationContext(rootObject, evaluator.getDiscoverer());
        Object content = evaluator.parse(annotation.content(), context);
        Log koalaLog = Log.builder().type(annotation.type()).content(content.toString()).createTime(new Date()).build();
        try {
            koalaLog.setArguments(objectMapper.writeValueAsString(args));
        } catch (JsonProcessingException e) {
            log.error("KOALA-LOG: 序列化方法参数失败", e);
        }
        //处理HTTP请求相关日志
        generateRequest(koalaLog);
        return koalaLog;
    }

    /**
     * 生成请求数据, 填入日志实体
     *
     * @param log 日志实体
     */
    private void generateRequest(Log log) {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            log.setRequestMethod(request.getMethod());
            log.setRequestUrl(request.getRequestURI());
            log.setIp(IpUtil.getRemoteIpAddress(request));
        }
    }
}
