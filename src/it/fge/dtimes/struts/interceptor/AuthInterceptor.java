package it.fge.dtimes.struts.interceptor;

import it.fge.dtimes.business.login.LoginManager;
import it.fge.dtimes.model.user.UserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = 1L;
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    private static String session_attribute_userHandler = "USER_HANDLER";

    private static final String LOGIN_ATTEMPT = "LOGIN_ATTEMPT";
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";

    private static String failedResult = "not.authorized";

    private static String error = "error";

    @Autowired
    private LoginManager loginManager;

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
	if (logger.isDebugEnabled())
	    logger.debug("AuthInterceptor()");
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession();

	UserDTO userDTO = null;

	try {
	    userDTO = (UserDTO) session.getAttribute(session_attribute_userHandler);
	    if (userDTO == null) {
		String username = request.getParameter(USERNAME);
		String password = request.getParameter(PASSWORD);
		if (userDTO == null) {
		    userDTO = loginManager.login(username, password);
		    if (userDTO == null) {
			String loginAttempt = request.getParameter(LOGIN_ATTEMPT);
			if (!StringUtils.isBlank(loginAttempt)) {
			    Object action = actionInvocation.getAction();
			    if (action instanceof ValidationAware) {
				((ValidationAware) action).addActionError("Username e/o Password errata");
			    }

			}
			return failedResult;
		    }
		}
		session.setAttribute(session_attribute_userHandler, userDTO);
	    }
	    return actionInvocation.invoke();

	} catch (Throwable t) {
	    logger.error("Errore: {}", t, t);
	    return error;
	}
    }
}
