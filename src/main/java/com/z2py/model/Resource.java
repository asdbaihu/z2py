package com.z2py.model;

import com.z2py.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@TargetUrl("http://pianyuan.net/r_*.html")
@HelpUrl("http://pianyuan.net/m_*.html")
public class Resource implements AfterExtractor, Serializable {
	
	//附加信息，不用做数据库存储
	@ExtractBy("//div[@class='col-sm-10  col-md-10 col-lg-10 text-left']/h2/a/text()")
	private String m_name = ""; 			// 影视名字
	@ExtractBy(value = "http://www.zimuku.net/search\\?q=tt(.*?)\" target", type = ExtractBy.Type.Regex)
	private String m_imdb; 			// imdb
	@ExtractBy(value = "/movie.douban.com/subject/(.*?)/", type = ExtractBy.Type.Regex)
	private String m_douban = ""; 			// 豆瓣ID
	private String m_year = ""; 			// 年代

	//正式字段
	private String r_id;
	@ExtractBy("//div[@class='col-sm-10  col-md-10 col-lg-10 text-left']/h1/text()")
	private String r_name; // 资源名称
	@ExtractBy("//ul[@class='base clearfix']/li[1]/text()")
	private String r_quality; // 资源画质
	@ExtractBy("//ul[@class='base clearfix']/li[2]/text()")
	private String r_size; // 资源大小
	@ExtractBy("//div[@class='tdown']/a[1]/@href")
	private String r_torrent; // 资源torrent路径
	private String r_magnet;
	private String mid;
	private Date r_time;

	public String getR_id() {
		return r_id;
	}

	public void setR_id(String r_id) {
		this.r_id = r_id;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getR_name() {
		return r_name;
	}

	public void setR_name(String r_name) {
		this.r_name = r_name;
	}

	public String getR_quality() {
		return r_quality;
	}

	public void setR_quality(String r_quality) {
		this.r_quality = r_quality;
	}

	public String getR_size() {
		return r_size;
	}

	public void setR_size(String r_size) {
		this.r_size = r_size;
	}

	public String getR_torrent() {
		return r_torrent;
	}

	public void setR_torrent(String r_torrent) {
		this.r_torrent = r_torrent;
	}

	public String getR_magnet() {
		return r_magnet;
	}

	public void setR_magnet(String r_magnet) {
		this.r_magnet = r_magnet;
	}

	public Date getR_time() {
		return r_time;
	}

	public void setR_time(Date r_time) {
		this.r_time = r_time;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public String getM_imdb() {
		return m_imdb;
	}

	public void setM_imdb(String m_imdb) {
		this.m_imdb = m_imdb;
	}

	public String getM_douban() {
		return m_douban;
	}

	public void setM_douban(String m_douban) {
		this.m_douban = m_douban;
	}

	public String getM_year() {
		return m_year;
	}

	public void setM_year(String m_year) {
		this.m_year = m_year;
	}

	@Override
	public String toString() {
		return "Resource [m_name=" + m_name + ", m_imdb=" + m_imdb + ", m_douban=" + m_douban + ", m_year=" + m_year
				+ ", r_id=" + r_id + ", r_name=" + r_name + ", r_quality=" + r_quality + ", r_size=" + r_size
				+ ", r_torrent=" + r_torrent + ", r_magnet=" + r_magnet + ", mid=" + mid + ", r_time=" + r_time + "]";
	}

	@Override
	public void afterProcess(Page page) {
		m_year = StringUtil.getMiddleString(m_name, "(", ")");
		if (StringUtils.isNotEmpty(m_imdb)) {
			m_imdb = "tt" + m_imdb;
		}
		if (StringUtils.isNotEmpty(r_torrent)) {
			r_torrent = r_torrent.replace("/dl/", "/dlbt/");
			r_torrent = "http://pianyuan.net" + r_torrent;
		}
		/*if (StringUtils.isNotEmpty(m_name)) {
			//System.out.println("-----------------resource is not null : " + page.getRequest().getUrl() + "-----------------");
		} else {
			System.out.println("------------resource is null : " + page.getRequest().getCookies() + "------------");
		}*/
	}
	
}
