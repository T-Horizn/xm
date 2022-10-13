package com.offcn.service;

import com.offcn.dao.CategoryDao;
import com.offcn.entity.Category;
import com.offcn.utils.PageTool;

import java.util.List;

public class CategoryService {
    // 创建一个CategoryDao的属性
    private CategoryDao dao = new CategoryDao();

    public int findTotalCount() {
        int totalCount =  dao.findTotalCount();
        return totalCount;
    }

    public List<Category> findCategoryByPage(PageTool pageTool) {
        return dao.findCategoryByPage(pageTool);
    }

    public boolean addCategory(Category category) {
        int row = dao.addCategory(category);
        return row != 0 ? true : false;
    }

    public Category findCategoryByCid(String cid) {
        return dao.findCategoryByCid(cid);
    }

    public boolean updateCategoryByCid(Category category) {
       int row =  dao.updateCategoryByCid(category);
       return row != 0 ? true : false;
    }

    public boolean batchDelete(String cids) {
        int row = dao.batchDelete(cids);

        return row != 0 ? true : false;
    }

    public List<Category> findAllCategory() {
        return dao.findAllCategory();
    }

    public List<Category> findAllCategoryByOrderNumber() {
        return dao.findCategoryByOrderNumber();
    }
}
