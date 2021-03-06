package zone.yiqing.mall.component;

import cn.hutool.json.JSONUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import zone.yiqing.mall.common.api.CommonResult;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 当未登录或者token失效访问接口时，自定义的返回结果.
 *
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-11.
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, AuthenticationException e)
      throws IOException, ServletException {
    httpServletResponse.setCharacterEncoding("UTF-8");
    httpServletResponse.setContentType("application/json");
    httpServletResponse.getWriter()
        .println(JSONUtil.parse(CommonResult.unauthorized(e.getMessage())));
    httpServletResponse.getWriter().flush();
  }
}
