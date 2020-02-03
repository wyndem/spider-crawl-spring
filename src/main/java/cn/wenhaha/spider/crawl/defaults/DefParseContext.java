package cn.wenhaha.spider.crawl.defaults;

import cn.wenhaha.spider.crawl.ParseContext;
import cn.wenhaha.spider.util.BrowserUA;
import us.codecraft.webmagic.Site;

public class DefParseContext implements ParseContext {
    @Override
    public Site getSite() {
        return Site.me().setUserAgent(BrowserUA.getComputerUA());
    }

}
