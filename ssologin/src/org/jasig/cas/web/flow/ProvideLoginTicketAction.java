package org.jasig.cas.web.flow;

import javax.servlet.http.HttpServletRequest;  

import org.jasig.cas.util.UniqueTicketIdGenerator;  
import org.jasig.cas.web.support.WebUtils;  
import org.springframework.webflow.action.AbstractAction;  
import org.springframework.webflow.execution.Event;  
import org.springframework.webflow.execution.RequestContext;  
  
/** 
 * Opens up the CAS web flow to allow external retrieval of a login ticket. 
 *  
 * @author cydiay 
 */  
public class ProvideLoginTicketAction extends AbstractAction{  
  
    private static final String PREFIX = "LT";  
      
    @Override  
    protected Event doExecute(RequestContext context) throws Exception {  
        final HttpServletRequest request = WebUtils.getHttpServletRequest(context);  
        if (request.getParameter("get-lt") != null && request.getParameter("get-lt").equalsIgnoreCase("true")) {  
            final String loginTicket = this.ticketIdGenerator.getNewTicketId(PREFIX);  
            WebUtils.putLoginTicket(context, loginTicket);  
            return result("loginTicketRequested");  
        }  
        return result("continue");  
    }  
      
    private UniqueTicketIdGenerator ticketIdGenerator;  
      
    public void setTicketIdGenerator(final UniqueTicketIdGenerator generator) {  
        this.ticketIdGenerator = generator;  
    }  
}  