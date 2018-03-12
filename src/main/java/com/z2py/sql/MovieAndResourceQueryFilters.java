package com.z2py.sql;

import org.apache.commons.lang.StringUtils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MovieAndResourceQueryFilters {

	private Map<String, Object> movieFilters = new HashMap<>();
	private Map<String, Object> resourceFilters = new HashMap<>();
	private String order = "";
	private Integer page;
	private Integer pageSize;

	public MovieAndResourceQueryFilters() {
		super();
		movieFilters.put("name", "");
		movieFilters.put("type", "");
		movieFilters.put("area", "");
		movieFilters.put("year", "");
		
		resourceFilters.put("name", "");
		resourceFilters.put("cat", "");
		resourceFilters.put("type", "");
		resourceFilters.put("area", "");
		resourceFilters.put("year", "");
		resourceFilters.put("quality", "");
	}

	public Map<String, Object> getMovieFilters() {
		return movieFilters;
	}

	public void setMovieFilters(Map<String, Object> movieFilters) {
		this.movieFilters = movieFilters;
	}

	public Map<String, Object> getResourceFilters() {
		return resourceFilters;
	}

	public void setResourceFilters(Map<String, Object> resourceFilters) {
		this.resourceFilters = resourceFilters;
	}
	
	public String getName() {
		return (String) resourceFilters.get("name");
	}
	
	public void setName(String name) {
		resourceFilters.put("name", name);
		movieFilters.put("name", name);
	}

	public String getCat() {
		return (String) resourceFilters.get("cat");
	}
	
	public void setCat(String cat) {
		resourceFilters.put("cat", cat);
		movieFilters.put("cat", cat);
	}
	
	public String getType() {
		return (String) resourceFilters.get("type");
	}
	
	public void setType(String type) {
		resourceFilters.put("type", type);
		movieFilters.put("type", type);
	}
	
	public String getArea() {
		return (String) resourceFilters.get("area");
	}
	
	public void setArea(String area) {
		resourceFilters.put("area", area);
		movieFilters.put("area", area);
	}
	
	public String getYear() {
		return (String) resourceFilters.get("year");
	}
	
	public void setYear(String year) {
		resourceFilters.put("year", year);
		movieFilters.put("year", year);
	}
	
	public String getQuality() {
		return (String) resourceFilters.get("quality");
	}
	
	public void setQuality(String quality) {
		resourceFilters.put("quality", quality);
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void removeFilter(String key) {
		resourceFilters.remove(key);
		movieFilters.remove(key);
	}

	public String resourceFiltersToString() {
		try {
			List<String> filters = new ArrayList<>();
			for (Entry<String, Object> e : resourceFilters.entrySet()) {
				if (!"".equals(e.getValue())) {
					filters.add(e.getKey() + "=" + URLEncoder.encode((String) e.getValue(), "UTF-8"));
				}
			}
			if (StringUtils.isNotEmpty(order)) {
				filters.add("order=" + order);
			}
			String str = StringUtils.join(filters, "&");
			if (str == null || "".equals(str)) {
				return "";
			} else {
				return "?" + str;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String movieFiltersToString() {
		try {
			List<String> filters = new ArrayList<>();
			for (Entry<String, Object> e : movieFilters.entrySet()) {
				if (!"".equals(e.getValue())) {
					filters.add(e.getKey() + "=" + URLEncoder.encode((String) e.getValue(), "UTF-8"));
				}
			}
			if (StringUtils.isNotEmpty(order)) {
				filters.add("order=" + order);
			}
			String str = StringUtils.join(filters, "&");
			if (str == null || "".equals(str)) {
				return "";
			} else {
				return "?" + str;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
