package com.example.foodcourt;

public class Auth {
    private String id;
    private String pw;

    public Auth(String i, String p) {
        id = i;
        pw = p;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }
}
