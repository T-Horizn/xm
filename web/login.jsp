<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    //获取项目名
    String path = request.getContextPath();
    //获取tomcat 协议+地址+端口号+ 获取项目名 http://localhost:80/xm/
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    // 把数据存储到页面域对象中,可以通过EL取出结果
    pageContext.setAttribute("basePath", basePath);
%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录页面</title>
    <link rel="stylesheet" href="css/index.css">
    <script src="js/jquery.1.11.1.min.js"></script>
    <style>

    </style>
<script type="text/javascript">
//读秒的方法
var iTime = 59;
var Account;
function RemainTime(){
	document.getElementById('zphone').disabled = true;
	var iSecond,sSecond="",sTime="";
	if (iTime >= 0){
		iSecond = parseInt(iTime%60);
		iMinute = parseInt(iTime/60)
		if (iSecond >= 0){
			if(iMinute>0){
				sSecond = iMinute + "分" + iSecond + "秒";
			}else{
				sSecond = iSecond + "秒";
			}
		}
		sTime=sSecond;
		if(iTime==0){
			clearTimeout(Account);
			sTime='获取手机验证码';
			iTime = 59;
			document.getElementById('zphone').disabled = false;
		}else{
			Account = setTimeout("RemainTime()",1000);
			iTime=iTime-1;
		}
	}else{
		sTime='没有倒计时';
	}
	document.getElementById('zphone').value = sTime;
}
</script>
</head>
<body>
<div class="register_head_on">

</div>
<div class="register_head">
    <a href="index.html"><img src="img/logo.jpg" alt=""></a>
    <div class="register_head_right">
        <p class="register_head_right_p1">小 米 商 城</p>
        <p class="register_head_right_p2">让每个人都享受科技乐趣</p>
    </div>

</div>

<div class="register">
    <div class="register_boby">
        <div class="register_boby_min">
            <div class="register_boby_no1">
                <div class="register_boby_no1_in">
                    <span style="color: #ff6700">手机验证码登录 </span>
                </div>
            </div>
            <form id="f3" action="${basePath}user" method="post">
            <!-- fs区分的方法 -->
            <input name="method" value="login" type="hidden">
            <div class="register_boby_no2">
            	<span id="msg" style="color: red;font-size: 12px;margin-left: 20px;"></span>
                <input id="phone" name="phone" type="text" placeholder="手机号码">
                
                <input id="code" name="code"  type="text" placeholder="手机校验码" style="width: 200px; margin-left: 15px;float: left;">
                <!-- 新增加 -->
                <input id="zphone" type="button" value=" 获取手机验证码 " style="width: 138px;float: left;height: 53px;margin-left: 5px;"  >
                <div style="clear: both;">
                
                <div id="loginBtn" class="register_boby_no2_div">
                    <span id="sub">登录</span>
                </div>
            </div>
            </div>
            </form>
            
            <div class="register_boby_no3">
                <a href="javascript:void(0);" style="color: #ff6700">账号密码登录</a>
                <sapn class="register_boby_no3_span">
                    <a href="register.jsp">立即注册</a>
                    <span>|</span>
                    <a href="avascript:void(0);">忘记密码?</a>
                </sapn>

            </div>
            <div class="register_boby_no4">
                <img src="img/register02.jpg" alt="">
            </div>

        </div>
    </div>
</div>
<div class="register_foot">
    <img  src="img/register03.jpg" alt="">
</div>


    <script>
        $("#zphone").click(function (){
            var phone = $("#phone").val();
            // 校验手机号是否为空
            if (phone == null || phone == "") {
                $("#msg").text("手机号不能为空").css("color", "red");
                return;
            }
            // 不为空校验手机号格式是否合法
            if (!(/^1[3-9][0-9]{9}$/.test(phone))) {
                $("#msg").text("手机号格式不合法").css("color", "red");
                return;
            }
            // 校验手机号是否已被注册, 后台校验
            $.ajax({
                url:"${basePath}user",
                data:{"method":"checkPhone", "phone": phone},
                type:"post",
                dataType:"json",
                success:function (respData) {
                    // respData: true 说明数据库中没有这个手机号, 不发送短信
                    // respData: false 说明数据库中有这个手机号, 发送短信
                    if (!respData){
                        // 如果都校验通过, 向后台请求生成验证码
                        RemainTime
                        createCode(phone);
                    }else{ // true
                        $("#msg").html("未注册,<a href='${basePath}register.jsp'><span style='color:blue'> 请先去注册</span></a>")
                    }
                }
            })
        })
        // 生成验证的函数
        function createCode(phone)  {
            $.ajax({
                url:"${basePath}user",
                data:{"method":"createCode", "phone":phone},
                type: "post",
                dataType: "json",
                success:function (respData){
                    if (respData) {
                        $("#msg").text("验证法发送成功,请注意查收").css("color", "green");
                    }else{
                        $("#msg").text("验证法发送失败").css("color", "red");
                    }
                }
            })
        }

        $("#loginBtn").click(function (){
            var phone = $("#phone").val();
            var code = $("#code").val();
            if (phone == null || phone == "" || code == null || code == "") {
                $("#msg").text("手机号或验证码为空");
                return;
            }
            // 表单提交
            $("#f3").submit();
        })
    </script>
</body>
</html>