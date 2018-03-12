package com.z2py.dao;

import com.z2py.model.Resource;
import com.z2py.sql.MovieAndResourceQueryFilters;
import com.z2py.sql.MovieAndResourceSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
/**
 * <p>Description: 资源Dao类</p>
 * @author Lonely Walker丶
 * 2017年4月1日 下午12:09:25
 */
@CacheNamespace(implementation=org.mybatis.caches.ehcache.EhcacheCache.class)
@Repository
public interface ResourceDao {

	/**
	 * 保存Resource
	 * @param resource
	 * @return
	 * @throws Exception
	 */
	@Insert("insert ignore into tb_resource(`r_id`,`r_name`,`r_quality`,`r_size`,`r_torrent`,`r_magnet`,`mid`,`r_time`) values(#{r_id},#{r_name},#{r_quality},#{r_size},#{r_torrent},#{r_magnet},#{mid},#{r_time})")
	int save(Resource resource) throws Exception;

	/**
	 * 删除Resource
	 * @param uuid
	 */
	@Delete("delete from tb_resource where r_id=#{0}")
	void delete(String uuid) throws Exception;

	/**
	 * 更新Resource
	 * @param resource
	 */
	@Update("update tb_resource set r_name=#{r_name},r_quality=#{r_quality},r_size=#{r_size},r_torrent=#{r_torrent},r_magnet=#{r_magnet},mid=#{mid},r_time=#{r_time} where r_id=#{r_id}")
	void update(Resource resource) throws Exception;

	@SelectProvider(type = MovieAndResourceSqlProvider.class, method = "findResourceCountByFilters")
	@Options(useCache = true)
	int findAllCount(MovieAndResourceQueryFilters filters) throws Exception;
	
	/**
	 * 获取所有资源列表
	 * @return
	 */
	@SelectProvider(type = MovieAndResourceSqlProvider.class, method = "findResourceByFilters")
	@Options(useCache = true)
	List<Map<String, Object>> findAll(MovieAndResourceQueryFilters filters) throws Exception;

	/**
	 * 根据Resource.r_id查询相关字段的一条记录
	 * @param rid
	 * @return
	 */
	@Select("select * from tb_resource r inner join tb_movie m on r.mid = m.m_id where r.r_id = #{0}")
	Map<String, Object> findById(String rid) throws Exception;
	
	/**
	 * 查询相关资源列表
	 * @param movieAndResource
	 * @return
	 */
	@Select("select r.r_id, r.r_name, r.r_size, r.r_quality from tb_resource r where r.mid = #{mid} and r.r_id!=#{r_id}")
	@Options(useCache = true)
	List<Resource> findRelative(Map<String, Object> movieAndResource) throws Exception;
	
	/**
	 * 根据mid查询3条resource纪录
	 * @param mid
	 * @return
	 */
	@Select("select r.r_id, r.r_name, r.r_size, r.r_quality from tb_resource r where r.mid=#{0} limit 3")
	List<Resource> findTop3ByMid(String mid);
	
	/**
	 * 根据mid查询所有记录
	 * @param mid
	 * @return
	 */
	@Select("select r.r_id, r.r_name, r.r_size, r.r_quality, r.r_time from tb_resource r where r.mid=#{0}")
	@Options(useCache = true)
	List<Resource> findByMid(String mid) throws Exception;

	@Select("select r.r_id, r.r_name, r.r_quality, r.r_time from tb_resource r where r.r_name like \"%\"#{0}\"%\"")
	@Options(useCache = true)
	List<Resource> search(String keyword) throws Exception;

	@Update("update tb_resource set r_torrent_json=#{r_torrent_json} where r_id=#{r_id}")
	@Options(flushCache = Options.FlushCachePolicy.TRUE)
    void updateJson(@Param("r_id") String rid,@Param("r_torrent_json") String r_torrent_json);
}
