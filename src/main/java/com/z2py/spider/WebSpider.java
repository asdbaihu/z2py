package com.z2py.spider;

import com.z2py.model.Movie;
import com.z2py.model.Resource;
import com.z2py.pipeline.MovieDaoPipeline;
import com.z2py.pipeline.ResourceDaoPipeline;
import com.z2py.spider.scheduler.SpecialSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Spider.Status;
import us.codecraft.webmagic.model.OOSpider;

import java.util.Timer;

@Component
public class WebSpider {
	
	@Qualifier("MovieDaoPipeline")
    @Autowired
    private MovieDaoPipeline movieDaoPipeline;
    
    @Qualifier("ResourceDaoPipeline")
    @Autowired
    private ResourceDaoPipeline resourceDaoPipeline;

	private Spider spider;
	private Integer schedulerTime = 20;
	private Integer threads = 10;
	private String cookies = "Hm_lvt_0ef4d2ab3569e6ba0768bca5c4a7b7bf=1518864924,1519040437,1519221210,1519824240; py_loginauth=WyJxNTIyODQ5MTAwIiwxNTE5ODI0MjExLCJlNDhmN2UyYzBjOTkzMGQyIl0%3D; Hm_lvt_6d5e86213b07ede18ec639f1da3bc86b=1518864927,1519040439,1519221211,1519824241; yunsuo_session_verify=2bb686d020073d1a33ce041dfdc38621; PHPSESSID=ol6rlv7o6vpsd79td00h8kggr3; Hm_lpvt_0ef4d2ab3569e6ba0768bca5c4a7b7bf=1519824240; UBGLAI63GV=pbcfm.1519824241; Hm_lpvt_6d5e86213b07ede18ec639f1da3bc86b=1519824241; cpmpop_cm_t4=true";
	private Timer timer;

	public boolean start() {
		if (spider == null || spider.getThreadAlive() == 0 || spider.getStatus() == Status.Stopped) {
			spider = OOSpider.create(Site.me()
					.setDomain("pianyuan.net")
					.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:57.0) Gecko/20100101 Firefox/57.0")
					.addHeader("Cookie", cookies)
					.addCookie("Hm_lvt_0ef4d2ab3569e6ba0768bca5c4a7b7bf", "1516342617,1516440731,1516523315,1516691399")
					.addCookie("py_loginauth", "WyJxNTIyODQ5MTAwIiwxNTE5MjIxMTk3LCJiYTM1OTM0ODIzYmNkZmE5Il0%3D")
					.addCookie("yunsuo_session_verify", "cdec7993db495e2256f6b88ecc003106")
					.addCookie("Hm_lvt_6d5e86213b07ede18ec639f1da3bc86b", "1515952035,1516202648,1516263835,1516342617")
					.addCookie("PHPSESSID", "ol6rlv7o6vpsd79td00h8kggr3")
					.addCookie("Hm_lpvt_0ef4d2ab3569e6ba0768bca5c4a7b7bf", "1516373342")
					.addHeader("Referer", "http://pianyuan.net")
					.setRetryTimes(3).setSleepTime(2000).setTimeOut(20000))
	        		.addPageModel(movieDaoPipeline, Movie.class)
	        		.addPageModel(resourceDaoPipeline, Resource.class)
					.setScheduler(new SpecialSchedule(schedulerTime, cookies, "http://pianyuan.net/mv?p=1", "http://pianyuan.net/tv?p=1"))
	                .thread(threads);
			spider.start();

			return true;
		}
		return false;
	}
	
	public boolean stop() {
		if (timer != null) {
			timer.cancel();
		}
		if (spider != null) {
			spider.stop();
			spider = null;
			return true;
		}
		return false;
	}
	
	public Spider getSpider() {
		return spider;
	}

	public void setSpider(Spider spider) {
		this.spider = spider;
	}

	public Integer getSchedulerTime() {
		return schedulerTime;
	}

	public void setSchedulerTime(Integer schedulerTime) {
		this.schedulerTime = schedulerTime;
	}

	public Integer getThreads() {
		return threads;
	}

	public void setThreads(Integer threads) {
		this.threads = threads;
	}
	
	
	
}
