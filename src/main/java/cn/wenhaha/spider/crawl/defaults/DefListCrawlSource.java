package cn.wenhaha.spider.crawl.defaults;

import cn.wenhaha.spider.build.CrawlMethod;
import cn.wenhaha.spider.build.CrawlProxy;
import cn.wenhaha.spider.crawl.ListCrawlSource;
import cn.wenhaha.spider.crawl.ParseContext;
import cn.wenhaha.spider.util.BrowserUA;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefListCrawlSource implements ListCrawlSource, PageProcessor {

    protected final HashMap<Class<?>, Map<String, CrawlMethod>> crawlMethodMaps;
    protected final List<Class<?>> classes;
    protected String[] url;
    protected Spider spider;
    private Map<String,Page>  mapperTable;
    private ParseContext parseContext;


    public DefListCrawlSource(List<Class<?>> classes, HashMap<Class<?>, Map<String, CrawlMethod>> crawlMethodMaps, String[] url)   {
        this.crawlMethodMaps = crawlMethodMaps;
        this.url=url;
        this.classes = classes;
        this.mapperTable=new HashMap<String,Page>();
    }

    @Override
    public <T> List<T> getMappers(Class<T> type) {
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
        ArrayList<T> ts = new ArrayList<>();
        for (int i = 0; i < url.length; i++) {
            Page page = mapperTable.get(url[i]);
            InvocationHandler tCrawlProxy= new CrawlProxy(page,stringCrawlMethodMap);
            Object o = Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, tCrawlProxy);
            ts.add((T)o);
        }
        return ts;
    }

    @Override
    public void setParseContext(ParseContext parseContext) {
        if (parseContext==null){
            throw new RuntimeException("parseContext对象不能为空");
        }
        this.parseContext=parseContext;
    }

    @Override
    public void process(Page page) {
        mapperTable.put(page.getRequest().getUrl(),page);
    }

    @Override
    public Site getSite() {
        if (parseContext==null){
            return Site.me().setUserAgent(BrowserUA.getComputerUA());
        }else {
            Site site = parseContext.getSite();
            if (site==null){
                throw  new RuntimeException("爬虫Site对象为Null");
            }
            return site;
        }
    }

}
