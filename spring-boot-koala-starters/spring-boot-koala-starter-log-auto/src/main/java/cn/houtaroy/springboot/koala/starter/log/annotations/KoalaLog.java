package cn.houtaroy.springboot.koala.starter.log.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Houtaroy
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface KoalaLog {

    /**
     * 功能模块
     *
     * @return 功能模块
     */
    String type() default "";

    /**
     * 业务描述
     *
     * @return 业务描述
     */
    String content() default "";

}
