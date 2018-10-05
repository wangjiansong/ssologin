package com.opac.cas.utils;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.params.HttpConnectionParamBean;
import org.codehaus.jackson.map.ObjectMapper;

import com.opac.cas.utils.HttpConnectionManager;



/**
 * 团队信息接口
 * @author huang
 *
 */
public class TeamInfoUtil {
	
	private static final Log log = LogFactory.getLog(TeamInfoUtil.class);
	
	private static HttpClient httpClient;
	/**
	 * @param rdid
	 * @param rdpasswd
	 * @return
	 */
	@SuppressWarnings("finally")
	public static Map<String, String> getTeamInfo(String username, String teamUrl) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			httpClient = HttpConnectionManager.getHttpClient();
			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
			URI uri = new URI(teamUrl);
			String host = uri.getHost();
			int port = uri.getPort();
			String scheme = uri.getScheme();
			uri = URIUtils.createURI(scheme, host, port, "/Interface/function/getmember/username/" + username, 
					URLEncodedUtils.format(qparams, "UTF-8"), null);
			HttpGet httpGet = new HttpGet(uri);
			HttpConnectionParamBean hcpb = new HttpConnectionParamBean(httpClient.getParams());
			hcpb.setSoTimeout(10000);
			HttpResponse response = httpClient.execute(httpGet);
			
			InputStream iin = response.getEntity().getContent();
			
			String jsonStr = IOUtils.toString(iin, "UTF-8");
			System.out.println(jsonStr);
			httpGet.releaseConnection();
			iin.close();
			//{"id":10,"username":"440506198612080034","mobile":"13826442598","email":"","identitycard":"440506198612080034",
			//"realname":"\u9ec4\u5fb7\u9e3f","create_time":1497942010,"update_time":1498137753,"last_login_ip":"14.154.176.196",
			//"teamid":59,"gender":1,"name":"\u68a6\u4e4b\u97f5\u5408\u5531\u56e2","isboutique":0,"orgid":"FT_MGC"}
			ObjectMapper mapper = new ObjectMapper(); //
			Map m = mapper.readValue(jsonStr, Map.class);
			if(m == null) {
				return map;
			}
			String teamid = m.get("teamid") == null? "0":m.get("teamid").toString();
			String teamName = m.get("name") == null? "":m.get("name").toString();
			String isboutique = m.get("isboutique") == null? "":m.get("isboutique").toString();
			String orgid = m.get("orgid") == null? "":m.get("orgid").toString();
			if(!teamid.equals("0")) {
				map.put("teamId", "teamid");
				map.put("teamMemberTitle", "teamLeader");
				map.put("teamName", teamName);
				map.put("teamtype", isboutique);
				map.put("teamLib", orgid);
				map.put("teamMemberNum", "20");
			} 
			return map;
		} catch(Exception e) {
			log.error(e.toString());
			return null;
		} finally {
		}
	}
	
	public static void main(String[] args) {
	}
}
