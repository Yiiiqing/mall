package zone.yiqing.mall.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zone.yiqing.mall.common.api.CommonResult;
import zone.yiqing.mall.dto.OrderParam;
import zone.yiqing.mall.service.OmsPortalOrderService;

/**
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-19.
 */
@RestController
@RequestMapping("/order")
@Api(tags = "OmsPortalOrderController", description = "订单管理")
public class OmsPortalOrderController {

  @Autowired
  private OmsPortalOrderService omsPortalOrderService;

  @ApiOperation("根据购物车信息生成订单")
  @PostMapping("/generate")
  public CommonResult<Object> generateOrder(@RequestBody OrderParam orderParam) {
    return omsPortalOrderService.generateOrder(orderParam);
  }

}
