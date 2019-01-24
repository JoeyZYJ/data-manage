package cn.zyj.platform.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO
 *
 * @author <a href="779557664@qq.com">Joey Zeng</a>
 * @version 1.0.0, 2018-12-22
 */
public class MyStringUtils {

  public static String replaceMultipleLineToOne(String data){
    Pattern p = Pattern.compile("(\r?\n(\\s*\r?\n)+)");
    Matcher m = p.matcher(data);
    return m.replaceAll("\n");
  }
}
