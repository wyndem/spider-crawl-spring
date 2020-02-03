package cn.wenhaha.spider.build.selectable;

import cn.wenhaha.spider.build.SpiderSelectable;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import us.codecraft.webmagic.selector.Html;

import java.util.*;

public class SpiderSelectableXpth implements SpiderSelectable {

    private static Map<Html,TagNode> tagNodeMap=new HashMap<>();
    private static LinkedList<Html> keys=new LinkedList<>();
    private  Object[] objects;
    private Object[] sourceData;
    private TagNode tagNode;


    public SpiderSelectableXpth(Html html) {
       synchronized (SpiderSelectableXpth.class){
            //不等于空，并且不为首头时
           if (!keys.isEmpty()&&keys.getFirst()!=html){
               keys.remove(html);
               keys.addFirst(html);
           }
           tagNode = tagNodeMap.get(html);
           if (Objects.isNull(tagNode)){
               tagNode = new HtmlCleaner().clean(html.get());
               tagNodeMap.put(html,tagNode);
               keys.addFirst(html);
           }


           while (keys.size()>5){
               Html key = keys.removeLast();
               tagNodeMap.remove(key);
           }
       }
    }

    @Override
    public SpiderSelectable parse(String reg) {
        try {
            if (Objects.nonNull(sourceData)){
                List<Object> parse = parse(reg, sourceData);
                objects=parse.toArray();
            }else {
                CharSequence text = tagNode.getText();
//                System.out.println(text);
                parseScope("").parse(reg);
            }

        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public SpiderSelectable parseScope(String reg) {
        try {
            if (Objects.nonNull(sourceData)){
                List<Object> tagNodesList = parse(reg, sourceData);
                if (!tagNodesList.isEmpty()){
                    sourceData=tagNodesList.toArray();
                }
            }else {
                sourceData =tagNode.evaluateXPath(reg);
            }

        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return this;
    }

    private List<Object> parse(String reg,Object[] sourceData) throws XPatherException {
        ArrayList<Object> tagNodesList = new ArrayList<>();
        for (int i = 0; i < sourceData.length; i++) {
            TagNode node=(TagNode) sourceData[i];
            Object[] objects = node.evaluateXPath(reg);
            List<Object> tagNodes = Arrays.asList(objects);
            tagNodesList.addAll(tagNodes);
        }
        return tagNodesList;
    }


    @Override
    public List<String> all() {
        List<String> strings = new ArrayList<>(objects.length);
        for (int i = 0; i < objects.length; i++) {
            strings.add(objects[i].toString());
        }

        return strings;
    }

    @Override
    public String get() {
        if (objects==null) return "";
        if (objects.length<=0)return "";
        return objects[0].toString();
    }


}
