package com.example.ptms.interceptor;

public class UserContext {
    private static final ThreadLocal<Long> CURRENT_USER = new ThreadLocal<>();
    public static void set(Long userId){
        CURRENT_USER.set(userId);
    }
    public static Long get(){
        return CURRENT_USER.get();
    }

    public static void clear(){
        CURRENT_USER.remove();
    }

}
