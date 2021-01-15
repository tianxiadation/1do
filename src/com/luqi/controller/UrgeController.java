package com.luqi.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.luqi.common.model.T1doUrgeWebsocket;
import com.luqi.common.model.TRegUser;
import com.luqi.service.DoService;
import com.luqi.util.JsonUtil;
import com.luqi.util.MsgUtil;

@ServerEndpoint("/websocketForUrge")
public class UrgeController extends Controller{
	//concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<UrgeController> webSocketSet = new CopyOnWriteArraySet<UrgeController>();
    //这个session不是Httpsession，相当于用户的唯一标识，用它进行与指定用户通讯
    private Session session=null;

	@OnOpen
	public void onOpen(Session session) {
		
		System.out.println("Session " + session.getId() + " has opened a connection");
       // try {
            this.session=session;
           // webSocketSet.add(this);     //加入set中
            webSocketSet.add(this);
            //System.out.println(JsonKit.toJson(MsgUtil.successMsg(session.getId())));
            //session.getBasicRemote().sendText(JsonKit.toJson(MsgUtil.successMsg(session.getId())));
           // session.getBasicRemote().send
        /*} catch (IOException ex) {
            ex.printStackTrace();
        } */
	}

	@OnClose
	public void onClose(Session session) {
		// Websocket.updateScoketIsOnline(session.getId());
		T1doUrgeWebsocket.updateOnline(session.getId());
		System.out.println("Session " +session.getId()+" has closed!");
		 webSocketSet.remove(this);  //从set中删除
	       // setSessionAttr(session.getId(), false);
	}
	
	@OnMessage
	public void onMessage(String requestJson, Session session) throws IOException, InterruptedException, EncodeException {				
       //{"type":1,"user":"PQV8oo3jeeiDkLbY"}
		JSONObject json=JSON.parseObject(requestJson);
        json.put("sessionid", session.getId());
        json.toJavaObject(T1doUrgeWebsocket.class).save();
       // T1doUrgeWebsocket u=((JSON) JSON.parseObject(requestJson).put("sessionid", session.getId())).toJavaObject(T1doUrgeWebsocket.class);        
     List<Record> list=  Db.find("select b.SHOW_ID,b.O_DESCRIBE,b.O_CREATE_TIME,b.O_FINISH_TIME,b.LIGHTNING,b.URGENAME,b.URGESHOWID from t_1do_pstatus a,t_1do_base b "
+"where a.SHOW_ID=b.SHOW_ID and a.O_USER=? and a.USER_TYPE=3 and a.isDelete=1 and a.urge_isLook=0 and b.O_STATUS<5 ORDER BY LIGHTNING desc",json.getString("user"));
		session.getBasicRemote().sendText(JsonKit.toJson(MsgUtil.successMsg(list)));
	 
	}	
	public void sendMessage(String SHOW_ID) {
        //群发消息
        for(UrgeController item: webSocketSet){
           
            	List<Record> r=Db.find("select a.sessionid,b.SHOW_ID,b.O_DESCRIBE,b.O_CREATE_TIME,b.O_FINISH_TIME,b.LIGHTNING,b.URGENAME,b.URGESHOWID from t_1do_urge_websocket a,t_1do_base b,t_1do_pstatus c "+
		"where a.user=c.O_USER and b.SHOW_ID=c.SHOW_ID and b.SHOW_ID=? "+
				"and a.isOnline=1 and b.O_STATUS<5 and c.USER_TYPE=3 and c.isDelete=1 and c.urge_isLook=0 and a.sessionid=? ",SHOW_ID,item.session.getId());
            	if(r.size()>0)
					try {
						item.session.getBasicRemote().sendText(JsonKit.toJson(MsgUtil.successMsg(r)));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
						onClose(item.session);
					}
           
        }
        //renderJson(1);
    }
	/**
	66      * 发生错误时调用
	67      * @param session
	68      * @param error*/
	     @OnError
	     public void onError(Session session, Throwable error){
	         System.out.println("发生错误");
	         error.printStackTrace();
	     }
	 /*
	 2019年2月26日 coco 注解：
	*/
	public void getUrge() {
		JSONObject json=JsonUtil.getJSONObject(getRequest());
		if(!json.getString("appkey").equals("zhxxpt")){
			renderJson(MsgUtil.errorMsg("appkey error"));
			return;
		}
		List<Record> list=Db.find("select * from (SELECT b.SHOW_ID showId,a.otherid isMain,a.isRead urgeIsLook,b.O_DESCRIBE title,b.LIGHTNING lightning,b.O_CREATE_TIME createTime,b.O_FINISH_TIME finishTime,b.URGENAME ouserName,cast(b.SEND_TIME as char) updateTime FROM t_1do_pstatus a,t_1do_base b,t_reg_user c"
+" where a.O_USER=c.SHOW_ID and a.SHOW_ID=b.SHOW_ID and a.isDelete=1 and (a.isRead=2 or a.otherid=1) and a.USER_TYPE!=2 and b.O_STATUS<5"
+" and  (c.U_MAIL=? or U_LOGIN_NAME=?) ORDER BY otherid desc)d  GROUP BY showId",json.getString("userName"),json.getString("userName"));
		new Thread(new Runnable() {			
			@Override
			public void run() {			
				DoService.cbts1(json.getString("userName"));//催报发催办接口
			}
		}).start();
		renderJson(MsgUtil.successMsg(list));
	}
	/*
	 2019年2月26日 coco 注解：
	 */
	public void look() {
		JSONObject json=JsonUtil.getJSONObject(getRequest());
		if(!json.getString("appkey").equals("zhxxpt")){
			renderJson(MsgUtil.errorMsg("appkey error"));
			return;
		}
		if(StrKit.notBlank(json.getString("appName"))){//1call传appName
			renderJson(MsgUtil.successMsg(Db.update("update t_1do_pstatus set urge_isLook=1 where SHOW_ID=? and O_USER =(select SHOW_ID from t_reg_user where U_LOGIN_NAME=? or U_MAIL=? or SHOW_ID=?)",json.getString("showId"),json.getString("appName"),json.getString("appName"),json.getString("appName"))));
		}else if(StrKit.notBlank(json.getString("userName"))){//综合信息系统传userName
			renderJson(MsgUtil.successMsg(Db.update("update t_1do_pstatus set urge_isLook=1 where SHOW_ID=? and O_USER=(select SHOW_ID from t_reg_user where U_LOGIN_NAME=? or U_MAIL=?)",json.getString("showId"),json.getString("userName"),json.getString("userName"))));
		}else if(StrKit.notBlank(json.getString("loginName"))){//1call传appName
			renderJson(MsgUtil.successMsg(Db.update("update t_1do_pstatus set urge_isLook=1 where SHOW_ID=? and O_USER =(select SHOW_ID from t_reg_user where U_LOGIN_NAME=? or U_MAIL=? or SHOW_ID=?)",json.getString("SHOW_ID"),json.getString("loginName"),json.getString("loginName"),json.getString("loginName"))));
		}else{
			renderJson(MsgUtil.errorMsg("缺少参数"));
		}
		
	}
	
}