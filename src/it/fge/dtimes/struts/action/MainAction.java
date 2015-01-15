package it.fge.dtimes.struts.action;

import org.apache.log4j.Logger;

public class MainAction extends BaseActionSupport {
    private static final long serialVersionUID = 1L;

    private static Logger logger = Logger.getLogger(MainAction.class);

    public String index() {
	if (logger.isDebugEnabled())
	    logger.debug("index()");
	return SUCCESS;
    }

    public String logout() throws Exception {

	if (getSession() != null) {
	    getSession().invalidate();
	}
	return "logout";
    }

}