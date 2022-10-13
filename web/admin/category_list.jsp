<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>商品分类</title>
    <link href="<%=basePath %>admin/css/style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=basePath %>js/jquery-3.3.1.js"></script>

    <script type="text/javascript">

        // old write
        $(document).ready(function () {
            $(".click").click(function () {
                $(".tip").fadeIn(200);
            });

            $(".tiptop a").click(function () {
                $(".tip").fadeOut(200);
            });

            $(".sure").click(function () {
                $(".tip").fadeOut(100);
            });

            $(".cancel").click(function () {
                $(".tip").fadeOut(100);
            });

        });

    </script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">分类管理</a></li>
    </ul>
</div>
<div class="rightinfo">
    <div class="tools">
        <ul class="toolbar">
            <li style="cursor: pointer;" onclick="add_category()"><span><img src="<%=basePath %>admin/images/t01.png"/></span>添加类别
            </li>
            <li style="cursor: pointer;" onclick="batchDelete()"><span><img
                    src="<%=basePath %>admin/images/t03.png"/></span>批量删除
            </li>
        </ul>
    </div>
    <table class="tablelist">
        <thead>
        <tr>
            <th><input id="all" type="checkbox"/></th>
            <th>序号<i class="sort"><img src="<%=basePath %>admin/images/px.gif"/></i></th>
            <th>类别名称</th>
            <th>启用状态</th>
            <th>排序序号</th>
            <th>创建时间</th>
            <th>描述</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="category">
            <tr>
                <td>
                    <input class="one" type="checkbox" value="${category.cid}"/>
                </td>
                <td>
                        ${category.cid}
                </td>
                <td>
                        ${category.cname}
                </td>
                <td>
                    <c:if test="${category.state == 1}">启用</c:if>
                    <c:if test="${category.state == 0}">未启用</c:if>
                </td>
                <td>
                        ${category.order_number}
                </td>
                <td>
                    <fmt:formatDate value="${category.create_time}" pattern="yyyy-MM-dd"></fmt:formatDate>
                </td>
                <td>
                        ${category.description}
                </td>
                <td>
                    <a href="${basePath}category?method=findCategoryByCid&cid=${category.cid}">修改</a>
                </td>
            </tr>

        </c:forEach>
        </tbody>
    </table>
    <div class="pagin">
        <div class="message">共<i class="blue"></i>${page.totalCount}条记录，当前显示第&nbsp;<i
                class="blue">&nbsp;${page.currentPage}&nbsp;</i>页
        </div>
        <ul class="paginList">
            <li class="paginItem"><a href="${basePath}category?method=findCategoryByPage&currentPage=1">首页</a></li>
            <li class="paginItem"><a href="${basePath}category?method=findCategoryByPage&currentPage=${page.prePage}">上一页</a>
            </li>
            <li class="paginItem"><a href="${basePath}category?method=findCategoryByPage&currentPage=${page.nextPage}">下一页</a>
            </li>
            <li class="paginItem"><a href="${basePath}category?method=findCategoryByPage&currentPage=${page.totalPage}">尾页</a>
            </li>
        </ul>
    </div>
    <div class="tip">
        <div class="tiptop"><span>提示信息</span><a></a></div>
        <div class="tipinfo">
            <span><img src="images/ticon.png"/></span>
            <div class="tipright">
                <p>是否确认对信息的修改 ？</p>
                <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
            </div>
        </div>
        <div class="tipbtn">
            <input name="" type="button" class="sure" value="确定"/>&nbsp;
            <input name="" type="button" class="cancel" value="取消"/>
        </div>
    </div>
</div>
<script type="text/javascript">
    $('.tablelist tbody tr:odd').addClass('odd');


    // 点击添加类别按钮触发的函数
    function add_category() {
        // 实现页面的跳转
        window.location = "${basePath}admin/category_add.jsp"
    }

    $("#all").click(function () {
        $(".one").prop("checked", $(this).prop("checked"))
    })

    // 批量删除函数
    function batchDelete() {
        // 定义变量存储所有要删除商品类别的cid值
        var cids = "";
        $(".one:checked").each(function (i, e) {
            cids += "," + $(e).val();
        })
        if (cids == "") {
            alert("请先选择你要删除的商品类型");
            return;
        }
        cids = cids.substring(1);
        if (confirm("您确定要删除这些数据吗")) {
            // 发送请求
            window.location = "${basePath}category?method=batchDelete&cids=" + cids;
        }
    }

</script>
</body>
</html>
