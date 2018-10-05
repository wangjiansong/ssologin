package org.jasig.services.persondir.support;

import java.util.Collections;  
import java.util.Date;
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;
import javax.validation.constraints.NotNull;

import org.jasig.cas.authentication.handler.EncryptDecryptData;
import org.jasig.services.persondir.IPersonAttributes;
import org.springframework.jdbc.core.JdbcTemplate;

import com.opac.cas.utils.TimeUtils;

public class SecondAccoutAttributeDao extends StubPersonAttributeDao {  
  
    @NotNull
    private JdbcTemplate jdbcTempfb;
  
    @Override  
    public IPersonAttributes getPerson(String uid) {  
    	
    	String sql = "select rdId,rdName,rdLoginId,rdCertify,rdPasswd," +
				"rdBornDate,rdSex,rdCFState,rdUnit," +
				"rdType,rdLib,rdStartDate,rdAddress," +
				"rdEndDate,rdInTime,dueTime,rdEmail from reader where rdId = ?";
		final Map<String, Object> values = jdbcTempfb.queryForMap(sql, new Object[] { uid });
		Map<String, List<Object>> map = new HashMap<String, List<Object>>();  
		try {
		if(values != null) {
			String rdpasswd = (String) values.get("rdPasswd");
			String skey = "64074f968502295ca41b7db452c7c639";
			String rdpasswdDes = EncryptDecryptData.decryptWithCode(skey, rdpasswd);
			
			map.put("rdLoginId", Collections.singletonList((Object) values.get("rdLoginId")));//手机号
			map.put("rdPasswd", Collections.singletonList((Object) rdpasswdDes));//解密的密码
			map.put("rdName", Collections.singletonList((Object) values.get("rdName")));//姓名
			map.put("rdCertify", Collections.singletonList((Object) values.get("rdCertify")));//身份证
			map.put("rdUnit", Collections.singletonList((Object) values.get("rdUnit")));//单位
			map.put("rdAddress", Collections.singletonList((Object) values.get("rdAddress")));//地址
			
//			String rdBornDate = TimeUtils.dateToString((Date) values.get("rdBornDate"), TimeUtils.YYYYMMDD);
//			String rdStartDate = TimeUtils.dateToString((Date) values.get("rdStartDate"), TimeUtils.YYYYMMDD);
//			String rdEndDate = TimeUtils.dateToString((Date) values.get("rdEndDate"), TimeUtils.YYYYMMDD);
//			String rdInTime = TimeUtils.dateToString((Date) values.get("rdInTime"), TimeUtils.YYYYMMDD);
//			String dueTime = TimeUtils.dateToString((Date) values.get("dueTime"), TimeUtils.YYYYMMDD);
			
			map.put("rdBornDate", Collections.singletonList((Object) values.get("rdBornDate")));//出生日期
			map.put("rdSex", Collections.singletonList((Object) values.get("rdSex")));//性别
			map.put("rdCFState", Collections.singletonList((Object) values.get("rdCFState")));//读者状态
			map.put("rdType", Collections.singletonList((Object) values.get("rdType")));//读者类型
			map.put("rdLib", Collections.singletonList((Object) values.get("rdLib")));//馆代码
			map.put("rdStartDate", Collections.singletonList((Object) values.get("rdStartDate")));//读者开启时间
			map.put("rdEndDate", Collections.singletonList((Object) values.get("rdEndDate")));//读者结束时间
			map.put("rdInTime", Collections.singletonList((Object) values.get("rdInTime")));//读者加入时间
			map.put("dueTime", Collections.singletonList((Object) values.get("dueTime")));//修改时间
			map.put("rdEmail", Collections.singletonList((Object) values.get("rdEmail")));//邮箱
			map.put("rdId", Collections.singletonList((Object) values.get("rdId")));//读者证号
		}
//			map.put("roles", Collections.singletonList((Object) "admin"));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new AttributeNamedPersonImpl(map);  
    }  
    
    public JdbcTemplate getJdbcTemplate() {  
        return jdbcTempfb;  
    }  
  
    public void setJdbcTemplate(JdbcTemplate jdbcTempfb) {  
        this.jdbcTempfb = jdbcTempfb;  
    } 
}  