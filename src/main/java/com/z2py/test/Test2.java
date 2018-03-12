package com.z2py.test;

import java.util.ArrayList;
import java.util.List;


public class Test2 {

	public static void main(String[] args) {
		List<String> strs = new ArrayList<>();
		strs.add("BDMV\\STREAM\\00801.m2ts\\");
		strs.add("BDMV\\index.bdmv\\");
		strs.add("BDMV\\STREAM\\00083.m2ts\\");
		strs.add("BDMV\\STREAM\\00086.m2ts\\");
		strs.add("BDMV\\PLAYLIST\\00371.mpls\\");
		strs.add("CERTIFICATE\\BACKUP\\app.discroot.crt\\");
		strs.add("CERTIFICATE\\BACKUP\\bu.discroot.crt\\");
		strs.add("RARBG.com.txt\\");
		
		/**
		 * 技术部
		 * 		张山
		 * 		李四
		 * 宣传部
		 * 		王五
		 * 
		 */
		
		/*List<Node> nodes = new ArrayList<>();
		int cur = 1, parent = 0;
		for (String str : strs) {
			String[] arr = str.split("\\\\");
			for (int i = 0; i < arr.length; i++) {
				Node node = new Node(cur, i == 0 ? 0 : parent, arr[i]);
				if (!nodes.contains(node)) {
					nodes.add(node);
					parent = cur;
				} else {
					parent = nodes.get(nodes.indexOf(node)).getNid();
				}
				cur++;
			}
		}
		System.out.println(nodes.size());
*//*
		for (Node node : nodes)
			System.out.println(node.getText());
		*//*
		Tree tree = new Tree();
		tree.createTree(nodes);
		//tree.middlePrint(tree.getRoot());
		System.out.println(JSON.toJSONString(tree));
		System.out.println(tree.getHtml(tree.getRoot()));*/
	}
}
