package com.z2py.model;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("http://pianyuan.net/r_*.html")
@HelpUrl("http://pianyuan.net/\\?p=\\d+")
public class MovieAndResource {

	@ExtractBy("//div[@class='col-sm-10  col-md-10 col-lg-10 text-left']/h2/a/text()")
	private String m_name = ""; 			// 影视名字
	@ExtractBy(value = "又名:</strong>\n          <div>\n          (.*?)          </div>", type = ExtractBy.Type.Regex)
	private String m_alias = ""; 			// 别名
	@ExtractBy(value = "地区:</strong>\n          <div>\n          (.*?)          </div>", type = ExtractBy.Type.Regex)
	private String m_area = ""; 			// 地区
	@ExtractBy(value = "类型:</strong>\n          <div>\n          (.*?)          </div>", type = ExtractBy.Type.Regex)
	private String m_type = ""; 			// 类型
	@ExtractBy(value = "导演:</strong>\n          <div>\n          (.*?)          </div>", type = ExtractBy.Type.Regex)
	private String m_director = ""; 		// 导演
	@ExtractBy(value = "编剧:</strong>\n          <div>\n          (.*?)          </div>", type = ExtractBy.Type.Regex)
	private String m_scenarist = ""; 		// 编剧
	@ExtractBy(value = "主演:</strong>\n          <div>\n          (.*?)          </div>", type = ExtractBy.Type.Regex)
	private String m_performer = ""; 		// 主演
	@ExtractBy(value = "http://www.zimuku.net/search\\?q=tt(.*?)\" target", type = ExtractBy.Type.Regex)
	private String m_imdb; 			// imdb
	@ExtractBy(value = "/movie.douban.com/subject/(.*?)/", type = ExtractBy.Type.Regex)
	private String m_douban = ""; 			// 豆瓣ID
	private Float m_score; 					// 评分
	private String m_year = ""; 			// 年代
	@ExtractBy("//div[@class='minfo']/a/img/@src")
	private String m_poster = ""; 			// 海报

	@ExtractBy("//div[@class='col-sm-10  col-md-10 col-lg-10 text-left']/h1/text()")
	private String r_name; 					// 资源名称
	@ExtractBy("//ul[@class='base clearfix']/li[1]/text()")
	private String r_quality; 				// 资源画质
	@ExtractBy("//ul[@class='base clearfix']/li[2]/text()")
	private String r_size; 					// 资源大小
	@ExtractBy("//div[@class='tdown']/a[1]/@href")
	private String r_torrent; 				// 资源torrent路径
	@ExtractBy(value = "magnet(.*?)\" class", type = ExtractBy.Type.Regex)
	private String r_magnet; 				// 磁力链接

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public String getM_alias() {
		return m_alias;
	}

	public void setM_alias(String m_alias) {
		this.m_alias = m_alias;
	}

	public String getM_area() {
		return m_area;
	}

	public void setM_area(String m_area) {
		this.m_area = m_area;
	}

	public String getM_type() {
		return m_type;
	}

	public void setM_type(String m_type) {
		this.m_type = m_type;
	}

	public String getM_director() {
		return m_director;
	}

	public void setM_director(String m_director) {
		this.m_director = m_director;
	}

	public String getM_scenarist() {
		return m_scenarist;
	}

	public void setM_scenarist(String m_scenarist) {
		this.m_scenarist = m_scenarist;
	}

	public String getM_performer() {
		return m_performer;
	}

	public void setM_performer(String m_performer) {
		this.m_performer = m_performer;
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

	public Float getM_score() {
		return m_score;
	}

	public void setM_score(Float m_score) {
		this.m_score = m_score;
	}

	public String getM_year() {
		return m_year;
	}

	public void setM_year(String m_year) {
		this.m_year = m_year;
	}

	public String getM_poster() {
		return m_poster;
	}

	public void setM_poster(String m_poster) {
		this.m_poster = m_poster;
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

	@Override
	public String toString() {
		return "MovieAndResource [m_name=" + m_name + ", m_alias=" + m_alias + ", m_area=" + m_area + ", m_type="
				+ m_type + ", m_director=" + m_director + ", m_scenarist=" + m_scenarist + ", m_performer="
				+ m_performer + ", m_imdb=" + m_imdb + ", m_douban=" + m_douban + ", m_score=" + m_score + ", m_year="
				+ m_year + ", m_poster=" + m_poster + ", r_name=" + r_name + ", r_quality=" + r_quality + ", r_size="
				+ r_size + ", r_torrent=" + r_torrent + ", r_magnet=" + r_magnet + "]";
	}

}
