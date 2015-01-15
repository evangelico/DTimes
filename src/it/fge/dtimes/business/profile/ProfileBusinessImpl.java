package it.fge.dtimes.business.profile;

import it.fge.dtimes.model.user.UserDAO;
import it.fge.dtimes.model.user.UserDTO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class ProfileBusinessImpl implements ProfileBusiness {

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public void update(UserDTO oldProfile, UserDTO profileToUpdate) throws Exception {
	UserDTO profileTmp = userDAO.findOne(oldProfile.getId());
	profileTmp.setName(profileToUpdate.getName());
	profileTmp.setSurname(profileToUpdate.getSurname());
	profileTmp.setUsername(profileToUpdate.getUsername());
	if (StringUtils.isNotBlank(profileToUpdate.getPassword())) {
	    profileTmp.setPassword(profileToUpdate.getPassword());
	}
    }

}
