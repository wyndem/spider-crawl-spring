package cn.wenhaha.spider.build;

import java.util.List;

public interface SpiderSelectable {

    public SpiderSelectable parse(String reg);
    SpiderSelectable parseScope(String reg);
    public List<String> all();
    public String toString();
    public String get();

}
