package com.wanghl.torablog.controller;

import com.wanghl.torablog.entity.ToraUser;
import com.wanghl.torablog.service.ToraUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.google.code.kaptcha.impl.DefaultKaptcha;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * @author wanghl
 * @date 2021/4/12 - 13:19
 */
@Controller
public class LoginController {

    @Autowired
    private ToraUserService toraUserService;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @PostMapping("/doLogin")
    public String doLogin(ToraUser toraUser,
                          HttpSession session,
                          RedirectAttributes attributes,
                          HttpServletRequest request){
        return toraUserService.doLogin(toraUser,session,attributes,request);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        return toraUserService.logout(session);
    }

    @PostMapping("/doRegister")
    public String doRegister(ToraUser toraUser,
                          RedirectAttributes attributes,
                          HttpServletRequest request){
        return toraUserService.doRegister(toraUser,attributes,request);
    }

    @PostMapping("/checkEmail")
    @ResponseBody
    public Map checkEmail(String email){
        return toraUserService.checkEmail(email);
    }

    @PostMapping("/checkUser")
    @ResponseBody
    public Map checkUser(String username){
        return toraUserService.checkUser(username);
    }




    //?????????????????????
    @GetMapping("/getVerityCode")
    public void getVerityCode(HttpServletResponse response, HttpServletRequest request) {
        //??????response???????????????image/jpeg
        response.setDateHeader("Expires",0);
        response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
        response.setHeader("Cache-Control","post-check=0, pre-check=0");
        response.setHeader("Pragma","no-cache");
        response.setContentType("image/jpeg");

        //???????????????
        //???????????????????????????
        String code = defaultKaptcha.createText();
        System.out.println("??????????????????"+code);
        //????????????????????????session
        request.getSession().setAttribute("code",code);
        //?????????????????????
        BufferedImage image = defaultKaptcha.createImage(code);
        ServletOutputStream outputStream = null;
        try {
            outputStream= response.getOutputStream();
            //???????????????????????????????????????jpg
            ImageIO.write(image,"jpg",outputStream);
            outputStream.flush();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (null!=outputStream){
                try {
                    outputStream.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

}
