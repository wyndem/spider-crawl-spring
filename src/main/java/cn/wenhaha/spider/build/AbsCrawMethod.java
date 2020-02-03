package cn.wenhaha.spider.build;


import cn.wenhaha.spider.annotations.SpiderExpression;
import cn.wenhaha.spider.annotations.SpiderScope;
import us.codecraft.webmagic.selector.Html;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

abstract class AbsCrawMethod implements CrawlMethod {


    protected Html html;
    protected final Method method;
    protected final String[] expression;
    protected final String[] scope;
    protected final SpiderExpression.Type type;
    protected SpiderSelectable spiderSelectable;
    protected AbsCrawMethod(Method method) {
        this.method = method;
        SpiderExpression annotation = this.method.getAnnotation(SpiderExpression.class);
        if (annotation==null){
            throw  new RuntimeException("必要注解SpiderExpression缺少。");
        }
        this.expression =removebBlank(annotation.value());
        this.type = annotation.type();
        SpiderScope spiderScope = this.method.getAnnotation(SpiderScope.class);
        if (spiderScope!=null){
            this.scope=removebBlank(spiderScope.value());
        }else {
            this.scope=null;
        }

    }


    public Object executeRouting(){
        Class<?> returnType = method.getReturnType();

        if (int.class==returnType || Integer.class==returnType){
            return execute(0);
        }
        else if (double.class==returnType || Double.class==returnType){
            return execute(0.0);
        }
        else  if (float.class==returnType || Float.class==returnType){
            return execute(0f);
        }
        else  if (Date.class==returnType){
            return execute(new  Date());
        }else if (String.class==returnType){
            return execute("");
        }else if (List.class == returnType){
            return execute(new ArrayList());
        }
        return null;
    }

    public Object execute(Html html){
        if (html==null){
            throw new RuntimeException("html不能为空");
        }
        this.html=html;

        spiderSelectable= SpiderSelectableFactory.build(html, type);
        if (Objects.nonNull(scope)){
            assert spiderSelectable != null;
            for (String ex : scope) {
                spiderSelectable=spiderSelectable.parseScope(ex);
            }
        }

        return executeRouting();
    }


    private String[] removebBlank(String [] strings){
        String[] arr=new String[strings.length];
        for (int i = 0; i < strings.length; i++) {
            arr[i]=strings[i].trim();
        }
        return arr;
    }


    abstract String  execute(String def);
    abstract Integer  execute(Integer def);
    abstract Double  execute(Double def);
    abstract Float  execute(Float def);
    abstract Date  execute(Date def);
    abstract List  execute(List def);


}
