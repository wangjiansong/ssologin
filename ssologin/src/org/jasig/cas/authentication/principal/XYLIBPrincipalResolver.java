package org.jasig.cas.authentication.principal;

import org.jasig.cas.authentication.Credential;

public class XYLIBPrincipalResolver
  implements PrincipalResolver
{
  public Principal resolve(Credential credential)
  {
    return new XYLIBSimplePrincipal(credential.getId());
  }
  
  public boolean supports(Credential credential)
  {
    return credential.getId() != null;
  }
}
