package cn.wenhaha.spider.crawl;

public interface CrawlFactory {


     CrawlSource openCrawl(String baseurl);

     ListCrawlSource openCrawl(String[] url);

     CrawlSource getCrawl(String html);

}
