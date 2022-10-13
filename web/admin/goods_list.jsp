<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%	
	//获取项目名
	String path = request.getContextPath();
	//获取tomcat 协议+地址+端口号+ 获取项目名
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	pageContext.setAttribute("basePath", basePath);
	//获取tomcat 协议+地址+端口号
	String imgPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/pic/";
	pageContext.setAttribute("imgPath", imgPath);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商品信息</title>
<link href="<%=basePath %>admin/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath %>js/jquery-3.3.1.js"></script>

<script type="text/javascript">

// old write 
$(document).ready(function(){
  $(".click").click(function(){
  $(".tip").fadeIn(200);
  });
  
  $(".tiptop a").click(function(){
  $(".tip").fadeOut(200);
});

  $(".sure").click(function(){
  $(".tip").fadeOut(100);
});

  $(".cancel").click(function(){
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
		        <li style="cursor: pointer;" onclick="add_goods()"><span><img src="<%=basePath %>admin/images/t01.png"  /></span>添加商品</li>
		        <li style="cursor: pointer;" onclick="batchDelete()"><span><img src="<%=basePath %>admin/images/t03.png" /></span>批量删除</li>
	        </ul>
	    </div>
	    <table class="tablelist">
	    	<thead>
		    	<tr>
			        <th><input id="all" type="checkbox" /></th>
			        <th>序号<i class="sort"><img src="<%=basePath %>admin/images/px.gif" /></i></th>
			        <th>商品类别</th>
			        <th>商品名称</th>
			        <th>商品颜色</th>
			        <th>商品价格</th>
			        <th width="10%">简介</th>
			        <th width="20%">详情</th>
			       	<th>商品展示图</th>
			       	<th>尺寸</th>
			       	<th>型号</th>
			       	<th>生产日期</th>
			       	<th>操作</th>
		        </tr>
	        </thead>
	        <tbody>
				<c:forEach items="${list}" var="goods">
					<tr>
						<td><input  class="one"  type="checkbox" value="${goods.gid}" /></td>
						<td>${goods.gid}</td>
						<td>${goods.category.cname}</td>
						<td>${goods.gname}</td>
						<td>${goods.color}</td>
						<td>${goods.price}</td>
						<td>${goods.description}</td>
						<td>${goods.full_description}</td>
						<td>
							<img width="50px" height="50px" src="${imgPath}${goods.pic}" alt="图片不见了">
						</td>
						<td>${goods.size}</td>
						<td>${goods.version}</td>
						<td>
							<fmt:formatDate value="${goods.product_date}" pattern="yyyy-MM-dd"></fmt:formatDate>
						</td>
						<td>
							<a href="${basePath}goods?method=findGoodsByGid&gid=${goods.gid}">修改</a>
						</td>
					</tr>
				</c:forEach>
	        </tbody>
	    </table>
	    <div class="pagin">
	    	<div class="message">共<i class="blue"></i> ${page.totalCount}条记录，当前显示第&nbsp;<i class="blue">${page.currentPage}&nbsp;</i>页</div>
	        <ul class="paginList">
		         <li class="paginItem"><a href="${basePath}goods?method=findGoodsByPage&currentPage=1">首页</a></li>
		         <li class="paginItem"><a href="${basePath}goods?method=findGoodsByPage&currentPage=${page.prePage}">上一页</a></li>
		         <li class="paginItem"><a href="${basePath}goods?method=findGoodsByPage&currentPage=${page.nextPage}">下一页</a></li>
		         <li class="paginItem"><a href="${basePath}goods?method=findGoodsByPage&currentPage=${page.totalPage}">尾页</a></li>
	        </ul>
	    </div>
	    <div class="tip">
	    	<div class="tiptop"><span>提示信息</span><a></a></div>
	      	<div class="tipinfo">
	        	<span><img src="images/ticon.png" /></span>
	        	<div class="tipright">
	        		<p>是否确认对信息的修改 ？</p>
	       	 		<cite>如果是请点击确定按钮 ，否则请点取消。</cite>
	        	</div>
	        </div>
	        <div class="tipbtn">
	        	<input name="" type="button"  class="sure" value="确定" />&nbsp;
	        	<input name="" type="button"  class="cancel" value="取消" />
	        </div>
	    </div>
    </div>

    <script type="text/javascript">
		$('.tablelist tbody tr:odd').addClass('odd');
		// 添加商品
		function  add_goods(){
			window.location = "${basePath}goods?method=findAllCategory";
		}
		// 全选全不选
		$("#all").click(function (){
			$(".one").prop("checked", $(this).prop("checked"))
		})
		// 批量删除
		function batchDelete(){
			var gids = "";
			$(".one:checked").each(function (i, e){
				gids += "," + $(e).val();
			})
			if (gids == "") {
				alert("请选择要删除的商品");
				return;
			}
			// 处理gids字符串 去掉开头的逗号
			gids = gids.substring(1);
			if (confirm("您确定要删除这些商品信息吗?")){
				window.location = "${basePath}goods?method=batchDelete&gids="+gids;
			}
		}
	</script>
</body>
</html>
