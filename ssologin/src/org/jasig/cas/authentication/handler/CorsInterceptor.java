package org.jasig.cas.authentication.handler;

//自定义拦截器
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CorsInterceptor implements HandlerInterceptor{

  @Override
  public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception{

  String origin = httpServletRequest.getHeader("Origin");
  httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
  httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
  httpServletResponse.setHeader("Access-Control-Allow-Headers","Origin,Content-Type,Accept,token,X-Requested-With");
  httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

  return true;
  }
  //其他postHandle,afterCompletion空继承
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, Exception arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}
}