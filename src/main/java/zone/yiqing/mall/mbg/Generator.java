package zone.yiqing.mall.mbg;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * 用于 MBG 的生成.
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-09-29.
 */
public class Generator {

  public static void main(String[] args) throws Exception {

    Logger logger = LoggerFactory.getLogger(Generator.class);

    // MBG 执行过程中的警告消息
    ArrayList<String> warnings = new ArrayList<>();
    // 当生成的代码重复时，覆盖原代码
    boolean overwrite = true;
    // 读取我们的 MBG 配置文件
    InputStream is = Generator.class.getResourceAsStream("/generatorConfig.xml");
    ConfigurationParser cp = new ConfigurationParser(warnings);
    Configuration config = cp.parseConfiguration(is);
    is.close();

    DefaultShellCallback callback = new DefaultShellCallback(overwrite);
    // 创建 MBG
    MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
    // 执行生成代码
    myBatisGenerator.generate(null);
    // print warnings.
    for (String warning : warnings
    ) {
      logger.warn(warning);
    }
  }
}
