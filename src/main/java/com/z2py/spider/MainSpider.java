package com.z2py.spider;

import com.z2py.model.Movie;
import com.z2py.model.Resource;
import com.z2py.pipeline.MovieDaoPipeline;
import com.z2py.pipeline.ResourceDaoPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.OOSpider;

@Component
public class MainSpider {
	 
    @Qualifier("MovieDaoPipeline")
    @Autowired
    private MovieDaoPipeline movieDaoPipeline;
    
    @Qualifier("ResourceDaoPipeline")
    @Autowired
    private ResourceDaoPipeline resourceDaoPipeline;

    public void crawl() {
        Spider spider = OOSpider.create(Site.me()
                .addHeader("Cookie", "yunsuo_session_verify=31a594832a46ee778e793420b8ee9225; PHPSESSID=oa4rkklnkttntmsb8ckvvrkn02; uv_cookie_113107=1; uv_cookie_113106=1; py_loginauth=WyJxNTIyODQ5MTAwIiwxNTAzNjYzMzcwLCJlMWRlYTQ2MjlkOTdjNjIzIl0%3D; Hm_lvt_007d2fd82f9feb403572bf36b223c24a=1503663054; Hm_lpvt_007d2fd82f9feb403572bf36b223c24a=1503663125")
                .setUserAgent("Mozilla/6.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36").setRetryTimes(3).setSleepTime(500).setTimeOut(10000))
                .addPageModel(movieDaoPipeline, Movie.class)
        		.addPageModel(resourceDaoPipeline, Resource.class)
        		//.addUrl("http://pianyuan.net/m_Keby9kWv0.html")
        		.addUrl("http://pianyuan.net/tv?p=1")
                .addUrl("http://pianyuan.net/mv?p=1")
                .thread(30);
        spider.run();
        System.out.println("finished");
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        final MainSpider jobCrawler = applicationContext.getBean(MainSpider.class);
        jobCrawler.crawl();
    }
}
