package it.fge.dtimes.util.crypto;

import it.fge.dtimes.model.user.UserDTO;

import javax.persistence.PostLoad;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class CryptoListener {


	/**
	 * Decrypt password after loading.
	 */
	@PostLoad
	@PostUpdate
	public void decryptPassword(Object pc) {
		if (!(pc instanceof UserDTO)) {
			return;
		}

		UserDTO user = (UserDTO) pc;

		if (user.getPassword() != null) {
			user.setPassword(EncryptorBean.decryptString(user.getPassword()));
		}
	}
	/**
	 * Decrypt password before persisting
	 */
	@PrePersist
	@PreUpdate
	public void encryptPassword(Object pc) {
		if (!(pc instanceof UserDTO)) {
			return;
		}

		UserDTO user = (UserDTO) pc;
		if (user.getPassword() != null) {
			user.setPassword(EncryptorBean.encryptString(user.getPassword()));
		}
	}
}
