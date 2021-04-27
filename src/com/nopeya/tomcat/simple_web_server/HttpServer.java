package com.nopeya.tomcat.simple_web_server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 一个简单的静态文件服务器
 */
public class HttpServer {

    // 静态资源根目录
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

    // 关闭服务器命令
    public static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    private boolean shutdown = false;


    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        // 创建服务器实例并启动监听
        HttpServer httpServer = new HttpServer();
        httpServer.await();

    }

    public void await() {

        ServerSocket  serverSocket = null;
        int port = 8080;

        // 创建服务端socket
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // 阻塞等待请求
        while (!shutdown) {

            Socket socket = null;

            try {
                socket = serverSocket.accept();
                InputStream input = socket.getInputStream();
                OutputStream out = socket.getOutputStream();

                // 创建请求对象
                Request request = new Request(input);
                request.parse();

                Response response = new Response(out, request);
                response.sendStaticResource();

                socket.close();
                shutdown = request.getUri().equals(SHUTDOWN_COMMAND);

            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

        }

    }
}
