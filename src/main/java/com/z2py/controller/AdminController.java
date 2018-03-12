package com.z2py.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.z2py.message.Message;
import com.z2py.model.Admin;
import com.z2py.model.Movie;
import com.z2py.service.AdminService;
import com.z2py.service.MovieService;
import com.z2py.spider.WebSpider;
import com.z2py.spider.config.SpiderConfig;
import com.z2py.sql.MovieAndResourceQueryFilters;
import com.z2py.util.SystemInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/admin")
@Controller
public class AdminController {
	
	private final int MOVIE_PAGE_SIZE = 15;
	
	@Autowired
	private MovieService movieService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private SpiderConfig spiderConfig;
	@Autowired
	private WebSpider webSpider;

	@RequestMapping("")
	public String admin(Model model, HttpServletRequest request) {
		SystemInfo sys = SystemInfo.getInstance(request);
		model.addAttribute("sys", sys);
		model.addAttribute("position", "home");
		return "admin/index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "admin/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam("username") String username,
						@RequestParam("password") String password,
						@RequestParam(value = "redirect", required = false) String redirect,
						HttpSession session) throws Exception {
		Admin admin = adminService.findByUsername(username);
		if (admin != null && DigestUtils.md5Hex(password).equals(admin.getPassword())) {
			session.setAttribute("admin", admin);
			if (redirect != null) {
				return "redirect:" + redirect;
			}
			return "redirect: /admin";
		} else {
			return "admin/login";
		}
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
	    session.removeAttribute("admin");
	    return "redirect: /admin/login";
    }

	@RequestMapping({"/movies", "/movies/page/{p}"})
	public String movies(Model model, 
			@PathVariable(value = "p", required = false) Integer p,
			MovieAndResourceQueryFilters filters) throws Exception {
		PageHelper.startPage(p == null ? 1 : p, MOVIE_PAGE_SIZE);
		List<Movie> movies = movieService.findAll(filters);
		model.addAttribute("pageInfo", new PageInfo<>(movies));
		model.addAttribute("position", "moviemanager-movies");
		model.addAttribute("filter", filters.movieFiltersToString());
		model.addAttribute("filters", filters);
		return "admin/movie";
	}
	
	/*@RequestMapping("/movie/delete")
	public String delete(Model model, List<String> ids) {
		return ""
	}*/
	@RequestMapping("/spider")
	public String spider(Model model) {
		model.addAttribute("spiderConfig", spiderConfig);
		model.addAttribute("webSpider", webSpider);
		model.addAttribute("position", "spider");
		return "admin/spider";
	}
	
	@RequestMapping("/spider/{action}")
	public String start(Model model, @PathVariable("action") String action) {
		switch (action) {
		case "start": 
			webSpider.start();
			break;
		case "stop": 
			webSpider.stop();
			break;
		}
		model.addAttribute("spiderConfig", spiderConfig);
		model.addAttribute("webSpider", webSpider);
		model.addAttribute("position", "spider");
		return "redirect:/admin/spider";
	}

	@RequestMapping(value = "/recommend", method = RequestMethod.PUT)
	public @ResponseBody Message recommend(@RequestParam("ids") List<String> ids, @RequestParam("recommend") Integer recommend) {
	    if (recommend < 0 || recommend > 1) {
	        return new Message(-1, "参数有误！");
        }
	    movieService.updateRecommend(ids, recommend);
	    return new Message(0, "更新成功！");
    }
}
