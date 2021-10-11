package zone.yiqing.mall.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zone.yiqing.mall.common.utils.JwtTokenUtil;
import zone.yiqing.mall.dao.UmsAdminRoleRelationDao;
import zone.yiqing.mall.mbg.mapper.UmsAdminMapper;
import zone.yiqing.mall.mbg.model.UmsAdmin;
import zone.yiqing.mall.mbg.model.UmsAdminExample;
import zone.yiqing.mall.mbg.model.UmsPermission;
import zone.yiqing.mall.service.UmsAdminService;

import java.util.Date;
import java.util.List;

/**
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-11.
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UmsAdminMapper adminMapper;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private UmsAdminRoleRelationDao umsAdminRoleRelationDao;

  @Override
  public UmsAdmin getAdminByUsername(String username) {
    UmsAdminExample umsAdminExample = new UmsAdminExample();
    umsAdminExample.createCriteria().andUsernameEqualTo(username);
    List<UmsAdmin> adminList = adminMapper.selectByExample(umsAdminExample);
    if (adminList != null && adminList.size() > 0) {
      return adminList.get(0);
    }
    return null;
  }

  @Override
  public UmsAdmin register(UmsAdmin umsAdminParam) {
    UmsAdmin admin = new UmsAdmin();
    BeanUtils.copyProperties(umsAdminParam, admin);
    admin.setCreateTime(new Date());
    admin.setStatus(1);
    // 检查重名
    UmsAdminExample umsAdminExample = new UmsAdminExample();
    umsAdminExample.createCriteria().andUsernameEqualTo(admin.getUsername());
    List<UmsAdmin> umsAdmins = adminMapper.selectByExample(umsAdminExample);
    if (umsAdmins.size() > 0) {
      // 有重复
      return null;
    }
    // 密码加密
    String encode = passwordEncoder.encode(admin.getPassword());
    admin.setPassword(encode);
    adminMapper.insert(admin);
    return admin;
  }

  /**
   * 登录. 1. 检查密码. 2. 设置SecurityContextHolder的authentication. 3.返回 token
   *
   * @param username 用户名
   * @param password 密码
   * @return
   */
  @Override
  public String login(String username, String password) {
    String token = null;
    try {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      if (!passwordEncoder.matches(password, userDetails.getPassword())) {
        LOGGER.warn("密码错误: db:{},user:{}", userDetails.getPassword(), password);
        throw new BadCredentialsException("密码错误");
      }
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      token = jwtTokenUtil.generateToken(userDetails);
    } catch (Exception e) {
      LOGGER.warn("登录异常: {}", e.getMessage());
    }
    return token;
  }

  @Override
  public List<UmsPermission> getPermissionList(Long adminId) {
    return umsAdminRoleRelationDao.getPermissionList(adminId);
  }
}
