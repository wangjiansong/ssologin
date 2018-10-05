package org.jasig.services.persondir.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jasig.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.jasig.cas.util.HttpClient;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.webflow.context.ExternalContextHolder;
import org.springframework.webflow.context.servlet.ServletExternalContext;


public class XYZWHttpServlet
  extends AbstractJdbcUsernamePasswordAuthenticationHandler{

  @NotNull
  private HttpClient httpClient;
  
  public HttpClient getHttpClient()
  {
    return httpClient;
  }
  
  public void setHttpClient(HttpClient httpClient)
  {
    this.httpClient = httpClient;
  }
  
  
  protected final HandlerResult authenticateUsernamePasswordInternal(UsernamePasswordCredential credential)
    throws GeneralSecurityException, PreventedException
  {
    String username = credential.getUsername();
    String encryptedPassword = getPasswordEncoder().encode(credential.getBbc());
    try
    {
        
        ServletExternalContext servletExternalContext = (ServletExternalContext)ExternalContextHolder.getExternalContext();
        HttpServletRequest request = (HttpServletRequest)servletExternalContext.getNativeRequest();
        
        String tokenId = request.getParameter("token");
        String random = request.getParameter("random");
    	String operatorId = "779c922743d3457d8014c9f9ac4a96c2";

        System.out.println("tokenId:" + tokenId + ";" + "random:" + random);
    	HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;// 返回结果字符串
		String uciDefault2 = null;//验证取到的密码
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
    			    		if(json.size()>0){
    			    			for(int i=0;i<json.size();i++){
    			    			JSONObject job = json.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
    			    				 uciDefault2 =  String.valueOf(job.get("uciDefault2"));
    			    				System.out.println("取到的密码是："+job.get("uciDefault2")) ;  // 得到 每个对象中的属性值
    				    			
    			    			}
    			    		}
    		            System.out.println("loginName:"+userPhone+",uciDefault2:"+uciDefault2);
    		            System.out.println("获取用户信息完整结束");
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
      
	if (!uciDefault2.equals(encryptedPassword)) {
        throw new FailedLoginException("Password does not match value on record.");
      }
    }
    catch (IncorrectResultSizeDataAccessException e)
    {
      if (e.getActualSize() == 0) {
        throw new AccountNotFoundException(username + " not found with SQL query");
      }
      throw new FailedLoginException("Multiple records found for " + username);
    }
    catch (DataAccessException e)
    {
      throw new PreventedException("SQL exception while executing query for " + username, e);
    }
    return createHandlerResult(credential, new SimplePrincipal(username), null);
  }
}
