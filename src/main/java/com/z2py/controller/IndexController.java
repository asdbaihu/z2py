package com.z2py.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.z2py.model.Movie;
import com.z2py.model.Resource;
import com.z2py.service.MovieService;
import com.z2py.service.ResourceService;
import com.z2py.spider.config.SpiderConfig;
import com.z2py.sql.MovieAndResourceQueryFilters;
import com.z2py.structure.Node;
import com.z2py.structure.TorrentFile;
import com.z2py.structure.Tree;
import com.z2py.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class IndexController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private final int INDEX_PAGE_SIZE = 40;
	private final int MV_AND_TV_PAGE_SIZE = 36;

	@Autowired
	private ResourceService resourceService;
	@Autowired
	private MovieService movieService;
	@Autowired
	private SpiderConfig spiderConfig;
	
	/**
	 * 首页
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping({"/", "/page/{pageNum}"})
	public String index(Model model, 
			@PathVariable(name = "pageNum", required = false) Integer pageNum,
			MovieAndResourceQueryFilters filters) throws Exception {
		if (pageNum == null)
			pageNum = 1;
		if (filters == null) {
			filters = new MovieAndResourceQueryFilters();
		}
		int count = resourceService.findAllCount(filters);
		//处理页数超过最大页数
		if (Math.ceil((double)count / INDEX_PAGE_SIZE) < pageNum) {
			pageNum = 1;
		}
		filters.setPage(pageNum);
		filters.setPageSize(INDEX_PAGE_SIZE);

		List<Map<String, Object>> movieAndResourceList = resourceService.findAll(filters);

		Page<Map<String, Object>> p = new Page<>(pageNum, INDEX_PAGE_SIZE);
		p.setTotal(count);
		p.addAll(movieAndResourceList);

		List<Movie> recommendMovies = movieService.findRecommendMovie();
		model.addAttribute("filter", filters.resourceFiltersToString());
		model.addAttribute("filters", filters);
		model.addAttribute("pageInfo", new PageInfo<>(p));
		model.addAttribute("recommendMovies", recommendMovies);
		return "index";
	}
	/**
	 * 电影或电视剧列表页
	 * @param model
	 * @param pageNum
	 * @param filters
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping({"/{cat}", "/{cat}/page/{pageNum}"})
	public String mvOrtv(Model model,
			@PathVariable("cat") String cat,
			@PathVariable(name = "pageNum", required = false) Integer pageNum,
			MovieAndResourceQueryFilters filters) throws Exception {
		if (!cat.matches("(mv|tv)"))
			return "404";
		if (pageNum == null)
			pageNum = 1;
		if (filters == null)
			filters = new MovieAndResourceQueryFilters();
		PageHelper.startPage(pageNum, MV_AND_TV_PAGE_SIZE);
		filters.setCat(cat);
		List<Movie> movieList = movieService.findAll(filters);
		filters.removeFilter("cat");
		model.addAttribute("filter", filters.movieFiltersToString());
		model.addAttribute("filters", filters);
		model.addAttribute("uri", cat + "/");
		model.addAttribute("pageInfo", new PageInfo<>(movieList));
		return "mv-tv";
	}
	
	/**
	 * 影视信息页
	 * @param model
	 * @param mid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/movie/{mid}")
	public String movie(Model model, @PathVariable("mid") String mid) throws Exception {
		Movie movie = movieService.findById(mid);
		if (movie != null) {
			List<Resource> list = resourceService.findByMid(mid);
			Map<String, List<Resource>> resources = new TreeMap<>();
			for (Resource resource : list) {
				if (resources.containsKey(resource.getR_quality())) {
					resources.get(resource.getR_quality()).add(resource);
				} else {
					List<Resource> value = new ArrayList<>();
					value.add(resource);
					resources.put(resource.getR_quality(), value);
				}
			}
			model.addAttribute("totalResources", list.size());
			model.addAttribute("resources", resources);
			model.addAttribute("movie", movie);
		} else {
			return "404";
		}
		return "movie";
	}
	/**
	 * 资源页
	 * @param rid
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/resource/{rid}")
	public String resource(@PathVariable("rid") String rid, Model model) throws Exception {
		Map<String, Object> movieAndResource = resourceService.findById(rid);
		if (movieAndResource != null) {
			String treeview = "";
			byte[] torrent_json = (byte[]) movieAndResource.get("r_torrent_json");
			if (torrent_json != null){
				Tree tree = JSON.parseObject(torrent_json, Tree.class);
				treeview = tree.getHtml(tree.getRoot());
			} else {
				String path = spiderConfig.getUploadPath() + movieAndResource.get("r_torrent");
				treeview = torrentToHtmlTreeView(path, (String) movieAndResource.get("r_id"), (String) movieAndResource.get("r_name"));
			}
			PageHelper.startPage(0, 5);
			List<Resource> relativeList = resourceService.findRelative(movieAndResource); 
			model.addAttribute("relativePage", new PageInfo<Resource>(relativeList));
			model.addAttribute("treeview", treeview);
			model.addAttribute("movieAndResource", movieAndResource);
		} else {
			return "404";
		}
		return "resource";
	}
	
	@RequestMapping("search/{keyword}")
	public String search(Model model,
			@PathVariable("keyword") String keyword,
			@RequestParam(name = "p", required = false, defaultValue = "0") Integer p,
			@RequestParam(name = "type", required = false, defaultValue = "0") Integer type) throws Exception {
		keyword = URLDecoder.decode(keyword, "UTF-8");
		model.addAttribute("keyword", keyword);
		model.addAttribute("type", type);
		if (type == 0) {
			PageHelper.startPage(p, 10);
			List<Movie> movieList = movieService.search(keyword);
			PageInfo<Movie> pageInfo = new PageInfo<>(movieList);
			model.addAttribute("pageInfo", pageInfo);
		} else if (type == 1) {
			PageHelper.startPage(p, 20);
			List<Resource> resourceList = resourceService.search(keyword);
			PageInfo<Resource> pageInfo = new PageInfo<>(resourceList);
			model.addAttribute("pageInfo", pageInfo);
		}
		return "search";
	}
	
	/**
	 * torrent文件列表转为html树形结构
	 */
	private String torrentToHtmlTreeView(String path, String rid, String r_name) {
		TorrentFile file;
		try {
			file = new TorrentFile(new File(path));
			String[] strs = file.getFilenames();
			long[] lens = file.getLengths();
			List<Long> list = new ArrayList<>();
			List<String> files = new ArrayList<>();
			for (int i = 0; i < strs.length; i++) {
				if (!strs[i].contains("_____padding_file_")) {
					files.add(strs[i]);
					list.add(lens[i]);
				}
			}
			List<Node> nodes = new ArrayList<>();
			int cur = 1, parent = 0;
			for (int j = 0; j < files.size(); j++) {
				String[] arr = files.get(j).split("/");
				for (int i = 0; i < arr.length; i++) {
					String text = arr[i];
					if (i == arr.length - 1) {
						text = arr[i] + "<small>(" + StringUtil.formatSize(list.get(j)) + ")" + "</small>";
					}
					Node node = new Node(cur, i == 0 ? 0 : parent, text);
					if (!nodes.contains(node)) {
						nodes.add(node);
						parent = cur;
					} else {
						parent = nodes.get(nodes.indexOf(node)).getNid();
					}
					cur++;
				}
			}
			Tree tree = new Tree(r_name);
			tree.createTree(nodes);
			resourceService.updateJson(rid, JSON.toJSONString(tree));
			return tree.getHtml(tree.getRoot());
		} catch (IllegalArgumentException | IOException e) {
		}
		return "";
	}
}
