<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    //获取项目名
    String path = request.getContextPath();
    //获取tomcat 协议+地址+端口号+ 获取项目名
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    pageContext.setAttribute("basePath", basePath);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>用户信息</title>
    <link href="${basePath}admin/css/style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${basePath}js/jquery-3.3.1.js"></script>

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
        <li><a href="#">商品管理</a></li>
    </ul>
</div>

<div class="rightinfo">

    <div class="tools">

        <ul class="toolbar">

            <!--

            <li class="click"><span><img src="images/t02.png" /></span>修改</li>
            <li><span><img src="images/t04.png" /></span>统计</li>
             -->
            <li style="cursor: pointer;" onclick="deleteAll()"><span><img
                    src="<%=basePath %>admin/images/t03.png"/></span>批量删除
            </li>
        </ul>

    </div>
    <table class="tablelist">
        <thead>
        <tr>
            <th><input id="all" type="checkbox" /></th>
            <th>序号<i class="sort"><img src="<%=basePath %>admin/images/px.gif"/></i></th>
            <th>姓名</th>
            <th>性别</th>
            <th>电话号码</th>
            <th>所在地区</th>
            <th>权限</th>
            <th>账号</th>
            <th>头像</th>
            <th>注册时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <%--c:forEach负责遍历域对象中集合,  items: 要遍历的集合对象  var: 指定变量名, 存储的就是遍历集合时取出的元素--%>
        <c:forEach items="${list}" var="user">
            <%-- tr>td*11  --%>
            <tr>
                <td>
                    <input class="one" type="checkbox" value="${user.uid}"/>
                </td>
                <td>
                        ${user.uid}
                </td>
                <td>
                        ${user.uname}
                </td>
                <td>
                    <c:if test="${user.gender == 1}">男</c:if>
                    <c:if test="${user.gender == 0}">女</c:if>
                </td>
                <td>
                        ${user.phone}
                </td>
                <td>
                        ${user.area}
                </td>
                <td>
                        <%-- ctrl+d--%>
                    <c:if test="${user.manager == 1}">普通用户</c:if>
                    <c:if test="${user.manager == 0}">
                       <span style="color:green">管理员</span>
                    </c:if>
                </td>
                <td>
                        ${user.username}
                </td>
                <td align="center">
                        <%--
                        在tomcat服务器中,是无法直接访问到tomcat之前的其他资源的. 必须把该本地资源挂载到
                        tomcat服务器中,才能访问本地资源的文件
                        --%>
                    <img width="40px" height="40px" src="http://localhost/pic/${user.photo}" alt="图片丢失了">
                </td>
                <td>
                    <fmt:formatDate value="${user.create_time}" pattern="yyyy-MM-dd"></fmt:formatDate>
                </td>
                <td>
                        <%--                           普通用户:提示设置为管理员--%>
                    <c:if test="${user.manager == 1}">
                        <a style="color: green" href="${basePath}user?method=updateManager&uid=${user.uid}&manager=0">设置为管理员</a>
                    </c:if>
                        <%--                           普通用户:提示设撤销管理员--%>
                    <c:if test="${user.manager  == 0}">
                        <a style="color: red" href="${basePath}user?method=updateManager&uid=${user.uid}&manager=1">撤销管理员</a>
                    </c:if>

                </td>
            </tr>

        </c:forEach>
        </tbody>
    </table>
    <%--处理分页展示的标签--%>
    <div class="pagin">
        <div class="message">共<i class="blue"></i>${page.totalCount}条记录，当前显示第&nbsp;<i
                class="blue">&nbsp;${page.currentPage}&nbsp;</i>页
        </div>
        <ul class="paginList">

            <li class="paginItem"><a href="${basePath}user?method=findUserByPage&currentPage=1">首页</a></li>
            <li class="paginItem"><a href="${basePath}user?method=findUserByPage&currentPage=${page.prePage}">上一页</a>
            </li>
            <li class="paginItem"><a href="${basePath}user?method=findUserByPage&currentPage=${page.nextPage}">下一页</a>
            </li>
            <li class="paginItem"><a href="${basePath}user?method=findUserByPage&currentPage=${page.totalPage}">尾页</a>
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

    // 全选全不选操作
    $("#all").click(function (){
        // 获取所有class为one复选框
        $(".one").prop("checked", $(this).prop("checked"))
    })

    function deleteAll() {
        // 定义变量存储选中复选框上的value值(删除用户的uid)
        var uids = "";
        // 获取到所有选中的状态且class为one的复选框
        // 参数i:存储就是数组中元素的索引 参数e: 表示数组中元素对象(选中复选框)
        $(".one:checked").each(function (i, e){
            uids += "," + $(e).val();
        })

        if (uids == "") {
            alert("请选择要删除的用户")
            return;
        }
        // 截取字符串 去掉最前面的逗号
        uids = uids.substring(1);
        if (confirm("你确定你要删除这些用户吗?")){
            // 向后台发送请求,删除这些选中用户
            window.location = "${basePath}user?method=deleteAll&uids="+uids;
        }
    }



</script>
</body>
</html>
