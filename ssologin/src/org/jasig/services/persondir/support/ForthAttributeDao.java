package org.jasig.services.persondir.support;

import java.util.Collections;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;
import javax.validation.constraints.NotNull;
import org.jasig.services.persondir.IPersonAttributes;
import org.springframework.jdbc.core.JdbcTemplate;

public class ForthAttributeDao extends StubPersonAttributeDao {  
  
    @NotNull
    private JdbcTemplate jdbcTempforth;
  
    @Override  
    public IPersonAttributes getPerson(String uid) {  
    	
    	String sql = "select rdId,rdName,rdLoginId,rdCertify,rdPasswd," +
				"rdBornDate,rdSex,rdCFState,rdUnit," +
				"rdType,rdLib,rdStartDate,rdAddress," +
				"rdEndDate,rdInTime,dueTime from reader where rdCertify = ?";
		final Map<String, Object> values = jdbcTempforth.queryForMap(sql, new Object[] { uid });
		Map<String, List<Object>> map = new HashMap<String, List<Object>>(); 
		System.out.println("uid----"+uid);
		try {
		if(values != null) {
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
		}
//			map.put("roles", Collections.singletonList((Object) "admin"));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new AttributeNamedPersonImpl(map);  
    }  
    
    public JdbcTemplate getJdbcTemplate() {  
        return jdbcTempforth;  
    }  
  
    public void setJdbcTemplate(JdbcTemplate jdbcTempforth) {  
        this.jdbcTempforth = jdbcTempforth;  
    } 
}  