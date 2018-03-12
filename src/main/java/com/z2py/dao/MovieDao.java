package com.z2py.dao;

import com.z2py.model.Movie;
import com.z2py.sql.MovieAndResourceQueryFilters;
import com.z2py.sql.MovieAndResourceSqlProvider;
import com.z2py.sql.WhereInExtendedLanguageDriver;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@CacheNamespace(implementation=org.mybatis.caches.ehcache.EhcacheCache.class)
@Repository
public interface MovieDao {
	/**
	 * 保存movie
	 * @param movie
	 * @return
	 * @throws Exception
	 */
	@Insert("insert ignore into tb_movie(`m_id`,`m_name`,`m_alias`,`m_area`,`m_type`,`m_director`,`m_scenarist`,`m_performer`,`m_imdb`,`m_douban`,`m_score`,`m_year`,`m_poster`,`m_total_episode`,`m_update_date`, `m_cat`) values(#{m_id},#{m_name},#{m_alias},#{m_area},#{m_type},#{m_director},#{m_scenarist},#{m_performer},#{m_imdb},#{m_douban},#{m_score},#{m_year},#{m_poster},#{m_total_episode},#{m_update_date},#{m_cat})")
	@Options(useGeneratedKeys = true, keyProperty = "m_id")
	int save(Movie movie) throws Exception;

	/**
	 * 根据name和year查询记录
	 * @param m_name
	 * @param m_year
	 * @return
	 * @throws Exception
	 */
	@Select("select * from tb_movie where m_name=#{m_name} and m_year=#{m_year}")
	Movie findByNameAndYear(@Param("m_name") String m_name, @Param("m_year") String m_year) throws Exception;

	/**
	 * 更新封面
	 * @param id
	 * @param poster
	 * @throws Exception
	 */
	@Update("update tb_movie set m_poster=#{poster} where m_id=#{id}")
	void updatePoster(@Param("id") String id, @Param("poster") String poster) throws Exception;

	/**
	 * 根据imdb查询记录
	 * @param m_imdb
	 * @return
	 * @throws Exception
	 */
	@Select("select * from tb_movie where m_imdb=#{0}")
	Movie findByImdb(String m_imdb) throws Exception;

	/**
	 * 根据douban查询记录
	 * @param m_douban
	 * @return
	 * @throws Exception
	 */
	@Select("select * from tb_movie where m_douban=#{0}")
	 Movie findByDouban(String m_douban) throws Exception;

	/**
	 * 根据id查询记录
	 * @param mid
	 * @return
	 * @throws Exception
	 */
	@Select("select * from tb_movie where m_id=#{0}")
	Movie findById(String mid) throws Exception;

	/**
	 * 更新更新时间
	 * @param mid
	 * @param date
	 * @throws Exception
	 */
	@Update("update tb_movie set m_update_date=#{date} where m_id=#{mid}")
	void updateUpdateDate(@Param("mid") String mid, @Param("date") Date date) throws Exception;

	/**
	 * 根据过滤条件查询记录
	 * @param filters
	 * @return
	 * @throws Exception
	 */
	@SelectProvider(type = MovieAndResourceSqlProvider.class, method = "findMovieByFilters")
	@Options(useCache = true)
	List<Movie> findAll(MovieAndResourceQueryFilters filters) throws Exception;

	/**
	 * 根据关键词搜索记录
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	@Select("select * from tb_movie where m_name like \"%\"#{0}\"%\" or m_alias like \"%\"#{0}\"%\" or m_director like \"%\"#{0}\"%\" or m_scenarist like \"%\"#{0}\"%\" or m_performer like \"%\"#{0}\"%\" or m_imdb=#{0}")
	@Results(value = {
			@Result(property = "m_id", column = "m_id"),
			@Result(property = "resources", 
					column = "m_id", 
					javaType = List.class, 
					many = @Many(select="com.z2py.dao.ResourceDao.findTop3ByMid"))
	})
	List<Movie> search(String keyword) throws Exception;

	/**
	 * 更新推荐movie
	 * @param ids
	 * @param recommend
	 */
	@Lang(WhereInExtendedLanguageDriver.class)
	@Update("update tb_movie set m_recommend=#{recommend} where m_id in (${ids})")
	@Options(flushCache = Options.FlushCachePolicy.TRUE)
	void updateRecommend(@Param("ids") List<String> ids,@Param("recommend") Integer recommend);

	/**
	 * 查找推荐movie纪录
	 * @return
	 */
	@Select("select m_id, m_name, m_poster from tb_movie where m_recommend=1")
	List<Movie> findRecommendMovie();
}
