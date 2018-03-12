package com.z2py.pipeline;

import com.z2py.model.Movie;
import com.z2py.service.MovieService;
import com.z2py.service.ResourceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

import javax.annotation.Resource;
import java.util.Date;

@Component("ResourceDaoPipeline")
public class ResourceDaoPipeline implements PageModelPipeline<com.z2py.model.Resource> {
	
	@Resource
	private ResourceService resourceService;
	@Resource
	private MovieService movieService;

	@Override
	public void process(com.z2py.model.Resource resource, Task task) {
		try {
			if (StringUtils.isNotEmpty(resource.getM_name()) && StringUtils.isNotEmpty(resource.getR_torrent())) {
				Movie movie = movieService.findByImdbOrDoubanOrNameYear(resource.getM_imdb(),
						resource.getM_douban(),
						resource.getM_name(),
						resource.getM_year());
				if (movie != null) {
					resource.setMid(movie.getM_id());
					if (resourceService.save(resource) == 1)
						movieService.updateUpdateDate(movie.getM_id(), new Date());
				} else {
					System.out.println("3333333333333333333333333333333");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
