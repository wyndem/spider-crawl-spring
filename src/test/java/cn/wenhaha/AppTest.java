package cn.wenhaha;

import static org.junit.Assert.assertTrue;

import cn.wenhaha.spider.annotations.SpiderPackage;
import cn.wenhaha.spider.crawl.CrawlFactory;
import cn.wenhaha.spider.crawl.CrawlFactoryBuild;
import cn.wenhaha.spider.crawl.CrawlSource;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
@SpiderPackage
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public  void test(){
        CrawlFactory build =new CrawlFactoryBuild().build(AppTest.class);

        CrawlSource crawlSource = build.openCrawl("https://blog.csdn.net/valada/article/details/99832608");
        Csdn mapper = crawlSource.getMapper(Csdn.class);
        String title = mapper.title();
        System.out.println(title);

    }
}
