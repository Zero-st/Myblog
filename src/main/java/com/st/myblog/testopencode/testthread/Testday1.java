package com.st.myblog.testopencode.testthread;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Testday1 {

    public static void main(String[] args) {

        Thread thread = Thread.currentThread();
        System.out.println(thread.getName());
        try {
            Class<?> threadLocal = Class.forName("java.lang.ThreadLocal");
            System.out.println(threadLocal+"111");
            System.out.println(threadLocal.getClass()+"222");
            System.out.println(threadLocal.newInstance()+"333");

            Method get = threadLocal.getDeclaredMethod("get");

            get.invoke(threadLocal.newInstance());
            System.out.println("##########");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }



}
