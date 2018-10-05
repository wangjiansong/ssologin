package com.opac.cas.utils;

import java.security.cert.CertificateException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.HttpClient;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpClient连接池
 * @author czq
 */
public class HttpConnectionManager {
	private static Logger log = LoggerFactory.getLogger(HttpConnectionManager.class);
	/** 最大连接数 */
	private static final int MAX_TOTAL_CONNECTIONS = 600;
	/** 每个路由最大连接数 */
	private static final int MAX_ROUTE_CONNECTIONS = 300;
	/** 连接超时时间：30秒 */
	private static final int CONNECT_TIMEOUT = 30 * 1000;
	/** 读取超时时间：20秒 */
	private static final int READ_TIMEOUT = 20 * 1000;
	/** 1小时 */
	private static final int INITIAL_DELAY = 60 * 60 * 1000;
	/** 1小时 */
	private static final int PERIOD =  60 * 60 * 1000;

	private static HttpClient httpClient = null;
	static {
		//scheme注册
		SchemeRegistry schemeRegistry  = new SchemeRegistry();
		//HTTP协议
		schemeRegistry.register(new Scheme("http",80,PlainSocketFactory.getSocketFactory()));
		//HTTPS协议
		schemeRegistry.register(new Scheme("https",443,SSLSocketFactory.getSocketFactory()));
		
		PoolingClientConnectionManager connManager = new PoolingClientConnectionManager(schemeRegistry);
		//设置最大连接数
		connManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
		//设置每个路由最大连接数
		connManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
		
		//设置Http参数
		HttpParams httpParams = new BasicHttpParams();
		httpParams.setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
		//设置连接超时时间
		HttpConnectionParams.setConnectionTimeout(httpParams, CONNECT_TIMEOUT);
		//设置读取超时时间
		HttpConnectionParams.setSoTimeout(httpParams, READ_TIMEOUT);
		
		
		//创建HttpClient对象
		httpClient = new DefaultHttpClient(connManager, httpParams);
		enableSSL(httpClient);
		//定期清理失效、闲置连接，每小时清理一次
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(new IdleConnectionHandler(connManager), INITIAL_DELAY, PERIOD, TimeUnit.MILLISECONDS);
	}
	
	public static HttpClient getHttpClient() {
		return httpClient;
	}
	
	/**
	 * 重写验证方法，取消检测ssl
	 */
	private static TrustManager truseAllManager = new X509TrustManager() {
		public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO Auto-generated method stub
		}
		public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO Auto-generated method stub
		}
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
	/**
	 * 访问https的网站
	 * @param httpclient
	 */
	private static void enableSSL(HttpClient httpclient) {
		// 调用ssl
		try {
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { truseAllManager }, null);
			SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme https = new Scheme("https", sf, 443);
			httpclient.getConnectionManager().getSchemeRegistry().register(https);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static class IdleConnectionHandler implements Runnable {
		
		PoolingClientConnectionManager connManager ;
		
		public  IdleConnectionHandler(PoolingClientConnectionManager connManager) {
			this.connManager = connManager;
		}
		
		public void run() {
			log.info("释放前连接数：" + connManager.getTotalStats().getAvailable());
			
			//关闭失效连接
			connManager.closeExpiredConnections();
			//关闭闲置连接
			connManager.closeIdleConnections(READ_TIMEOUT * 5, TimeUnit.MILLISECONDS);
			
			log.info("释放后连接数：" + connManager.getTotalStats().getAvailable());
		}
		
	}
}

