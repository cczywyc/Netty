package com.wangyc.nio;

import java.nio.IntBuffer;

/**
 * NIO Buffer
 *
 * @author wangyc
 */
public class BasicBuffer {
    public static void main(String[] args) {
        //Buffer的定义
        //private int mark = -1;
        //private int position = 0; 指针位置
        //private int limit;
        //private int capacity; 容量

        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }

        //buffer读写流切换，原理就是切换里面指针的位置
        intBuffer.flip();
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
