package com.offcn.servlet;

import com.offcn.entity.Category;
import com.offcn.service.CategoryService;
import com.offcn.utils.BaseServlet;
import com.offcn.utils.PageTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/category")
public class CategoryServlet extends BaseServlet {
    // 添加一个CategoryService的属性
    private CategoryService service = new CategoryService();

    public void findCategoryByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取当前页
        String currentPage = request.getParameter("currentPage");
        // 获取总个数
        int totalCount = service.findTotalCount();
        // 创建PageTool对象
        PageTool pageTool = new PageTool(totalCount, currentPage);
        List<Category> list = service.findCategoryByPage(pageTool);
        // 把数据存储到request域中
        request.setAttribute("page", pageTool);
        request.setAttribute("list", list);
        request.getRequestDispatcher("admin/category_list.jsp").forward(request, response);
    }

    // 添加商品类别
    public void addCategory(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, ParseException, IOException {
        // 获取请求参数
      /*  Map<String, String[]> map =  request.getParameterMap();
        Category category = new Category();
        // 这个工具类无法处理把字符串转为Date对象
        BeanUtils.populate(category, map);
        System.out.println(category);*/
        String cname = request.getParameter("cname");
        String state = request.getParameter("state");
        String description = request.getParameter("description");
        String create_time = request.getParameter("create_time");
        String order_number = request.getParameter("order_number");

        Category category = new Category();
        category.setCname(cname);
        category.setState(Integer.parseInt(state));
        category.setDescription(description);
        category.setOrder_number(Integer.parseInt(order_number));
        // 字符串转时间对象
        // 2022-07-18 09:31:51
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = sdf.parse(create_time);
        category.setCreate_time(date);

        // 调用service执行添加操作
        boolean isSuc = service.addCategory(category);
        if (isSuc) {
            response.sendRedirect("category?method=findCategoryByPage");
        }
    }

    public void findCategoryByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        Category category = service.findCategoryByCid(cid);
        request.setAttribute("category", category);
        request.getRequestDispatcher("admin/category_update.jsp").forward(request, response);
    }

    public void updateCategoryByCid(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
        String cid = request.getParameter("cid");
        String cname = request.getParameter("cname");
        String state = request.getParameter("state");
        String description = request.getParameter("description");
        String order_number = request.getParameter("order_number");
        String create_time = request.getParameter("create_time");

        Category category = new Category();
        category.setCid(Integer.parseInt(cid));
        category.setCname(cname);
        category.setState(Integer.parseInt(state));
        category.setDescription(description);
        category.setOrder_number(Integer.parseInt(order_number));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = sdf.parse(create_time);
        category.setCreate_time(date);
        // 测试数据
        // System.out.println(category);
        boolean isSuc = service.updateCategoryByCid(category);
        if (isSuc) {
            response.sendRedirect("category?method=findCategoryByPage");
        }
    }

    // 批量删除
    public void batchDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cids = request.getParameter("cids");

        boolean isSuc = service.batchDelete(cids);
        if (isSuc) {
            response.sendRedirect("category?method=findCategoryByPage");
        }
    }

}
