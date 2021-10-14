package zone.yiqing.mall.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zone.yiqing.mall.common.api.CommonPage;
import zone.yiqing.mall.common.api.CommonResult;
import zone.yiqing.mall.nosql.elasticsearch.document.EsProduct;
import zone.yiqing.mall.service.EsProductService;

import java.util.List;

/**
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-14.
 */
@RestController
@Api(tags = "EsProductController", description = "搜索商品管理")
@RequestMapping("/esProduct")
public class EsProductController {

  @Autowired
  private EsProductService esProductService;

  @ApiOperation(value = "导入所有数据库中商品到ES")
  @PostMapping("/importAll")
  public CommonResult<Integer> importAllList() {
    int count = esProductService.importAll();
    return CommonResult.success(count);
  }

  @ApiOperation(value = "根据id批量删除商品")
  @PostMapping(value = "/delete/batch")
  public CommonResult<Object> delete(@RequestParam("ids") List<Long> ids) {
    esProductService.delete(ids);
    return CommonResult.success(null);
  }

  @ApiOperation(value = "根据id创建商品")
  @PostMapping(value = "/create/{id}")
  public CommonResult<EsProduct> create(@PathVariable Long id) {
    EsProduct esProduct = esProductService.create(id);
    if (esProduct != null) {
      return CommonResult.success(esProduct);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation(value = "简单搜索")
  @GetMapping(value = "/search/simple")
  public CommonResult<CommonPage<EsProduct>> search(@RequestParam(required = false) String keyword,
      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
      @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
    Page<EsProduct> esProductPage = esProductService.search(keyword, pageNum, pageSize);
    return CommonResult.success(CommonPage.restPage(esProductPage));
  }
}
