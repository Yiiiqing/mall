package zone.yiqing.mall.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import zone.yiqing.mall.common.api.CommonResult;
import zone.yiqing.mall.component.CancelOrderSender;
import zone.yiqing.mall.dto.OrderParam;
import zone.yiqing.mall.service.OmsPortalOrderService;

import java.util.UUID;

/**
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-19.
 */
@Service
public class OmsPortalOrderServiceImpl implements OmsPortalOrderService {

  private static final Logger LOGGER = LoggerFactory.getLogger(OmsPortalOrderServiceImpl.class);

  @Value("${order.timeout}")
  private Long timeout;

  @Autowired
  private CancelOrderSender cancelOrderSender;

  @Override
  public CommonResult<Object> generateOrder(OrderParam orderParam) {
    // todo: 在这里可以插入一系列下单操作
    LOGGER.info("process generateOrder");
    // 下单完成后开启一个延迟消息，用于当用户没有付款时取消订单（orderId应该在下单后生成, 这里模拟了生成 orderId）
    long orderId = UUID.randomUUID().getMostSignificantBits();
    sendDelayMessageCancelOrder(orderId);
    return CommonResult.success(null, "下单成功");
  }

  @Override
  public void cancelOrder(Long orderId) {
    // todo: 执行一系列取消订单操作
    LOGGER.info("process cancelOrder. orderId:{}", orderId);
  }

  /**
   * 发送信息到死信队列.
   */
  private void sendDelayMessageCancelOrder(Long orderId) {
    // 获取订单超时时间
    long delayTimes = timeout * 60 * 1000L;
    cancelOrderSender.sendMessage(orderId, delayTimes);
  }

}
