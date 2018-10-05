<%@ page contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<title>正在登录....</title>
	</head>
	<body>
		<script type="text/javascript">
			<%
				String isFrame = request.getParameter("isframe");
				String isLogin = request.getParameter("isajax");
				
				System.out.print(isFrame+"-----"+isLogin);
				// 登录成功
				if("true".equals(isLogin)){
					if("true".equals(isFrame)){%>
						parent.location.replace('${service}?ticket=${ticket}')
					<%} else{%>
						location.replace('${service}?ticket=${ticket}')
					<%}
				}
			%>
			// 回调
			${callback}({'login':${isLogin ? '"success"': '"false"'}, 'msg': ${isLogin ? '""': '"用户名或密码错误！"'}})
		</script>
	</body>
</html>