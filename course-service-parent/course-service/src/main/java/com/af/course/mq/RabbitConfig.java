package com.af.course.mq;

import com.af.course.api.constant.CourseMqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mq配置
 *
 * @author Tanglinfeng
 * @date 2022/3/28 20:49
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue updateScoreQueue() {
        return new Queue(CourseMqConstant.UPDATE_SCORE_QUEUE);
    }

    @Bean
    public FanoutExchange updateScoreExchange() {
        return new FanoutExchange(CourseMqConstant.UPDATE_SCORE_EXCHANGE);
    }

    @Bean
    public Binding updateScoreBinding() {
        return BindingBuilder.bind(updateScoreQueue()).to(updateScoreExchange());
    }
}
