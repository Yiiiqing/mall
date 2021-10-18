package zone.yiqing.mall.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zone.yiqing.mall.common.api.CommonResult;
import zone.yiqing.mall.nosql.mongodb.document.MemberReadHistory;
import zone.yiqing.mall.service.MemberReadHistoryService;

import java.util.List;

/**
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-18.
 */
@RestController
@Api(tags = "MemberReadHistoryController", description = "会员商品浏览记录管理")
@RequestMapping("/member/readHistory")
public class MemberReadHistoryController {

  @Autowired
  private MemberReadHistoryService memberReadHistoryService;

  @ApiOperation("创建浏览记录")
  @PostMapping("/create")
  public CommonResult create(@RequestBody MemberReadHistory memberReadHistory) {
    int count = memberReadHistoryService.create(memberReadHistory);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("删除浏览记录")
  @PostMapping(value = "/delete")
  public CommonResult delete(@RequestParam("ids") List<String> ids) {
    int count = memberReadHistoryService.delete(ids);
    if (count > 0) {
      return CommonResult.success(count);
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("展示浏览记录")
  @GetMapping(value = "/list")
  public CommonResult<List<MemberReadHistory>> list(Long memberId) {
    List<MemberReadHistory> memberReadHistoryList = memberReadHistoryService.list(memberId);
    return CommonResult.success(memberReadHistoryList);
  }
}