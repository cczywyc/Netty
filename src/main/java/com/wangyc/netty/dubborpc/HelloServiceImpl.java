package com.wangyc.netty.dubborpc;

/**
 * @author wangyc
 */
public class HelloServiceImpl implements HelloService {
    /** 消息计数器 */
    private static int count = 0;

    @Override
    public String hello(String msg) {
        System.out.println("Receive client message:" + msg);
        if (msg != null) {
            return "Hello Client. I have received the message [" + msg + "]" + (++ count) + "times";
        } else {
            return "Hello Client. I have received the message";
        }
    }
}
