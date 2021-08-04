package com.wanghl.torablog.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public Exchange userEventExchange(){
        //String name, boolean durable, boolean autoDelete, Map<String, Object> arguments
        TopicExchange topicExchange = new TopicExchange("user-event-exchange",true,false,null);
        return topicExchange;
    }

    @Bean
    public Queue userSignRefreshQueue(){
        Queue queue = new Queue("user.sign.refresh.queue", true, false, false);
        return queue;
    }

    @Bean
    public Queue userSignRefreshDelayQueue(){
        //String name, boolean durable, boolean exclusive, boolean autoDelete, @Nullable Map<String, Object> arguments
        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange","user-event-exchange");
        map.put("x-dead-letter-routing-key","user.signed.refresh");
        map.put("x-message-ttl",10000);
        Queue queue = new Queue("user.sign.refresh.delay.queue",true,false,false,map);
        return queue;
    }

    @Bean
    public Binding userSignRefreshDelayQueueBinding(){
        //String destination, Binding.DestinationType destinationType, String exchange, String routingKey, @Nullable Map<String, Object> arguments
        Binding binding = new Binding("user.sign.refresh.delay.queue",
                Binding.DestinationType.QUEUE,
                "user-event-exchange",
                "user.signed",
                null);

        return binding;
    }

    @Bean
    public Binding userSignRefreshQueueBinding(){
        //String destination, Binding.DestinationType destinationType, String exchange, String routingKey, @Nullable Map<String, Object> arguments
        Binding binding = new Binding("user.sign.refresh.queue",
                Binding.DestinationType.QUEUE,
                "user-event-exchange",
                "user.signed.refresh",
                null);

        return binding;
    }

}
