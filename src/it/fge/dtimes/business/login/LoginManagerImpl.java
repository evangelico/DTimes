package it.fge.dtimes.business.login;

import it.fge.dtimes.model.user.UserDAO;
import it.fge.dtimes.model.user.UserDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginManagerImpl implements LoginManager {

    Logger logger = LoggerFactory.getLogger(LoginManagerImpl.class);

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDTO login(String username, String password) {
	logger.info("Login interna - Verifico utente : {}", username);
	UserDTO userDTO = userDAO.findByUsername(username);
	logger.info("Restituisco info su utente : {}", userDTO);
	if (userDTO != null) {
	    if (userDTO.getPassword().equals(password)) {
		return userDTO;
	    }
	}

	return null;

    }

}
