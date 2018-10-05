package org.jasig.services.persondir.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.FailedLoginException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jasig.cas.authentication.AbstractAuthenticationHandler;
import org.jasig.cas.authentication.Credential;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.HttpBasedServiceCredential;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.jasig.cas.util.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.webflow.context.ExternalContextHolder;
import org.springframework.webflow.context.servlet.ServletExternalContext;

public final class XYZWHttpHandler extends AbstractAuthenticationHandler{
	  private static final String PROTOCOL_HTTPS = "https";
	  private boolean requireSecure = true;
	  private final Logger logger = LoggerFactory.getLogger(getClass());
	  @NotNull
	  private HttpClient httpClient;
	  
	  public HandlerResult authenticate(Credential credential)throws GeneralSecurityException{
		  System.out.println("我来这里了---credential为："+credential);
	    HttpBasedServiceCredential httpCredential = (HttpBasedServiceCredential)credential;
	    if ((requireSecure) && (!httpCredential.getCallbackUrl().getProtocol().equals("https")))
	    {
	      logger.debug("Authentication failed because url was not secure.");
	      throw new FailedLoginException(httpCredential.getCallbackUrl() + " is not an HTTPS endpoint as required.");
	    }
	    logger.debug("Attempting to authenticate {}", httpCredential);
	    if (!httpClient.isValidEndPoint(httpCredential.getCallbackUrl())) {
	    	ServletExternalContext servletExternalContext = (ServletExternalContext)ExternalContextHolder.getExternalContext();
	        HttpServletRequest request = (HttpServletRequest)servletExternalContext.getNativeRequest();
		    logger.debug("襄阳政务中心传来的uid--- {}", request);

	    	System.out.println("襄阳政务中心传来的uid---");
	        String tokenId = request.getParameter("token");
	        String random = request.getParameter("random");
	    	String operatorId = "779c922743d3457d8014c9f9ac4a96c2";

	        System.out.println("tokenId:" + tokenId + ";" + "random:" + random);
	    	HttpURLConnection connection = null;
	        InputStream is = null;
	        BufferedReader br = null;
	        String result = null;// 返回结果字符串
	        Map<String, List<Object>> map = new HashMap<String, List<Object>>(); 

	        try {
	    	
	    	String lasturl = "http://221.232.224.75:8008/hbcaASS/rest/random/userInfo?operatorId="
	    	    			  +operatorId+"&tokenId="+tokenId+"&random="+random;

	    		// 创建远程url连接对象
	            URL url = new URL(lasturl);
	            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
	            connection = (HttpURLConnection) url.openConnection();
	            // 设置连接方式：get
	            connection.setRequestMethod("GET");
	            // 设置连接主机服务器的超时时间：15000毫秒
	            connection.setConnectTimeout(15000);
	            // 设置读取远程返回的数据时间：60000毫秒
	            connection.setReadTimeout(60000);
	            // 发送请求
	            connection.connect();
	            // 通过connection连接，获取输入流
	            if (connection.getResponseCode() == 200) {
	                is = connection.getInputStream();
	                // 封装输入流is，并指定字符集
	                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	                // 存放数据
	                StringBuffer sbf = new StringBuffer();
	                String temp = null;
	                while ((temp = br.readLine()) != null) {
	                    sbf.append(temp);
	                    sbf.append("\r\n");
	                }
	                result = sbf.toString();
	    			JSONObject lastjsStr =JSONObject.fromObject(result);
	    	        System.out.println("最后结果--"+lastjsStr);
	    		    String errorCode=(String) lastjsStr.get("errorCode");
	    	        System.out.println("errorCode:----"+errorCode);
	    	            
	    	            if("0".equals(errorCode)){//获取响应值，判断是否验证通过  errorCode != null && 
	    		            //取出json对象里的data数据。
	    			    		JSONObject jsonData=lastjsStr.getJSONObject("data");     
	    			    		System.out.println("取出json对象里的data数据  jsonData====="+jsonData);

	    			    		//取出jsonData数据里的pow,net,dev数据。
	    			    		String credenceList=jsonData.getString("credenceList");
	    			    		String userPhone = jsonData.getString("userPhone");
	    			    		System.out.println("取到的身份验证集合："+credenceList);
	    			    		System.out.println("取到的登录名是："+userPhone);
	    			          //转化为JSONArray类型的对象
	    			    		JSONArray json = JSONArray.fromObject(credenceList); 
	    			    		String uciDefault2 = null;
	    			    		if(json.size()>0){
	    			    			for(int i=0;i<json.size();i++){
	    			    			JSONObject job = json.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
	    			    				 uciDefault2 =  String.valueOf(job.get("uciDefault2"));
	    			    				System.out.println("取到的密码是："+job.get("uciDefault2")) ;  // 得到 每个对象中的属性值
	    				    			
	    			    			}
	    			    		}
	    		            System.out.println("loginName:"+userPhone+",uciDefault2:"+uciDefault2);
	    		            System.out.println("获取用户信息完整结束");

	    					map.put("rdLoginId", Collections.singletonList((Object) userPhone));//手机号
	    					map.put("rdPasswd", Collections.singletonList((Object) uciDefault2));//密码
	    					map.put("rdName", Collections.singletonList((Object) jsonData.getString("userName")));//姓名
	    					map.put("rdCertify", Collections.singletonList((Object) jsonData.getString("userIdcardNum")));//身份证
	    				
	    		            request.setAttribute("loginName", userPhone);
	    		            request.setAttribute("passWord", uciDefault2);
	    		            request.setAttribute("auto", "true");
	    		            
	    	            }
	            }
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            // 关闭资源
	            if (null != br) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }

	            if (null != is) {
	                try {
	                    is.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }

	            connection.disconnect();// 关闭远程连接
	        }
	      throw new FailedLoginException(
	        httpCredential.getCallbackUrl() + " 获得了一个不可通过的网页状态码 ；；；；；；；");
	    }
	    return new HandlerResult(this, httpCredential, new SimplePrincipal(httpCredential.getId()));
	  }
	  
	  public boolean supports(Credential credential)
	  {
	    return credential instanceof HttpBasedServiceCredential;
	  }
	  
	  public void setHttpClient(HttpClient httpClient)
	  {
	    this.httpClient = httpClient;
	  }
	  
	  public void setRequireSecure(boolean requireSecure)
	  {
	    this.requireSecure = requireSecure;
	  }
	}
