package zone.yiqing.mall.common.api;

/**
 * 封装 API 的错误码.
 *
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-09-29.
 */
public interface IErrorCode {

  long getCode();

  String getMessage();
}
