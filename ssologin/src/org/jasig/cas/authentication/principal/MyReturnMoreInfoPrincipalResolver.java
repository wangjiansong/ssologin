package org.jasig.cas.authentication.principal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.jasig.cas.authentication.Credential;
import org.jasig.services.persondir.IPersonAttributeDao;
import org.jasig.services.persondir.IPersonAttributes;
import org.jasig.services.persondir.support.StubPersonAttributeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class MyReturnMoreInfoPrincipalResolver implements PrincipalResolver {

	@NotNull
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean supports(final Credential credential) {
        return true;
    }

    @Override
    public final Principal resolve(final Credential credential) {
    	String principalId = extractPrincipalId(credential);

        if (principalId == null) {
            return null;
        }
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String sql = "select rdId,rdName,rdLoginId,rdCertify,rdPasswd," +
				"rdBornDate,rdSex,rdCFState,rdUnit," +
				"rdType,rdLib,rdStartDate,rdAddress," +
				"rdEndDate,rdInTime,dueTime from reader where rdId = ?";
			final Map<String, Object> values = jdbcTemplate.queryForMap(sql, principalId);
			if(values != null) {
				map.put("rdLoginId", values.get("rdLoginId"));//手机号
				map.put("rdPasswd", values.get("rdPasswd"));//密码
				map.put("rdName", values.get("rdName"));//姓名
				map.put("rdCertify", values.get("rdCertify"));//身份证
				map.put("rdUnit", values.get("rdUnit"));//单位
				map.put("rdAddress", values.get("rdAddress"));//地址
				map.put("rdBornDate", values.get("rdBornDate"));//出生日期
				map.put("rdSex", values.get("rdSex"));//性别
				map.put("rdCFState", values.get("rdCFState"));//读者状态
				map.put("rdType", values.get("rdType"));//读者类型
				map.put("rdLib", values.get("rdLib"));//馆代码
				map.put("rdStartDate", values.get("rdStartDate"));//读者开启时间
				map.put("rdEndDate", values.get("rdEndDate"));//读者结束时间
				map.put("rdInTime", values.get("rdInTime"));//读者加入时间
				map.put("dueTime", values.get("dueTime"));//修改时间
				map.put("rdId", values.get("rdId"));//读者证号
//				map.put("roles", "admin");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new SimplePrincipal(principalId, map);
    }


    /**
     * Extracts the id of the user from the provided credential. This method should be overridded by subclasses to
     * achieve more sophisticated strategies for producing a principal ID from a credential.
     *
     * @param credential the credential provided by the user.
     * @return the username, or null if it could not be resolved.
     */
    protected String extractPrincipalId(final Credential credential) {
        return credential.getId();
    }
    
    public JdbcTemplate getJdbcTemplate() {  
        return jdbcTemplate;  
    }  
  
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
        this.jdbcTemplate = jdbcTemplate;  
    } 
}
