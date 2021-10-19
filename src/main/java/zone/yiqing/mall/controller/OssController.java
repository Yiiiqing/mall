package zone.yiqing.mall.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zone.yiqing.mall.common.api.CommonResult;
import zone.yiqing.mall.dto.OssCallbackResult;
import zone.yiqing.mall.dto.OssPolicyResult;
import zone.yiqing.mall.service.OssService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-19.
 */
@RestController
@Api(tags = "OssController", description = "Oss管理")
@RequestMapping("/aliyun/oss")
public class OssController {

  @Autowired
  private OssService ossService;

  @ApiOperation("oss 上传签名生成")
  @GetMapping("/policy")
  public CommonResult<OssPolicyResult> policy() {
    OssPolicyResult policy = ossService.policy();
    return CommonResult.success(policy);
  }

  @ApiOperation("oss 上传成功回调")
  @PostMapping("/callback")
  public CommonResult<OssCallbackResult> callback(HttpServletRequest request) {
    OssCallbackResult callback = ossService.callback(request);
    return CommonResult.success(callback);
  }
}
