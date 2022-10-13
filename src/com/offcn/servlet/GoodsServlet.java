package com.offcn.servlet;

import com.offcn.entity.Category;
import com.offcn.entity.Goods;
import com.offcn.service.CategoryService;
import com.offcn.service.GoodsService;
import com.offcn.utils.BaseServlet;
import com.offcn.utils.DateTools;
import com.offcn.utils.PageTool;
import com.offcn.utils.UploadPicTools;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;

@WebServlet("/goods")
@MultipartConfig
public class GoodsServlet extends BaseServlet {

    // 创建一个GoodsService属性
    private GoodsService service = new GoodsService();

    //
    public void findGoodsByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPage = request.getParameter("currentPage");
        // 调用service层获取所有goods表中数据的总个数
        int totalCount = service.findTotalCount();
        // 创建PageTool对象
        PageTool pageTool = new PageTool(totalCount, currentPage);
        // System.out.println(pageTool);
        // 查询出当前页要展示的数据
        List<Goods> list = service.findGoodsByPage(pageTool);
        // 把pageTool对象及list对象存储到request域中
        request.setAttribute("page", pageTool);
        request.setAttribute("list", list);
        request.getRequestDispatcher("admin/goods_list.jsp").forward(request, response);
    }

    // 查询出所有商品类别
    public void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 创建商品类别service对象
        CategoryService categoryService = new CategoryService();
        List<Category> list = categoryService.findAllCategory();
        request.setAttribute("list", list);
        request.getRequestDispatcher("admin/goods_add.jsp").forward(request, response);
    }


    // 添加商品方法
    public void addGoods(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // 获取提交的参数
        String cid = request.getParameter("cid");
        String gname = request.getParameter("gname");
        String color = request.getParameter("color");
        String size = request.getParameter("size");
        String description = request.getParameter("description");
        String full_description = request.getParameter("full_description");
        String state = request.getParameter("state");
        String product_date = request.getParameter("product_date");
        String price = request.getParameter("price");
        String version = request.getParameter("version");
        // 处理上传图片的操作
        Part part = request.getPart("pic");
        String url = "admin/goods_add.jsp";
        if (part.getSize() == 0) {
            request.setAttribute("msg", "请先选择要上传的图片");
            request.getRequestDispatcher(url).forward(request, response);
        } else {
            String pic = UploadPicTools.upload(part, request, response, url);
            // 创建goods对象
            Goods goods = new Goods(
                    Integer.parseInt(cid),
                    gname,
                    color,
                    size,
                    Double.parseDouble(price),
                    description,
                    full_description,
                    pic,
                    Integer.parseInt(state),
                    version,
                    DateTools.stringToDate(product_date));

            // 调用service层添加的操作
            boolean isSuc = service.addGoods(goods);
            if (isSuc) {
                response.sendRedirect("goods?method=findGoodsByPage");
            }
        }
    }

    // 批量删除
    public void batchDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String gids = request.getParameter("gids");
        boolean isSuc = service.batchDelete(gids);
        if (isSuc) {
            response.sendRedirect("goods?method=findGoodsByPage");
        }
    }

    // 修改数据的回显
    public void findGoodsByGid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String gid = request.getParameter("gid");
        Goods goods = service.findGoodsByGid(gid);
        // 获取所有商品类别信息
        CategoryService categoryService = new CategoryService();
        List<Category> list = categoryService.findAllCategory();
        // 存储数据到request域中
        request.setAttribute("goods", goods);
        request.setAttribute("list", list);
        // 转发到修改页面
        request.getRequestDispatcher("admin/goods_update.jsp").forward(request, response);

    }

    // 修改商品的操作
    public void updateGoods(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // 获取提交的参数
        String gid = request.getParameter("gid");
        String cid = request.getParameter("cid");
        String gname = request.getParameter("gname");
        String color = request.getParameter("color");
        String size = request.getParameter("size");
        String description = request.getParameter("description");
        String full_description = request.getParameter("full_description");
        String state = request.getParameter("state");
        String product_date = request.getParameter("product_date");
        String price = request.getParameter("price");
        String version = request.getParameter("version");

        String picName = "";
        // 判断是否上传新的商品图片
        Part part = request.getPart("pic");
        if (part.getSize() != 0) { // 选择了新的图片, 调用工具类上传图片
            picName = UploadPicTools.upload(part, request, response, "admin/goods_update.jsp");

        } else { // 没有选择的新的图片,直接获取老的图片名即可
            picName = request.getParameter("oldPic");
        }

        // 创建Goods对象,存储信息
        //    public Goods(Integer gid, Integer cid, String gname, String color,
        //    String size, Double price, String description,
        //    String full_description, String pic, Integer state, String version, Date product_date) {
        Goods goods = new Goods(
                Integer.parseInt(gid),
                Integer.parseInt(cid),
                gname,
                color,
                size,
                Double.parseDouble(price),
                description,
                full_description,
                picName,
                Integer.parseInt(state),
                version,
                DateTools.stringToDate(product_date)
        );

       boolean isSuc =  service.updateGoods(goods);

       if (isSuc) {
           response.sendRedirect("goods?method=findGoodsByPage");
       }
    }

}
