package zone.yiqing.mall.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zone.yiqing.mall.dto.QueueEnum;

/**
 * 消息队列配置.
 *
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-18.
 */
@Configuration
public class RabbitMqConfig {

  /**
   * 订单消息实际消费队列所绑定的交换机.
   */
  @Bean("orderDirectExchange")
  DirectExchange orderDirect() {
    return (DirectExchange) ExchangeBuilder
        .directExchange(QueueEnum.QUEUE_ORDER_CANCEL.getExchange())
        .durable(true)
        .build();
  }

  /**
   * 订单延迟队列队列所绑定的交换机.
   */
  @Bean("orderTTLDirectExchange")
  DirectExchange orderTTLDirect() {
    return (DirectExchange) ExchangeBuilder
        .directExchange(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange())
        .durable(true)
        .build();
  }

  /**
   * 订单实际消费队列
   */
  @Bean("orderQueue")
  public Queue orderQueue() {
    return new Queue(QueueEnum.QUEUE_ORDER_CANCEL.getName());
  }

  /**
   * 订单延迟队列（死信队列）
   */
  @Bean("orderTTLQueue")
  public Queue orderTTLQueue() {
    return QueueBuilder
        .durable(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getName())
        // 到期后转发的交换机
        .withArgument("x-dead-letter-exchange", QueueEnum.QUEUE_ORDER_CANCEL.getExchange())
        //到期后转发的路由键
        .withArgument("x-dead-letter-routing-key", QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey())
        .build();
  }

  /**
   * 将订单队列绑定到交换机
   */
  @Bean
  Binding orderBinding(@Qualifier("orderDirectExchange") DirectExchange orderDirect,
      @Qualifier("orderQueue") Queue orderQueue) {
    return BindingBuilder
        .bind(orderQueue)
        .to(orderDirect)
        .with(QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey());
  }

  /**
   * 将订单延迟队列绑定到交换机
   */
  @Bean
  Binding orderTtlBinding(
      @Qualifier("orderTTLDirectExchange") DirectExchange orderTTLDirect,
      @Qualifier("orderTTLQueue") Queue orderTTLQueue) {
    return BindingBuilder
        .bind(orderTTLQueue)
        .to(orderTTLDirect)
        .with(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey());
  }
}
