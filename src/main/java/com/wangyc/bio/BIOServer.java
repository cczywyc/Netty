package com.wangyc.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BIO Server
 *
 * @author wangyc
 */
public class BIOServer {
    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("The server start......");

        while (true) {
            final Socket socket = serverSocket.accept();
            System.out.println("Connect to a client");

            executorService.execute(() -> handler(socket));
        }
    }

    private static void handler(Socket socket) {
        byte[] bytes = new byte[1024];
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            while (true) {
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert inputStream != null;
                inputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
