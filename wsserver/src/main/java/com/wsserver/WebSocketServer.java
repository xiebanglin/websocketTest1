package com.wsserver;

/**
 * @ProjectName: ws
 * @Package: com.wsserver
 * @ClassName: WebSocketServer
 * @Author: xiebanglin
 * @Description:
 * @Date: 2020/12/1 14:32
 * @Version: 1.0
 */

import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author gjx
 * @email 13450753745@163.com
 * @description websocket的服务端类
 * @Date 2019/10/14
 */
@ServerEndpoint("/serverTest")
@RestController
public class WebSocketServer {

    /**
     * 存放所有的在线客户端
     */
    private static Map<String, Session> clients = new ConcurrentHashMap<>();

    /**
     * 客户端与服务端连接时触发执行事件
     */
    @OnOpen
    public void onOpen(Session session) throws InterruptedException, IOException {
        System.out.println("有新的客户端连接进来了");
        clients.put(session.getId(), session);
    }

    /**
     * 向客户端发送字符串信息
     */
    private static void sendMsg(Session session, String msg) throws IOException {
        session.getBasicRemote().sendText(msg);
    }

    /**
     * 接收到消息后的处理方式，其中包含客户端的普通信息和心跳包信息，
     * 简单区别处理
     */
    @OnMessage
    public void onMessage(Session session, String msg) throws Exception {

        if (!msg.equals("keepalive")) {
            System.out.println("服务端收到消息：" + msg);
            Thread.sleep(3000L);
            sendMsg(session, "收到！！");
        } else {
            System.out.println("心跳维护包:" + msg);
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("有客户端断开连接了，断开客户为：" + session.getId());
        clients.remove(session.getId());
    }

    @OnError
    public void onError(Throwable throwable) {
        System.out.println("服务端出现错误");
        throwable.printStackTrace();
    }

}
