package com.wangyc.netty.protocoltcp;


/**
 * 协议包
 *
 * @author: wangyc
 */
public class MessageProtocol {
    /** 包长度 */
    private int len;
    /** 包内容 */
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}