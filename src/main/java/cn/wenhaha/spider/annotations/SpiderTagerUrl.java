package cn.wenhaha.spider.annotations;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 站点url
 */
@Documented
@Retention(RUNTIME)
@Target({TYPE})
public @interface SpiderTagerUrl {
    String value();
}
