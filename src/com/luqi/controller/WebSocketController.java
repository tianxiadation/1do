package com.luqi.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.luqi.common.model.T1doBase;
import com.luqi.common.model.T1doFeedback;
import com.luqi.common.model.Websocket;
import com.luqi.util.MsgUtil;

@ServerEndpoint("/websocket")
public class WebSocketController extends Controller{
	//concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocketController> webSocketSet = new CopyOnWriteArraySet<WebSocketController>();
    //这个session不是Httpsession，相当于用户的唯一标识，用它进行与指定用户通讯
    private Session session=null;

	@OnOpen
	public void onOpen(Session session) {
		System.out.println("Session " + session.getId() + " has opened a connection");
       
            this.session=session;
           // webSocketSet.add(this);     //加入set中
            webSocketSet.add(this);
            System.out.println(JsonKit.toJson(MsgUtil.successMsg(session.getId())));
         
    
	}

	@OnClose
	public void onClose(Session session) {
		 Websocket.updateScoketIsOnline(session.getId());
		 webSocketSet.remove(this);  //从set中删除
	       // setSessionAttr(session.getId(), false);
	     System.out.println("Session " +session.getId()+" has closed!");
	}
	
	@OnMessage
	public void onMessage(String requestJson, Session session) throws IOException, InterruptedException, EncodeException {
				
		       Websocket.saveSocket(session.getId(), requestJson,true);	
		       Websocket w=Websocket.selectSocket(session.getId());
				  List<T1doFeedback> list= T1doFeedback.dao.find(T1doBase.selectField+ " from t_1do_feedback where SHOW_ID=? and FB_TYPE!=4  and isoverdue=1 and id>?",requestJson,w.getFbid());
					  session.getBasicRemote().sendText(JsonKit.toJson(MsgUtil.successMsg(list)));
		
	}
	
	
	
	
	public void sendMessage(String showid){
		
		//List<Websocket> list=Websocket.selectScoket(showid);
        //群发消息
        for(WebSocketController item: webSocketSet){
            try {
            	 Websocket w=Websocket.selectScoket1(item.session.getId(),showid);
            	 if(w==null){
            		 continue;
            	 }
            	 List<T1doFeedback> list= T1doFeedback.dao.find(T1doBase.selectField+ " from t_1do_feedback where FB_TYPE!=4 and isoverdue=1 and SHOW_ID=? and id>? ",showid,w.getFbid());
            	 if(list.size()>0){
            		 Websocket.updateScoketFbid(item.session.getId());
                item.session.getBasicRemote().sendText(JsonKit.toJson(MsgUtil.successMsg(list)));
                
                }
            } catch (IOException e) {
               // e.printStackTrace();
                //continue;
            }
        }

    }
	
	
	
	
	
	
	
	
}
