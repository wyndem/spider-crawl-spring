package cn.wenhaha.spider.crawl;

import java.util.List;

public interface ListCrawlSource {

    <T> List<T> getMappers(Class<T> type);

    void setParseContext(ParseContext parseContext);

}
