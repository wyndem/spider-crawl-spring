package cn.wenhaha.spider.build;

import cn.wenhaha.spider.annotations.SpiderTimeFormat;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CrawlSourceMethod extends AbsCrawMethod{


    public CrawlSourceMethod(Method method) {
        super(method);
    }



    @Override
    String execute(String def) {

        for (String ex :  expression) {
            SpiderSelectable parse = spiderSelectable.parse(ex);
            String result = parse.get();
            if (!StringUtils.isEmpty(result)){
                return result;
            }
        }
        return def;
    }

    public Integer execute(Integer integer){
        String str=execute("");
        if (StringUtils.isEmpty(str)) return integer;
        return Integer.parseInt(str);
    }
    public Double execute(Double def){
        String str=execute("");
        if (StringUtils.isEmpty(str)) return def;
        return Double.parseDouble(str);
    }
    public Float execute(Float def){
        String str=execute("");
        if (StringUtils.isEmpty(str)) return def;
        return Float.parseFloat(str);
    }
    public Date execute(Date date){
        String str=execute("");
        if (StringUtils.isEmpty(str)) return null;
        SpiderTimeFormat annotation = method.getAnnotation(SpiderTimeFormat.class);
        if (annotation==null|| StringUtils.isEmpty(annotation.value())) {
            throw new RuntimeException("缺少时间转换格式，不能转换成日期类型");
        }
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(annotation.value());
        try {
            return simpleDateFormat.parse(str);
        } catch (ParseException e) {
           throw  new RuntimeException(e);
        }
    }

    @Override
    List execute(List def) {
        for (String ex :  expression) {
            SpiderSelectable parse = spiderSelectable.parse(ex);
            List result = parse.all();
            if (result!=null&&!result.isEmpty()){
                return result;
            }
        }
        return def;
    }




}
