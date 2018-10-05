package com.opac.cas.utils;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class RefererFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		    HttpServletRequest request=(HttpServletRequest)arg0;
		    if("GET".equalsIgnoreCase(request.getMethod())) {
		    	request.getSession().invalidate();//清空session
		    	if(request.getCookies() != null && request.getCookies().length > 0) {
		    		Cookie cookie = request.getCookies()[0];//获取cookie
		    		cookie.setMaxAge(0);//让cookie过期
		    	}
		    }
			String referer = "";
			boolean referer_sign = true;// true 站内提交，验证通过 //false 站外提交，验证失败
			@SuppressWarnings("rawtypes")
			Enumeration headerValues = request.getHeaders("referer");
			while (headerValues.hasMoreElements()) {
				referer = (String) headerValues.nextElement();
			}
			// 判断是否存在请求页面
			if (StringUtils.isBlank(referer))
				referer_sign = true;
			else {
				// 判断请求页面和getRequestURI是否相同
				String servername_str = request.getServerName();
				if (StringUtils.isNotBlank(servername_str)) {
					int index = 0;
					if (StringUtils.indexOf(referer, "https://") == 0) {
						index = 8;
					}
					else if (StringUtils.indexOf(referer, "http://") == 0) {
						index = 7;
					}
					if (referer.length() - index < servername_str.length()) {// 长度不够
						referer_sign = false;
					}
					else { // 比较字符串（主机名称）是否相同
						String referer_str = referer.substring(index, index + servername_str.length());
						if (!servername_str.equalsIgnoreCase(referer_str))
							referer_sign = false;
					}
				} else {
					referer_sign = false;
				}
			}
			if(!referer_sign)
				return;
			arg2.doFilter(arg0,arg1);  
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
