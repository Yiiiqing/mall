package zone.yiqing.mall.service;

/**
 * redis service, json format
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-09.
 */
public interface RedisService {

  /**
   * save data.
   * @param key
   * @param value
   */
  void set(String key, String value);

  /**
   * get data.
   * @param key
   * @return
   */
  String get(String key);

  /**
   * set expire time.
   * @param key
   * @param expire
   * @return
   */
  boolean expire(String key, long expire);

  /**
   * remove data.
   * @param key
   */
  void remove(String key);

  /**
   * increment.
   * @param key
   * @param delta
   * @return
   */
  Long increment(String key, long delta);

}
