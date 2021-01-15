package com.luqi.model;

import com.alibaba.fastjson.JSONArray;
import com.luqi.common.model.T1doBoard;
import com.luqi.util.node.Node;

import kafka.utils.json.JsonArray;

import java.util.List;

/**
 * @ClassName ProjectModel
 * @Description
 * @auther Sherry
 * @date 2019/7/24 6:53 PM
 */
public class ProjectModel {

    private Long id;
    //项目名
    private String name;
    //父节点id
    private Long parentId;
    //上级目录
    private List<T1doBoard> projectItem;
    //创建时间
    private Long createTime;
    //拟完成时间
    private Long finishTime;
    //干系人
    private List<ResPersonModel> stakeHolder;
    //项目子节点
    private JSONArray nodeList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<T1doBoard> getProjectItem() {
        return projectItem;
    }

    public void setProjectItem(List<T1doBoard> projectItem) {
        this.projectItem = projectItem;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }


    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Long finishTime) {
        this.finishTime = finishTime;
    }

    public List<ResPersonModel> getStakeHolder() {
        return stakeHolder;
    }

    public void setStakeHolder(List<ResPersonModel> stakeHolder) {
        this.stakeHolder = stakeHolder;
    }

    public JSONArray getNodeList() {
        return nodeList;
    }

    public void setNodeList(JSONArray nodeList) {
        this.nodeList = nodeList;
    }
}
