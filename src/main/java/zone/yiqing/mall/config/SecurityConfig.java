package zone.yiqing.mall.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import zone.yiqing.mall.component.JwtAuthenticationTokenFilter;
import zone.yiqing.mall.component.RestAuthenticationEntryPoint;
import zone.yiqing.mall.component.RestfulAccessDeniedHandler;
import zone.yiqing.mall.dto.AdminUserDetails;
import zone.yiqing.mall.mbg.model.UmsAdmin;
import zone.yiqing.mall.mbg.model.UmsPermission;
import zone.yiqing.mall.service.UmsAdminService;

import java.util.List;

/**
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-11.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UmsAdminService adminService;
  @Autowired
  private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
  @Autowired
  private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf()// ??????????????????JWT????????????????????????csrf
        .disable()
        .sessionManagement()// ??????token??????????????????session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, // ????????????????????????????????????????????????
            "/",
            "/*.html",
            "/favicon.ico",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js",
            "/swagger-resources/**",
            "/v2/api-docs/**"
        )
        .permitAll()
        .antMatchers("/admin/login", "/admin/register")// ????????????????????????????????????
        .permitAll()
        .antMatchers(HttpMethod.OPTIONS)//??????????????????????????????options??????
        .permitAll()
                .antMatchers("/**")//???????????????????????????
        .permitAll()
        .anyRequest()// ???????????????????????????????????????????????????
        .authenticated();
    // ????????????
    httpSecurity.headers().cacheControl();
    // ??????JWT filter
    httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(),
        UsernamePasswordAuthenticationFilter.class);
    //????????????????????????????????????????????????
    httpSecurity.exceptionHandling()
        .accessDeniedHandler(restfulAccessDeniedHandler)
        .authenticationEntryPoint(restAuthenticationEntryPoint);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService())
        .passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    //????????????????????????
    return username -> {
      UmsAdmin admin = adminService.getAdminByUsername(username);
      if (admin != null) {
        List<UmsPermission> permissionList = adminService.getPermissionList(admin.getId());
        return new AdminUserDetails(admin, permissionList);
      }
      throw new UsernameNotFoundException("????????????????????????");
    };
  }

  @Bean
  public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
    return new JwtAuthenticationTokenFilter();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

}