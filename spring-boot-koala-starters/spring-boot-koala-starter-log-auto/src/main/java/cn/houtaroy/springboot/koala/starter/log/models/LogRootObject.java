package cn.houtaroy.springboot.koala.starter.log.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * @author Houtaroy
 */
@Getter
@AllArgsConstructor
public class LogRootObject {

    /**
     * 目标方法
     */
    private final Method method;

    /**
     * 方法参数
     */
    private final Object[] args;

    /**
     * 目标类的类型信息
     */
    private final Class<?> targetClass;

}
