package com.st.myblog.entity;

import lombok.Data;

/**
 * @author Dell
 */
@Data
public class Result {

    private int code;
    private String msg;
    private Object data;


    public static Result succ(Object data) {
        return succ(200, "操作成功", data);
    }


    public static Result succ(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static Result fail(String mess) {
        Result m = new Result();
        m.setCode(-1);
        m.setData(null);
        m.setMsg(mess);
        return m;
    }
    public static Result fail(String mess, Object data) {
        Result m = new Result();
        m.setCode(-1);
        m.setData(data);
        m.setMsg(mess);
        return m;
    }


    public static Result fail(int code, String msg, Object data) {

        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
}
