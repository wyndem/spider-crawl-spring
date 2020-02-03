package cn.wenhaha.spider.build.selectable;

import cn.wenhaha.spider.build.SpiderSelectable;
import com.alibaba.fastjson.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 *  使用 fastJson 的 JsonPath 语法
 *  博客介绍 : https://blog.csdn.net/itguangit/article/details/78764212
 *  官网地址: https://github.com/alibaba/fastjson/wiki/JSONPath
 *
 */
public class SpiderSelectableJson implements SpiderSelectable {

    private static final Log log = LogFactory.getLog(SpiderSelectableJson.class);

    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private String data;
    private List dataList;

    public SpiderSelectableJson(Html html) {
        Selectable body = html.css("body","text");
        try {
            jsonObject=JSON.parseObject(body.get());
        }catch (JSONException | ClassCastException j){
            jsonArray=JSON.parseArray(body.get());
        }

    }


    @Override
    public SpiderSelectable parse(String reg) {
        Object eval = null;
        if (Objects.isNull(jsonObject) && Objects.nonNull(jsonArray)){
            eval=JSONPath.eval(jsonArray, reg);
        }else if (Objects.nonNull(jsonObject) && Objects.isNull(jsonArray)){
            eval=JSONPath.eval(jsonObject, reg);
        }
        jsonArray=null;
        jsonObject=null;
        if (eval instanceof  JSONObject){
            jsonObject=(JSONObject)eval;
        }else if (eval instanceof  JSONArray){
            jsonArray=(JSONArray)eval;
        }
        else if (eval instanceof  List){
            dataList=(List) eval;
        }
        else if (eval instanceof Object){
            data=eval.toString();
        }
        return this;
    }

    @Override
    public SpiderSelectable parseScope(String reg) {
        Object eval = JSONPath.eval(jsonObject, reg);
        if (eval instanceof  JSONObject){
            jsonObject=(JSONObject)eval;
        }else {
                log.warn("已经没有可访问的路径了，请注意表达式是否正确");
        }
        return this;
    }

    @Override
    public List<String> all() {
        if (Objects.nonNull(jsonArray)){
            return jsonArray.toJavaList(String.class);
        }else if (Objects.nonNull(dataList)){
            return dataList;
        }

        ArrayList<String> strings = new ArrayList<>();
        if (StringUtils.isNotEmpty(data)){
            strings.add(data);
        }

        return strings;
    }

    @Override
    public String get() {
        if (StringUtils.isNotEmpty(data)) return data;
        else if (Objects.nonNull(jsonObject)) return jsonObject.toString();
        else if (Objects.nonNull(jsonArray)) {
            String s = jsonArray.toString();
            return s.substring(1,s.length()-1);
        }
        else if (Objects.nonNull(dataList)){
            String s=dataList.toString();
            return s.substring(1,s.length()-1);
        }
        return "";
    }

}
