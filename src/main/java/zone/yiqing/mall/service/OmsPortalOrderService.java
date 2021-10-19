package zone.yiqing.mall.service;

import org.springframework.transaction.annotation.Transactional;
import zone.yiqing.mall.common.api.CommonResult;
import zone.yiqing.mall.dto.OrderParam;

/**
 * 前台订单管理Service.
 *
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-19.
 */
public interface OmsPortalOrderService {

  /**
   * 根据提交信息生成订单
   *
   * @param orderParam
   * @return
   */
  @Transactional
  CommonResult<Object> generateOrder(OrderParam orderParam);

  /**
   * 取消单个超时订单
   *
   * @param orderId
   */
  @Transactional
  void cancelOrder(Long orderId);
}
