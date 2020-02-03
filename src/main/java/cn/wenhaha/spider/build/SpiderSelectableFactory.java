package cn.wenhaha.spider.build;

import cn.wenhaha.spider.annotations.SpiderExpression;
import cn.wenhaha.spider.build.selectable.SpiderSelectableCss;
import cn.wenhaha.spider.build.selectable.SpiderSelectableJson;
import cn.wenhaha.spider.build.selectable.SpiderSelectableRegex;
import cn.wenhaha.spider.build.selectable.SpiderSelectableXpth;
import us.codecraft.webmagic.selector.Html;

public class SpiderSelectableFactory {


    public static SpiderSelectable build(Html html, SpiderExpression.Type type){
        switch (type){
            case Css:
                return new SpiderSelectableCss(html);
            case XPath:
                return new SpiderSelectableXpth(html);
            case Regex:
                return new SpiderSelectableRegex(html);
            case JsonPath:
                return new SpiderSelectableJson(html);
        }
        throw  new  RuntimeException("找不到对应解析类");
    }

}
