package zone.yiqing.mall.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt util.
 *
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-11.
 */
@Component
public class JwtTokenUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
  private static final String CLAIM_KEY_USERNAME = "sub";
  private static final String CLAIM_KEY_CREATED = "created";

  @Value("${jwt.secret}")
  private String secretKey;

  @Value("${jwt.expiration}")
  private Long expiration;

  /**
   * generate token by user details.
   *
   * @param userDetails
   * @return
   */
  public String generateToken(UserDetails userDetails) {
    HashMap<String, Object> claims = new HashMap<>();
    claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
    claims.put(CLAIM_KEY_CREATED, new Date());
    return generateToken(claims);
  }

  /**
   * validate token. 1. validate user. 2. validate token expiration.
   *
   * @param token
   * @param userDetails
   * @return
   */
  public boolean validateToken(String token, UserDetails userDetails) {
    String username = getUserNameFromToken(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  /**
   * decide if token ban be refresh. If token is not expired that token can be refresh.
   *
   * @param token
   * @return
   */
  public boolean canRefresh(String token) {
    return !isTokenExpired(token);
  }

  /**
   * refresh token.
   *
   * @param token
   * @return
   */
  public String refreshToken(String token) {
    Claims claims = getClaimsFromToken(token);
    claims.put(CLAIM_KEY_CREATED, new Date());
    return generateToken(claims);
  }

  /**
   * get expiration date.
   *
   * @return
   */
  private Date generateExpirationDate() {
    return new Date(System.currentTimeMillis() + expiration * 1000);
  }

  /**
   * parse token, get claims.
   *
   * @param token
   * @return
   */
  private Claims getClaimsFromToken(String token) {
    Claims claims = null;
    try {
      claims = Jwts.parser()
          .setSigningKey(secretKey)
          .parseClaimsJws(token)
          .getBody();
    } catch (Exception e) {
      LOGGER.info("JWT格式验证失败:{}", token);
    }
    return claims;
  }

  /**
   * get username from token.
   *
   * @param token
   * @return
   */
  public String getUserNameFromToken(String token) {
    String username;
    try {
      Claims claims = getClaimsFromToken(token);
      username = claims.getSubject();
    } catch (Exception e) {
      username = null;
    }
    return username;
  }


  /**
   * compare date now to expired date from token.
   *
   * @param token
   * @return
   */
  private boolean isTokenExpired(String token) {
    Date expiredDate = getExpiredDateFromToken(token);
    return expiredDate.before(new Date());
  }

  /**
   * get expired date.
   *
   * @param token
   * @return
   */
  private Date getExpiredDateFromToken(String token) {
    Claims claims = getClaimsFromToken(token);
    return claims.getExpiration();
  }


  /**
   * generate jwt token by claims.
   *
   * @param claims
   * @return
   */
  private String generateToken(Map<String, Object> claims) {
    return Jwts.builder()
        .setClaims(claims)
        .setExpiration(generateExpirationDate())
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .compact();
  }

}
