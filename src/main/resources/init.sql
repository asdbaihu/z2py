CREATE TABLE `tb_movie` (
  `m_id` VARCHAR(128) COLLATE utf8_bin NOT NULL,
  `m_name` VARCHAR(90) COLLATE utf8_bin DEFAULT NULL COMMENT '影视名字',
  `m_alias` VARCHAR(300) COLLATE utf8_bin DEFAULT NULL COMMENT '别名',
  `m_area` VARCHAR(60) COLLATE utf8_bin DEFAULT NULL COMMENT '地区',
  `m_type` VARCHAR(90) COLLATE utf8_bin DEFAULT NULL COMMENT '类型',
  `m_director` VARCHAR(150) COLLATE utf8_bin DEFAULT NULL COMMENT '导演',
  `m_scenarist` VARCHAR(450) COLLATE utf8_bin DEFAULT NULL COMMENT '编剧',
  `m_performer` VARCHAR(3333) COLLATE utf8_bin DEFAULT NULL COMMENT '主演',
  `m_imdb` VARCHAR(12) COLLATE utf8_bin DEFAULT NULL COMMENT 'imdb',
  `m_douban` VARCHAR(12) COLLATE utf8_bin DEFAULT NULL COMMENT '豆瓣ID',
  `m_score` FLOAT DEFAULT NULL COMMENT '评分',
  `m_year` VARCHAR(5) COLLATE utf8_bin DEFAULT NULL COMMENT '年代',
  `m_poster` VARCHAR(64) COLLATE utf8_bin DEFAULT NULL COMMENT '海报图片',
  `m_update_date` TIMESTAMP NULL DEFAULT NULL COMMENT '更新时间',
  `m_total_episode` INT(11) DEFAULT NULL COMMENT '总集数(电影:-1,剧集:总集数)',
  `m_cat` VARCHAR(2) COLLATE utf8_bin DEFAULT NULL COMMENT '分类(电影:mv,剧集:tv)',
  PRIMARY KEY (`m_id`),
  UNIQUE KEY `u_imdb` (`m_imdb`),
  UNIQUE KEY `u_douban` (`m_douban`),
  UNIQUE KEY `u_name` (`m_name`,`m_year`),
  KEY `f_update_date` (`m_update_date`),
  KEY `f_score` (`m_score`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `tb_resource` (
  `r_id` VARCHAR(128) COLLATE utf8_bin NOT NULL,
  `r_name` VARCHAR(200) COLLATE utf8_bin DEFAULT NULL COMMENT '资源名',
  `r_quality` VARCHAR(100) COLLATE utf8_bin DEFAULT NULL COMMENT '资源质量',
  `r_size` VARCHAR(20) COLLATE utf8_bin DEFAULT NULL COMMENT '资源大小',
  `r_torrent` VARCHAR(666) COLLATE utf8_bin DEFAULT NULL COMMENT '文件路径',
  `r_magnet` TEXT COLLATE utf8_bin COMMENT '磁力链接',
  `mid` VARCHAR(128) COLLATE utf8_bin DEFAULT NULL COMMENT '影视外键',
  `r_time` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`r_id`),
  UNIQUE KEY `u_rname` (`r_name`),
  KEY `f_mid` (`mid`),
  CONSTRAINT `f_mid` FOREIGN KEY (`mid`) REFERENCES `tb_movie` (`m_id`) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;