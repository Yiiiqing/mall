package zone.yiqing.mall.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import zone.yiqing.mall.common.api.CommonResult;
import zone.yiqing.mall.mbg.model.PmsBrand;
import zone.yiqing.mall.service.PmsBrandService;

import java.util.List;

/**
 * 品牌管理 Controller
 *
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-09-29.
 */
@Controller
@RequestMapping("/brand")
public class PmsBrandController {

  @Autowired
  PmsBrandService service;

  private static final Logger LOGGER = LoggerFactory.getLogger(PmsBrandController.class);

  @RequestMapping(value = "listAll", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult<List<PmsBrand>> getBrandList() {
    return CommonResult.success(service.listAllBrand());
  }
}
