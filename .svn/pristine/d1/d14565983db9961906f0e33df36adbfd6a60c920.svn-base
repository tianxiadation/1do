package com.luqi.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Record;
import com.luqi.common.model.T1doFeedback;
import com.luqi.common.model.T1doProjectStakeholder;
import com.luqi.interceptor.LoginInterceptor;
import com.luqi.service.BoardService;
import com.luqi.service.RecordService;
import com.luqi.util.JsonUtil;
import com.luqi.util.MsgUtil;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author coco
* @date 2019年9月6日 上午10:10:40
* 
*/
@Before(LoginInterceptor.class)
public class RecordController extends Controller {
    /**
     * @Author Sherry
     * @Description 转日志
     * @Date 3:26 PM 2019/9/26
    */
    public void transferLog() throws Exception {
        JSONObject json= JsonUtil.getJSONObject(getRequest());
        //反馈id
        Integer id = json.getInteger("id");
        if (id == null || id == 0) {
            renderJson(MsgUtil.errorMsg("反馈id不能为空"));
            return;
        }
        //获取反馈消息
        T1doFeedback feedback = T1doFeedback.getInstanceById(id);
        boolean flag;
        //用户
        JSONObject user = getSessionAttr("user");
        //权限控制
        //POWER=1整理层可以操作
        if (user.getInteger("POWER") == 1) {
            flag = RecordService.transferLog(feedback);
            //本人也可以操作
        } else {
            if (user.getString("loginName").equals(feedback.getOUser())) {
                flag = RecordService.transferLog(feedback);
            } else {
                renderJson(MsgUtil.errorMsg("没有权限操作"));
                return;
            }
        }
        if (flag) {
            renderJson(MsgUtil.successMsg("转日志成功"));
        } else {
            renderJson(MsgUtil.errorMsg("转日志失败"));
        }
    }
}
