package cn.zyj.platform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.zyj.platform.mapper")
@SpringBootApplication
public class PlatformApplication {
  public static void main(String[] args) {
    SpringApplication.run(PlatformApplication.class, args);
  }
}

