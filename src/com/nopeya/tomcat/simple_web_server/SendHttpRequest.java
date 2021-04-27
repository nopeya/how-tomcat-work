package com.nopeya.tomcat.simple_web_server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 发送http请求
 */
public class SendHttpRequest {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("127.0.0.1", 8080);
        boolean autoFlush = true;

        PrintWriter out = new PrintWriter(socket.getOutputStream(), autoFlush);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // 发送http请求
        out.println("GET /index.html HTTP/1.1");
        out.println("Host: localhost:8080");
        out.println("Connection: Close");
        out.println();

        // 读取响应
        boolean loop = true;
        StringBuilder sb = new StringBuilder(8096);
        while (loop) {
            if (in.ready()) {
                int i = 0;
                while (i != -1) {
                    i = in.read();
                    sb.append((char) i);
                }
                loop = false;
            }
            Thread.sleep(50);
        }

        System.out.println(sb.toString());
        socket.close();

    }
}
