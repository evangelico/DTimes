package it.fge.dtimes.struts.action;

import it.fge.dtimes.business.deadline.DeadlineBusiness;
import it.fge.dtimes.business.payment.PaymentBusiness;
import it.fge.dtimes.business.plain.PlainBusiness;
import it.fge.dtimes.business.subscription.SubscriptionBusiness;
import it.fge.dtimes.model.packageCourses.PackageCoursesDTO;
import it.fge.dtimes.model.payment.PaymentDTO;
import it.fge.dtimes.model.subscription.SubscriptionDTO;
import it.fge.dtimes.util.EmailValidator;
import it.fge.dtimes.util.FiscalCodeValidator;
import it.fge.dtimes.util.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

public class SubscriptionAction extends BaseActionSupport {
    private static Logger logger = Logger.getLogger(SubscriptionAction.class);

    private static final long serialVersionUID = 1L;

    private String birthdayDate;

    @Autowired
    private DeadlineBusiness deadlineBusiness;

    private String medicalCertificateDate;

    @Autowired
    private PaymentBusiness paymentBusiness;

    private Page<PaymentDTO> payments;

    @Autowired
    private PlainBusiness plainBusiness;

    private List<PackageCoursesDTO> plains = new ArrayList<PackageCoursesDTO>();

    private List<String> plainsSelected;

    private String registrationDate;

    private SubscriptionDTO subscription;

    @Autowired
    private SubscriptionBusiness subscriptionBusiness;

    // parametri di scambio
    private long subscriptionId;

    private long subscriptionIDToDelete;

    private long subscriptionIDToDisable;

    private long subscriptionIDToEdit;

    private long subscriptionIDToEnable;

    private long subscriptionIDToShow;

    private Page<SubscriptionDTO> subscriptions;

    public String detail() {
	if (logger.isDebugEnabled())
	    logger.debug("Dettaglio iscrizione: " + subscriptionIDToShow);

	try {
	    subscription = subscriptionBusiness.findById(subscriptionIDToShow);
	    payments = paymentBusiness.findAllBySubscription(page, size, subscriptionIDToShow);
	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return SUCCESS;
    }

    public String disable() {
	if (logger.isDebugEnabled())
	    logger.debug("Disattivo l'iscrizione con id: " + subscriptionIDToDisable);
	try {
	    SubscriptionDTO subscriptionTmp = subscriptionBusiness.findById(subscriptionIDToDisable);
	    if (subscriptionTmp == null) {
		addActionError("Impossibile disattivare l'iscrizione con id: " + subscriptionIDToDisable);
		subscriptions = subscriptionBusiness.findAll(page, size);
		return "subscription.delete.error";
	    }

	    subscriptionBusiness.disable(subscriptionIDToDisable);
	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return "subscription.insert.success";
    }

    public String edit() {
	if (logger.isDebugEnabled())
	    logger.debug("Modifico l'iscrizione: " + subscriptionIDToEdit);
	try {
	    subscription = subscriptionBusiness.findById(subscriptionIDToEdit);
	    birthdayDate = sdf.format(subscription.getBirthdayDate());
	    registrationDate = sdf.format(subscription.getRegistrationDate());
	    if (subscription.getMedicalCertificateDate() != null) {
		medicalCertificateDate = sdf.format(subscription.getMedicalCertificateDate());
	    }
	    plains = plainBusiness.findAll(0, 999).getContent();
	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return "subscription.to.modify";
    }

    public String enable() {
	if (logger.isDebugEnabled())
	    logger.debug("Attivare l'iscrizione con id: " + subscriptionIDToEnable);
	try {
	    SubscriptionDTO subscriptionTmp = subscriptionBusiness.findById(subscriptionIDToEnable);
	    if (subscriptionTmp == null) {
		addActionError("Impossibile attivare l'iscrizione con id: " + subscriptionIDToEnable);
		subscriptions = subscriptionBusiness.findAll(page, size);
		return "subscription.delete.error";
	    }

	    subscriptionBusiness.enable(subscriptionIDToEnable);

	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return "subscription.insert.success";
    }

    public String getBirthdayDate() {
	return birthdayDate;
    }

    public String getMedicalCertificateDate() {
	return medicalCertificateDate;
    }

    public Page<PaymentDTO> getPayments() {
	return payments;
    }

    public List<PackageCoursesDTO> getPlains() {
	return plains;
    }

    public List<String> getPlainsSelected() {
	return plainsSelected;
    }

    public String getRegistrationDate() {
	return registrationDate;
    }

    public SubscriptionDTO getSubscription() {
	return subscription;
    }

    public long getSubscriptionId() {
	return subscriptionId;
    }

    public long getSubscriptionIDToDelete() {
	return subscriptionIDToDelete;
    }

    public long getSubscriptionIDToDisable() {
	return subscriptionIDToDisable;
    }

    public long getSubscriptionIDToEdit() {
	return subscriptionIDToEdit;
    }

    public long getSubscriptionIDToEnable() {
	return subscriptionIDToEnable;
    }

    public long getSubscriptionIDToShow() {
	return subscriptionIDToShow;
    }

    public Page<SubscriptionDTO> getSubscriptions() {
	return subscriptions;
    }

    public String index() {
	if (logger.isDebugEnabled())
	    logger.debug("Lista iscrizioni");

	try {
	    plains = plainBusiness.findAll(0, 999).getContent();

	    if (StringUtils.isNotBlank(nameFilter) && plainFilter > 0) {
		subscriptions = subscriptionBusiness.findLikeNominativeAndPlain(nameFilter, plainFilter, page, size);
	    } else if (StringUtils.isNotBlank(nameFilter)) {
		subscriptions = subscriptionBusiness.findLikeNominative(nameFilter, page, size);
	    } else if (plainFilter > 0) {
		subscriptions = subscriptionBusiness.findByPlain(plainFilter, page, size);
	    } else {
		subscriptions = subscriptionBusiness.findAll(page, size);
	    }
	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return SUCCESS;
    }

    public String insert() {
	if (logger.isDebugEnabled())
	    logger.debug("Inserimento nuova iscrizione");
	try {
	    plains = plainBusiness.findAll(0, 999).getContent();
	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return SUCCESS;
    }

    public String remove() {
	if (logger.isDebugEnabled())
	    logger.debug("Elimino l'iscrizione con id: " + subscriptionIDToDelete);
	try {
	    SubscriptionDTO subscriptionTmp = subscriptionBusiness.findById(subscriptionIDToDelete);
	    if (subscriptionTmp == null) {
		addActionError("Impossibile eliminare l'iscrizione con id: " + subscriptionIDToDelete);
		subscriptions = subscriptionBusiness.findAll(page, size);
		return "subscription.delete.error";
	    }

	    if (subscriptionTmp.getPayments() != null && !subscriptionTmp.getPayments().isEmpty()) {
		addActionError("Impossibile eliminare l'iscrizione con id: " + subscriptionIDToDelete + ", sono presenti dei pagamenti associati");
		subscriptions = subscriptionBusiness.findAll(page, size);
		return "subscription.delete.error";
	    }

	    subscriptionBusiness.delete(subscriptionIDToDelete);
	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return "subscription.insert.success";
    }

    public String saveEdit() {
	if (logger.isDebugEnabled())
	    logger.debug("Salvataggio modifiche iscrizione: " + subscription.getName() + " " + subscription.getSurname());
	try {

	    EmailValidator emailValidator = new EmailValidator();
	    FiscalCodeValidator fiscalCodeValidator = new FiscalCodeValidator();

	    SubscriptionDTO subscriptionTmp = subscriptionBusiness.findById(subscription.getId());

	    if (subscriptionTmp == null) {
		addActionError("Impossibile aggiornare l'iscrizione: " + subscription.getId());
		return "subscription.insert.error";
	    }
	    plainsSelected =  Util.removeEmptyObjectInList(plainsSelected);
	    if (plainsSelected.isEmpty()) {
		addActionError("Selezionare almeno un pacchetto corsi");
		plains = plainBusiness.findAll(0, 999).getContent();
		birthdayDate = sdf.format(subscription.getBirthdayDate());
		registrationDate = sdf.format(subscription.getRegistrationDate());
		return "subscription.insert.error";
	    }

	    plainsSelected = Util.removeDuplcatesStringInList(plainsSelected);

	    for (String pc : plainsSelected) {
		if (StringUtils.isNotBlank(pc.trim())) {
		    PackageCoursesDTO plainTmp = plainBusiness.findById(Long.parseLong(pc));
		    if (plainTmp != null) {
			subscription.getPackagesCourses().add(plainTmp);
		    }
		}
	    }

	    if (StringUtils.isBlank(registrationDate)) {
		addActionError("Il campo 'Data iscrizione' è obbligatorio");
		plains = plainBusiness.findAll(0, 999).getContent();
		return "subscription.insert.error";
	    }

	    if (StringUtils.isBlank(birthdayDate)) {
		addActionError("Il campo 'Data di nascita' è obbligatorio");
		plains = plainBusiness.findAll(0, 999).getContent();
		registrationDate = sdf.format(subscription.getRegistrationDate());
		return "subscription.insert.error";
	    }

	    if (StringUtils.isBlank(subscription.getName())) {
		addActionError("Il campo 'Nome' è obbligatorio");
		plains = plainBusiness.findAll(0, 999).getContent();
		birthdayDate = sdf.format(subscription.getBirthdayDate());
		registrationDate = sdf.format(subscription.getRegistrationDate());
		return "subscription.insert.error";
	    }
	    if (StringUtils.isBlank(subscription.getSurname())) {
		addActionError("Il campo 'Cognome' è obbligatorio");
		plains = plainBusiness.findAll(0, 999).getContent();
		birthdayDate = sdf.format(subscription.getBirthdayDate());
		registrationDate = sdf.format(subscription.getRegistrationDate());
		return "subscription.insert.error";
	    }
	    subscription.setFiscalCode(subscription.getFiscalCode().trim()); 
	    
//	    if (StringUtils.isBlank(subscription.getFiscalCode())) {
//		addActionError("Il campo 'Codice Fiscale' è obbligatorio");
//		plains = plainBusiness.findAll(0, 999).getContent();
//		birthdayDate = sdf.format(subscription.getBirthdayDate());
//		registrationDate = sdf.format(subscription.getRegistrationDate());
//		return "subscription.insert.error";
//	    }

	    if (StringUtils.isBlank(subscription.getAddress())) {
		addActionError("Il campo 'Indirizzo' è obbligatorio");
		plains = plainBusiness.findAll(0, 999).getContent();
		birthdayDate = sdf.format(subscription.getBirthdayDate());
		registrationDate = sdf.format(subscription.getRegistrationDate());
		return "subscription.insert.error";
	    }

	    if (StringUtils.isBlank(subscription.getPhoneNumber()) && StringUtils.isBlank(subscription.getCellNumber())) {
		addActionError("Inserire almeno uno tra numero di telefono e numero di cellulare");
		plains = plainBusiness.findAll(0, 999).getContent();
		birthdayDate = sdf.format(subscription.getBirthdayDate());
		registrationDate = sdf.format(subscription.getRegistrationDate());
		return "subscription.insert.error";
	    }

//	    if (!fiscalCodeValidator.ControllaCF(subscription.getFiscalCode())) {
//		addActionError("Inserire un Codice fiscale valido");
//		plains = plainBusiness.findAll(0, 999).getContent();
//		birthdayDate = sdf.format(subscription.getBirthdayDate());
//		registrationDate = sdf.format(subscription.getRegistrationDate());
//		return "subscription.insert.error";
//	    }

	    if (StringUtils.isNotBlank(subscription.getEmail()) && !emailValidator.validate(subscription.getEmail())) {
		addActionError("Inserire un indirizzo email valido");
		plains = plainBusiness.findAll(0, 999).getContent();
		birthdayDate = sdf.format(subscription.getBirthdayDate());
		registrationDate = sdf.format(subscription.getRegistrationDate());
		return "subscription.insert.error";
	    }

	    Calendar registration = Calendar.getInstance();
	    registration.setTime(sdf.parse(registrationDate));

	    subscription.setRegistrationDate(registration.getTime());

	    Calendar birthday = Calendar.getInstance();
	    birthday.setTime(sdf.parse(birthdayDate));
	    birthday.set(Calendar.HOUR, 0);
	    birthday.set(Calendar.MINUTE, 0);
	    birthday.set(Calendar.SECOND, 0);
	    birthday.set(Calendar.MILLISECOND, 0);

	    subscription.setBirthdayDate(birthday.getTime());

	    if (StringUtils.isNotBlank(medicalCertificateDate)) {
		Calendar expireCertificate = Calendar.getInstance();
		expireCertificate.setTime(sdf.parse(medicalCertificateDate));
		expireCertificate.set(Calendar.HOUR, 0);
		expireCertificate.set(Calendar.MINUTE, 0);
		expireCertificate.set(Calendar.SECOND, 0);
		expireCertificate.set(Calendar.MILLISECOND, 0);

		subscription.setMedicalCertificateDate(expireCertificate.getTime());
	    }
	    subscriptionBusiness.update(subscription);

	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return "subscription.insert.success";
    }

    public String saveInsert() {
	if (logger.isDebugEnabled())
	    logger.debug("Salvataggio nuova iscrizione: " + subscription.getName() + " " + subscription.getSurname());
	try {

	    EmailValidator emailValidator = new EmailValidator();
	    FiscalCodeValidator fiscalCodeValidator = new FiscalCodeValidator();
	   
	    plainsSelected =  Util.removeEmptyObjectInList(plainsSelected);
	    if (plainsSelected.isEmpty()) {
		addActionError("Selezionare almeno un pacchetto corsi");
		plains = plainBusiness.findAll(0, 999).getContent();
		return "subscription.insert.error";
	    }

	    plainsSelected = Util.removeDuplcatesStringInList(plainsSelected);

	    for (String pc : plainsSelected) {
		if (StringUtils.isNotBlank(pc.trim())) {
		    PackageCoursesDTO plainTmp = plainBusiness.findById(Long.parseLong(pc));
		    if (plainTmp != null) {
			subscription.getPackagesCourses().add(plainTmp);
		    }
		}
	    }

	    if (StringUtils.isBlank(subscription.getName())) {
		addActionError("Il campo 'Nome' è obbligatorio");
		plains = plainBusiness.findAll(0, 999).getContent();
		return "subscription.insert.error";
	    }
	    if (StringUtils.isBlank(subscription.getSurname())) {
		addActionError("Il campo 'Cognome' è obbligatorio");
		plains = plainBusiness.findAll(0, 999).getContent();
		return "subscription.insert.error";
	    }
//	    if (StringUtils.isBlank(subscription.getFiscalCode())) {
//		addActionError("Il campo 'Codice Fiscale' è obbligatorio");
//		plains = plainBusiness.findAll(0, 999).getContent();
//		return "subscription.insert.error";
//	    }

	    if (StringUtils.isBlank(registrationDate)) {
		addActionError("Il campo 'Data iscrizione' è obbligatorio");
		plains = plainBusiness.findAll(0, 999).getContent();
		return "subscription.insert.error";
	    }

	    if (StringUtils.isBlank(birthdayDate)) {
		addActionError("Il campo 'Data di nascita' è obbligatorio");
		plains = plainBusiness.findAll(0, 999).getContent();
		return "subscription.insert.error";
	    }

	    if (StringUtils.isBlank(subscription.getAddress())) {
		addActionError("Il campo 'Indirizzo' è obbligatorio");
		plains = plainBusiness.findAll(0, 999).getContent();
		return "subscription.insert.error";
	    }

	    if (StringUtils.isBlank(subscription.getPhoneNumber()) && StringUtils.isBlank(subscription.getCellNumber())) {
		addActionError("Inserire almeno uno tra numero di telefono e numero di cellulare");
		plains = plainBusiness.findAll(0, 999).getContent();
		return "subscription.insert.error";
	    }
	    
	    subscription.setFiscalCode(subscription.getFiscalCode().trim()); 
	    
//	    if (!fiscalCodeValidator.ControllaCF(subscription.getFiscalCode())) {
//		addActionError("Inserire un Codice fiscale valido");
//		plains = plainBusiness.findAll(0, 999).getContent();
//		return "subscription.insert.error";
//	    }

	    if (StringUtils.isNotBlank(subscription.getEmail()) && !emailValidator.validate(subscription.getEmail())) {
		addActionError("Inserire un indirizzo email valido");
		plains = plainBusiness.findAll(0, 999).getContent();
		return "subscription.insert.error";
	    }

	    if (StringUtils.isNotBlank(subscription.getFiscalCode()) && subscriptionBusiness.findByCF(subscription.getFiscalCode()) != null) {
		addActionError("L'utente " + subscription.getName() + " " + subscription.getSurname() + " con CF " + subscription.getFiscalCode() + " risulta gia' iscritto");
		plains = plainBusiness.findAll(0, 999).getContent();
		return "subscription.insert.error";
	    }

	    Calendar registration = Calendar.getInstance();
	    registration.setTime(sdf.parse(registrationDate));

	    subscription.setRegistrationDate(registration.getTime());

	    Calendar birthday = Calendar.getInstance();
	    birthday.setTime(sdf.parse(birthdayDate));
	    birthday.set(Calendar.HOUR, 0);
	    birthday.set(Calendar.MINUTE, 0);
	    birthday.set(Calendar.SECOND, 0);
	    birthday.set(Calendar.MILLISECOND, 0);

	    subscription.setBirthdayDate(birthday.getTime());
	    if (StringUtils.isNotBlank(medicalCertificateDate)) {
		Calendar expireCertificate = Calendar.getInstance();
		expireCertificate.setTime(sdf.parse(medicalCertificateDate));
		expireCertificate.set(Calendar.HOUR, 0);
		expireCertificate.set(Calendar.MINUTE, 0);
		expireCertificate.set(Calendar.SECOND, 0);
		expireCertificate.set(Calendar.MILLISECOND, 0);

		subscription.setMedicalCertificateDate(expireCertificate.getTime());
	    }

	    subscription.setActive(true);
	    subscription = subscriptionBusiness.save(subscription);

	    subscriptionId = subscription.getId();

	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return "subscription.insert.payment";
    }

    public void setBirthdayDate(String birthdayDate) {
	this.birthdayDate = birthdayDate;
    }

    public void setMedicalCertificateDate(String medicalCertificateDate) {
	this.medicalCertificateDate = medicalCertificateDate;
    }

    public void setPayments(Page<PaymentDTO> payments) {
	this.payments = payments;
    }

    public void setPlains(List<PackageCoursesDTO> plains) {
	this.plains = plains;
    }

    public void setPlainsSelected(List<String> plainsSelected) {
	this.plainsSelected = plainsSelected;
    }

    public void setRegistrationDate(String registrationDate) {
	this.registrationDate = registrationDate;
    }

    public void setSubscription(SubscriptionDTO subscription) {
	this.subscription = subscription;
    }

    public void setSubscriptionId(long subscriptionId) {
	this.subscriptionId = subscriptionId;
    }

    public void setSubscriptionIDToDelete(long subscriptionIDToDelete) {
	this.subscriptionIDToDelete = subscriptionIDToDelete;
    }

    public void setSubscriptionIDToDisable(long subscriptionIDToDisable) {
	this.subscriptionIDToDisable = subscriptionIDToDisable;
    }

    public void setSubscriptionIDToEdit(long subscriptionIDToEdit) {
	this.subscriptionIDToEdit = subscriptionIDToEdit;
    }

    public void setSubscriptionIDToEnable(long subscriptionIDToEnable) {
	this.subscriptionIDToEnable = subscriptionIDToEnable;
    }

    public void setSubscriptionIDToShow(long subscriptionIDToShow) {
	this.subscriptionIDToShow = subscriptionIDToShow;
    }

    public void setSubscriptions(Page<SubscriptionDTO> subscriptions) {
	this.subscriptions = subscriptions;
    }

}