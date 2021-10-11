package zone.yiqing.mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import zone.yiqing.mall.service.RedisService;

import java.util.concurrent.TimeUnit;

/**
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-09.
 */
@Service
public class RedisServiceImpl implements RedisService {

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  @Override
  public void set(String key, String value) {
    stringRedisTemplate.opsForValue().set(key, value);
  }

  @Override
  public String get(String key) {
    return stringRedisTemplate.opsForValue().get(key);
  }

  @Override
  public boolean expire(String key, long expire) {
    return stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
  }

  @Override
  public void remove(String key) {
    stringRedisTemplate.delete(key);
  }

  // 只有当存放的值能够被解析为数字的时候, increment 才有效
  @Override
  public Long increment(String key, long delta) {
    return stringRedisTemplate.opsForValue().increment(key, delta);
  }
}
