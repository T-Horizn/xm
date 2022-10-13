package com.offcn.dao;

import com.offcn.entity.Goods;
import com.offcn.utils.DruidUtils;
import com.offcn.utils.PageTool;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class GoodsDao {
    // 创建执行sql语句的对象QueryRunner的对象
    private QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());

    public int findTotalCount() {
        String sql = "select count(*) from goods";
        Long count = null;
        try {
            count = (Long) queryRunner.query(sql, new ScalarHandler<>());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count.intValue();
    }
    public List<Goods> findGoodsByPage(PageTool pageTool) {
        String sql = "select * from goods limit ?, ?";
        List<Goods> list = null;
        try {
            list = queryRunner.query(sql,
                    new BeanListHandler<>(Goods.class),
                    pageTool.getStartIndex(),
                    pageTool.getPageSize());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public int addGoods(Goods goods) {
        String sql = "insert into goods" +
        "(`cid`, `gname`, `color`,`size`,`price`, `description`,`full_description`,`pic`,`state`,`version`, `product_date`)" +
        "values (?,?,?, ?,?,?,?,?,?,?,?)";
        int row = 0;
        try {
            row = queryRunner.update(
                     sql,
                     goods.getCid(),
                     goods.getGname(),
                     goods.getColor(),
                     goods.getSize(),
                     goods.getPrice(),
                     goods.getDescription(),
                     goods.getFull_description(),
                     goods.getPic(),
                     goods.getState(),
                     goods.getVersion(),
                     goods.getProduct_date());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return row;
    }

    public int batchDelete(String gids) {
        String sql = "delete from goods where gid in (" + gids+")";
        int row = 0;
        try {
            row = queryRunner.update(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return row;
    }

    public Goods findGoodsByGid(String gid) {
        String sql = "select * from goods where gid = ?";
        Goods goods = null;
        try {
            goods = queryRunner.query(sql, new BeanHandler<>(Goods.class), gid);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return goods;
    }

    public int updateGoods(Goods goods) {

        String sql = "update goods" +
                " set `cid` = ?," +
                "  `gname` = ?," +
                "  `color` = ?," +
                "  `size` = ?," +
                "  `price` = ?," +
                "  `description` = ?," +
                "  `full_description` = ?," +
                "  `pic` = ?," +
                "  `state` = ?," +
                "  `version` = ?," +
                "  `product_date` = ?" +
                "where `gid` = ?";

        int row = 0;
        try {
            row = queryRunner.update(sql,
                    goods.getCid(),
                    goods.getGname(),
                    goods.getColor(),
                    goods.getSize(),
                    goods.getPrice(),
                    goods.getDescription(),
                    goods.getFull_description(),
                    goods.getPic(),
                    goods.getState(),
                    goods.getVersion(),
                    goods.getProduct_date(),
                    goods.getGid());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return row;

    }
}
