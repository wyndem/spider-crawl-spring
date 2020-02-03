package cn.wenhaha.spider.build.selectable;

import cn.wenhaha.spider.build.SpiderSelectable;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;
import java.util.Objects;


public class SpiderSelectableCss extends AbsSpiderSelectable {

    private Selectable sorcesSelectable;
    private String reg;

    public SpiderSelectableCss(Html html) {
        super(html);
    }


    // 并行关系，在同一层面上
    @Override
    public SpiderSelectable parse(String reg) {
        this.reg=reg;
        //查找属性
        int i = reg.lastIndexOf("/@");
        String attr ="text";
        if (i!=-1){
            attr= reg.substring(i+2);
            reg=reg.substring(0,i);
            this.reg=reg;
        }

        //切割
        int  last=reg.lastIndexOf("/");
        if (last!=-1){
            reg=reg.substring(0,last);
        }


        if (sorcesSelectable!=null){
            selectable = sorcesSelectable.css(reg,attr);
        }else {
            selectable=html.css(reg,attr);
        }

        return this;
    }

    // 串行关系，类似递归
    @Override
    public SpiderSelectable parseScope(String reg) {
        if (sorcesSelectable!=null){
            Selectable css = sorcesSelectable.css(reg);
            if (Objects.nonNull(css)){
                sorcesSelectable=css;
            }
        }else {
            sorcesSelectable = html.css(reg);
        }
        return this;
    }

    @Override
    public String get() {
        if (selectable!=null){
            //查看是否有设置索引
            int start = reg.lastIndexOf("/[");
            int end = reg.lastIndexOf("]");
            if (start!=-1&&end!=-1){
                if (start+2==end){
                    throw new RuntimeException("需要指定下标");
                }
                int index = Integer.parseInt(reg.substring(start + 2, end));
                List<String> all = selectable.all();
                if (index<0){
                    index=all.size()+index;
                }
               return  all.get(index);
            }
        }
        return super.get();
    }
}
