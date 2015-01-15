package it.fge.dtimes.struts.action;

import it.fge.dtimes.business.deadline.DeadlineBusiness;
import it.fge.dtimes.business.plain.PlainBusiness;
import it.fge.dtimes.model.deadline.DeadlineDTO;
import it.fge.dtimes.model.packageCourses.ExpirationType;
import it.fge.dtimes.model.packageCourses.PackageCoursesDAO;
import it.fge.dtimes.model.packageCourses.PackageCoursesDTO;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PlainAction extends BaseActionSupport {
    private static final long serialVersionUID = 1L;

    private static Logger logger = Logger.getLogger(PlainAction.class);

    private Page<PackageCoursesDTO> plains;

    @Autowired
    private PackageCoursesDAO packageCoursesDAO;

    @Autowired
    private PlainBusiness plainBusiness;

    @Autowired
    private DeadlineBusiness deadlineBusiness;

    private PackageCoursesDTO plain;

    private long plainIDToEdit;

    private long plainIDToDelete;

    private long plainSelected;

    private List<ExpirationType> expirationTypes = new ArrayList<ExpirationType>();

    private InputStream plainFind;

    private long expiredSelected;

    public String edit() {
	if (logger.isDebugEnabled())
	    logger.debug("Modifico il pacchetto corsi: " + plainIDToEdit);
	try {
	    plain = plainBusiness.findById(plainIDToEdit);
	    expirationTypes = Arrays.asList(ExpirationType.values());
	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return "plain.to.modify";
    }

    public List<ExpirationType> getExpirationTypes() {
	return expirationTypes;
    }

    public PackageCoursesDTO getPlain() {
	return plain;
    }

    public InputStream getPlainFind() {
	return plainFind;
    }

    public String getPlainFromId() {
	if (logger.isDebugEnabled())
	    logger.debug("Recupero pacchetto a partire dall'id: " + plainSelected);
	Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	try {
	    PackageCoursesDTO plainTmp = plainBusiness.findById(plainSelected);
	    plainFind = new ByteArrayInputStream(gson.toJson(plainTmp).getBytes());
	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return "ajaxPlain";
    }

    public String getPlainFromDeadline() {
	if (logger.isDebugEnabled())
	    logger.debug("Recupero pacchetto a partire dall'id della scadenza: " + expiredSelected);
	Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	try {
	    DeadlineDTO deadlineTmp = deadlineBusiness.findById(expiredSelected);
	    PackageCoursesDTO plainTmp = plainBusiness.findById(deadlineTmp.getPackageCourses().getId());
	    plainFind = new ByteArrayInputStream(gson.toJson(plainTmp).getBytes());
	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return "ajaxPlain";
    }

    public long getPlainIDToDelete() {
	return plainIDToDelete;
    }

    public long getPlainIDToEdit() {
	return plainIDToEdit;
    }

    public Page<PackageCoursesDTO> getPlains() {
	return plains;
    }

    public long getPlainSelected() {
	return plainSelected;
    }

    public String index() {
	if (logger.isDebugEnabled())
	    logger.debug("Lista pacchetti corsi disponibili");

	try {
	    if (StringUtils.isNotBlank(nameFilter)) {
		plains = plainBusiness.findAllByName(nameFilter, page, size);
	    } else {
		plains = plainBusiness.findAll(page, size);
	    }
	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return SUCCESS;
    }

    public String insert() {
	if (logger.isDebugEnabled())
	    logger.debug("Inserimento nuovo pacchetto corsi");

	expirationTypes = Arrays.asList(ExpirationType.values());

	return SUCCESS;
    }

    public String remove() {
	if (logger.isDebugEnabled())
	    logger.debug("Elimino il pacchetto corsi: " + plainIDToDelete);
	try {
	    PackageCoursesDTO plainTmp = plainBusiness.findById(plainIDToDelete);
	    if (plainTmp == null) {
		addActionError("Impossibile eliminare il pacchetto corsi: " + plainIDToDelete);
		plains = plainBusiness.findAll(page, size);
		return "plain.delete.error";
	    }

	    if (plainTmp.getPayments() != null && !plainTmp.getPayments().isEmpty()) {
		addActionError("Impossibile eliminare il pacchetto corsi: " + plainIDToDelete + ", sono presenti dei pagamenti associati");
		plains = plainBusiness.findAll(page, size);
		return "plain.delete.error";
	    }

	    if (plainTmp.getSubscriptions() != null && !plainTmp.getSubscriptions().isEmpty()) {
		addActionError("Impossibile eliminare il pacchetto corsi: " + plainIDToDelete + ", sono presenti delle iscrizioni ai corsi contenuti nel pacchetto");
		plains = plainBusiness.findAll(page, size);
		return "plain.delete.error";
	    }

	    plainBusiness.delete(plainIDToDelete);
	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return "plain.insert.success";
    }

    public String saveEdit() {
	if (logger.isDebugEnabled())
	    logger.debug("Salvataggio modifiche per pacchetto corsi: " + plain.getId());
	try {
	    PackageCoursesDTO plainTmp = plainBusiness.findById(plain.getId());

	    if (plainTmp == null) {
		addActionError("Impossibile aggiornare il pacchetto corsi: " + plain.getId());
		return "plain.insert.error";
	    }

	    if (StringUtils.isBlank(plain.getName())) {
		addActionError("Il campo 'Nome' è obbligatorio");
		return "plain.insert.error";
	    }

	    if (plain.getExpirationType() == null) {
		addActionError("Il campo 'Giorni validità' è obbligatorio");
		return "plain.insert.error";
	    }

	    if (plain.getAmount() < 0) {
		addActionError("Il campo 'Costo' è obbligatorio");
		return "plain.insert.error";
	    }

	    plainBusiness.update(plain);
	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return "plain.insert.success";
    }

    public String saveInsert() {
	if (logger.isDebugEnabled())
	    logger.debug("Salvataggio nuovo pacchetto corsi: " + plain.getName());
	try {
	    if (StringUtils.isBlank(plain.getName())) {
		addActionError("Il campo 'Nome' è obbligatorio");
		expirationTypes = Arrays.asList(ExpirationType.values());
		return "plain.insert.error";
	    }

	    if (plain.getExpirationType() == null) {
		addActionError("Il campo 'Giorni validità' è obbligatorio");
		expirationTypes = Arrays.asList(ExpirationType.values());
		return "plain.insert.error";
	    }

	    if (plain.getAmount() < 0) {
		addActionError("Il campo 'Costo' è obbligatorio");
		expirationTypes = Arrays.asList(ExpirationType.values());
		return "plain.insert.error";
	    }

	    plainBusiness.save(plain);
	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return "plain.insert.success";
    }

    public void setExpirationTypes(List<ExpirationType> expirationTypes) {
	this.expirationTypes = expirationTypes;
    }

    public void setPlain(PackageCoursesDTO plain) {
	this.plain = plain;
    }

    public void setPlainFind(InputStream plainFind) {
	this.plainFind = plainFind;
    }

    public void setPlainIDToDelete(long plainIDToDelete) {
	this.plainIDToDelete = plainIDToDelete;
    }

    public void setPlainIDToEdit(long plainIDToEdit) {
	this.plainIDToEdit = plainIDToEdit;
    }

    public void setPlains(Page<PackageCoursesDTO> plains) {
	this.plains = plains;
    }

    public void setPlainSelected(long plainSelected) {
	this.plainSelected = plainSelected;
    }

    public long getExpiredSelected() {
	return expiredSelected;
    }

    public void setExpiredSelected(long expiredSelected) {
	this.expiredSelected = expiredSelected;
    }

}