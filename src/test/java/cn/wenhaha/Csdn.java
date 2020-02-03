package cn.wenhaha;

import cn.wenhaha.spider.annotations.SpiderExpression;
import cn.wenhaha.spider.annotations.SpiderTagerUrl;

@SpiderTagerUrl("blog.csdn.net")
public interface Csdn {

    //规则
    @SpiderExpression(value = ".title-article",type = SpiderExpression.Type.Css)
    String title();
}