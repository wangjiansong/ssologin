<%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License.  You may obtain a
    copy of the License at the following location:

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="java.util.*,com.opac.cas.utils.PropertyUtils" %>
<jsp:directive.include file="includes/top.jsp" />
		<br><br>
		<h1>您已经登录统一用户后台</h1>
		<br><br><br>
		<%
			Properties sysConfig=PropertyUtils.getProperties("sysConfig");
			String rhUrl = sysConfig.getProperty("rhUrl");
		%>
		<a href="<%=rhUrl%>">进入后台管理中心</a>
		<br><br><br>
		
		<a href="<c:url value='/logout' />">注销账号</a>
		
<c:redirect url="<%=rhUrl%>"/>
<jsp:directive.include file="includes/bottom.jsp" />

