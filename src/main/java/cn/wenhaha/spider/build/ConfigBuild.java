package cn.wenhaha.spider.build;

import cn.wenhaha.spider.annotations.SpiderExpression;
import cn.wenhaha.spider.annotations.SpiderParseContext;
import cn.wenhaha.spider.annotations.SpiderTagerUrl;
import cn.wenhaha.spider.crawl.ParseContext;
import cn.wenhaha.spider.exceptions.CrawlConflictException;
import cn.wenhaha.spider.io.ResolverUtil;

import java.lang.reflect.Method;
import java.util.*;

public class ConfigBuild {

    protected  HashMap<String,List<Class<?>>>  crawlUrlWebRegistryMap=new HashMap();
    protected  HashMap<Class<?>, Map<String, CrawlMethod>>  crawlRegistryMap=new HashMap();
    public Class<ParseContext> parseContextClass;

    public HashMap<String, List<Class<?>>> getCrawlUrlWebRegistryMap() {
        return crawlUrlWebRegistryMap;
    }

    public HashMap<Class<?>, Map<String, CrawlMethod>> getCrawlRegistryMap() {
        return crawlRegistryMap;
    }

    public  void addMappers(String mapperPackage){
        ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<Class<?>>();
        resolverUtil.find(new ResolverUtil.IsA(Object.class), mapperPackage);
        Set<Class<? extends Class<?>>> mapperSet = resolverUtil.getClasses();
        LinkedList<Class<?>> classes = new LinkedList<>();
        for (Class<?> mapperClass : mapperSet) {

            if (mapperClass.isInterface()){
                classes.add(mapperClass);
            }else if (ParseContext.class.isAssignableFrom(mapperClass)&&mapperClass.isAnnotationPresent(SpiderParseContext.class)){
               //如果设置了爬虫设置
                parseContextClass= (Class<ParseContext>) mapperClass;
            }
        }


        addMappers(classes);
    }

    public  boolean hasMapper(String key,Class<?>  aclass) {
        if (crawlUrlWebRegistryMap.containsKey(key)){
           return crawlUrlWebRegistryMap.get(key).contains(aclass);
        }else{
            return false;
        }

    }


    private void addMappers(List<Class<?>> clazz){
        for (Class<?> mapperClass:clazz ) {
            SpiderTagerUrl annotation = mapperClass.getAnnotation(SpiderTagerUrl.class);
            if (annotation!=null){
                if (hasMapper(annotation.value(),mapperClass)){
                    throw new CrawlConflictException("当前"+annotation.value()+"已被注册");
                }
                try {
                    List<Class<?>> classes = crawlUrlWebRegistryMap.get(annotation.value());
                    if (classes==null){
                        classes=new ArrayList<>();
                        crawlUrlWebRegistryMap.put(annotation.value(),classes);
                    }
                    classes.add(mapperClass);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }



    public ConfigBuild parse(){
        for (String key: crawlUrlWebRegistryMap.keySet()) {
            List<Class<?>> classes = crawlUrlWebRegistryMap.get(key);
            for ( Class<?> aClass : classes){

                Method[] methods = aClass.getMethods();
                HashMap<String, CrawlMethod> crawlMethodHashMap = new HashMap<>();

                for (Method m : methods) {
                    SpiderExpression annotation = m.getAnnotation(SpiderExpression.class);
                    if (annotation!=null){
                        crawlMethodHashMap.put(m.getName(),new CrawlSourceMethod(m));
                    }
                }
                crawlRegistryMap.put(aClass,crawlMethodHashMap);
            }
        }

        return this;
    }



}
