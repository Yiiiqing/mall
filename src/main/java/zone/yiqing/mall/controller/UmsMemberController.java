package zone.yiqing.mall.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zone.yiqing.mall.common.api.CommonResult;
import zone.yiqing.mall.service.UmsMemberService;

/**
 * 用户登录管理.
 *
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-09.
 */
@RestController
@Api(tags = "UmsMemberController", description = "用户登录管理")
@RequestMapping("/sso")
public class UmsMemberController {

  @Autowired
  private UmsMemberService umsMemberService;

  @ApiOperation("获取验证码")
  @GetMapping("/getAuthCode")
  public CommonResult getAuthCode(@RequestParam String telephone) {
    return umsMemberService.generateAuthCode(telephone);
  }

  @ApiOperation("判断验证码是否正确")
  @PostMapping("/verifyAuthCode")
  public CommonResult verifyAuthCode(@RequestParam String telephone,
      @RequestParam String authCode) {
    return umsMemberService.verifyAuthCode(telephone, authCode);
  }
}
