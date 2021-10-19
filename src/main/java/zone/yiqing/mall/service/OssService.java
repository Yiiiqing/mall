package zone.yiqing.mall.service;

import zone.yiqing.mall.dto.OssCallbackResult;
import zone.yiqing.mall.dto.OssPolicyResult;

import javax.servlet.http.HttpServletRequest;

/**
 * oss上传管理Service
 *
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-19.
 */
public interface OssService {

  /**
   * oss 上传策略生成
   */
  OssPolicyResult policy();

  /**
   * oss 上传成功回调
   */
  OssCallbackResult callback(HttpServletRequest request);
}
