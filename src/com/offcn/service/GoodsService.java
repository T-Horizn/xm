package com.offcn.service;

import com.offcn.dao.GoodsDao;
import com.offcn.entity.Category;
import com.offcn.entity.Goods;
import com.offcn.utils.PageTool;

import java.util.List;

public class GoodsService {
    // 创建一个GoodsDao的属性
    private GoodsDao dao = new GoodsDao();

    public int findTotalCount() {
        // 调用dao层获取good表数据总个数
       return dao.findTotalCount();
    }
    public List<Goods> findGoodsByPage(PageTool pageTool) {
        // return dao.findGoodsBuPage(pageTool);
        // 查询出每个商品信息之后, 然后把它对应商品类别信息也查询出来,存储到该商品对象中
        List<Goods> list = dao.findGoodsByPage(pageTool);
        // 创建一个商品分类service对象
        CategoryService categoryService = new CategoryService();
        for (Goods goods : list) {
            // 查询出商品对象存储cid对应商品类别对象的信息
            Integer cid = goods.getCid();
            Category category = categoryService.findCategoryByCid(cid + "");
            goods.setCategory(category);
        }
        return list;
    }

    public boolean addGoods(Goods goods) {
        int row = dao.addGoods(goods);
        return  row != 0 ? true : false;
    }

    public boolean batchDelete(String gids) {
        int row = dao.batchDelete(gids);
        return row != 0 ? true : false;
    }

    public Goods findGoodsByGid(String gid) {
        return  dao.findGoodsByGid(gid);
    }

    public boolean updateGoods(Goods goods) {
        int row = dao.updateGoods(goods);
        return row != 0 ? true : false;
    }
}
