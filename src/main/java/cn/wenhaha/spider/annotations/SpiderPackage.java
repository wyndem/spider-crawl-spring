package cn.wenhaha.spider.annotations;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 需要扫描的包路径
 */
@Documented
@Retention(RUNTIME)
@Target({TYPE})
public @interface SpiderPackage {
    String value()  default "";
}
