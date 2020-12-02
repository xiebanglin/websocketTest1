package com.wsclient;

/**
 * @ProjectName: ws
 * @Package: com.wsclient
 * @ClassName: Main
 * @Author: xiebanglin
 * @Description:
 * @Date: 2020/12/1 14:34
 * @Version: 1.0
 */

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

/**
 * @Author gjx
 * @email 13450753745@163.com
 * @description
 * @Date 2019/10/14
 */
public class Main {
    /**
     * 服务器地址
     */
    private static String uri = "ws://localhost:10000/serverTest";
    private static Session session;
    /**
     * 消息发送事件
     */
    private static long date;
    /**
     * 连接状态
     */
    private boolean running = false;

    private void start() {
        WebSocketContainer container = null;
        try {
            container = ContainerProvider.getWebSocketContainer();
        } catch (Exception ex) {
            System.out.println("error" + ex);
        }
        while (true) {
            try {
                URI r = URI.create(uri);
                session = container.connectToServer(Client.class, r);
                break;
            } catch (DeploymentException | IOException e) {
                System.out.println("连接失败");
                try {
                    // 睡眠
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                continue;
//                e.printStackTrace();
            }
        }

    }

    public static void aciton() {
        Main client = new Main();
        client.start();
        new Thread(client.new KeepAlive()).start();
        String input = "";
        try {

            for (int i = 0; i < 5; i++) {
                /**
                 *注意：此处对session做了同步处理，
                 * 因为下文中发送心跳包也是用的此session,
                 * 不用synchronized做同步处理会报
                 * Exception in thread "Thread-5" java.lang.IllegalStateException: The remote endpoint was in state [TEXT_FULL_WRITING] which is an invalid state for called method
                 * 错误
                 */
                synchronized (session) {
                    Main.session.getBasicRemote().sendText("javaclient");
                }
                date = System.currentTimeMillis();
                Thread.sleep(3000L);
            }

        } catch (Exception e) {
            System.out.println("客户端出错");
            e.printStackTrace();
        }
    }

    /**
     * 内部类，用来客户端给服务单发送心跳包维持连接
     */
    class KeepAlive implements Runnable {

        @Override
        public void run() {
            while (true) {
                if (System.currentTimeMillis() - date > 5000L) {
                    try {
                        System.out.println("发送心跳包");
                        synchronized (session) {
                            Main.session.getBasicRemote().sendText("keepalive");
                        }
                        date = System.currentTimeMillis();
                    } catch (IOException e) {
                        System.out.println("維持心跳包出錯");
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Thread.sleep(3000L);
                    } catch (InterruptedException e) {

                    }
                }
            }
        }

        public KeepAlive() {
        }
    }

}