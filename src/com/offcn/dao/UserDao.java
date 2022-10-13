package com.offcn.dao;

import com.offcn.entity.User;
import com.offcn.utils.DruidUtils;
import com.offcn.utils.PageTool;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

// 处理用户相关数据库的操作
public class UserDao {
    // 创建一个执行sql语句对象QueryRunner
    private QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());
    // 根据账号查找用户的操作
    public User findUserByUsername(String username) {

        String sql = "select * from user where username = ?";
        // 执行sql
        User user = null;
        try {
            user = queryRunner.query(sql, new BeanHandler<>(User.class), username);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }
    // 根据手机号找到User对象的方法
    public User findUserByPhone(String phone) {

        // sql语句
        String sql = "select * from user where phone = ?";
        User user = null;
        try {
            // 执行sql语句
            // 参数1: sql语句
            // 参数2:查询结果封装为一个User对象
            // 参数3:问号处要绑定的数据
            user = queryRunner.query(sql, new BeanHandler<>(User.class), phone);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }
    // 添加用户信息
    public int insertUser(User user) {

        String sql = "insert into user (uname, phone, area, username, password, photo, create_time) values (?, ?, ?, ?, ?, ?, ?)";

        int row = 0;
        try {
            row = queryRunner.update(sql,
                    user.getUname(),
                    user.getPhone(),
                    user.getArea(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getPhoto(),
                    user.getCreate_time());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return row;

    }
    // 根据用户名和密码查询用户信息
    public User findAdminByUsernameAndPassword(User user) {
        // and manager = 0 表示是管理员身份
        String sql = "select * from user where username = ? and password = ? and manager = 0";
        User admin = null;
        try {
            admin = queryRunner.query(sql, new BeanHandler<>(User.class), user.getUsername(), user.getPassword());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return admin;

    }
    // 查询所有用户
    public List<User> findAllUser() {

        String sql = "select * from user";
        // 执行查询操作
        List<User> list = null;
        try {
            list = queryRunner.query(sql, new BeanListHandler<>(User.class));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;

    }
    // 查询用户总个数
    public int findTotalCount() {
        String sql = "select count(*) from user";
        try {
            Long count  = (Long)queryRunner.query(sql, new ScalarHandler<>());
            return count.intValue();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
    // 分页查询用户信息
    public List<User> findUserByPageTool(PageTool pageTool) {
        String sql = "select * from user limit ?, ?";
        List<User> list = null;
        try {
            list = queryRunner.query(
                    sql,
                    new BeanListHandler<>(User.class),
                    pageTool.getStartIndex(),
                    pageTool.getPageSize());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
    // 修改用户权限
    public int updateManger(User user) {
        String sql = "update user set manager = ? where uid = ?";
        int row = 0;
        try {
            row = queryRunner.update(sql, user.getManager(), user.getUid());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return row;
    }
    // 批量删除
    public int deleteAll(String uids) {
        String sql = "delete from user where uid in ("+ uids+")";
        int row = 0;
        try {
            row = queryRunner.update(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return row;
    }
}
