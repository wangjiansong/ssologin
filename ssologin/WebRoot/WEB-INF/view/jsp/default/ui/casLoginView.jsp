<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String action = request.getParameter("action");
    if (action != null && action.equals("getlt")) {
        String callbackName = request.getParameter("callback");
        String jsonData = "{\"lt\":\"" + request.getAttribute("loginTicket") + "\", \"execution\":\"" + request.getAttribute("flowExecutionKey") + "\"}";
        String jsonp = callbackName + "(" + jsonData + ")";
	System.out.print(jsonp);
        //response.setContentType("application/javascript");
        response.getWriter().write(jsonp);
} else {

%>
<%@page session="true"%>
<%@page pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*,java.io.*,java.net.*,com.opac.cas.utils.PropertyUtils"%>
<%@ page language="java" import="net.sf.json.*"%>
<%@ page language="java" import="org.jasig.services.persondir.support.*"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-Type" content="text/html; charset=UTF-8" />
<title>首页</title>
<meta name="Robots" content="all" />
<meta name="Keywords" content="首页" />
<meta name="Description" content="首页" />

<script type="text/javascript" src="<c:url value="/js/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/device.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/md5util.js" />"></script>
 <link type="text/css" rel="stylesheet" href="<c:url value='/css/cas.css'/>" />
<script src="<c:url value="js/dl.js" />"></script>
<script type="text/javascript">
	$(window).resize(
		function(){
			var _winheight = $(window).height();
			var _winwidth = $(window).width();
			$(".xf_bg").css({"height":_winheight,"width":_winwidth,"overflow":"hidden"});
		}
	);

	if(top!=window){//解决session销毁时，页面嵌套登录问题
		top.location.href='${pageContext.request.contextPath}/login';
	}
	function loadCSSFile(cssFile){
 		var writestr='<link rel="stylesheet" type="text/css" href="';
		writestr=writestr+cssFile;
		writestr=writestr+'"><\/link>';
		try{
 			document.write(writestr);
 		}catch(exception){
			alert(exception);
		}
	}
</script>
<script type="text/javascript">
	if(device.android() || device.iphone()){
		loadCSSFile("<c:url value='/css/zhuce.css'/>");
	}else{
		loadCSSFile("<c:url value='/css/zhuce.css'/>");
	}
</script>

<!--[if lte IE 6]>
    <script src="<c:url value="/js/ie6png.js" /></script>
       <script type="text/javascript">
    	   DD_belatedPNG.fix('*');
       </script>
    <![endif]-->
</head>
<%
	Properties sysConfig = PropertyUtils.getProperties("sysConfig");
	String regUrl = sysConfig.getProperty("regUrl");
%>
<body class="xf_bodl">
	<div class="xf_bg ">
	</div>
	<div class="wrap_top" action="<c:url value='/login' />" method="post">
		<form:form method="post" id="form_submit" cssClass="fm-v clearfix" commandName="${commandName}" htmlEscape="true">
			<form:errors path="*" id="msg" cssClass="errors" element="div" />
			<div class="xf_logo"></div>
			<div class="dlyanz">
				<p class="xf_jiaodianp" style="font-size: 14px;color: #ffabab;font-weight: bold;" ></p>
				<form:input placeholder="读者证号/身份证号"
							id="username" name="username" accesskey="${userNameAccessKey}" type="text" 
							path="username" autocomplete="false" htmlEscape="true" />
				<form:password cssClass="mimak" placeholder="密码"
							id="password" path="bbc"
							accesskey="${passwordAccessKey}" htmlEscape="true"
							autocomplete="off" />
				<input type="submit" value="" id="accountLoginBt" name="submit" accesskey="l"></input>
			<%-- <div style="width:200px;color:red;"><a href="${WeiXinClientUrl}"></a></div>
				<a href="${QqClientUrl}"></a>
				<a href="${WeiboClientUrl}"></a>
			</div> --%>
			<!--<div style="width:200px;overflow: hidden;float:left;">
		 <a style="color: #fff;" href="http://221.232.224.75:8888/yztdl/web/login.jsp?appId=bd16a8e04c7044989e62cede6a60b671" >政务中心账户登录</a><br/>
     <a style="color: #fff;" href="http://221.232.224.75:8888/yztdl/web/login.jsp?appId=4afa25cbee5e4e40a617d8f78ec70db9" >政务中心本地测试账户登录</a>
			</div>
			<div style="width:200px;overflow: hidden;float:right;">
				<a style="color: #fff;" href="http://221.232.224.75:8888/yztdl/web/userGrRegister.jsp" >政务中心账户注册</a>
		    </div>
		    -->
			<input type="hidden" name="lt" value="${loginTicket}" /> <input
				type="hidden" name="execution" value="${flowExecutionKey}" /> <input
				type="hidden" name="_eventId" value="submit" />
		</form:form>
		<script type="text/javascript">
	
		var href=window.location.href;
		
		var strCookie=document.cookie; 
// 		alert(strCookie);
		//将多cookie切割为多个名/值对 
		var arrCookie=strCookie.split("; "); 
		var username=""; 
		var password="";
		//遍历cookie数组，处理每个cookie对 
		for(var i=0;i<arrCookie.length;i++){ 
			var arr=arrCookie[i].split("="); 
			//找到名称为userId的cookie，并返回它的值 
			if("username"==arr[0]){ 
				username=arr[1]; 
			}else if("password"==arr[0]){
				password=arr[1];
			}
		}
		$("#username").val(username);
		
		if(password!=""){
			$("#keepUsername").attr("checked","checked");
		}
		var error=$("#msg").text();
		if(error!=""||href.indexOf("?relogin=1")>0){
			$(".xf_jiaodianp").css({"display":"block"});
			document.cookie="password="+password+"; expires="+new Date().toGMTString(); 
			$("#keepUsername").removeAttr("checked");
		}
		
		var checked=$("#keepUsername").is(':checked');
		if(username!=""&&password!=""&&checked){
			window.location.href="?username="+username+"&password="+password;
		}

		$("#accountLoginBt").click(function(){
			username=$("#username").val();
		
			if(username==""){
				$(".xf_jiaodianp").html("请输入账号");
				$(".xf_jiaodianp").css({"display":"block"});
				return false;
			}
			
			var password=$("#password").val();
			if(password!=""){
				//md5加密操作，直接在页面调用，取消cas的验证
				//password=$.md5(password);
				$("#password").val(password);
			}else{
				$(".xf_jiaodianp").html("请输入密码");
				$(".xf_jiaodianp").css({"display":"block"});
				return false;
			}
			
			checked=$("#keepUsername").is(':checked');
			var future=new Date(); 
			future.setTime(future.getTime()+365*24*3600*1000); 
			if(checked){
				document.cookie="password="+password+"; expires="+future.toGMTString(); 
			}else{
				document.cookie="password="+password+"; expires="+new Date().toGMTString(); 
			}
			document.cookie="username="+username+"; expires="+future.toGMTString(); 
		
			return true;
		});
	
		if(href.indexOf("username")>0&&href.indexOf("password")>0){
			var username=href.substring(href.indexOf("username")+9,href.indexOf("&"));
			var password=href.substring(href.indexOf("password")+9);
			$("#username").val(username);
			$("#password").val(password);
			document.forms["form_submit"].submit();
		}
		</script>
		
	</div>
	
	
	<!-- wap -->
    <div class="xf_warpbg">
      <script>
        $(document).ready(function () {
          var xf_winwidth = $(window).width();
          var xf_winheight = $(window).height();
          $("html").css("fontSize",xf_winwidth/160*7+"px");
          $('.xf_warpbg').css('height',xf_winheight);
		});
        $(window).on('resize',function(){
          var xf_winwidth = $(window).width();
          var xf_winheight = $(window).height();
          $("html").css("fontSize",xf_winwidth/160*7+"px");
          $('.xf_warpbg').css('height',xf_winheight);
        });
      </script>
      
      <div class="xf_logo"><img src="images/xf_waplogo.png" alt=""></div>
      <div class="xf_dlyz">
        <form action="<c:url value='/login' />" method="post" name="form_submit">
          <em class="xf_jiaodianp"></em>
          <p><input placeholder="账号" id="username_warp" name="username" accesskey="${userNameAccessKey}" type="text" /></p>
          <p><input placeholder="密码" id="password_warp" name="bbc" class="mimak" type="password" /></p>
          <input type="submit" value="登录" id="accountLoginBt_warp" class="xf_denglu" />

		<%-- <input type="hidden" name="username" value="${loginName}" /> 
		<input type="hidden" name="password" value="${passWord}" /> --%>
           <input type="hidden" name="lt" value="${loginTicket}" /> <input
			type="hidden" name="execution" value="${flowExecutionKey}" /> <input
			type="hidden" name="_eventId" value="submit" />
        </form>
        <script type="text/javascript">
	
		var href=window.location.href;
		
		var strCookie=document.cookie; 
// 		alert(strCookie);
		//将多cookie切割为多个名/值对 
		var arrCookie=strCookie.split("; "); 
		var username=""; 
		var password="";
		//遍历cookie数组，处理每个cookie对 
		for(var i=0;i<arrCookie.length;i++){ 
			var arr=arrCookie[i].split("="); 
			//找到名称为userId的cookie，并返回它的值 
			if("username"==arr[0]){ 
				username=arr[1]; 
			}else if("password"==arr[0]){
				password=arr[1];
			}
		}
		$("#username_warp").val(username);
		
		if(password!=""){
			$("#keepUsername_warp").attr("checked","checked");
		}
		var error=$("#msg").text();
		if(error!=""||href.indexOf("?relogin=1")>0){
			$(".xf_jiaodianp").css({"display":"block"});
			document.cookie="password="+password+"; expires="+new Date().toGMTString(); 
			$("#keepUsername_warp").removeAttr("checked");
		}
		
		var checked=$("#keepUsername_warp").is(':checked');
		if(username!=""&&password!=""&&checked){
			window.location.href="?username="+username+"&password="+password;
		}

		$("#accountLoginBt_warp").click(function(){
			username=$("#username_warp").val();
		
			if(username==""){
				$(".xf_jiaodianp").html("请输入账号");
				$(".xf_jiaodianp").css({"display":"block"});
				return false;
			}
			
			var password=$("#password_warp").val();
			if(password!=""){
				password=$.md5(password);
				$("#password_warp").val(password);
			}else{
				$(".xf_jiaodianp").html("请输入密码");
				$(".xf_jiaodianp").css({"display":"block"});
				return false;
			}
			
			checked=$("#keepUsername_warp").is(':checked');
			var future=new Date(); 
			future.setTime(future.getTime()+365*24*3600*1000); 
			if(checked){
				document.cookie="password="+password+"; expires="+future.toGMTString(); 
			}else{
				document.cookie="password="+password+"; expires="+new Date().toGMTString(); 
			}
			document.cookie="username="+username+"; expires="+future.toGMTString(); 
		
			return true;
		});
	
		if(href.indexOf("username")>0&&href.indexOf("password")>0){
			var username=href.substring(href.indexOf("username")+9,href.indexOf("&"));
			var password=href.substring(href.indexOf("password")+9);
			$("#username_warp").val(username);
			$("#password_warp").val(password);
			document.getElementsByName("form_submit").submit();
		};
		</script>
      </div>
    </div>
  <!-- wapend -->
	
</body>
</html>
<% } %>