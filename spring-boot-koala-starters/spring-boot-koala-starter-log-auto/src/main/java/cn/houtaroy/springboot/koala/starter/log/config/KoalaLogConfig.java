package cn.houtaroy.springboot.koala.starter.log.config;

import cn.houtaroy.springboot.koala.starter.log.KoalaLogAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

/**
 * @author Houtaroy
 */
@Configuration
public class KoalaLogConfig {

    /**
     * 日志切面
     *
     * @return 日志切面
     */
    @Bean
    @ConditionalOnMissingBean(name = "koalaLogAspect")
    public KoalaLogAspect koalaLogAspect() {
        return new KoalaLogAspect();
    }

    /**
     * HTTP请求上下文监听器声明
     *
     * @return HTTP请求上下文监听器
     */
    @Bean
    @ConditionalOnMissingBean(name = "requestContextListener")
    public ServletListenerRegistrationBean<RequestContextListener> requestContextListener() {
        return new ServletListenerRegistrationBean<>(new RequestContextListener());
    }

}
