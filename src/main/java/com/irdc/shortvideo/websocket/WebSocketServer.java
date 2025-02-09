package com.irdc.shortvideo.websocket;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Package short-video
 *
 * @author chenliquan on 2021/3/10 19:55
 * Description：
 */

@Slf4j
@Component
@ServerEndpoint(value = "/websocket/{userId}")
public class WebSocketServer {

    private static int onlineCount = 0;
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static ConcurrentHashMap<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收userId*/
    private String userId="";

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) throws IOException, EncodeException {
        if(!StringUtils.isEmpty(userId)) {

            this.session = session;
            this.userId = userId;
            if (webSocketMap.containsKey(userId)) {
                webSocketMap.remove(userId);
                webSocketMap.put(userId, this);
                //加入set中
            } else {
                webSocketMap.put(userId, this);
                //加入set中
                addOnlineCount();
                //在线数加1
            }

            log.info("用户连接:" + userId + ",当前在线人数为:" + getOnlineCount());

            try {
                sendText("连接成功");
            } catch (IOException e) {
                log.error("用户:" + userId + ",网络异常!!!!!!");
            }
        }

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户退出: "+userId+",当前在线人数为: " + getOnlineCount());
    }

//    /**
//     * 收到客户端消息后调用的方法
//     *
//     * @param message 客户端发送过来的消息*/
//    @OnMessage
//    public void onMessage(String message, Session session) {
//        log.info("用户消息:"+userId+",报文:"+message);
//        //可以群发消息
//        //消息保存到数据库、redis
//        if(StringUtils.isNotBlank(message)){
//            try {
//                //解析发送的报文
//                JSONObject jsonObject = JSON.parseObject(message);
//                //追加发送人(防止串改)
//                jsonObject.put("fromUserId",this.userId);
//                String toUserId=jsonObject.getString("toUserId");
//                //传送给对应toUserId用户的websocket
//                if(StringUtils.isNotBlank(toUserId)&&webSocketMap.containsKey(toUserId)){
//                    webSocketMap.get(toUserId).sendText(jsonObject.toJSONString());
//                }else{
//                    log.error("请求的userId:"+toUserId+"不在该服务器上");
//                    //否则不在这个服务器上，发送到mysql或者redis
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:"+this.userId+",原因:"+error.getMessage());
//        error.printStackTrace();
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户退出(异常): "+userId+",当前在线人数为: " + getOnlineCount());
    }

    /**
     * 实现服务器主动推送String
     */
    public void sendText(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 向特定用户推送Object
     */
    public void sendMessageToUser(Object message, String userId) throws IOException, EncodeException {
        WebSocketServer webSocketServer = webSocketMap.get(userId);
        String messageStr = JSONObject.toJSONString(message);
        webSocketServer.sendText(messageStr);
        log.info("向 " + userId + " 发送数据: " + messageStr);
    }

    /**
     * 查询用户是否在线
     * @param userId
     * @return
     */
    public boolean isOnline(String userId){
        return webSocketMap.containsKey(userId);
    }


    /**
     * 发送自定义消息
     * */
    public static void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
        log.info("发送消息到:"+userId+"，报文:"+message);
        if(StringUtils.isNotBlank(userId)&&webSocketMap.containsKey(userId)){
            webSocketMap.get(userId).sendText(message);
        }else{
            log.error("用户"+userId+",不在线！");
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }


}