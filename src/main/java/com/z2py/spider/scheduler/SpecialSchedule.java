package com.z2py.spider.scheduler;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.DuplicateRemovedScheduler;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class SpecialSchedule extends DuplicateRemovedScheduler {
	
	private Timer timer;
	private BlockingQueue<Request> queue = new LinkedBlockingDeque<>();
	private Set<String> urls = new HashSet<String>();
	private String cookies;
	
	public SpecialSchedule(int munites, String cookies, String...startUrls) {
		this.cookies = cookies;
		timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if (queue.isEmpty()) {
					urls.clear();
					for (String url : startUrls) {
						queue.add(new Request(url).addHeader("Cookie", cookies)
								.addHeader("User-Agent",
										"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:57.0) Gecko/20100101 Firefox/57.0"));
					}
				}
			}
		}, 0, munites * 60000);
	}
	
	@Override
	public synchronized void push(Request request, Task task) {
		if (urls.add(request.getUrl())) {
			request.addHeader("Cookie", cookies).addHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:57.0) Gecko/20100101 Firefox/57.0");
			queue.add(request);
		}

	}

	@Override
	public Request poll(Task task) {
		Request take = null;
		while (take == null) {
			try {
				take = queue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return take;
	}

}
