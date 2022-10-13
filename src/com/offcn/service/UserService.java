package com.offcn.service;

import com.offcn.dao.UserDao;
import com.offcn.entity.User;
import com.offcn.utils.PageTool;

import javax.servlet.http.HttpSession;
import java.util.List;
// 处理用户相关的逻辑
public class UserService {
    private UserDao dao = new UserDao();
    // 校验账号名
    public boolean checkUsername(String username) {
        User user = dao.findUserByUsername(username);
        // true表示账号未被注册,可以使用
        // false账号已被注册,不可以使用
        return user == null ? true : false;
    }

    // 校验手机号
    public boolean checkPhone(String phone) {
        User user = dao.findUserByPhone(phone);
        // true表示手机号未被注册,可以使用
        // false手机号已被注册,不可以使用
        return user == null ? true : false;
    }

    // 插入用户
    public boolean insertUser(User user) {
        int row = dao.insertUser(user);
        return row != 0 ? true : false;
    }

    // 生成验证码
    public boolean createCode(String phone, HttpSession session) {
        // 调用工具类发送验证码
       /*
        String s = SendMsgTools.sendMessage(phone, session);
        return "2".equals(s);
        */
        session.setAttribute("code", phone + "#" + "6666");
        return true;
    }

    // 校验验证码登录
    public boolean validateCode(String phone, String code, HttpSession session) {
        // 取出session中存储手机号和验证码
        String oldCode = (String) session.getAttribute("code");
        String newCode = phone + "#" + code;
        if (oldCode.equals(newCode)) {
            // 根据手机号查出用户的所有信息,存储User对象中,再把User对象Session
            User user = dao.findUserByPhone(phone);
            session.setAttribute("user", user);
            return true;
        } else {
            return false;
        }
    }

    // 校验管理员登录
    public boolean checkAdminLogin(User user, HttpSession session) {
        // 调用dao层,根据用户名和密码查询管理员信息
        User admin = dao.findAdminByUsernameAndPassword(user);
        if (admin == null) {
            return false;
        } else {
            session.setAttribute("admin", admin);
            return true;
        }
    }

    // 查询所有用户
    public List<User> findAllUser() {
        // 调用Dao层查询所有用户信息的方法
        List<User> list = dao.findAllUser();
        return list;
    }

    // 查询用户总个数
    public int findTotalCount() {

        int totalCount = dao.findTotalCount();
        return totalCount;
    }

    // 分页查询用户
    public List<User> findUserByPageTool(PageTool pageTool) {
        List<User> list = dao.findUserByPageTool(pageTool);
        return list;
    }

    // 修改用户权限
    public boolean updateManger(User user) {
       int row = dao.updateManger(user);
       return row != 0 ? true : false;
    }

    // 批量删除
    public boolean deleteAll(String uids) {
        int row =  dao.deleteAll(uids);
        return row != 0 ? true : false;
    }
}
