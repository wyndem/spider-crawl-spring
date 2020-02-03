package cn.wenhaha.spider.build.selectable;

import cn.wenhaha.spider.build.SpiderSelectable;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.Objects;

public class SpiderSelectableRegex extends AbsSpiderSelectable {

    private Selectable sorcesSelectable;

    public SpiderSelectableRegex(Html html) {
        super(html);
    }

    @Override
    public SpiderSelectable parse(String reg) {
        if (sorcesSelectable!=null){
            selectable = sorcesSelectable.regex(reg);
        }else {
            selectable=html.regex(reg);
        }
        return this;
    }

    @Override
    public SpiderSelectable parseScope(String reg) {
        if (sorcesSelectable!=null){
            Selectable regex = sorcesSelectable.regex(reg);
            if (Objects.nonNull(regex)){
                sorcesSelectable=regex;
            }
        }else {
            sorcesSelectable = html.regex(reg);
        }
        return this;
    }
}
