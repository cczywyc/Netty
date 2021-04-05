package com.wangyc.nio.channel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NIO FileChannel writefile
 *
 * @author wangyc
 */
public class FileChannelWrite {
    public static void main(String[] args) throws IOException {
        String str = "Hello Netty";
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\file01.txt");
        FileChannel fileChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
