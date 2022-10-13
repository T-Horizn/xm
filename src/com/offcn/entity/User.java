package com.offcn.entity;

import java.util.Date;

public class User {
    private Integer uid; // 用户id
    private String uname; // 用户真实姓名
    private String phone; //电话号码
    private String area; // 地区
    private Integer manager; //  1普通用户 0 管理员
    private String username; // 账号
    private String password;
    private String photo;
    private Date create_time;
    private Integer gender;

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    // 标准java类几点要求:
    // 1.属性必须私有
    // 2.必须空参构造
    public User(){}
    // 3.属性必须有setter和getter
    // alt+insert --> setter and getter  --> ctrl + a --> ok

    public Integer getUid() {
        return uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }

    public Integer getManager() {
        return manager;
    }

    public void setManager(Integer manager) {
        this.manager = manager;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
    // alt + insert ---> toString

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", uname='" + uname + '\'' +
                ", phone='" + phone + '\'' +
                ", area='" + area + '\'' +
                ", manager=" + manager +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", photo='" + photo + '\'' +
                ", create_time=" + create_time +
                ", gender=" + gender +
                '}';
    }
}
