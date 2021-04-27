package com.nopeya.tomcat.simple_web_server;

import java.io.IOException;
import java.io.InputStream;

public class Request {

    private InputStream input;
    private String uri;

    public Request(InputStream input) {
        this.input = input;
    }

    public void parse() {

        StringBuilder request = new StringBuilder(2048);
        int i;
        byte[] buffer = new byte[2048];
        try {
            i = input.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }
        for (int j = 0; j < i; j++) {
            request.append((char) buffer[j]);
        }
        System.out.println(request.toString());
        uri = parseUri(request.toString());
    }

    private String parseUri(String request) {
        String[] s = request.split(" ");
        if (s.length > 1) {
            return s[1];
        }
        return null;
    }

    public String getUri() {
        return uri;
    }

}
