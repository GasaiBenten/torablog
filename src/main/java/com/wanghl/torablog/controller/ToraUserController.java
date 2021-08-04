package com.wanghl.torablog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanghl.torablog.entity.ToraUser;
import com.wanghl.torablog.result.Result;
import com.wanghl.torablog.service.ToraUserService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wanghl
 * @since 2021-04-12
 */
@Controller
public class ToraUserController {

    @Autowired
    private ToraUserService toraUserService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/userSign")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Result userSign(ToraUser user, HttpSession session){
        user.setIsSigned(1);
        boolean b = toraUserService.updateById(user);
        if (b){
            ToraUser toraUser = toraUserService.getById(user);
            toraUser.setPassword(null);
            session.setAttribute("user",toraUser);
            rabbitTemplate.convertAndSend("user-event-exchange", "user.signed", user, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    message.getMessageProperties().setDeliveryTag(user.getId());
                    return message;
                }
            }, new CorrelationData(user.getId().toString()));
        }
        return b ? Result.ok().data("user",user) : Result.error();
    }

    @GetMapping("/user")
    @ResponseBody
    public Result getUserById(ToraUser user,HttpSession session){
        ToraUser byId = toraUserService.getById(user);
        byId.setPassword(null);
        session.setAttribute("user",byId);
        return Result.ok();
    }

}

