package com.offcn.servlet;

import com.offcn.utils.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cookie")
public class CookieServlet extends BaseServlet {


    public void setCookie(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cookie c = new Cookie("key", "123");
        resp.addCookie(c);

    }
    public void getCookie(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        if (!(cookies == null || cookies.length == 0)) {
            for (Cookie cookie : cookies) {
                if ("key".equals(cookie.getName())) {
                    System.out.println(cookie.getValue());
                }
            }
        }
    }
}

