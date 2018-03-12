package com.z2py.test;

import com.z2py.util.HttpDownloadUtil;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Test1 {

	public static void main(String[] args) throws IllegalArgumentException, IOException, IllegalAccessException, NoSuchAlgorithmException {
		/*
		 * MovieAndResource mar = new MovieAndResource();
		 * mar.setM_alias("111111"); mar.setM_area("2222222222"); Movie movie =
		 * new Movie(); BeanUtils.copyProperties(mar, movie);
		 * 
		 * System.out.println(movie);
		 */
		/*String path = "C:/Users/Shinelon/Desktop/神奇动物在哪里.Fantastic.Beasts.and.Where.to.Find.Them.2016.1080p.KORSUB.HDRip.x264.AAC2.0-中文字幕-RARBT.torrent";
		
		TorrentFile file = new TorrentFile(new File(path));
		System.out.println(file.getName());
		String[] strs = file.getFilenames();
		long[] longs = file.getLengths();
		System.out.println(strs.length + " " + longs.length);

		for (int i = 0; i < strs.length; i++) {
			System.out.println(new String(strs[i].getBytes("ISO-8859-1"), "UTF-8"));
		}*/
		/*Torrent torrent = Torrent.load(new File("E:/upload/4/9/[摆渡人]See.You.Tomorrow.2016.BluRay.720p.x264.2Audio.AC3-CnSCG.torrent"));
		List<String> list = torrent.getFilenames();
		System.out.println("--------------------------------------");
		for (String s : list) {
			System.out.println(s.indexOf("\\"));
		}*/
		/*TorrentFile t = new TorrentFile(new File("C:\\Users\\Shinelon\\Desktop\\The.Legend.of.the.Condor.Heroes.EP01-52.2017.WEB-DL.1080P.x264.AAC-HQC.torrent"));

		for (String s : t.getFilenames()) {
			System.out.println(s);
		}*/
		//System.out.println(URLDecoder.decode("Steven%20M.%20Smith", "UTF-8"));
		HttpDownloadUtil.downloadImg("http://static.pianyuan.net/Picture/litpic/06/05Jun2017102650.jpg",
				"C:\\Users\\Shinelon\\Desktop\\111.jpg");
	}
}
