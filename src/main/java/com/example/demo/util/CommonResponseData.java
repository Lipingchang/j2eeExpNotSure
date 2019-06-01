package com.example.demo.util;


public class CommonResponseData {
    String msg;
    int statusCode;
    Object data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Object getData() {
        return data;
    }
}
