package com.offcn.servlet;

import com.offcn.entity.User;
import com.offcn.service.UserService;
import com.offcn.utils.BaseServlet;
import com.offcn.utils.PageTool;
import com.offcn.utils.UploadPicTools;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

// 这个类用来出来用户操作相关的前端请求,并对请求做出响应
// 此注解是用来指定访问UserServlet的路径
@WebServlet("/user")
@MultipartConfig  // 文件上传是Servlet必须加这个注解
public class UserServlet extends BaseServlet {
    private UserService service = new UserService();

    // 批量删除
    public void deleteAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取要删除用的uids
        String uids = request.getParameter("uids");
        boolean isSuc = service.deleteAll(uids);
        if (isSuc) {
            response.sendRedirect("user?method=findUserByPage");
        }
    }
    // 修改用户权限
    public void updateManager(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        BeanUtils.populate(user, map);
        // 调用service层修改用户权限
        boolean isSuc = service.updateManger(user);
        if (isSuc) {
            // 重定向到当前的UserSerlvet再根据分页查询出用户的信息在列表页面显示
            response.sendRedirect("user?method=findUserByPage");
        }
    }
    // 分页查询用户信息
    public void findUserByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPage = request.getParameter("currentPage");
        // 调用service方法获取user表总个数
        int totalCount = service.findTotalCount();
        // 初始化PageTool
        PageTool pageTool = new PageTool(totalCount, currentPage);
        System.out.println(pageTool);

        // 根据PageTool查询出每页要显示的用户数据
        List<User> list = service.findUserByPageTool(pageTool);
        // 存储到request域中
        request.setAttribute("list", list);
        // 把分页工具类存储到request域中
        request.setAttribute("page", pageTool);

        // 请求转发 admin/user_list.jsp
        request.getRequestDispatcher("admin/user_list.jsp").forward(request, response);
    }
    // 查询所有用户信息的方法
    public void findAllUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 调用service层获取所有用户的方法
        List<User> list = service.findAllUser();
        System.out.println(list);
        // 存储到request域中
        request.setAttribute("list", list);
        // 请求转发 admin/user_list.jsp
        request.getRequestDispatcher("admin/user_list.jsp").forward(request, response);
    }
    // 登出
    public void loginOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 销毁session对象实现登出
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("admin/login.jsp");
    }
    // 登录
    public void adminLogin(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        BeanUtils.populate(user, map);

        HttpSession session = request.getSession();
        // 调用service层,判断管理员能否登录成功
        boolean isSuc = service.checkAdminLogin(user, session);
        if (isSuc) {
            request.getRequestDispatcher("admin/main.jsp").forward(request, response);
        }else{
            request.setAttribute("msg", "账号或者密码错误");
            request.getRequestDispatcher("admin/login.jsp").forward(request, response);
        }
    }
    // 登录方法
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String phone = request.getParameter("phone");
        String code = request.getParameter("code");
        // sevice层校验验证码是否合法
        HttpSession session = request.getSession();
        boolean isSuc = service.validateCode(phone, code, session);
        if (isSuc) {
            response.sendRedirect("index.jsp");
        }else{
            request.setAttribute("msg", "输入的验证码或手机号有误");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
    // 生成验证码
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取手机号
        String phone = request.getParameter("phone");
        // 获取会话对象HttpSession对象
        HttpSession session = request.getSession();

        boolean isSuc = service.createCode(phone, session);
        response.getWriter().print(isSuc);
    }
    // 校验账号是否唯一的方法
    public void checkUsername(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        // 调用service校验账号
        boolean isCan = service.checkUsername(username);
        // 响应结果
        response.getWriter().print(isCan);
    }
    // 封装一个处理校验手机号的方法
    public void checkPhone(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取前端传递的手机号
        String phone = request.getParameter("phone");
        // 调用service层校验手机号
        boolean isCan = service.checkPhone(phone);
        // 响应结果
        response.getWriter().print(isCan);
    }
    // 注册方法
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException, ServletException {

        // 获取用户要注册的数据
        /*
            String uname = request.getParameter("uname");
            String gender = request.getParameter("gender");
            String area = request.getParameter("area");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String phone = request.getParameter("phone");
            System.out.println("uname = " + uname);
            System.out.println("gender = " + gender);
            System.out.println("area = " + area);
            System.out.println("username = " + username);
            System.out.println("password = " + password);
            System.out.println("phone = " + phone);
        */
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        // BeanUtils把请求对象获取的参数自动封装到java对象中一个工具.
        // 有一点需要注意: request对象参数名字一定要java对象中属性名一致,才能封装
        BeanUtils.populate(user, map);
        // 自定指定注册的时间
        user.setCreate_time(new Date());
        // 头像一会单独操作
        // part中存储就是要上传的图片信息
        Part part = request.getPart("photo");

        if (part.getSize() == 0) {
            request.setAttribute("msg", "请选择图片");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }else {
            String fileName = UploadPicTools.upload(part, request, response, "register.jsp");
            // 赋值给User对象
            user.setPhoto(fileName);
        }
        // 调用UserService层注册用户的判断方法
        boolean isSuc = service.insertUser(user);
        if (isSuc) {
            // 重定向跳转登录页面
            response.sendRedirect("login.jsp");
        }
    }
}
