package com.offcn.utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8"); // 解决post请求中文乱码
        // 获取子类的类对象
        Class c  = this.getClass();
        // 根据请求参数中method,获取子类要动态执行的是那个方法
        String methodName = req.getParameter("method");
        // 获取子类中方法对象
        // 参数1: 要获取方法对象的方法名
        // 参数2: 是一个可变参数, 传入要获取子类方法参数类型类对象
        try {
            Method method = c.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

