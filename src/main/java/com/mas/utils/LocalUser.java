package com.mas.utils;

import com.mas.model.UserInfo;

public class LocalUser {

    private LocalUser(){}


    //线程变量隔离（每个线程都有各自的LOCAL，互不干扰）  ThreadLocal屏蔽了线程间的通讯，避免了多线程问题
    private static final ThreadLocal<UserInfo> USER_THREAD = new ThreadLocal<>();

    public static void put(UserInfo userInfo){
        USER_THREAD.set(userInfo);
    }

    public static UserInfo get(){
        return USER_THREAD.get();
    }
    public static void remove(){
        USER_THREAD.remove();
    }
}
