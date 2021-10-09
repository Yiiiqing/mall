package zone.yiqing.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis 配置类
 *
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-09-29.
 */
@Configuration
@MapperScan("zone.yiqing.mall.mbg.mapper")
public class MyBatisConfig {

}
