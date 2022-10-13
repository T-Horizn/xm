package com.offcn.dao;

import com.offcn.entity.Category;
import com.offcn.utils.DruidUtils;
import com.offcn.utils.PageTool;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class CategoryDao {
    // 创建一个QueryRunner属性
    private QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());

    public int findTotalCount() {
        String sql = "select count(*) from category";
        try {
            Long count = (Long) queryRunner.query(sql, new ScalarHandler<>());
            return count.intValue();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public List<Category> findCategoryByPage(PageTool pageTool) {
        String sql = "select * from category limit ?, ? ";
        List<Category> list = null;
        try {
            list = queryRunner.query(
                    sql,
                    new BeanListHandler<>(Category.class),
                    pageTool.getStartIndex(),
                    pageTool.getPageSize()
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public int addCategory(Category category) {//  value 和 values

        String sql = "insert into category (cname, state, order_number, description, create_time) values (?, ?, ?, ?, ?)";
        int row = 0;
        try {
            row = queryRunner.update(
                    sql,
                    category.getCname(),
                    category.getState(),
                    category.getOrder_number(),
                    category.getDescription(),
                    category.getCreate_time()
                    );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return row;

    }

    public Category findCategoryByCid(String cid) {
        String sql = "select * from category where cid = ?";
        Category category = null;
        try {
            category = queryRunner.query(sql, new BeanHandler<>(Category.class), cid);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return category;
    }

    public int updateCategoryByCid(Category category) {
        String sql =
                "update category " +
                " set" +
                "  `cname` = ?," +
                "  `state` = ?," +
                "  `order_number` = ?," +
                "  `description` = ?," +
                "  `create_time` = ?" +
                " where `cid` = ?";
        int row = 0;
        try {
            row = queryRunner.update(
                    sql,
                    category.getCname(),
                    category.getState(),
                    category.getOrder_number(),
                    category.getDescription(),
                    category.getCreate_time(),
                    category.getCid());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return row;
    }

    public int batchDelete(String cids) {

        String sql = "delete from category where cid in (" + cids + " )";
        int row = 0;
        try {
            row = queryRunner.update(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return row;
    }
    // 查找所有的商品分类
    public List<Category> findAllCategory() {
        String sql = "select * from category where state  = 1";
        List<Category> query = null;
        try {
            query = queryRunner.query(sql, new BeanListHandler<>(Category.class));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return query;
    }

    public List<Category> findCategoryByOrderNumber() {
        String sql="select * from category where state=1 order by order_number limit 10";
        List<Category> list=null;
        try {
            list= queryRunner.query(sql,new BeanListHandler<>(Category.class));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
}
