package com.luqi.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.template.stat.ast.If;
import com.luqi.common.model.*;
import com.luqi.dao.LogDao;
import com.luqi.model.BoardTemp;
import com.luqi.model.ProjectModel;
import com.luqi.model.ProjectProgressModel;
import com.luqi.util.HttpUtil;
import com.luqi.util.MsgUtil;
import com.luqi.util.UrlUtil;
import com.luqi.util.node.InfiniteLevelTreeUtil;
import com.luqi.util.node.Node;

import javax.servlet.http.HttpSession;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @ClassName BoardService
 * @Description 项目看板
 * @auther Sherry
 * @date 2019/7/24 1:57 PM
 */
public class BoardService {


    /**
     * 插入节点
     *
     * @param item
     * @return
     */
    public static void addItem(T1doBoard item, JSONObject user,Long projectId,List<BoardTemp> boardTemps) {
        if (item.getParentId() == null) {
            item.setParentId(0L);
        }
        //插入
        T1doBoard.insertItem(item);
        //若为项目子节点
        if (item.getTYPE() == 2) {
            //修改项目最后更新时间
            T1doProject.updateUpdateTime(new Date(), projectId);
        }
        //插入临时记录表(2-修改)
        boardTemps.add(new BoardTemp(item, 1));
        //插入历史记录（1-新增）
        T1doBoardLog.insertT1doBoardLog(item, user.getString("loginName"), 1);
    }

    /**
     * 项目列表新建看板
     *
     * @param item
     * @param user
     * @param type 0-空白，1-模版
     * @return
     */
    public static JSONObject addProjectBoard(T1doBoard item, JSONObject user, int type) {
        //插入项目
        //新建看板时父id为0
        item.setParentId(1l);
        //类型为3-项目
        item.setTYPE(3);
        //插入
        T1doBoard.insertItem(item);
        //插入项目表
        T1doProject t1doProject = new T1doProject();
        t1doProject.setItemId(item.getID());
        t1doProject.setOCreateTime(new Date());
        t1doProject.setNAME(item.getItemName());
        t1doProject.setCrateUser(user.getString("loginName"));
        t1doProject.save();
        //插入历史记录（1-新增）
        T1doBoardLog.insertT1doBoardLog(item, user.getString("loginName"), 1);
        //若为模版，则插入模版
        if (type == 1) {
            //模版
            Map<String, String[]> stencil = new HashMap<>();
            String[] children = {"项目管理", "技术实施"};
            stencil.put("原则", children);
            children = new String[]{"需求梳理", "预算确定", "任务分解", "招标采购", "项目实施与对接", "验收", "评价"};
            stencil.put("步骤", children);
            children = new String[]{"工作内容", "预算分解"};
            stencil.put("分工", children);
            for (String key : stencil.keySet()) {
                long parentId = addBoard(key, item.getID());
                String[] value = stencil.get(key);
                for (String name : value) {
                    addBoard(name, parentId);
                }
            }
        }
        return getProjectChildNode(0,user, item.getID());
    }

    //插入节点
    private static long addBoard(String name, long parentId) {
        T1doBoard t1doBoard = new T1doBoard();
        t1doBoard.setItemName(name);
        t1doBoard.setParentId(parentId);
        //项目子分类
        t1doBoard.setTYPE(2);
        t1doBoard.save();
        return t1doBoard.getID();
    }

    /**
     * 修改节点
     *
     * @param item
     * @param user
     */
    public static void updateItem(T1doBoard item, JSONObject user,Long projectId, List<BoardTemp> boardTemps) {
        T1doBoard t1doBoard = T1doBoard.getItemById(item.getID());
        //1do节点，查询不到
        if (t1doBoard == null) {
            T1doProject1do.updateItemId(item.getParentId(), item.getID()+"");
        } else {
            item.setTYPE(t1doBoard.getTYPE());
            //修改
            item.update();
            //插入临时记录表(2-修改)
            boardTemps.add(new BoardTemp(t1doBoard, 2));
            //插入历史记录（2-修改）
            T1doBoardLog.insertT1doBoardLog(t1doBoard, user.getString("loginName"), 2);
        }
        //若为项目子节点
        if (item.getTYPE() == 2) {
            //修改项目最后更新时间
            T1doProject.updateUpdateTime(new Date(), projectId);
        }
    }

    /**
     * 删除节点
     *
     * @param item
     * @param user
     */
    public static void deleteItem(T1doBoard item, JSONObject user, Long projectId, List<BoardTemp> boardTemps) {
        //插入
        item.delete();
        //插入临时记录表(3-删除)
        boardTemps.add(new BoardTemp(item, 3));
        //插入历史记录(3-删除)
        T1doBoardLog.insertT1doBoardLog(item, user.getString("loginName"), 3);
        //若为项目子节点
        if (item.getTYPE() == 2) {
            //修改项目最后更新时间
            T1doProject.updateUpdateTime(new Date(), projectId);
        }
    }


    /**
     * 新增项目
     *
     * @param projectModel
     * @param user
     */
    public static void addProject(ProjectModel projectModel, JSONObject user) {
        //项目插入看板表
        T1doBoard t1doBoard = new T1doBoard();
        t1doBoard.setItemName(projectModel.getName());
        t1doBoard.setParentId(projectModel.getParentId());
        //项目类型为3
        t1doBoard.setTYPE(3);
        T1doBoard.insertItem(t1doBoard);
        //插入项目表
        T1doProject t1doProject = new T1doProject();
        t1doProject.setItemId(t1doBoard.getID());
        t1doProject.setOCreateTime(new Date());
        if (projectModel.getFinishTime() != null) {
            t1doProject.setOFinishTime(new Date(projectModel.getFinishTime()));
        }
        t1doProject.save();
        //插入项目干系人关联表
        T1doProjectStakeholder.batchSave(projectModel.getStakeHolder(), t1doBoard.getID());
        //插入历史记录（1-新增）
        T1doBoardLog.insertT1doBoardLog(t1doBoard, user.getString("loginName"), 1);
        if (projectModel.getNodeList() != null && projectModel.getNodeList().size() > 0) {
            addChildren(projectModel.getNodeList(), t1doBoard.getID());
        }
    }

    /**
     * 新增子节点
     *
     * @param nodes
     * @param parentId
     */
    private static void addChildren(JSONArray nodes, long parentId) {
        for (int i = 0; i < nodes.size(); i++) {
            JSONObject jsonObject = nodes.getJSONObject(i);
            T1doBoard t1doBoard = new T1doBoard();
            t1doBoard.setParentId(parentId);
            t1doBoard.setItemName(jsonObject.getString("item_name"));
            t1doBoard.setTYPE(2);
            t1doBoard.save();
            if (jsonObject.getJSONArray("children") != null && jsonObject.getJSONArray("children").size() > 0) {
                addChildren(jsonObject.getJSONArray("children"), t1doBoard.getID());
            }
        }
    }

    /**
     * 修改项目
     *
     * @param projectModel
     * @param user
     * @return
     */
    public static void updateProject(ProjectModel projectModel, JSONObject user) {
        //项目修改看板表
        T1doBoard t1doBoard = new T1doBoard();
        t1doBoard.setID(projectModel.getId());
        t1doBoard.setItemName(projectModel.getName());
        t1doBoard.setParentId(projectModel.getParentId() == null ? 0 : projectModel.getParentId());
        //项目类型为3
        t1doBoard.setTYPE(3);
        t1doBoard.update();
        //修改项目表
        T1doProject t1doProject = new T1doProject();
        t1doProject.setItemId(t1doBoard.getID());
        if (projectModel.getFinishTime() != null) {
            t1doProject.setOFinishTime(new Date(projectModel.getFinishTime()));
        }
        t1doProject.update();
        //删除项目干系人
        T1doProjectStakeholder.deleteByProjectId(t1doBoard.getID());
        //插入项目干系人关联表
        T1doProjectStakeholder.batchSave(projectModel.getStakeHolder(), t1doBoard.getID());
        //插入历史记录(2-修改)
        T1doBoardLog.insertT1doBoardLog(t1doBoard, user.getString("loginName"), 2);
    }

    /**
     * 删除项目
     *
     * @param project
     * @param user
     */
    public static void deleteProject(T1doBoard project, JSONObject user) {
        //删除看板表(暂时不删除项目表和干系人表)
        project.delete();
        //插入历史记录(3-删除)
        T1doBoardLog.insertT1doBoardLog(project, user.getString("loginName"), 3);
    }

    /**
     * 获取项目分类
     *
     * @return
     */
    public static JSONObject getClassification(int hide, JSONObject user) {
        List<Record> items = T1doBoard.getAllItems(1);
        /*//重点项目
        List<Record> keyProjects = T1doProject.getKeyProjects();
        //父id为重点项目的a
        keyProjects.forEach(item -> {
            item.set("parentid", "-1");
            item.set("isKey", true);
        });*/
        /*//权限POWER=1整理层，POWER=2领导，POWER=3普通用户
        int power = user.getInteger("POWER");
        //只有POWER=1整理层和POWER=2领导可以看到全部
        if ((power == 1 || power == 2)) {
            //获取所有项目分类节点
            items = T1doBoard.getAllItems(1);
        } else {
            //获取该人的所有项目，干系人可以查看
            List<T1doProjectStakeholder> projects = T1doProjectStakeholder.getProjectsByUser(user.getString("loginName"));
            //获取该人所有与项目相关的1do
            List<T1doProject1do> doItems = T1doProject1do.getItemsByShowId(user.getString("loginName"));
            //获取1do对应的项目
            List<T1doBoard> doProjects = new ArrayList<>();
            for (T1doProject1do doItem : doItems) {
                doProjects.addAll(T1doBoard.getItems(doItem.getItemId()));
            }
            //存放所有的项目id
            HashSet<Long> projectIds = new HashSet<>();
            if (projects != null && projects.size() > 0) {
                for (T1doProjectStakeholder stakeholder : projects) {
                    projectIds.add(stakeholder.getItemId());
                }
            }
            if (doProjects.size() > 0) {
                for (T1doBoard doBoard : doProjects) {
                    if (doBoard.getTYPE() == 3) {
                        projectIds.add(doBoard.getID());
                    }
                }
            }
            if (projectIds.size() > 0) {
                //重点项目
                List<Record> newKeyProjects = new ArrayList<>();
                //查询项目的相关项目分类
                for (long projectId : projectIds) {

                    items.addAll(T1doBoard.getAllParents(projectId, 1));
                    for (Record record : keyProjects) {
                        if (projectId + 100000000 == record.getLong("id")) {
                            newKeyProjects.add(record);
                            break;
                        }
                    }
                }
                //删除重复数据
                HashSet h = new HashSet(items);
                items.clear();
                items.addAll(h);
                keyProjects = newKeyProjects;
            }
        }*/
        /*boolean flag = false;
        if (items.size() > 0 && keyProjects.size() > 0) {
            items.addAll(keyProjects);
            flag = true;
        }
        items.forEach(item -> {
            if (T1doBoard.getIsFinish(item.getLong("id"))) {
                item.set("isFinish", T1doBoard.getIsFinish(item.getLong("id")));
            }
        });
        //若隐藏办结项目
        if (hide == 1) {
            items = items.stream().filter(item -> (item.getBoolean("isFinish") == null || !item.getBoolean("isFinish"))).collect(Collectors.toList());
        }
        //项目总览节点
        List<Record> records = items.stream().filter(item -> item.getInt("type") == 1).collect(Collectors.toList());*/
        for (int i = 0; i < items.size(); i++) {
            Record record = items.get(i);
            //若为项目,父节点标记为有项目的节点
            if (record.getInt("type") == 3) {
                //判断是否是重点项目
                record.set("isKey", T1doProject.isKeyT1doProjectById(record.getLong("id")));
                //项目增加计算完成情况
                List<Record> projectChildren = T1doBoard.getProjectChildren(record.getLong("id"), 0);
                if (projectChildren != null && projectChildren.size() > 0) {
                    List<Record> completion = projectChildren.stream().filter(item -> "已完成".equals(item.getStr("COMPLETION"))).collect(Collectors.toList());
                    record.set("completeTask", completion.size());
                    record.set("allTask", projectChildren.size());
                    record.set("rate", Math.round(((float) completion.size()) * 100 / projectChildren.size()));
                } else {
                    record.set("completeTask", 0);
                    record.set("allTask", 0);
                    record.set("rate", 0);
                }
            } else {
                //不是项目，去除isComplete节点
                record.remove("isComplete");
            }


            /*//若为项目，且未办结
            if (record.getInt("type") == 3 && (record.getBoolean("isFinish") == null || !record.getBoolean("isFinish"))) {
                //已过拟办结时间
                if (record.getDate("finishTime") != null && record.getDate("finishTime").before(new Date())) {
                    record.set("timeout", 1);
                }
            } else {
                record.set("timeout", 0);
            }
            //record.remove("isFinish");
            record.remove("finishTime");*/

                if (record.getLong("parentid") == 0) {
                    record.set("isroot", true);
                    record.remove("parentid");
                } else {
                    record.set("direction", "right");
                }
            /*if (record.getStr("parentid") != null && !record.getStr("parentid").equals("-1")) {
                record.set("isKey", T1doProject.isKeyT1doProjectById(Long.valueOf(record.getStr("id"))));
            }*/

        }

        if (items.size() == 0) {items.add(getModel());}

        JSONObject res = new JSONObject();
        res.put("items", items);
        //res.put("power", power);
        return res;
    }

    /**
     * 获取项目子节点
     * @param hide 
     *
     * @return
     */
    public static JSONObject getProjectChildNode(Integer hide, JSONObject user, long id) {
        //重点项目处理
/*    	if(id>10000000) {
    		id=Long.valueOf(String.valueOf(id).substring(1));
    	}*/
        //获取所有项目子节点
        List<Record> items = T1doBoard.getProjectChildren(id,hide);
        
        //权限POWER=1整理层，POWER=2领导，POWER=3普通用户
       // int power = user.getInteger("POWER");
        int power =1;
        //查询1do
        //所有节点的id
        StringBuilder ids = new StringBuilder(id + "");
        for (Record record : items) {
            ids.append(",").append(record.getStr("id"));
        }
        // List<Record> dos = T1doProject1do.getProject1do(ids.toString(), user.getString("loginName"));
        List<Record> dos = T1doProject1do.getProject1do2(ids.toString(),hide);
        //若为普通用户
        /*if (power == 3) {
            //判断是否是干系人
            T1doProjectStakeholder stakeholder = T1doProjectStakeholder.isExist(id, user.getString("loginName"));
            if (stakeholder == null) {
            	items.clear();
            	dos=T1doProject1do.getProject1do(ids.toString(), user.getString("loginName"));
                for (Record record : dos) {
                    //查询上级
                    items.addAll(T1doBoard.getAllParents(record.getLong("parentid"), 2));

                }
                items.stream().filter(item -> item.getInt("type") != 3).collect(Collectors.toList());
                //去重
                HashSet h = new HashSet(items);
                items.clear();
                items.addAll(h);
            } else {
                //干系人为4
                power = 4;
            }
        }*/
        int i = 0;
        for (Record record : dos) {
            //判断状态(5-已办结)，若未办结并已超出时间，则为超时
            /*if (record.getInt("status") != null && record.getInt("status") != 5 && record.getDate("finishTime")!=null && record.getDate("finishTime").before(new Date())) {
                record.set("timeout", 1);
            } else {
                record.set("timeout", 0);
            }*/
            record.remove("finishTime");
            record.set("parentid", record.getLong("parentid") + "");
            i++;
            //插入type为4
            record.set("type", 4);
        }

        //加上方向字段
        items.forEach(item -> {
                    //干系人
                    List<Record> stakeholders = T1doProjectStakeholder.getUserByProjectId(item.getLong("id"));
                    item.set("stakeholders", stakeholders);
                    item.set("direction", "right");
                    //展示id
                    item.set("showId", "【需求ID" + (item.getLong("id") + 100000) + "】");
                }
        );
        items.addAll(dos);
        //获取项目
        Record project = T1doBoard.getProjectById(id);
        project.set("type", 5);
        project.set("isroot", true);
        //干系人
        List<Record> stakeholders = T1doProjectStakeholder.getUserByProjectId(id);
        project.set("stakeholders", stakeholders);

        items.add(project);
        JSONObject res = new JSONObject();
        res.put("items", items);
        res.put("power", power);
        res.put("itemId", id);
        res.put("isKey", project.getBoolean("isKey"));
        return res;
    }

    /**
     * 设置为重点项目
     *
     * @param id
     */
    public static String setKeyProject(long id) {
        //获取对应项目
        T1doProject t1doProject = T1doProject.getT1doProjectById(id);
        //若已是重点项目则取消，不是则标记为重点项目
        String str = "设置重点项目成功";
        if (t1doProject.getIsKey()) {
            T1doProject.setKeyProject(id, 0);
            str = "取消重点项目成功";
        } else {
            T1doProject.setKeyProject(id, 1);
        }
        return str;
    }



    /**
     * 设置为办结
     *
     * @param id
     */
    public static void setFinish(long id) {
        T1doProject.setFinish(id);
    }

    /**
     * 获取所有项目分类
     *
     * @return
     */
    public static List<Node> getProjectItems() {
        //查询项目分类
        List<T1doBoard> t1doBoardList = T1doBoard.getAllItem(1);
        return InfiniteLevelTreeUtil.getStartLevelTree(t1doBoardList);

    }

    /**
     * 获取所有项目分类
     *
     * @return
     */
    public static List<Node> getProjectItems1() {
        //查询项目分类
        List<T1doBoard> t1doBoardList = T1doBoard.getAllItem1(1);
        return InfiniteLevelTreeUtil.getStartLevelTree(t1doBoardList);

    }

    /**
     * 获取项目（编辑项目）
     *
     * @param id
     * @return
     */
    public static Record getProject(long id) {
        //获取项目信息
        Record record = T1doProject.getProject(id);
        //将时间改为时间戳
        if (record.getDate("finishTime") != null) {
            record.set("finishTime", record.getDate("finishTime").getTime());
        }
        if (record.getDate("createTime") != null) {
            record.set("createTime", record.getDate("createTime").getTime());
        }
        //获取项目分类
        List<Record> items = T1doBoard.getAllParents(id, 1);
        record.set("items", items);
        //获取项目子节点
        List<T1doBoard> children = T1doBoard.getChildren(id);
        children.add(T1doBoard.getItemById(id));
        //将所有的项目的子节点形成树形结构
        List<Node> nodeList = InfiniteLevelTreeUtil.getInfiniteLevelTree(children);
        record.set("children", nodeList);
        //项目干系人
        List<Record> stakeholders = T1doProjectStakeholder.getUserByProjectId(id);
        record.set("stakeholders", stakeholders);
        return record;
    }

    /**
     * 更新项目分类（思维导图）
     *
     * @param user
     * @param oldData
     * @param newData
     * @param type    1-项目分类， 2-项目子节点
     */
/*    public static void updateProjectItems(JSONObject user, JSONArray oldData, JSONArray newData, int type) {
        //若旧数组长，说明为删除
        if (oldData.size() > newData.size()) {
            for (int i = 0; i < oldData.size(); i++) {
                JSONObject old;
                String str = oldData.getObject(i, Record.class).toJson();
                if (str.equals("{}")) {
                    old = oldData.getJSONObject(i);
                } else {
                    old = JSON.parseObject(str);
                }
                //删除标志（true-删除，false-不是删除）
                boolean flag = true;
                for (int j = 0; j < newData.size(); j++) {
                    //若新数组中存在这个id，则不是删除
                    if (old.getLong("id") == newData.getJSONObject(j).getLong("id")) {
                        flag = false;
                        break;
                    }
                }
                //若为删除，则去数据库删除这条数据
                if (flag) {
                    //获取时候存在
                    T1doBoard item = T1doBoard.getItemById(old.getLong("id"));
                    if (item == null) {
                        return;
                    }
                    deleteItem(item, user,);
                    return;
                }
            }
            //若新数组长，则为新增
        } else if (oldData.size() < newData.size()) {
            for (int i = 0; i < newData.size(); i++) {
                JSONObject itemModel = newData.getJSONObject(i);
                //新增标志（true-新增，false-不是新增）
                boolean flag = true;
                for (int j = 0; j < oldData.size(); j++) {
                    JSONObject old;
                    String str = oldData.getObject(j, Record.class).toJson();
                    if (str.equals("{}")) {
                        old = oldData.getJSONObject(j);
                    } else {
                        old = JSON.parseObject(str);
                    }
                    //若旧数组中存在这个id，则不是新增
                    if (itemModel.getLong("id") == old.getLong("id")) {
                        flag = false;
                    }
                }
                //若为新增，则去数据库新增这条数据
                if (flag) {
                    T1doBoard item = new T1doBoard();
                    item.setID(itemModel.getLong("id"));
                    item.setTYPE(type);
                    item.setItemName(itemModel.getString("topic"));
                    item.setParentId(itemModel.getLong("parentid"));
                    addItem(item, user);
                    return;
                }
            }
            //若等长，则为修改
        } else {
            for (int i = 0; i < newData.size(); i++) {
                JSONObject itemModel = newData.getJSONObject(i);
                for (int j = 0; j < oldData.size(); j++) {
                    JSONObject old;
                    String str = oldData.getObject(j, Record.class).toJson();
                    if (str.equals("{}")) {
                        old = oldData.getJSONObject(j);
                    } else {
                        old = JSON.parseObject(str);
                    }
                    if (itemModel.getLong("id") == old.getLong("id")) {
                        //若id相同，但内容和父节点不同，则为修改
                        if (!itemModel.getString("topic").equals(old.getString("topic")) || (itemModel.getLong("parentid") != old.getLong("parentid"))) {
                            T1doBoard item = T1doBoard.getItemById(itemModel.getLong("id"));
                            if (item == null) {
                                return;
                            }
                            item.setItemName(itemModel.getString("topic"));
                            item.setParentId(itemModel.getLong("parentid") == null ? 0 : itemModel.getLong("parentid"));
                            updateItem(item, user);
                            return;
                        }
                    }
                }

            }
        }
    }*/

    /**
     * 获取每日项目公司日报报送时间
     * @param projectId
     * @param date
     * @return
     */
    public static List<Record> getCompanyLogTimes(long projectId, String date) {
        //获取报送时间列表
        List<Record> times = T1doBoardDaliyReport.getCompanyLogTimeList(projectId, date);
        for (Record record : times) {
            //时间去掉年月日
            Date time = record.getDate("time");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            record.set("time", simpleDateFormat.format(time));
            String[] depts = record.getStr("companyName").split("●");
            record.set("companyName", getCompanyName(depts));
        }
        return times;
    }

    /**
     * 获取报告标题，及所有时间
     */
    public static Record getTitle(T1doBoard project) {
        Record res = new Record();
        res.set("title", project.getItemName() + "项目进展情况汇报");
        List<Record> dates = T1doBoardDaliyReport.getProjectReportDate(project.getID());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Record record : dates) {
            Date date = record.getDate("date");
            record.set("date", simpleDateFormat.format(date));
        }
        res.set("dates", dates);
        return res;
    }


    /**
     * 获取日报字数
     * @param projectId
     * @param date
     * @return
     */
    public static List<Record> getLogNumber(long projectId, String date) {
        //获取报送字数列表
        List<Record> numbers = T1doBoardDaliyReport.getLogNumber(projectId, date);
        for (Record record : numbers) {
            String[] depts = record.getStr("companyName").split("●");
            record.set("companyName", getCompanyName(depts));
        }
        return numbers;
    }

    /**
     * 修改/新增报告备注
     * @param t1doBoardRemark
     */
    public static void updateRemarks(T1doBoardRemark t1doBoardRemark) {
        //获取对象
        T1doBoardRemark old = T1doBoardRemark.getInstance(t1doBoardRemark);
        //若存在修改，不存在新增
        if (old == null) {
            t1doBoardRemark.save();
        } else {
            t1doBoardRemark.setID(old.getID());
            t1doBoardRemark.update();
        }
    }

    /**
     * 获取日报备注接口
     * @param projectId
     * @param date
     */
    public static List<T1doBoardRemark> getReportRemarks(long projectId, String date) {
        List<T1doBoardRemark> t1doBoardRemarks = T1doBoardRemark.getProjectRemarks(projectId, date);
        //空余部分加上
        if (t1doBoardRemarks.size() < 4) {
            for (int i = 1; i <= 4; i++) {
                boolean flag = false;//标记，无-false
                for (T1doBoardRemark remark : t1doBoardRemarks) {
                    if (remark.getTYPE() == i) {
                        flag = true;
                        break;
                    }
                }
                //若不存在
                if (!flag) {
                    T1doBoardRemark t1doBoardRemark = new T1doBoardRemark();
                    t1doBoardRemark.setCONTENT("");
                    t1doBoardRemark.setTYPE(i);
                    t1doBoardRemarks.add(t1doBoardRemark);
                }
            }
        }
        return t1doBoardRemarks;
    }

    /**
     * 修改/新增报告备注
     * @param t1doBoardTaskRemarks
     */
    public static void updateEvaluate(T1doBoardTaskRemarks t1doBoardTaskRemarks) {
        //获取对象
        T1doBoardTaskRemarks old = T1doBoardTaskRemarks.getInstance(t1doBoardTaskRemarks.getTaskId(), t1doBoardTaskRemarks.getCompanyId());
        //若存在修改，不存在新增
        if (old == null) {
            t1doBoardTaskRemarks.save();
        } else {
            t1doBoardTaskRemarks.setID(old.getID());
            t1doBoardTaskRemarks.update();
        }
    }

    /**
     * 获取项目公司日志接口
     * @param task
     * @return
     */
    public static List<Record> getLogs(T1doBoardTask task) {
        //拼接公司showId
        StringBuilder companyShowId = new StringBuilder();
        if (StrKit.notBlank(task.getPrincipleShowId())) {
            String[] companies = task.getPrincipleShowId().split(",");
            if (StrKit.notBlank(companies[0])) {
                companyShowId.append("'").append(companies[0]).append("'");
            }
            for (int i = 1; i < companies.length; i++) {
                if (StrKit.notBlank(companies[i])) {
                    companyShowId.append(",'").append(companies[i]).append("'");
                }
            }
        }
        List<Record> recordList = T1doBoardDaliyReport.getLogByCompany(task.getProjectId(), task.getDATE(), task.getItemId(), companyShowId.toString());
        for (Record record : recordList) {
            String[] depts = record.getStr("companyName").split("●");
            record.set("companyName", getCompanyName(depts));
        }
        return recordList;
    }

    /**
     * 任务关联日志接口
     * @param taskId
     * @param logId
     */
    public static void taskLinkedLogs(long taskId,  Long itemId,long logId) throws Exception {
        T1doBoardDaliyReport t1doBoardDaliyReport = T1doBoardDaliyReport.dao.findById(logId);
        T1doBoardDaliyReport report;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("workRecordId", t1doBoardDaliyReport.getReportId());
        if (t1doBoardDaliyReport.getTaskId() == null || t1doBoardDaliyReport.getTaskId() == 0) {
            T1doBoardDaliyReport.linkedTask(itemId, logId);
            //关联日志和取消关联日志操作报告  1关联2取消关联
             updateTask(taskId, 1);
            report = T1doBoardDaliyReport.getInstanceById(logId);
            jsonObject.put("projectId", report.getProjectId());
            jsonObject.put("projectName", report.getStr("projectName"));
            jsonObject.put("taskId", report.getTaskId());
            jsonObject.put("taskName", report.getStr("taskName"));
        } else {
            T1doBoardDaliyReport.linkedTask(null, logId);
            //关联日志和取消关联日志操作报告  1关联2取消关联
            updateTask(taskId, 2);
        }
        String result = HttpUtil.doPost(PropKit.get("logUrl")+ "/join/fixProjectTask", jsonObject);
        JSONObject res = JSON.parseObject(result);
        if (res.getInteger("code") != 2000) {
            LogKit.error("任务挂接日志返回日志系统不成功");
        }
    }

    /**
     * 获取任务进展计划
     * @param projectId
     * @param date
     * @return
     */
    public static List<JSONObject> getProjectProgress(long projectId, String date) {
        List<Record> list = T1doBoardTask.getProjectProgress(projectId, date);
        List<JSONObject> res = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Record record : list) {
                boolean flag = false;//标记公司是否存在，是-true
                for (JSONObject object : res) {
                    boolean flag1 = false;//标记是否为同一个任务，是-true
                    if (object.getString("company").equals(record.getStr("D_NAME"))) {
                        flag = true;
                        List<ProjectProgressModel> projectProgressModels = (List<ProjectProgressModel>) object.get("table");
                        for (ProjectProgressModel model : projectProgressModels) {
                            //同一个任务合并
                            if (model.getTaskId().equals(record.getLong("ID"))) {
                                model.getLogs().add(record.getStr("CONTENT"));
                                flag1 = true;
                                break;
                            }
                        }
                        if (!flag1) {
                            List<String> logs = new ArrayList<>();
                            logs.add(record.getStr("CONTENT"));
                            ProjectProgressModel projectProgressModel = new ProjectProgressModel(record.getLong("ID"),record.getStr("TASK"), record.getStr("PLANNED_DATE"), logs, record.getStr("evaluate"),record.getStr("completion"));
                            projectProgressModels.add(projectProgressModel);
                        }
                        break;
                    }
                }
                //不存在则新建
                if (!flag) {
                    JSONObject object = new JSONObject();
                    object.put("company", record.getStr("D_NAME"));
                    object.put("principle", record.getStr("principle"));
                    object.put("companyShowId", record.getStr("COMPANY"));
                    ArrayList<ProjectProgressModel> progressModels = new ArrayList<>();
                    List<String> logs = new ArrayList<>();
                    logs.add(record.getStr("CONTENT"));
                    ProjectProgressModel projectProgressModel = new ProjectProgressModel(record.getLong("ID"),record.getStr("TASK"), record.getStr("PLANNED_DATE"), logs, record.getStr("evaluate"),record.getStr("completion"));
                    progressModels.add(projectProgressModel);
                    object.put("table", progressModels);
                    res.add(object);
                }

            }
        }
        return res;
    }

    /**
     * 日志系统挂接日志
     * @param json
     */
    public static void updateLog(JSONObject json) {
        //日志id
        Long reportId = json.getLong("WorkRecordId");
        //删除原数据
        T1doBoardDaliyReport.deleteByReportId(reportId);
        //若为删除，不需要新增
        String op = json.getString("op");
        if (StrKit.notBlank(op) && op.equals("delete")) {
            return;
        }
        //内容
        String content = json.getString("WorkRecordName");
        //插入时间
        Date time = json.getDate("msgAddTime");
        //日志日期
        Date date = json.getDate("WorkRecordDate");
        //账号
        String account = json.getString("account");
        //项目任务列表
        JSONArray projectTaskList = json.getJSONArray("projectTask");
        //现是一对一
        JSONObject projectTask = projectTaskList.getJSONObject(0);
        //项目id
        Long projectId = projectTask.getLong("projectId");
        //任务id
        Long taskId = projectTask.getLong("taskId");
        //获取人员的showID
        TRegUser user = TRegUser.getUserByLoginName(account);
        //获取公司
        String company = T1doProjectStakeholder.getCompanyByUser(user.getShowId());
        //先删除原有数据

        //插入数据
        T1doBoardDaliyReport daliyReport = new T1doBoardDaliyReport();
        daliyReport.setCONTENT(content);
        daliyReport.setTIME(time);
        daliyReport.setDATE(date);
        daliyReport.setNUMBER(content.length());
        daliyReport.setCOMPANY(company);
        daliyReport.setProjectId(projectId);
        daliyReport.setTaskId(taskId);
        daliyReport.setReportId(reportId);
        daliyReport.save();
    }

    /**
     * 　* 描述：
     * 　* 创建人：coco
     * 　* 创建时间：2019年8月15日 上午9:42:43
     */
    public static Record getModel() {
        Record r = new Record();
        r.set("isKey", false);
        r.set("topic", "下城项目");
        r.set("isroot", true);
        r.set("id", "1");
        r.set("type", 1);
        r.set("timeout", 0);

        return r;
    }


    /**
     * 根据群名获取项目及任务
     * @return
     */
    public static Record getProjectsByGroup(String groupId) {
        T1doBoard project = T1doBoard.getProjectByGroupId(groupId);
        if (project == null) {
            return null;
        } else {
            List<Record> tasks = T1doBoard.getProjectChildren(project.getID(),0);
            for (Record record : tasks) {
                record.remove("parentid", "type");
            }
            Record res = new Record();
            res.set("project", project);
            res.set("tasks", tasks);
            return res;
        }
    }


    /**
     * 获取公司名
     * @param depts
     * @return
     */
    private static String getCompanyName(String[] depts) {
        if (depts.length > 0) {
            //取合作公司下一级,若不是合作公司，取末级
            for (int i = 0; i < depts.length; i++) {
                if (depts[i].equals("合作公司")) {
                    return depts[i + 1];
                }
            }
            return depts[depts.length - 1];

        }
        return "";
    }
    /**   
	　* 描述：   关联日志和取消关联日志操作报告  1关联2取消关联
	　* 创建人：coco   
	　* 创建时间：2019年10月31日 下午5:31:19         
	*/
	public static void updateTask(Long taskId,int type) {
		 T1doBoardTask tk = T1doBoardTask.dao.findById(taskId);
		 if(type==1) {
			 //如果未关联
			 if(!tk.getIsRelation()) {
				 if(tk.getCOMPLETION().equals("已完成")) {
					 String str=tk.getBTEMP();
					 tk.setBTEMP(tk.getDTEMP()).setDTEMP(str).setIsRelation(true).update();
				 }else {
					 String str=tk.getDATA();
					 tk.setDATA(tk.getDTEMP()).setDTEMP(str).setIsRelation(true).update();
				 }
			 }
		 }else {
			 int i=Db.queryInt("select count(*) from t_1do_board_daliy_report where date=? and TASK_ID=?",tk.getDATE(),tk.getItemId());
			 //没有关联了
			 if(i==0) {
				 if(tk.getCOMPLETION().equals("已完成")) {
					 String str=tk.getBTEMP();
					 tk.setBTEMP(tk.getDTEMP()).setDTEMP(str).setIsRelation(false).update();
				 }else {
					 String str=tk.getDATA();
					 tk.setDATA(tk.getDTEMP()).setDTEMP(str).setIsRelation(false).update();
				 }
			 }
		 }

	}

    /**
     * 节点撤销
     * @param boardTemps
     */
    public static void undoNode(List<BoardTemp> boardTemps) {
        BoardTemp boardTemp = boardTemps.get(boardTemps.size() - 1);
        switch (boardTemp.getType()){
            //新增
            case 1 : boardTemp.getT1doBoard().delete();
                boardTemps.remove(boardTemp);
                break;
            //修改
            case 2 : T1doBoard.update(boardTemp.getT1doBoard());
                boardTemps.remove(boardTemp);
                break;
            //删除
            case 3 : boardTemp.getT1doBoard().save();
                boardTemps.remove(boardTemp);
                break;
        }
    }

    /**
     * 项目总览节点关联项目
     * @param id
     * @param projectIds
     * @return
     */
    public static void relatedProject(long id, List<Long> projectIds) {
        for (Long projectId : projectIds) {
            //将项目节点的父id修改
            T1doBoard item = new T1doBoard();
            item.setID(projectId);
            item.setParentId(id);
            item.update();
        }
    }

    /**
     * 项目总览节点取消关联项目
     * @param t1doBoard
     */
    public static void unRelatedProject(T1doBoard t1doBoard) {
        t1doBoard.setParentId(1L);
        t1doBoard.update();
    }

    /**
     * 获取项目进展图
     * @param projectId
     * @return
     */
    public static List<T1doBoardTaskProgress> getProjectTrend(long projectId) {
        return T1doBoardTaskProgress.getProgressListByProjectId(projectId);
    }
}


