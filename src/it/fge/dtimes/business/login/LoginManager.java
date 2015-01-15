package it.fge.dtimes.business.login;

import it.fge.dtimes.model.user.UserDTO;

public interface LoginManager {

    public UserDTO login(String username, String password);
}
