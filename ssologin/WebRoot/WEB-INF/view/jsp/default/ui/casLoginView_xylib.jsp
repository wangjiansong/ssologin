<%@page session="true"%>
<%@page pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*,com.opac.cas.utils.PropertyUtils"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-Type" content="text/html; charset=UTF-8" />
<title>襄阳统一认证登录中心</title>
<meta name="Robots" content="all" />
<meta name="Keywords" content="襄阳统一认证登录中心" />
<meta name="Description" content="襄阳统一认证登录中心" />

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
		loadCSSFile("<c:url value='/css/pubuic-style.css'/>");
	}else{
		loadCSSFile("<c:url value='/css/pubuic-style.css'/>");
	}
</script>

<!--[if lte IE 6]>
    <script src="<c:url value="/js/ie6png.js" /></script>
       <script type="text/javascript">
    	   DD_belatedPNG.fix('*');
       </script>
    <![endif]-->
<%

	Properties sysConfig=PropertyUtils.getProperties("sysConfig");
	String regUrl = sysConfig.getProperty("regUrl");
	String regTeam = sysConfig.getProperty("regTeam");
	String forgotUrl = sysConfig.getProperty("forgotUrl");
%>
</head>
<body>
	<div class="login-container">
		<div class="login-mask"></div>
		<form:form method="post" id="fm1" cssClass="fm-v clearfix" commandName="${commandName}" htmlEscape="true">
			<form:errors path="*" id="msg" cssClass="errors" element="div" />
		<div class="out-container">
		<!-- 左边部分 -->
			 <!-- <div class="left-area">
				<div class="logo"></div>
				<div class="lib-name">襄阳统一认证登录中心</div>
			</div> --> 
			<div class="login-wrapper">

				<!-- <div class="login-close lg-close-btn">
					<img src="images/close.png" alt="" />
				</div> -->
				<div class="login-content">
					<div class="login-title ">
						读者登录
					</div>
						<div class="login-input">
							<form:input cssClass="cid login-area" placeholder="读者证号"
								id="username" name="username" accesskey="${userNameAccessKey}" type="text" 
								path="username" autocomplete="false" htmlEscape="true" onkeypress="javascript:if(event.keyCode==13){password.focus();return false;}" />
								<div data-bind="visible:rPhone.isSelected()==true" class="tips" id="utips">
			                          <span class="ico_arw"></span>
			                          <span class="ico_arw_i"></span>
			                          <span class="ico_t_note"></span>
			                          <p>请输入您的读者证号</p>
			                      </div>
						</div>
						<div class="login-input">
							<form:password cssClass="pwd login-area" placeholder="密码"
								id="password" path="bbc" name="password"
								accesskey="${passwordAccessKey}" htmlEscape="true"
								autocomplete="off" onkeypress="javascript:if(event.keyCode==13){doSubmit();return false;}" />
							<div  class="tips">
			                          <span class="ico_arw"></span>
			                          <span class="ico_arw_i"></span>
			                          <span class="ico_t_note"></span>
			                          <p>请输入您的注册密码</p>
			                      </div>
						</div>
						<div class="login-input">
							
						</div>
						<%-- <div class="login-tip clearfix">
							<div class="forget-pwd">
								<a href="<%=forgotUrl%>">
									忘记密码？
								</a>
							</div>

						</div> --%>
						<div class="login-submit">
							<input type="hidden" name="lt" value="${loginTicket}" /> 
							<input type="hidden" name="execution" value="${flowExecutionKey}" /> 
							<input type="hidden" name="_eventId" value="submit" />
							<a href="" id="accountLoginBt" >登录</a>
						</div>
						<!--添加代码-->
						<div class="signIn" style="float:right;">
							<a href="http://221.232.224.75:8888/yztdl/web/userGrRegister.jsp" class="person" >政务中心账户注册</a>
						</div>
						<div class="signIn" style="float:left;">
														
							<a href="http://221.232.224.75:8888/yztdl/web/login.jsp?appId=bd16a8e04c7044989e62cede6a60b671" class="person" >
								政务中心账号登录<br><!-- <img style="border: 0px;
									height: auto;
									max-height: 80px;
									max-width: 50px;
									overflow:hidden;" src="images/zwlogo.png" alt="襄阳政务中心" title="襄阳政务中心" /> -->
							</a>
						</div>
				</div>
			</div>
		</div>
		</form:form>
	</div>

	<script type="text/javascript" src="<c:url value="/js/placeholderfriend.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/js/login.js"/>"></script>
	<script type="text/javascript">

	$("#accountLoginBt").on("click",function(e){
		e.preventDefault();
		doSubmit();
	 })
	function doSubmit() {
		username=$("#username").val();
		if(username==""){
			$("#username").focus();
			return;
		}
		var password=$("#password").val();
		if(password!=""){
			//password=$.md5(password);
			$("#password").val(password);
		}else{
			$("#password").focus();
			return;
		}
		$("#accountLoginBt").html("正在登录...");
		document.forms["fm1"].submit();
	}
	$(document).ready(function(){
	    //flash error box
	   	var username=$("#username").val();
	    var error=$("#msg").text();
	    if(error != "") {
	    	if(username != "") {
		    	$("#utips p").html(error);
		    	$("#username").focus();
	    	}
	    }
	    //$('#msg.errors').animate({ backgroundColor: 'rgb(187,0,0)' }, 30).animate({ backgroundColor: 'rgb(255,238,221)' }, 500);
	});
	</script>
</body>

</html>
