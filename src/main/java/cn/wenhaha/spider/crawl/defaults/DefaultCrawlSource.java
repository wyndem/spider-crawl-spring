package cn.wenhaha.spider.crawl.defaults;

import cn.wenhaha.spider.build.CrawlMethod;
import cn.wenhaha.spider.build.CrawlProxy;
import cn.wenhaha.spider.crawl.BaseCrawlSource;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DefaultCrawlSource extends BaseCrawlSource {

    private final String url;
    private final  Map<Class<?>, InvocationHandler> invocationCache;

    public DefaultCrawlSource(List<Class<?>> classes, HashMap<Class<?>, Map<String, CrawlMethod>> crawlMethodMaps, String url) {
        super(crawlMethodMaps,classes);
        this.url=url;
        this.invocationCache=new HashMap<>();
    }




    @Override
    public <T> T getMapper(Class<T> type) {

        if (!classes.contains(type)){
            throw  new RuntimeException("当前class不支持解析该站点");
        }


        if (parseContext==null){
            setParseContext(new DefParseContext());
        }

        if (spider==null){
            spider=Spider.create(this);
            spider.addUrl(url)
                    .addPipeline(new ConsolePipeline())
                    .run();
        }

        Map<String, CrawlMethod> stringCrawlMethodMap = crawlMethodMaps.get(type);
        boolean b = invocationCache.containsKey(type);

        InvocationHandler tCrawlProxy=null;
        if (!b){
            tCrawlProxy= new CrawlProxy(page,stringCrawlMethodMap);
            invocationCache.put(type,tCrawlProxy);
        }else{
            tCrawlProxy=invocationCache.get(type);
        }

        Object o = Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, tCrawlProxy);
        return (T)o;
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
    public String getLink() {
        return url;
    }




}
