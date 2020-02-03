package cn.wenhaha.spider.crawl;

import cn.wenhaha.spider.build.CrawlMethod;
import cn.wenhaha.spider.build.CrawlProxy;
import us.codecraft.webmagic.selector.Html;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlCrawlSource implements CrawlSource {

    protected final HashMap<Class<?>, Map<String, CrawlMethod>> crawlMethodMaps;
    protected final List<Class<?>> classes;
    private  String html;
    private   Html htmlH;
    private final  Map<Class<?>, CrawlProxy> invocationCache;

    public HtmlCrawlSource(HashMap<Class<?>, Map<String, CrawlMethod>> crawlMethodMaps, List<Class<?>> classes,String html) {
        this.crawlMethodMaps=crawlMethodMaps;
        this.classes=classes;
        this.html=html;
        this.htmlH=new Html(html);
        this.invocationCache=new HashMap<>();
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        Map<String, CrawlMethod> stringCrawlMethodMap = crawlMethodMaps.get(type);
        boolean b = invocationCache.containsKey(type);

        CrawlProxy tCrawlProxy=null;
        if (!b){
            tCrawlProxy= new CrawlProxy(getHtml(),stringCrawlMethodMap);
            invocationCache.put(type,tCrawlProxy);
        }else{
            tCrawlProxy=invocationCache.get(type);
        }

        Object o = Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, tCrawlProxy);
        return (T)o;
    }

    @Override
    public void setParseContext(ParseContext parseContext) {

    }

    @Override
    public int getMapperNumber() {
        return classes.size();
    }

    @Override
    public <T> boolean remove(Class<T> type) {
        return classes.remove(type);
    }

    @Override
    public String getHtmlSource() {
        return html;
    }



    @Override
    public Html getHtml() {
        return htmlH;
    }

    @Override
    public String getLink() {
        return null;
    }

    @Override
    public void setHtmlSource(String source) {
        this.htmlH=new Html(source);
        this.html=source;
        for (CrawlProxy value : invocationCache.values()) {
            value.setHtml(getHtml());
        }
    }
}
