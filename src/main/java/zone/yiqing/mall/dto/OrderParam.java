package zone.yiqing.mall.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-19.
 */
@Getter
@Setter
public class OrderParam {

  // 收货地址
  private Long memberReceiveAddressId;

  // 优惠券 id
  private Long couponId;

  // 使用的积分数
  private Integer userIntegration;

  /// 支付方式
  private Integer payType;

}
