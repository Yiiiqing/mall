package zone.yiqing.mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import zone.yiqing.mall.common.api.CommonResult;
import zone.yiqing.mall.service.RedisService;
import zone.yiqing.mall.service.UmsMemberService;

import java.util.Random;

/**
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-09.
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {

  @Autowired
  RedisService redisService;

  @Value("${redis.key.prefix.authCode}")
  private String REDIS_KEY_PREFIX_AUTH_CODE;

  @Value("${redis.key.expire.authCode}")
  private Long AUTH_CODE_EXPIRE_SECONDS;

  @Override
  public CommonResult generateAuthCode(String telephone) {
    StringBuilder stringBuilder = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < 6; i++) {
      stringBuilder.append(random.nextInt(10));
    }
    // 绑定手机
    String key = REDIS_KEY_PREFIX_AUTH_CODE + telephone;
    String value = stringBuilder.toString();
    redisService.set(key, value);
    redisService.expire(key, AUTH_CODE_EXPIRE_SECONDS);
    return CommonResult.success(value, "获取验证码成功");
  }

  @Override
  public CommonResult verifyAuthCode(String telephone, String authCode) {
    if (StringUtils.isEmpty(authCode)) {
      return CommonResult.failed("请输入验证码");
    }
    String realCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
    boolean result = authCode.equals(realCode);
    return result ? CommonResult.success(null, "验证码校验成功") : CommonResult.failed("验证码校验失败");
  }
}
