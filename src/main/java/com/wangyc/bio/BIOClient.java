package com.wangyc.bio;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * BIO Client
 *
 * @author wangyc
 */
public class BIOClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 6666);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
        bw.write("Hello World");
        bw.flush();
        bw.close();
    }
}
