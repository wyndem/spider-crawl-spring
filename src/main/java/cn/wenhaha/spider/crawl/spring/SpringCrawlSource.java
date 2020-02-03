package cn.wenhaha.spider.crawl.spring;

import cn.wenhaha.spider.annotations.SpiderExpression;
import cn.wenhaha.spider.build.CrawlMethod;
import cn.wenhaha.spider.build.CrawlProxy;
import cn.wenhaha.spider.build.CrawlSourceMethod;
import cn.wenhaha.spider.crawl.CrawlSource;
import cn.wenhaha.spider.crawl.ParseContext;
import us.codecraft.webmagic.selector.Html;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;


public class SpringCrawlSource implements CrawlSource {

    private Html html;

    private String source;
    private  HashMap<Class,Map<String, CrawlMethod>> crawlMethodHashMap = new HashMap<>();

    private   CrawlProxy tCrawlProxy=null;
    public SpringCrawlSource(String source) {
        setHtmlSource(source);
    }

    @Override
    public  synchronized  <T> T getMapper(Class<T> type) {
        Map<String, CrawlMethod> crawlMethodMap = crawlMethodHashMap.get(type);

        if (crawlMethodMap==null){
            HashMap<String, CrawlMethod> stringCrawlProxyHashMap = new HashMap<>();
            Method[] methods = type.getMethods();
            for (Method m : methods) {
                SpiderExpression annotation = m.getAnnotation(SpiderExpression.class);
                if (annotation!=null){
                    stringCrawlProxyHashMap.put(m.getName(),new CrawlSourceMethod(m));
                }
            }
            crawlMethodMap=stringCrawlProxyHashMap;
            crawlMethodHashMap.put(type,crawlMethodMap);
        }

        tCrawlProxy=new CrawlProxy(html,crawlMethodMap);


        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, tCrawlProxy);
    }

    @Override
    public void setParseContext(ParseContext parseContext) {

    }

    @Override
    public int getMapperNumber() {
        return 0;
    }

    @Override
    public <T> boolean remove(Class<T> type) {
        return false;
    }

    @Override
    public String getHtmlSource() {
        return source;
    }

    @Override
    public Html getHtml() {
        return html;
    }

    @Override
    public String getLink() {
        return null;
    }

    @Override
    public void setHtmlSource(String source) {
        this.source=source;
        html=Html.create(source);
        if (tCrawlProxy!=null)    tCrawlProxy.setHtml(html);
    }
}
