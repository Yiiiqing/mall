package zone.yiqing.mall.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zone.yiqing.mall.common.api.CommonResult;
import zone.yiqing.mall.dto.UmsAdminLoginParam;
import zone.yiqing.mall.mbg.model.UmsAdmin;
import zone.yiqing.mall.mbg.model.UmsPermission;
import zone.yiqing.mall.service.UmsAdminService;

import java.util.HashMap;
import java.util.List;

/**
 * 后台用户管理.
 *
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-11.
 */
@RestController
@Api(tags = "UmsAdminController", description = "后台用户管理")
@RequestMapping("/admin")
public class UmsAdminController {

  @Autowired
  private UmsAdminService adminService;

  @Value("${jwt.tokenHead}")
  private String tokenHead;

  @Value("${jwt.tokenHeader}")
  private String tokenHeader;

  @ApiOperation(value = "用户注册")
  @PostMapping("/register")
  public CommonResult<UmsAdmin> register(@RequestBody UmsAdmin umsAdminParam,
      BindingResult result) {
    // BindingResult 是为了和 Valid 注解进行参数校验, 但是这里并未启用
    UmsAdmin umsAdmin = adminService.register(umsAdminParam);
    if (umsAdmin == null) {
      CommonResult.failed();
    }
    return CommonResult.success(umsAdmin);
  }

  @ApiOperation("登录后返回 token")
  @PostMapping("/login")
  public CommonResult login(@RequestBody UmsAdminLoginParam umsAdminLoginParam,
      BindingResult result) {
    String token = adminService
        .login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
    if (token == null) {
      return CommonResult.validateFailed("用户名或密码错误");
    }
    HashMap<String, String> tokenMap = new HashMap<>();
    tokenMap.put("token", token);
    tokenMap.put("tokenHead", tokenHead);
    return CommonResult.success(tokenMap);
  }

  @ApiOperation("获取用户所有权限（包括+-权限）")
  @GetMapping("/permission/{adminId}")
  public CommonResult<List<UmsPermission>> getPermissionList(@PathVariable Long adminId){
    List<UmsPermission> permissionList = adminService.getPermissionList(adminId);
    return CommonResult.success(permissionList);
  }
}
