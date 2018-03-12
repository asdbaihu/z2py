package com.z2py.structure;

import com.z2py.util.FileTypeUtil;

import java.util.List;

public class Tree {

	private Node root;
	
	public Tree() {
		super();
	}

	public Tree(String text) {
		root = new Node(0, -1, text);
	}
	
	public void createTree(List<Node> nodes) {
		for (Node node : nodes) {
			//父亲是根节点，直接添加到根节点下面
			if (node.getPid() == root.getNid()) {
				root.addChild(node);
			} else {	//父亲是其他节点
				Node parent = findParent(root, node.getPid());
				if (parent != null) {
					parent.addChild(node);
				}
			}
		}
	}
	
	private Node findParent(Node node, int pid) {
		Node result = null;
		for (Node n : node.getChildren()) {
			if (n.getNid() == pid) {
				return n;
			} else {
				//递归搜索
				if (n.getChildren() != null)
					result =  findParent(n, pid);
			}
		}
		return result;
	}
	
	public void middlePrint(Node tnode) {
		if (tnode.getChildren() == null) {
			return;
		}
		for (Node node : tnode.getChildren()) {
			System.out.println(node.getText());
			middlePrint(node);
		}
	}
	
	public String getHtml(Node tnode) {
		
		if (tnode.getChildren() == null) {	//叶子节点
			return "<li><span class=\"" + FileTypeUtil.getFileType(tnode.getText()) + "\">" + tnode.getText() + "</span></li>";
		}
		
		String str = "";
		if (tnode.getNid() == root.getNid()) {	//根节点
			str += "<ul class=\"filetree treeview\"><p><span class=\"bticon\">" + root.getText() + "</span></p>";
		} else {				//子节点
			str += "<li class=\"closed\"><span class=\"folder\">" + tnode.getText() + "</span><ul>";
		}
		
		for (Node node : tnode.getChildren()) {
			str += getHtml(node);
		}
		
		if (tnode == root) {	//根节点
			return str += "</ul>";
		} else {				//子节点
			return str += "</ul></li>";
		}
	}
	
	public boolean checkExist(Node tnode, String text) {
		boolean exist = false;
		if (tnode.getChildren() == null) {
			return false;
		}
		for (Node node : tnode.getChildren()) {
			exist |= checkExist(node, text);
		}
		return exist;
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}
	
	
}
