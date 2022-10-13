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
<title>修改商品</title>
<link href="<%=basePath %>admin/css/style.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=basePath %>admin/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
	<div class="place">
	    <span>位置：</span>
	    <ul class="placeul">
	    	<li><a href="#">修改商品</a></li>
	    </ul>
    </div>
    <div class="formbody">
    	<div class="formtitle"><span>商品信息</span></div>
	    <form action="${basePath}goods" method="post" enctype="multipart/form-data">
			<input type="hidden" name="method" value="updateGoods">
			<input type="hidden" name="gid" value="${goods.gid}">
			<ul class="forminfo">
		    	<!-- 商品分类信息 -->
		    	<li>
		    		<label>商品分类</label>
		    		<select name="cid" class="dfinput">
		    			<c:forEach items="${list}" var="category">
							<option value="${category.cid}"   <c:if test="${goods.cid == category.cid}">selected</c:if> >${category.cname}</option>
						</c:forEach>
		    		</select>
		    	</li>
			    <li><label>商品名称</label><input name="gname" value="${goods.gname}" type="text" class="dfinput" /><i>商品名称不能超过30个字符</i></li>
			    <li><label>颜色</label><input name="color" value="${goods.color}" type="text" class="dfinput" /></li>
			    <li><label>规格尺寸</label><input name="size" value="${goods.size}" type="text" class="dfinput" /></li>
			    <li><label>单价</label><input name="price" value="${goods.price}" type="text" class="dfinput" /></li>
			    <li><label>简介</label>
			    	<textarea name="description"  cols="10" rows="10" class="textinput" style="height: 80px">${goods.description}</textarea>
			    </li>
			    <li><label>完整介绍</label>
			    	<textarea name="full_description" cols="10" rows="10" class="textinput" style="height: 80px">${goods.full_description}</textarea>
			    </li>
			    <li><label>商品展示图</label>
					<img width="80px" height="80px" src="${imgPath}${goods.pic}" alt="图片不见了">
<%--					这个input框用来存储旧的图片,如果用户上传的图片,那么就获取下面input框中上传图片名即可--%>
					<input type="hidden" name="oldPic" value="${goods.pic}">
			    	<input name="pic" type="file"/><font style="color: red"></font>
			    </li>
			    <li><label>商品小类别</label>
				    <cite>
						<input name="state" type="radio" value="0" <c:if test="${goods.state == 0}">checked</c:if> />正常&nbsp;&nbsp;&nbsp;&nbsp;
					    <input name="state" type="radio" value="1" <c:if test="${goods.state == 1}">checked</c:if> />热门产品
					    <input name="state" type="radio" value="2" <c:if test="${goods.state == 2}">checked</c:if> />为你推荐
					    <input name="state" type="radio" value="3" <c:if test="${goods.state == 3}">checked</c:if> />新品
					    <input name="state" type="radio" value="4" <c:if test="${goods.state == 4}">checked</c:if>/>小米明星单品
				    </cite>
			    </li>
			    <li><label>型号</label><input name="version" value="${goods.version}" type="text" class="dfinput" /></li>
			    <li><label>生产日期</label>
			    	<input class="Wdate" style="width: 345px;height: 32px;line-height: 32px;" onclick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss'})" name="product_date" type="text" class="dfinput" value="${goods.product_date}" />
			    </li>
			    <li><label>&nbsp;</label><input type="submit" class="btn" value="确认保存"/></li>
		    </ul>
	    </form>
    </div>
</body>
</html>
