package com.wangyc.netty.dubborpc;

/**
 * @author wangyc
 */
public class ClientBootstrap {
    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) throws InterruptedException {
        Client client = new Client();
        HelloService helloService = (HelloService) client.getBean(HelloService.class, providerName);
        for (; ;) {
            Thread.sleep(2 * 1000);
            String res = helloService.hello("hello dubbo");
            System.out.println("���õĽ�� res=" + res);
        }
    }
}