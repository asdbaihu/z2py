package com.z2py.service;

import com.z2py.dao.ResourceDao;
import com.z2py.spider.config.SpiderConfig;
import com.z2py.sql.MovieAndResourceQueryFilters;
import com.z2py.util.HttpDownloadUtil;
import org.eclipse.ecf.protocol.bittorrent.TorrentFile;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ResourceService {

	@Resource
	private SpiderConfig spiderConfig;
	
	@Resource
	private ResourceDao resourceDao;

	public int save(com.z2py.model.Resource resource) throws Exception {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		resource.setR_id(uuid);
		resource.setR_time(new Date());
		int result = resourceDao.save(resource);
		if (result == 1) {
			String path = "";
			try {
				String sufix = "/" + uuid.charAt(0) + "/" + uuid.charAt(1) + "/" + HttpDownloadUtil.getRandomFileName() + ".torrent";
				path = spiderConfig.getUploadPath() + sufix;
				boolean rtn = HttpDownloadUtil.download(resource.getR_torrent(), path);
				if (rtn) {
					TorrentFile torrent = new TorrentFile(new File(path));
					resource.setR_magnet("magnet:?xt=urn:btih:" + torrent.getHexHash() + "&dn=" + resource.getR_name());
					resource.setR_torrent(sufix);
					resourceDao.update(resource);
				} else {
					resourceDao.delete(uuid);
                    System.out.println(path);
                }
			} catch (Exception e) {
				resourceDao.delete(uuid);
                System.out.println(path);
			}
		}
		return result;
	}

	public List<Map<String, Object>> findAll(MovieAndResourceQueryFilters filters) throws Exception {
		return resourceDao.findAll(filters);
	}

	public Map<String, Object> findById(String rid) throws Exception {
		return resourceDao.findById(rid);
	}

	public List<com.z2py.model.Resource> findRelative(Map<String, Object> movieAndResource) throws Exception {
		return resourceDao.findRelative(movieAndResource);
	}

	public List<com.z2py.model.Resource> findByMid(String mid) throws Exception {
		return resourceDao.findByMid(mid);
	}

	public List<com.z2py.model.Resource> search(String keyword) throws Exception {
		return resourceDao.search(keyword);
	}

	public void updateJson(String rid, String s) {
		resourceDao.updateJson(rid, s);
	}

	public int findAllCount(MovieAndResourceQueryFilters filters) throws Exception {
		return resourceDao.findAllCount(filters);
	}
}
