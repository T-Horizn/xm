package com.offcn.servlet;

import com.offcn.entity.Category;
import com.offcn.service.CategoryService;
import com.offcn.service.GoodsService;
import com.offcn.utils.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/index")
public class IndexServlet extends BaseServlet {
    private CategoryService categoryService=new CategoryService();
    private GoodsService goodsService=new GoodsService();
    public void indexInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> cateList=categoryService.findAllCategoryByOrderNumber();
        request.setAttribute("cateList",cateList);
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }

}
