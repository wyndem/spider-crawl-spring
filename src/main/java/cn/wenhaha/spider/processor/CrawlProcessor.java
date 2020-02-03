package cn.wenhaha.spider.processor;

import cn.wenhaha.spider.util.BrowserUA;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class CrawlProcessor implements PageProcessor {

    @Override
    public void process(Page page) {

    }

    @Override
    public Site getSite() {
        return Site.me().setUserAgent(BrowserUA.getPhoneUA());
    }
}
