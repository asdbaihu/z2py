package com.z2py.model;

import com.z2py.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.selector.Html;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@TargetUrl("http://pianyuan.net/m_*.html")
@HelpUrl("http://pianyuan.net/(mv|tv)\\?p=(1|2|3)")
public class Movie implements AfterExtractor, Serializable {

    private String m_id;
    @ExtractBy("//div[@class='col-sm-12  col-md-12 col-lg-12 text-left nopd']/h1/text()")
    private String m_name; // 影视名字
    @ExtractBy(value = "又名:</strong> \n        <div>\n          (.*?) \n        </div>", type = ExtractBy.Type.Regex)
    private String m_alias; // 别名
    @ExtractBy(value = "地区:</strong> \n        <div>\n          (.*?) \n        </div>", type = ExtractBy.Type.Regex)
    private String m_area; // 地区
    @ExtractBy(value = "类型:</strong> \n        <div>\n          (.*?) \n        </div>", type = ExtractBy.Type.Regex)
    private String m_type; // 类型
    @ExtractBy(value = "导演:</strong> \n        <div> \n         (.*?) \n        </div>", type = ExtractBy.Type.Regex)
    private String m_director; // 导演
    @ExtractBy(value = "编剧:</strong> \n        <div> \n         (.*?) \n        </div>", type = ExtractBy.Type.Regex)
    private String m_scenarist; // 编剧
    @ExtractBy(value = "主演:</strong> \n        <div> \n         (.*?) \n        </div>", type = ExtractBy.Type.Regex)
    private String m_performer; // 主演
    @ExtractBy(value = "imdb:tt(.*?)\"", type = ExtractBy.Type.Regex)
    private String m_imdb; // imdb
    @ExtractBy(value = "/movie.douban.com/subject/(.*?)/", type = ExtractBy.Type.Regex)
    private String m_douban; // 豆瓣ID
    @ExtractBy("//i[@class='sum']/allText()")
    private Float m_score; // 评分
    private String m_year; // 年代
    @ExtractBy("//div[@class='litpic']/a/img/@src")
    private String m_poster; // 海报
    @ExtractBy(value = "集数&nbsp;&nbsp;<b>\\( (.*?) \\)</b></small>", type = ExtractBy.Type.Regex)
    private Integer m_total_episode = -1;    //剧集集数，电影标记为-1
    private Date m_update_date;    //最后更新时间
    private String m_cat;
    private Integer m_recommend = 0;

    private List<Resource> resources;

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

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

    public Date getM_update_date() {
        return m_update_date;
    }

    public void setM_update_date(Date m_update_date) {
        this.m_update_date = m_update_date;
    }

    public Integer getM_total_episode() {
        return m_total_episode;
    }

    public void setM_total_episode(Integer m_total_episode) {
        this.m_total_episode = m_total_episode;
    }

    public String getM_cat() {
        return m_cat;
    }

    public void setM_cat(String m_cat) {
        this.m_cat = m_cat;
    }

    public Integer getM_recommend() {
        return m_recommend;
    }

    public void setM_recommend(Integer m_recommend) {
        this.m_recommend = m_recommend;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    @Override
    public String toString() {
        return "Movie [m_id=" + m_id + ", m_name=" + m_name + ", m_alias=" + m_alias + ", m_area=" + m_area
                + ", m_type=" + m_type + ", m_director=" + m_director + ", m_scenarist=" + m_scenarist
                + ", m_performer=" + m_performer + ", m_imdb=" + m_imdb + ", m_douban=" + m_douban + ", m_score="
                + m_score + ", m_year=" + m_year + ", m_poster=" + m_poster + ", m_update_date=" + m_update_date
                + ", m_total_episode=" + m_total_episode + "]";
    }

    @Override
    public void afterProcess(Page page) {

        if (StringUtils.isNotEmpty(m_name)) {
            m_name = m_name.trim();
            m_year = StringUtil.getMiddleString(m_name, "(", ")");
        }

        if (StringUtils.isNotEmpty(m_director)) {
            Html html = new Html(m_director);
            List<String> list = html.xpath("//a/text()").all();
            m_director = StringUtils.join(list, ",");
            list.clear();
        }

        if (StringUtils.isNotEmpty(m_scenarist)) {
            Html html = new Html(m_scenarist);
            List<String> list = html.xpath("//a/text()").all();
            m_scenarist = StringUtils.join(list, ",");
            list.clear();
        }

        if (StringUtils.isNotEmpty(m_performer)) {
            Html html = new Html(m_performer);
            List<String> list = html.xpath("//a/text()").all();
            m_performer = StringUtils.join(list, ",");
            list.clear();
        }

        if (StringUtils.isNotEmpty(m_imdb)) {
            m_imdb = "tt" + m_imdb;
        }

        m_update_date = new Date();
        m_cat = m_total_episode == -1 ? "mv" : "tv";

        if (!StringUtils.isNotEmpty(m_name)) {
            System.out.println(count++);
        }
        //System.out.println(page.getRequest().getUrl());
        /*if (!StringUtils.isNotEmpty(m_name)) {
            System.out.println("-----------------movie is not null : " + page.getRequest().getUrl() + "-----------------\r\n" +
            page.getHtml());
        }*/
        /*System.out.println(page.getHtml().links().regex("(http://subbt\\.com/r_(.*?)\\.html)").all());
		page.addTargetRequests(page.getHtml().links().regex("(http://subbt\\.com/r_(.*?)\\.html)").all());*/

    }

    public static int count = 0;

}
