package cn.wenhaha.spider.build;

import cn.wenhaha.spider.exceptions.ExceptionUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

public class CrawlProxy implements InvocationHandler {


    private final Map<String, CrawlMethod> methodMap;
    private Html html;


    public CrawlProxy(Page page,Map<String, CrawlMethod> methodMap) {
        this.methodMap = methodMap;
        this.html=page.getHtml();
    }

    public CrawlProxy(Html html, Map<String, CrawlMethod> methodMap) {
        this.methodMap = methodMap;
        this.html=html;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            try {
                return method.invoke(this, args);
            } catch (Throwable t) {
                throw ExceptionUtil.unwrapThrowable(t);
            }
        }
        CrawlMethod crawlMethod = methodMap.get(method.getName());
        if (Objects.isNull(crawlMethod)){
            throw  new RuntimeException("该方法无法被执行，因为没有被声明");
        }
        if (html!=null){
            return crawlMethod.execute(html);
        }else {
            throw new RuntimeException("无源码");
        }
    }

    public void setHtml(Html html){
        this.html=html;
    }


}
