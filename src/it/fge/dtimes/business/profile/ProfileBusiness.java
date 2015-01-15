package it.fge.dtimes.business.profile;

import it.fge.dtimes.model.user.UserDTO;

public interface ProfileBusiness {

    void update(UserDTO oldProfile,UserDTO profileToUpdate) throws Exception;
    

}
