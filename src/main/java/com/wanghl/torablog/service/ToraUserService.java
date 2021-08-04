package com.wanghl.torablog.service;

import com.wanghl.torablog.entity.ToraUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanghl
 * @since 2021-04-12
 */
public interface ToraUserService extends IService<ToraUser> {

    String doLogin(ToraUser toraUser, HttpSession session, RedirectAttributes attributes, HttpServletRequest request);

    String doRegister(ToraUser toraUser, RedirectAttributes attributes, HttpServletRequest request);

    Map checkEmail(String email);

    Map checkUser(String email);

    String logout(HttpSession session);

    void refreshSign(ToraUser user);

}
