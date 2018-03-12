package com.z2py.pipeline;

import com.z2py.model.Movie;
import com.z2py.service.MovieService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

import javax.annotation.Resource;

@Component("MovieDaoPipeline")
public class MovieDaoPipeline implements PageModelPipeline<Movie> {

	@Resource
	private MovieService movieService;
	
	@Override
	public void process(Movie movie, Task task) {
		try {
			movie.setM_poster("http://pianyuan.net" + movie.getM_poster());
			if (StringUtils.isNotEmpty(movie.getM_name()))
				movieService.checkExistAndSave(movie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
