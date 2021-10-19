package zone.yiqing.mall.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zone.yiqing.mall.service.OmsPortalOrderService;

/**
 * 取消订单消息的消费者.
 *
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-18.
 */
@Component
@RabbitListener(queues = "mall.order.cancel")
public class CancelOrderReceiver {

  private static final Logger LOGGER = LoggerFactory.getLogger(CancelOrderReceiver.class);

  @Autowired
  private OmsPortalOrderService portalOrderService;

  @RabbitHandler
  public void handle(Long orderId) {
    LOGGER.info("receive delay message orderId:{}", orderId);
    portalOrderService.cancelOrder(orderId);
  }

}