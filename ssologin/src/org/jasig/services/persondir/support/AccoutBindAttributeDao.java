package org.jasig.services.persondir.support;

import java.util.Collections;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.validation.constraints.NotNull;

import org.jasig.services.persondir.IPersonAttributes;
import org.springframework.jdbc.core.JdbcTemplate;

import com.opac.cas.utils.PropertyUtils;
import com.opac.cas.utils.TeamInfoUtil;  
  
public class AccoutBindAttributeDao extends StubPersonAttributeDao {  
  
    @NotNull
    private JdbcTemplate jdbcBindTemplate;
    
    private static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(100);
  
    @Override  
    public IPersonAttributes getPerson(String uid) {  
    	String sql = "select rdId,rdName,rdLoginId,rdCertify,rdPasswd," +
				"rdBornDate,rdSex,rdCFState,rdUnit," +
				"rdType,rdLib,rdStartDate,rdAddress," +
				"rdEndDate,rdInTime,dueTime from reader where rdName = ? or rdLoginId =?";
		final Map<String, Object> values = jdbcBindTemplate.queryForMap(sql, new Object[] { uid, uid });
		Map<String, List<Object>> map = new HashMap<String, List<Object>>();  
		try {
			String whgumsUrl = PropertyUtils.getProperty("sysConfig", "whgumsUrl");
			String scoreUrl = PropertyUtils.getProperty("sysConfig", "scoreUrl");
			String scoreLevel = "";
			if(values != null){
				map.put("rdLoginId", Collections.singletonList((Object) values.get("rdLoginId")));//手机号
				map.put("rdPasswd", Collections.singletonList((Object) values.get("rdPasswd")));//密码
				map.put("rdName", Collections.singletonList((Object) values.get("rdName")));//姓名
				map.put("rdCertify", Collections.singletonList((Object) values.get("rdCertify")));//身份证
				map.put("rdUnit", Collections.singletonList((Object) values.get("rdUnit")));//单位
				map.put("rdAddress", Collections.singletonList((Object) values.get("rdAddress")));//地址
				map.put("rdBornDate", Collections.singletonList((Object) values.get("rdBornDate")));//出生日期
				map.put("rdSex", Collections.singletonList((Object) values.get("rdSex")));//性别
				map.put("rdCFState", Collections.singletonList((Object) values.get("rdCFState")));//读者状态
				map.put("rdType", Collections.singletonList((Object) values.get("rdType")));//读者类型
				map.put("rdLib", Collections.singletonList((Object) values.get("rdLib")));//馆代码
				map.put("rdStartDate", Collections.singletonList((Object) values.get("rdStartDate")));//读者开启时间
				map.put("rdEndDate", Collections.singletonList((Object) values.get("rdEndDate")));//读者结束时间
				map.put("rdInTime", Collections.singletonList((Object) values.get("rdInTime")));//读者加入时间
				map.put("dueTime", Collections.singletonList((Object) values.get("dueTime")));//修改时间
				map.put("rdId", Collections.singletonList((Object) values.get("rdId")));//读者证号
				map.put("weixinid", Collections.singletonList((Object) values.get("weixinid")));//微信id
				map.put("qqId", Collections.singletonList((Object) values.get("qqid")));//qq
				map.put("sinaWeiboId", Collections.singletonList((Object) values.get("sinaweiboid")));//weibo
				map.put("content", Collections.singletonList((Object) values.get("content")));//weibo
//				map.put("scoreLevel", Collections.singletonList((Object) values.get("score_level")));
//				scoreLevel = values.get("score_level")==null?"":values.get("score_level").toString();
//				map.put("picPath", Collections.singletonList((Object)(whgumsUrl + "/" + (String) values.get("pic_path"))));//头像
				
	            //执行加积分
//	            String username = (String)values.get("username");
//	            EXECUTOR_SERVICE.execute(new com.opac.cas.utils.SendQueue(username, scoreUrl));
			}
			//调用团队接口获取团队信息
//			String teamUrl = PropertyUtils.getProperty("sysConfig", "teamUrl");
//			Map<String, String> teamMap = TeamInfoUtil.getTeamInfo(uid, teamUrl);
//
//			if(teamMap != null && teamMap.size() > 0) {
//				map.put("teamId", Collections.singletonList((Object) teamMap.get("teamId")));
//				map.put("teamMemberTitle", Collections.singletonList((Object) teamMap.get("teamMemberTitle")));
//				map.put("teamName", Collections.singletonList((Object) teamMap.get("teamName")));
//				map.put("teamtype", Collections.singletonList((Object) teamMap.get("teamtype")));
//				map.put("teamLib", Collections.singletonList((Object) teamMap.get("teamLib")));
//				map.put("teamMemberNum", Collections.singletonList((Object) teamMap.get("teamMemberNum")));
//			}
//			
//			String scoreLevelName = TeamInfoUtil.getScoreLevelName(scoreLevel, scoreUrl);
//			System.out.println(scoreLevelName);
//			map.put("scoreLevel", Collections.singletonList((Object) scoreLevelName));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new AttributeNamedPersonImpl(map);  
    }

	public JdbcTemplate getJdbcBindTemplate() {
		return jdbcBindTemplate;
	}

	public void setJdbcBindTemplate(JdbcTemplate jdbcBindTemplate) {
		this.jdbcBindTemplate = jdbcBindTemplate;
	}  
    

    
}  