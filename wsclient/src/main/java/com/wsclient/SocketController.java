package com.wsclient;

/**
 * @ProjectName: ws
 * @Package: com.wsclient
 * @ClassName: SocketController
 * @Author: xiebanglin
 * @Description:
 * @Date: 2020/12/1 14:35
 * @Version: 1.0
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author gjx
 * @email 13450753745@163.com
 * @description
 * @Date 2019/10/14
 */
@RestController
@RequestMapping("/socket")
public class SocketController {

    //访问此方法建立websocket连接。
    @RequestMapping("/action")
    public void actionSocket() {
        Main.aciton();
    }

    @RequestMapping("/test")
    public void actionTest() {
        System.out.println("测试聊通性");
    }
}