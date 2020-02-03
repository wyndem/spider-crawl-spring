package cn.wenhaha.spider.crawl;

import cn.wenhaha.spider.annotations.SpiderPackage;
import cn.wenhaha.spider.build.ConfigBuild;
import cn.wenhaha.spider.crawl.defaults.DefaultCrawlFactory;
import org.apache.commons.lang3.StringUtils;

public class CrawlFactoryBuild {

    private ConfigBuild configBuild;

    public CrawlFactoryBuild() {
        this.configBuild = new ConfigBuild();
    }

    public CrawlFactory build(Class<?> primarySource){
        String mapperPackage = primarySource.getAnnotation(SpiderPackage.class).value();
        if (StringUtils.isEmpty(mapperPackage)){
            mapperPackage=primarySource.getPackage().getName();
        }
        configBuild.addMappers(mapperPackage);

        return  new DefaultCrawlFactory(configBuild.parse());
    }


}
