package com.wsclient;

import javax.websocket.*;
import java.io.IOException;

/**
 * @ProjectName: ws
 * @Package: com.wsclient
 * @ClassName: Client
 * @Author: xiebanglin
 * @Description:
 * @Date: 2020/12/1 14:33
 * @Version: 1.0
 */
@ClientEndpoint()
public class Client {

    @OnOpen
    public void onOpen(Session session) {
    }
    @OnMessage
    public void onMessage(Session session,String message) throws Exception {
        System.out.println("Client收到服务端消息: " + message);
    }

    @OnClose
    public void onClose() {}

    private static void sendMsg(Session session,String msg) throws IOException {
        session.getBasicRemote().sendText(msg);
    }
}
