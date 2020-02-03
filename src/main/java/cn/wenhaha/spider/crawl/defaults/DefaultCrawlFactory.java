package cn.wenhaha.spider.crawl.defaults;

import cn.wenhaha.spider.build.ConfigBuild;
import cn.wenhaha.spider.crawl.*;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DefaultCrawlFactory implements CrawlFactory {

    private ConfigBuild configBuild;

    public DefaultCrawlFactory(ConfigBuild configBuild) {
        this.configBuild = configBuild;
    }


    private String checkingClass(String url){
        Set<String> configBuildKeySet = configBuild.getCrawlUrlWebRegistryMap().keySet();
        String crawlKey = null;

        for (String key : configBuildKeySet) {
            if (url.contains(key)){
                crawlKey=key;
                break;
            }
        }

        if (StringUtils.isEmpty(crawlKey)){
            throw new RuntimeException("没有找到指定的class");
        }
        return crawlKey;
    }


    private ParseContext getParseContext(){
        Class<ParseContext> parseContextClass= configBuild.parseContextClass;
        if (parseContextClass!=null){
            Constructor<ParseContext> declaredConstructor = null;
            try {
                declaredConstructor = parseContextClass.getDeclaredConstructor();
                return  declaredConstructor.newInstance();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }

        }

        return new DefParseContext();
    }



    @Override
    public CrawlSource openCrawl(String url) {
        String crawlKey = checkingClass(url);
        List<Class<?>> classes = configBuild.getCrawlUrlWebRegistryMap().get(crawlKey);
        DefaultCrawlSource defaultCrawlSource = new DefaultCrawlSource(classes, configBuild.getCrawlRegistryMap(), url);
        defaultCrawlSource.setParseContext(getParseContext());
        return  defaultCrawlSource;


    }

    @Override
    public ListCrawlSource openCrawl(String[] url) {
        String crawlKey=null;
        for (int i = 0; i < url.length; i++) {
            String s = checkingClass(url[i]);

            if (crawlKey==null){
                crawlKey=s;
            }
            else if (!s.equals(crawlKey)){
                throw new RuntimeException(String.format("指定站点必须一致,%s和%s不是同一个站点",crawlKey,s));
            }
        }
        List<Class<?>> classes = configBuild.getCrawlUrlWebRegistryMap().get(crawlKey);
        DefListCrawlSource defListCrawlSource = new DefListCrawlSource(classes, configBuild.getCrawlRegistryMap(), url);
        defListCrawlSource.setParseContext(getParseContext());

        return   defListCrawlSource;
    }

    @Override
    public CrawlSource getCrawl(String html) {

        List<Class<?>> classes = new ArrayList<>();

        HashMap<String, List<Class<?>>> crawlUrlWebRegistryMap =
                configBuild.getCrawlUrlWebRegistryMap();
        crawlUrlWebRegistryMap.forEach((k,v)->classes.addAll(v));
        HtmlCrawlSource htmlCrawlSource = new HtmlCrawlSource(configBuild.getCrawlRegistryMap(),classes, html);
        return htmlCrawlSource;
    }


}
