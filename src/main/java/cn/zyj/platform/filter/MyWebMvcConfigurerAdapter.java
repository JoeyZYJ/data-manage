package cn.zyj.platform.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.List;

/**
 * webmvc 配置类
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-22
 */
@Configuration
public class MyWebMvcConfigurerAdapter implements WebMvcConfigurer {

//  继承WebMvcConfigurationSupport时为了注入自己的配置
//  @Bean
//  public HttpMessageConverter<String> responseBodyConverter() {
//    StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
//    return converter;
//  }

  /**
   * 配置接口中文乱码问题，以及ajax返回json数据拦截问题
   * @param converters
   */
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
    converters.add(converter);
    converters.add(mappingJackson2HttpMessageConverter);
  }

  @Override
  public void configureContentNegotiation(
    ContentNegotiationConfigurer configurer) {
    configurer.favorPathExtension(false);
  }
  /**
   * 配置静态资源的访问
   * @param registry
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
  }
  /**
   * 配置拦截规则
   * @param registry
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**") //用于添加拦截规则
      .excludePathPatterns("/login") // 用于开放登录页
      .excludePathPatterns("/index") // 用于排除拦截登录请求
      .excludePathPatterns("/ad/get") // 开放随机获取广告话术接口
      .excludePathPatterns("/tiktok/get") // 开放获取抖音帐号信息接口
      .excludePathPatterns("/tiktok/add") // 开放添加抖音帐号接口
      .excludePathPatterns("/weibo/get") // 开放获取微博信息接口
      .excludePathPatterns("/weibo/add") // 开放添加微博信息接口
      .excludePathPatterns("/weibo/upd") // 开放修改微博信息状态接口
      .excludePathPatterns("/static/**"); // 用于排除拦截静态资源
  }

}
