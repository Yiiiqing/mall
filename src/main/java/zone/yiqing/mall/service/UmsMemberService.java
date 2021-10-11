package zone.yiqing.mall.service;

import zone.yiqing.mall.common.api.CommonResult;

/**
 * 用户登录管理
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-09.
 */
public interface UmsMemberService {

  // 生成验证码
  CommonResult generateAuthCode(String telephone);

  // 验证验证码
  CommonResult verifyAuthCode(String telephone, String authCode);

}
