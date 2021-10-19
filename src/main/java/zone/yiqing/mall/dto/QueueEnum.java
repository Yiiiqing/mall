package zone.yiqing.mall.dto;

import lombok.Getter;

/**
 * 消息队列枚举配置.用于延迟消息队列及处理取消订单消息队列的常量定义，包括交换机名称、队列名称、路由键名称.
 *
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-18.
 */
@Getter
public enum QueueEnum {

  /**
   * 信息通知队列
   */
  QUEUE_ORDER_CANCEL("mall.order.direct", "mall.order.cancel", "mall.order.cancel"),

  /**
   * 消息通知ttl队列
   */
  QUEUE_TTL_ORDER_CANCEL("mall.order.direct.ttl", "mall.order.cancel.ttl", "mall.order.cancel.ttl");

  private String exchange;
  private String name;
  private String routeKey;

  QueueEnum(String exchange, String name, String routeKey) {
    this.exchange = exchange;
    this.name = name;
    this.routeKey = routeKey;
  }

  public String getExchange() {
    return exchange;
  }

  public String getName() {
    return name;
  }

  public String getRouteKey() {
    return routeKey;
  }
}
