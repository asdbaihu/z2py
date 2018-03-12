package com.z2py.service;

import com.z2py.dao.MovieDao;
import com.z2py.model.Movie;
import com.z2py.spider.config.SpiderConfig;
import com.z2py.sql.MovieAndResourceQueryFilters;
import com.z2py.util.HttpDownloadUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MovieService {

	@Resource
	private SpiderConfig spiderConfig;

	@Resource
	private MovieDao movieDao;
	
	@Transactional
	public Movie checkExistAndSave(Movie movie) throws Exception {

		Movie temp = findByImdbOrDoubanOrNameYear(movie.getM_imdb(), movie.getM_douban(), movie.getM_name(), movie.getM_year());

		String uuid = UUID.randomUUID().toString().replace("-", "");
		String tempPoster = movie.getM_poster();
		String posterPath = "/" + uuid.charAt(0) + "/" + uuid.charAt(1) + "/" + HttpDownloadUtil.getRandomFileName() + ".jpg";

		if (temp != null) {
			File file = new File(spiderConfig.getPosterPath() + temp.getM_poster());
			if (!file.exists()) {
				String filePath = spiderConfig.getPosterPath() + posterPath;
				HttpDownloadUtil.downloadImg(tempPoster, filePath);
				movieDao.updatePoster(temp.getM_id(), posterPath);
			}
			return temp;
		}
		
		movie.setM_id(uuid);
		movie.setM_poster(posterPath);

		int result = movieDao.save(movie);
		if (result == 1) {
			String filePath = spiderConfig.getPosterPath() + posterPath;
			HttpDownloadUtil.downloadImg(tempPoster, filePath);
		}
		return movie;
	}

	public Movie findById(String mid) throws Exception {
		return movieDao.findById(mid);
	}

	public Movie findByImdbOrDoubanOrNameYear(String m_imdb, String m_douban, String m_name, String m_year) throws Exception {
		Movie movie = null;
		if (StringUtils.isNotEmpty(m_imdb)) {
			movie = movieDao.findByImdb(m_imdb);
		} else if (StringUtils.isNotEmpty(m_douban)) {
			movie = movieDao.findByDouban(m_douban);
		} else {
			movie = movieDao.findByNameAndYear(m_name, m_year);
		}
		return movie;
	}

	public void updateUpdateDate(String mid, Date date) throws Exception {
		movieDao.updateUpdateDate(mid, date);
	}

	public List<Movie> findAll(MovieAndResourceQueryFilters filters) throws Exception {
		return movieDao.findAll(filters);
	}

	public List<Movie> search(String keyword)  throws Exception {
		return movieDao.search(keyword);
	}

	public void updateRecommend(List<String> ids, Integer recommend) {
		movieDao.updateRecommend(ids, recommend);
	}

    public List<Movie> findRecommendMovie() {
		return movieDao.findRecommendMovie();
    }
}
