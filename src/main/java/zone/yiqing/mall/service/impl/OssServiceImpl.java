package zone.yiqing.mall.service.impl;

import cn.hutool.json.JSONUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import zone.yiqing.mall.dto.OssCallbackParam;
import zone.yiqing.mall.dto.OssCallbackResult;
import zone.yiqing.mall.dto.OssPolicyResult;
import zone.yiqing.mall.service.OssService;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

/**
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-19.
 */
@Service
public class OssServiceImpl implements OssService {

  private static final Logger LOGGER = LoggerFactory.getLogger(OssServiceImpl.class);

  @Value("${aliyun.oss.policy.expire}")
  private int ALIYUN_OSS_EXPIRE;
  @Value("${aliyun.oss.maxSize}")
  private int ALIYUN_OSS_MAX_SIZE;
  @Value("${aliyun.oss.callback}")
  private String ALIYUN_OSS_CALLBACK;
  @Value("${aliyun.oss.bucketName}")
  private String ALIYUN_OSS_BUCKET_NAME;
  @Value("${aliyun.oss.endpoint}")
  private String ALIYUN_OSS_ENDPOINT;
  @Value("${aliyun.oss.dir.prefix}")
  private String ALIYUN_OSS_DIR_PREFIX;

  @Autowired
  private OSSClient ossClient;

  /**
   * 签名生成
   */
  @Override
  public OssPolicyResult policy() {
    LOGGER.info("process generate policy...");
    OssPolicyResult result = new OssPolicyResult();
    // 存储目录
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    String dir = ALIYUN_OSS_DIR_PREFIX + df.format(new Date());
    // 签名有效期
    long expireTime = System.currentTimeMillis() + ALIYUN_OSS_EXPIRE * 1000L;
    Date expiration = new Date(expireTime);
    // 文件大小
    long maxSize = ALIYUN_OSS_MAX_SIZE * 1024L * 1024L;
    // 回调
    OssCallbackParam callbackParam = new OssCallbackParam();
    callbackParam.setCallbackUrl(ALIYUN_OSS_CALLBACK);
    callbackParam.setCallbackBody(
        "filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
    callbackParam.setCallbackBodyType("application/x-www-form-urlencoded");
    // 提交结点
    String action = "http://" + ALIYUN_OSS_BUCKET_NAME + "." + ALIYUN_OSS_ENDPOINT;
    try {
      PolicyConditions policyConditions = new PolicyConditions();
      policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
      policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
      String postPolicy = ossClient.generatePostPolicy(expiration, policyConditions);
      byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
      String policy = BinaryUtil.toBase64String(binaryData);
      String signature = ossClient.calculatePostSignature(postPolicy);
      String callbackData = BinaryUtil
          .toBase64String(JSONUtil.parse(callbackParam).toString().getBytes(
              StandardCharsets.UTF_8));
      // 返回结果
      result.setAccessKeyId(ossClient.getCredentialsProvider().getCredentials().getAccessKeyId());
      result.setPolicy(policy);
      result.setSignature(signature);
      result.setDir(dir);
      result.setCallback(callbackData);
      result.setHost(action);
    } catch (Exception e) {
      LOGGER.error("签名生成失败", e);
    }
    return result;
  }

  @Override
  public OssCallbackResult callback(HttpServletRequest request) {
    OssCallbackResult result = new OssCallbackResult();
    String filename = request.getParameter("filename");
    filename = "http://".concat(ALIYUN_OSS_BUCKET_NAME).concat(".").concat(ALIYUN_OSS_ENDPOINT)
        .concat("/").concat(filename);
    result.setFilename(filename);
    result.setSize(request.getParameter("size"));
    result.setSize(request.getParameter("mimeType"));
    result.setWidth(request.getParameter("width"));
    result.setHeight(request.getParameter("height"));
    return result;
  }
}
