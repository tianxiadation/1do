package com.luqi.util.node;

import java.util.ArrayList;
import java.util.List;

import com.luqi.common.model.T1doBoard;

public class InfiniteLevelTreeUtil {
    public static void main(String[] args) {
       /* Node n1 = new Node("0", "根节点", "-1", 0);
        Node n2 = new Node("01", "一级子节点", "0", 0);
        Node n3 = new Node("011", "二级子节点1", "01", 3);
        Node n4 = new Node("012", "二级子节点2", "01", 2);
        Node n5 = new Node("013", "二级子节点3", "01", 1);
        Node n6 = new Node("0131", "三级子节点1", "013", 1);
        Node n7 = new Node("0132", "三级子节点2", "013", 1);
        List<Node> nodeList=new ArrayList<>();
        nodeList.add(n1);
        nodeList.add(n2);
        nodeList.add(n3);
        nodeList.add(n4);
        nodeList.add(n5);
        nodeList.add(n6);
        nodeList.add(n7);
        List<Node> list=getInfiniteLevelTree(nodeList);*/

    }
    // 项目入口方法
    public static List<Node> getInfiniteLevelTree(List<T1doBoard> t1doBoards) {
    	List<Node> nodeList=new ArrayList<>();
    	for (T1doBoard t1doBoard : t1doBoards) {
    		nodeList.add(Node.getNode(t1doBoard));
		}
        List<Node> list = new ArrayList<>();
        // 遍历节点列表
        for (Node node : nodeList) {
            if (node.getType()==3) {
                // 类型为3-项目作为入口
                node.setChildren(getChildrenNode(node.getId(), nodeList));
                list.add(node);
            }
        }
        // 排序
        list.sort(new NodeOrderComparator());
        return list;
    }

    // 开始入口方法
    public static List<Node> getStartLevelTree(List<T1doBoard> t1doBoards) {
        List<Node> nodeList=new ArrayList<>();
        for (T1doBoard t1doBoard : t1doBoards) {
            nodeList.add(Node.getNode(t1doBoard));
        }
      
        List<Node> list = new ArrayList<>();
        // 遍历节点列表
        for (Node node : nodeList) {
            if (node.getParent_id() == 0) {
                // parentID为0（根节点）作为入口
                node.setChildren(getChildrenNode(node.getId(), nodeList));
                list.add(node);
            }
        }
        // 排序
        list.sort(new NodeOrderComparator());
        return list;
    }

    // 获取子节点的递归方法
    public static List<Node> getChildrenNode(Long id, List<Node> nodeList) {
        List<Node> lists = new ArrayList<>();
        if(id==972) {
        	System.out.println(111);
        	System.out.println(nodeList.size());
        }
        for (Node node : nodeList) {
            if (node.getParent_id().equals(id)) {
                // 递归获取子节点
            	
                node.setChildren(getChildrenNode(node.getId(), nodeList));
                lists.add(node);
            }
        }
        // 排序
        lists.sort(new NodeOrderComparator());
        return lists;
    }

}
