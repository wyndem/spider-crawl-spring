package cn.wenhaha.spider.crawl;

import us.codecraft.webmagic.selector.Html;


public interface CrawlSource {

    /**
     * 获取代理
     * @param type
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<T> type);

    /**
     * 设置Site
     * @param parseContext
     */
    void setParseContext(ParseContext parseContext);

    /**
     * 获取映射数
     * @return
     */
    int getMapperNumber();

    /**
     * 移除某个映射
     * @param type
     * @param <T>
     * @return
     */
    <T> boolean remove(Class<T> type);

    /**
     * 获取网页源码
     * @return
     */
    String getHtmlSource();

    /**
     * 获取html
     * @return
     */
    Html getHtml();

    /**
     * 获取Link
     * @return
     */
    String getLink();

    void setHtmlSource(String source);
}
