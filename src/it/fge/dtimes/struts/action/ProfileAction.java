package it.fge.dtimes.struts.action;

import it.fge.dtimes.business.profile.ProfileBusiness;
import it.fge.dtimes.model.user.UserDTO;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class ProfileAction extends BaseActionSupport {
    private static final long serialVersionUID = 1L;

    private static Logger logger = Logger.getLogger(ProfileAction.class);

    private UserDTO userNew;

    @Autowired
    ProfileBusiness profileBusiness;

    public UserDTO getUserNew() {
	return userNew;
    }

    public String index() {
	if (logger.isDebugEnabled())
	    logger.debug("Modifica dati amministratore: " + userDTO.getUsername());

	return SUCCESS;
    }

    public String saveEdit() {
	if (logger.isDebugEnabled())
	    logger.debug("Salvo le modifiche dei dati dell'amministratore: " + userDTO.getUsername());
	try {
	    if (StringUtils.isBlank(userNew.getUsername())) {
		addActionError("Il campo 'Username' è obbligatorio");
		return "profile.insert.error";
	    }

	    if (StringUtils.isBlank(userNew.getName())) {
		addActionError("Il campo 'Nome' è obbligatorio");
		return "profile.insert.error";
	    }

	    if (StringUtils.isBlank(userNew.getSurname())) {
		addActionError("Il campo 'Cognome' è obbligatorio");
		return "profile.insert.error";
	    }

	    profileBusiness.update(userDTO, userNew);

	    setSessionAttribute(session_attribute_userHadler, userNew);

	    return "profile.insert.success";
	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
    }

    public void setUserNew(UserDTO userNew) {
	this.userNew = userNew;
    }

}