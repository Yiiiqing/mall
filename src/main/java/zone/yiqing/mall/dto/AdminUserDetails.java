package zone.yiqing.mall.dto;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import zone.yiqing.mall.mbg.model.UmsAdmin;
import zone.yiqing.mall.mbg.model.UmsPermission;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SpringSecurity 需要的用户详情
 *
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-11.
 */
@AllArgsConstructor
public class AdminUserDetails implements UserDetails {

  private UmsAdmin umsAdmin;
  private List<UmsPermission> permissionList;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // 返回当前用户的权限
    return permissionList.stream()
        .filter(umsPermission -> umsPermission.getValue() != null)
        .map(umsPermission -> new SimpleGrantedAuthority(umsPermission.getValue()))
        .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return umsAdmin.getPassword();
  }

  @Override
  public String getUsername() {
    return umsAdmin.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return umsAdmin.getStatus().equals(1);
  }
}
