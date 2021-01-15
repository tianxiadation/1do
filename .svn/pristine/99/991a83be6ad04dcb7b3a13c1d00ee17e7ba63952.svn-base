package com.luqi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.luqi.common.model.TRegCompanyDept;
import com.luqi.common.model.TRegUser;
import com.luqi.common.model.TRegUserDept;
import com.luqi.token.Jwt;
import com.luqi.util.HttpUtil;
import com.luqi.util.JsonUtil;
import com.luqi.util.Md5Util;
import com.luqi.util.MsgUtil;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.luqi.util.UrlUtil;

import org.apache.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpMethod;

import java.io.UnsupportedEncodingException;
import java.util.*;



/**
 * @author xiexingxing
 * @Created by 2020-08-18 17:17.
 */

public class SSOController extends Controller {


    /**
     * 密钥
     */
    private final static String APP_ID = "5c6cba30b2ca402180833e2252e26f7a";
    private final static String APP_SECRET = "46c94d71da959dbc110bd1c4c0ba2cbe74868455";
    private final static String AUTHORYITY_CODE = "person_manager";

    /**
     * 统一用户登录跳转
     * @param
     * @param
     * @throws Exception
     */

    public void ssoCallback()  {
        SortedMap<String, Object> parameters = new TreeMap<>();
        String token = getPara("token");
        String time = getPara("time");
        String sign = getPara("sign");
        parameters.put("client_id", APP_ID);
        parameters.put("token", token);
        parameters.put("time", time);
        boolean isValid = checkSign(parameters, sign);
        if (!isValid) {
            //  非法 请求
            return;
        }
        //根据token 获取用户信息
        //HttpHeaders headers = new HttpHeaders();
        //headers.add("Authorization", "Bearer " + token);
        //HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        //获取用户信息url
       // String getUserInfoUrl = "http://193.168.57.5:8080/auth/account/info";
       String result= HttpUtil.ssoCallback(UrlUtil.getUserInfoUrl, token);	

        if(result==null){
            //获取用户信息失败
            return ;
        }
        JSONObject json = JSON.parseObject(result);
         String username = json.getJSONObject("data").getString("username");

        // User user = personService.getLoginUser(username);

        redirect(UrlUtil.ip+"/1do/index.html#/?token=" + Jwt.getToke(username, "1do"));

    }


    public boolean checkSign(SortedMap<String, Object> parameters, String sign) {
        String encodeSign = createSign(parameters, APP_SECRET);
        return encodeSign.equals(sign);
    }

    /**
     * 根据参数列表 生成签名
     *
     * @param parameters
     * @param key
     * @return
     */
    public String createSign(SortedMap<String, Object> parameters, String key) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);
        try {
            return Md5Util.encode(sb.toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Author coco
     * @Description  //统一用户同步账号
     * @Date 15:41 2020/9/17
     * @Param []
     * @return void
     **/
    public void synchronousAccount(){
        JSONObject json=JsonUtil.getJSONObject(getRequest());
        JSONArray arr= json.getJSONArray("list");
        for (int i = 0; i < arr.size(); i++) {
            JSONObject jsonObject = arr.getJSONObject(i);
            JSONArray brr= jsonObject.getJSONArray("accounts");
            if(brr==null){
                continue;
            }
            for (int i1 = 0; i1 < brr.size(); i1++) {
                JSONObject jsonObject1 = brr.getJSONObject(i1);
                TRegUser tRegUser=TRegUser.dao.findFirst("select * from t_reg_user where show_id=?",jsonObject1.getString("accountCode"));
                if(tRegUser==null){
                    new TRegUser()
                    .setShowId(jsonObject1.getString("accountCode"))
                    .setUTrueName(jsonObject1.getString("nickName"))
                    .setUMail(jsonObject1.getString("email"))
                    .setUMobile(jsonObject1.getString("registerMobile"))
                    .setULoginName(jsonObject1.getString("username")).save();
                }else{
                    tRegUser.setUTrueName(jsonObject1.getString("nickName"))
                            .setUMail(jsonObject1.getString("email"))
                            .setUMobile(jsonObject1.getString("registerMobile"))
                            .setULoginName(jsonObject1.getString("username")).setLoginJson(null).update();

                }
                Db.delete("delete from t_reg_user_dept where U_NAME=?",jsonObject1.getString("accountCode"));
                new TRegUserDept().setUName(jsonObject1.getString("accountCode")).setUDeptId(jsonObject1.getString("organizeId")).save();

            }

        }
        renderJson(MsgUtil.successMsg("同步成功"));

    }
    /**
     * @Author coco
     * @Description  //统一用户同步组织
     * @Date 15:41 2020/9/17
     * @Param []
     * @return void
     **/
    public void synchronousOrg(){
        JSONObject json=JsonUtil.getJSONObject(getRequest());
        JSONArray arr= json.getJSONArray("list");
        for (int i = 0; i < arr.size(); i++) {
            JSONObject jsonObject = arr.getJSONObject(i);
            TRegCompanyDept treg = TRegCompanyDept.dao.findFirst("select * from t_reg_company_dept where show_id=?",jsonObject.getString("id"));
            if(treg==null){
                new TRegCompanyDept().setShowId(jsonObject.getString("id")).setDName(jsonObject.getString("name"))
                        .setDParentidShowId(jsonObject.getString("parentId")).setDPath(jsonObject.getString("path"))
                        .setDPathName(jsonObject.getString("displayPath")).save();
            }else{
                treg.setShowId(jsonObject.getString("id")).setDName(jsonObject.getString("name"))
                        .setDParentidShowId(jsonObject.getString("parentId")).setDPath(jsonObject.getString("path"))
                        .setDPathName(jsonObject.getString("displayPath")).update();
            }
        }
        renderJson(MsgUtil.successMsg("同步成功"));
    }



}