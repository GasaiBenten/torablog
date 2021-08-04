package com.wanghl.torablog.listener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.wanghl.torablog.entity.ToraUser;
import com.wanghl.torablog.service.ToraUserService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.io.IOException;


@Component
@RabbitListener(queues = {"user.sign.refresh.queue"})
public class UserSignRefreshListener {

    @Autowired
    private ToraUserService toraUserService;

    @RabbitHandler
    public void refreshListener(ToraUser user, Message message, Channel channel) throws IOException {
        try {
            toraUserService.refreshSign(user);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }

}
