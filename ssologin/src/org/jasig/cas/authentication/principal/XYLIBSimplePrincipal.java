package org.jasig.cas.authentication.principal;

import java.util.Collections;
import java.util.Map;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.util.Assert;

public class XYLIBSimplePrincipal
  implements Principal
{
  private static final long serialVersionUID = -1255260750151385796L;
  private final String id;
  private Map<String, Object> attributes;
  
  private XYLIBSimplePrincipal()
  {
    this.id = null;
  }
  
  public XYLIBSimplePrincipal(String id)
  {
    this(id, null);
  }
  
  public XYLIBSimplePrincipal(String id, Map<String, Object> attributes)
  {
    Assert.notNull(id, "id cannot be null");
    this.id = id;
    this.attributes = attributes;
  }
  
  public Map<String, Object> getAttributes()
  {
    return (Map<String, Object>) (this.attributes == null ? 
      Collections.emptyMap() : 
      Collections.unmodifiableMap(this.attributes));
  }
  
  public String toString()
  {
    return this.id;
  }
  
  public int hashCode()
  {
    HashCodeBuilder builder = new HashCodeBuilder(83, 31);
    builder.append(this.id);
    return builder.toHashCode();
  }
  
  public final String getId()
  {
    return this.id;
  }
  
  public boolean equals(Object o)
  {
    return ((o instanceof XYLIBSimplePrincipal)) && (((XYLIBSimplePrincipal)o).getId().equals(this.id));
  }
}
