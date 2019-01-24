package cn.zyj.platform.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * TODO
 *  mybatis 分页帮助类配置
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-24
 */
@Configuration
public class MybatisPageHelperConfig {
  @Bean( name = "pageHelper" )
  public PageHelper pageHelper() {
    PageHelper pageHelper = new PageHelper();
    //添加配置，也可以指定文件路径
    Properties p = new Properties();
    p.setProperty("helperDialect", "mysql");
    p.setProperty("reasonable", "true");
    p.setProperty("supportMethodsArguments", "true");
    p.setProperty("params", "count=countSql");
    p.setProperty("autoRuntimeDialect", "true");
    pageHelper.setProperties(p);
    return pageHelper;
  }
}
