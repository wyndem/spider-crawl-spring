package cn.wenhaha.spider.crawl;

import cn.wenhaha.spider.build.CrawlMethod;
import cn.wenhaha.spider.util.BrowserUA;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract public class BaseCrawlSource implements CrawlSource, PageProcessor {

    protected final HashMap<Class<?>, Map<String, CrawlMethod>> crawlMethodMaps;
    protected final List<Class<?>> classes;
    protected ParseContext parseContext;
    protected Page page;
    protected Spider spider;
    protected  String html;
    protected   Html htmlH;

    public BaseCrawlSource(HashMap<Class<?>, Map<String, CrawlMethod>> crawlMethodMaps, List<Class<?>> classes) {
        this.crawlMethodMaps = crawlMethodMaps;
        this.classes = classes;
    }

    public void setParseContext(ParseContext parseContext) {
        if (parseContext==null){
            throw new RuntimeException("parseContext对象不能为空");
        }
        this.parseContext = parseContext;
    }

    @Override
    public String getHtmlSource() {
        if (StringUtils.isEmpty(html)){
            html=page.getHtml().get();
        }
        return html;
    }

    @Override
    public Html getHtml() {
        if (htmlH==null){
            htmlH=page.getHtml();
        }
        return htmlH;
    }

    @Override
    public void process(Page page) {
        this.page=page;
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

    @Override
    public void setHtmlSource(String source) {
        this.htmlH=new Html(source);
        this.html=source;
    }
}
