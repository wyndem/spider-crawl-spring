package cn.wenhaha.spider.crawl.spring;

import cn.wenhaha.spider.crawl.CrawlFactory;
import cn.wenhaha.spider.crawl.CrawlSource;
import cn.wenhaha.spider.crawl.ListCrawlSource;
import cn.wenhaha.spider.util.Collar;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;


@Component
public class SpringCrawlFactory implements CrawlFactory, Serializable {



    @Override
    public CrawlSource openCrawl(String baseurl) {
        try {
            String source = Collar.getSource(baseurl, "utf-8");
            return getCrawl(source);
        } catch (IOException e) {
            throw  new RuntimeException(e);
        }
    }

    @Override
    public ListCrawlSource openCrawl(String[] url) {
        throw  new RuntimeException("暂不支持批量url处理");
    }

    @Override
    public CrawlSource getCrawl(String html) {
        return new SpringCrawlSource(html);
    }
}
