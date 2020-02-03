package cn.wenhaha.spider.build;


import us.codecraft.webmagic.selector.Html;


public interface CrawlMethod {

    Object execute(Html html);


}
