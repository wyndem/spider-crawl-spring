package cn.wenhaha.spider.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *  XPath 表达式
 */
@Documented
@Retention(RUNTIME)
@Target({ElementType.METHOD})
public @interface SpiderExpression {

    String[] value();

    SpiderExpression.Type type() default SpiderExpression.Type.XPath;

    enum Type {
        XPath,
        Regex,
        Css,
        JsonPath;

        private Type() {
        }
    }
}
