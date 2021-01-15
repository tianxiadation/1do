package com.luqi.util.node;

import java.util.ArrayList;
import java.util.List;

import com.luqi.common.model.T1doBoard;

public class Node {
    private Long id;
    private String item_name;
    private Long parent_id;
    private int type;
    private List<Node> children = new ArrayList<>();
    public static Node getNode(T1doBoard arr){
        return  new Node(arr.getID(),arr.getItemName(),arr.getParentId(),arr.getTYPE());

    }
    
	public Node(Long id, String item_name, Long parent_id, int type) {
		super();
		this.id = id;
		this.item_name = item_name;
		this.parent_id = parent_id;
		this.type = type;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public Long getParent_id() {
		return parent_id;
	}
	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<Node> getChildren() {
		return children;
	}
	public void setChildren(List<Node> children) {
		this.children = children;
	}
	

}
