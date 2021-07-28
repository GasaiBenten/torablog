package com.wanghl.torablog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanghl.torablog.Utils.MD5;
import com.wanghl.torablog.entity.ToraUser;
import com.wanghl.torablog.mapper.ToraUserMapper;
import com.wanghl.torablog.service.ToraUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wanghl
 * @since 2021-04-12
 */
@Service
public class ToraUserServiceImpl extends ServiceImpl<ToraUserMapper, ToraUser> implements ToraUserService {

    @Override
    public String doLogin(ToraUser toraUser, HttpSession session, RedirectAttributes attributes, HttpServletRequest request) {
        String username = toraUser.getUsername();
        String password = toraUser.getPassword();
        String code = toraUser.getCode();
        String kaptcha = (String) request.getSession().getAttribute("code");
        String referer = toraUser.getReferer();

        if (!kaptcha.equalsIgnoreCase(code)){
            attributes.addFlashAttribute("message","验证码错误");
            return "redirect:/login";
        }

        ToraUser user = baseMapper.selectOne(
                new QueryWrapper<ToraUser>()
                        .eq("username",username)
                        .eq("password",MD5.encode(password)));

        if (null==user){
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/login";
        } else {
            user.setPassword(null);
            session.setAttribute("user",user);
            if (referer == ""){
                return "redirect:/";
            }
            if (referer.indexOf("localhost:8002") == -1){
                return "redirect:/";
            } else {
                if (referer.indexOf("login") != -1){
                    return "redirect:/";
                } else if (referer.indexOf("register") != -1){
                    return "redirect:/";
                } else {
                    return "redirect:"+referer;
                }
            }
        }
    }

    @Override
    public String doRegister(ToraUser toraUser, RedirectAttributes attributes, HttpServletRequest request) {
        String email = toraUser.getEmail();
        String username = toraUser.getUsername();
        String code = toraUser.getCode();
        String kaptcha = (String) request.getSession().getAttribute("code");

        Map map = checkEmail(email);
        int resultCode = (Integer) map.get("code");
        if (resultCode == 20001){
            attributes.addFlashAttribute("message","邮箱已注册");
            return "redirect:/register";
        }

        Map map1 = checkUser(username);
        int resultCode1 = (Integer) map1.get("code");
        if (resultCode1 == 20001){
            attributes.addFlashAttribute("message","用户名已注册");
            return "redirect:/register";
        }

        if (!kaptcha.equalsIgnoreCase(code)){
            attributes.addFlashAttribute("message","验证码错误");
            return "redirect:/register";
        }

        toraUser.setPassword(MD5.encode(toraUser.getPassword()));
        toraUser.setAvatar("https://seikai-tora-blog.oss-cn-beijing.aliyuncs.com/Guest.png");
        int insert = baseMapper.insert(toraUser);
        if (insert==0){
            attributes.addFlashAttribute("message","发生错误，注册失败！");
            return "redirect:/register";
        } else {
            attributes.addFlashAttribute("success","恭喜，注册成功！");
            return "redirect:/login";
        }
    }

    @Override
    public Map checkEmail(String email) {
        Map<String,Object> map = new HashMap<>();

        QueryWrapper<ToraUser> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        ToraUser user = baseMapper.selectOne(wrapper);
        if (null!=user){
            map.put("code",20001);
            map.put("success",false);
            map.put("message","邮箱已注册");
        } else {
            map.put("code",20000);
            map.put("success",true);
            map.put("message","邮箱可以注册");
        }
        return map;
    }

    @Override
    public Map checkUser(String username) {
        Map<String,Object> map = new HashMap<>();

        QueryWrapper<ToraUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        ToraUser user = baseMapper.selectOne(wrapper);
        if (null!=user){
            map.put("code",20001);
            map.put("success",false);
            map.put("message","用户名已注册");
        } else {
            map.put("code",20000);
            map.put("success",true);
            map.put("message","用户名可以注册");
        }
        return map;
    }

    @Override
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }
}
