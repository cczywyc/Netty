package com.wangyc.netty.protocoltcp;

import lombok.Data;

/**
 * 协议包
 *
 * @author: wangyc
 */
@Data
public class MessageProtocol {
    /** 包长度 */
    private int len;
    /** 包内容 */
    private byte[] content;
}