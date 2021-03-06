package zone.yiqing.mall.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zone.yiqing.mall.common.api.CommonPage;
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
@Api(tags = "PmsBrandController", description = "商品品牌管理")
@RestController
@RequestMapping("/brand")
public class PmsBrandController {

  @Autowired
  PmsBrandService service;

  private static final Logger LOGGER = LoggerFactory.getLogger(PmsBrandController.class);

  @ApiOperation("获取所有品牌列表")
  @RequestMapping(value = "listAll", method = RequestMethod.GET)
  @PreAuthorize("hasAuthority('pms:brand:read')")
  public CommonResult<List<PmsBrand>> getBrandList() {
    return CommonResult.success(service.listAllBrand());
  }

  @ApiOperation("添加商品")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @PreAuthorize("hasAuthority('pms:brand:create')")
  public CommonResult createBrand(@RequestBody PmsBrand pmsBrand) {
    CommonResult commonResult;
    int count = service.createBrand(pmsBrand);
    if (count == 1) {
      commonResult = CommonResult.success(pmsBrand);
      LOGGER.debug("createBrand success:{}", pmsBrand);
    } else {
      commonResult = CommonResult.failed("操作失败");
      LOGGER.debug("createBrand failed:{}", pmsBrand);
    }
    return commonResult;
  }

  @ApiOperation("更新指定 id 品牌信息")
  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
  @PreAuthorize("hasAuthority('pms:brand:update')")
  public CommonResult updateBrand(@PathVariable("id") Long id, @RequestBody PmsBrand pmsBrandDto,
      BindingResult result) {
    CommonResult commonResult;
    int count = service.updateBrand(id, pmsBrandDto);
    if (count == 1) {
      commonResult = CommonResult.success(pmsBrandDto);
      LOGGER.debug("updateBrand success:{}", pmsBrandDto);
    } else {
      commonResult = CommonResult.failed("操作失败");
      LOGGER.debug("updateBrand failed:{}", pmsBrandDto);
    }
    return commonResult;
  }

  @ApiOperation("删除指定 id 品牌信息")
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
  @PreAuthorize("hasAuthority('pms:brand:delete')")
  public CommonResult deleteBrand(@PathVariable("id") Long id) {
    int count = service.deleteBrand(id);
    if (count == 1) {
      LOGGER.debug("deleteBrand success :id={}", id);
      return CommonResult.success(null);
    } else {
      LOGGER.debug("deleteBrand failed :id={}", id);
      return CommonResult.failed("操作失败");
    }
  }

  @ApiOperation("分页查询品牌列表")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @PreAuthorize("hasAuthority('pms:brand:read')")
  public CommonResult<CommonPage<PmsBrand>> listBrand(
      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
      @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {
    List<PmsBrand> brandList = service.listBrand(pageNum, pageSize);
    return CommonResult.success(CommonPage.restPage(brandList));
  }

  @ApiOperation("获取指定 id 的品牌详情")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @PreAuthorize("hasAuthority('pms:brand:read')")
  public CommonResult<PmsBrand> brand(@PathVariable("id") Long id) {
    return CommonResult.success(service.getBrand(id));
  }
}
