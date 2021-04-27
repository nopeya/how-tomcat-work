package com.nopeya.tomcat.simple_web_server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class Response {

    private static final int BUFFER_SIZE = 1024;
    private OutputStream out;
    private Request request;

    public Response(OutputStream out, Request request) {
        this.out = out;
        this.request = request;
    }

    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            File file = new File(HttpServer.WEB_ROOT, request.getUri());
            if (file.exists()) {
                fis = new FileInputStream(file);
                int ch = fis.read(bytes, 0, BUFFER_SIZE);
                while (ch != -1) {
                    out.write(bytes, 0, ch);
                    ch = fis.read(bytes, 0, BUFFER_SIZE);
                }
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("HTTP/1.1 404 File Not Found\r\n");
                sb.append("Content-Type: text/html\r\n");
                sb.append("Content-Length: 23\r\n");
                sb.append("\r\n");
                sb.append("<h1>File Not Found</h1>");
                out.write(sb.toString().getBytes());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (Objects.nonNull(fis)) {
                fis.close();
            }
        }
    }
}
