package com.z2py.sql;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map.Entry;

public class MovieAndResourceSqlProvider {
	
	public String findResourceByFilters(MovieAndResourceQueryFilters filters) {
		String prefixSql = "SELECT r.r_id, r.r_name, r.r_size, r.r_time, m.m_id, m.m_poster, m.m_name, m.m_performer FROM tb_resource r INNER JOIN (";
		String sufixSql = " LIMIT " + (filters.getPage() - 1) * filters.getPageSize() + "," + filters.getPageSize() + ") temp USING(r_id) INNER JOIN tb_movie m ON r.mid=m.m_id";
		return prefixSql + subSelectResource(filters) + sufixSql;
	}

	public String findResourceCountByFilters(MovieAndResourceQueryFilters filters) {
		return "select count(0) from (" + subSelectResource(filters) + ") temp";
	}

	public String subSelectResource(MovieAndResourceQueryFilters filters) {

		return new SQL(){{
			SELECT("r_id");
			FROM("tb_resource,tb_movie");
			WHERE("m_id=mid");
			for (Entry<String, Object> e : filters.getResourceFilters().entrySet()) {
				if (StringUtils.isNotEmpty((String) e.getValue())) {
					//防止sql注入不采用字符串拼接参数的方式
					if ("quality".equals(e.getKey())) {
						WHERE("r_" + e.getKey() + " like \"%\"#{" + e.getKey() + "}\"%\"");
					} else {
						WHERE("m_" + e.getKey() + " like \"%\"#{" + e.getKey() + "}\"%\"");
					}
				}
			}
			if (StringUtils.isNotEmpty(filters.getOrder())) {

			} else {
				ORDER_BY("r_time desc");
			}

		}}.toString();
	}
	
	public String findMovieByFilters(MovieAndResourceQueryFilters filters) {
		return new SQL(){{
			SELECT("m.m_id, m.m_poster, m.m_name, m.m_type, m_score, m_cat, m_total_episode, m_update_date, m_recommend");
			FROM("tb_movie m");
			for (Entry<String, Object> e : filters.getMovieFilters().entrySet()) {
				if (StringUtils.isNotEmpty((String) e.getValue())) {
					//防止sql注入不采用字符串拼接参数的方式
					WHERE("m_" + e.getKey() + " like \"%\"#{" + e.getKey() + "}\"%\"");
				}
			}
			if (StringUtils.isNotEmpty(filters.getOrder())) {
				if ("update".equals(filters.getOrder())) {
					ORDER_BY("m_update_date desc");
				} else if ("score".equals(filters.getOrder())) {
					ORDER_BY("m_score desc");
				}
			} else {
				ORDER_BY("m_update_date desc");
			}
		}}.toString();
	}

}
