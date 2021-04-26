package cn.houtaroy.springboot.koala.starter.security.utils;

import cn.houtaroy.springboot.koala.domain.models.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Houtaroy
 */
public class SecurityUtil {

    /**
     * 获取当前用户
     *
     * @return 当前用户
     */
    public static User currentUser() {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
