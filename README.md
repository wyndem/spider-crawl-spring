# 面向接口抓取爬虫


# 快速入门

接口：
``` java

// 声明网站类型
@SpiderTagerUrl("blog.csdn.net")
public interface Csdn {
    
    //规则
    @SpiderExpression(value = ".title-article",type = SpiderExpression.Type.Css)
    String title();
}

```

测试类


``` java

    CrawlFactory build =new CrawlFactoryBuild().build(AppTest.class);

    CrawlSource crawlSource = build.openCrawl("https://blog.csdn.net/valada/article/details/99832608");
    Csdn mapper = crawlSource.getMapper(Csdn.class);
    String title = mapper.title();
    System.out.println(title);
        
```

AppTest.java
```
//表示扫描包的路径
@SpiderPackage
public class AppTest {
    ...
}
```


# spring boot 方式

``` java
 @Autowired
 private CrawlFactory crawlFactory;
```
主要是获取工厂类，其他方式不变，无需加`SpiderPackage`注解

## 启动类
注解：

@SpiderPackage

开启包扫描，可以指定报名位置，默认为当前类的包路径


## 接口

### 注解：

@SpiderTagerUrl

填写爬取的站点url，多个接口可重复填写相同的url，只能在类中声明

@SpiderExpression

爬取的表达式，默认为XPath表达式，可以通过type属性更换，可进行多层表达式，当第一种爬取不到，就会使用第二种。以此轮推。

@SpiderScope

爬取的范围，在SpiderExpression之前进行筛选，筛选之后的元素在通过SpiderExpression表达式中。可进行多重筛选

@SpiderTimeFormat

如果返回类型为Date类型，则必须指定时间格式



### Selectable的Css选择器
在Selectable的原有写法上，增加了几个语法。

- 获取 某个 标签的属性：/@href 代表获取href属性
> 注意获取属性的操作必须要放在最后，并且保持唯一，不支持选择多个属性，如果不声明代表获取该标签的文本但不包括子标签的文本。

- 获取元素位置：/[index]  
> index 从0开始，为负数代表从结尾开始。写法必须放在/@href的前面，又要在正常写法的后面。如: `p>a/[-2]/@rel` 。代表获取a标签的倒数第二个，并获取他的rel属性。


# 返回类型
List(支持String泛型，其他暂不支持)、Integer、String、Double、Float、Date