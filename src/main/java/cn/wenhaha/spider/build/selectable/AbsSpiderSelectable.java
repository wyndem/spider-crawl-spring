package cn.wenhaha.spider.build.selectable;

import cn.wenhaha.spider.build.SpiderSelectable;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

abstract public class AbsSpiderSelectable implements SpiderSelectable {
    protected final Html html;
    protected Selectable selectable;
    public AbsSpiderSelectable(Html html) {
        this.html = html;
    }

    public List<String> all(){
        if (selectable!=null){
            List<String> all = selectable.all();
            return all;
        }
        return null;
    }
    public String toString(){
        if (selectable!=null){
            return selectable.toString();
        }
        return toString();
    }
    public String get(){
        if (selectable!=null){
            return selectable.get();
        }
        return null;
    }
}
