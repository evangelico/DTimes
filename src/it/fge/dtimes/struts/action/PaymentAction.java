package it.fge.dtimes.struts.action;

import it.fge.dtimes.business.deadline.DeadlineBusiness;
import it.fge.dtimes.business.payment.PaymentBusiness;
import it.fge.dtimes.business.plain.PlainBusiness;
import it.fge.dtimes.business.subscription.SubscriptionBusiness;
import it.fge.dtimes.model.deadline.DeadlineDTO;
import it.fge.dtimes.model.packageCourses.PackageCoursesDTO;
import it.fge.dtimes.model.payment.PaymentDTO;
import it.fge.dtimes.model.subscription.SubscriptionDTO;
import it.fge.dtimes.util.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

public class PaymentAction extends BaseActionSupport {
    private static final long serialVersionUID = 1L;

    private static Logger logger = Logger.getLogger(PaymentAction.class);

    @Autowired
    private SubscriptionBusiness subscriptionBusiness;

    @Autowired
    private PlainBusiness plainBusiness;

    @Autowired
    private PaymentBusiness paymentBusiness;

    @Autowired
    private DeadlineBusiness deadlineBusiness;

    private SubscriptionDTO subscription;

    private List<String> plainsSelected;
    private List<String> plainsSelectedExtra;

    private List<PackageCoursesDTO> plains = new ArrayList<PackageCoursesDTO>();

    // parametri di scambio
    private long subscriptionId;
    private long paymentId;
    private long subscriptionIDToShow;

    private String paymentDate;

    private PaymentDTO payment;

    private Page<SubscriptionDTO> subscriptions;

    private boolean addPayment;

    private long invoiceId;

    private Collection<PackageCoursesDTO> plainAlreadyPaied = new HashSet<PackageCoursesDTO>();

    private List<DeadlineDTO> deadlinesExpiredList = new ArrayList<DeadlineDTO>();

    private List<String> expiredSelected;

    private List<DeadlineDTO> expiredPaied = new ArrayList<DeadlineDTO>();

    public String edit() {
	if (logger.isDebugEnabled())
	    logger.debug("Modifico il pagamento: " + paymentId);
	try {
	    payment = paymentBusiness.findById(paymentId);
	    subscription = subscriptionBusiness.findById(payment.getSubscription().getId());
	    paymentDate = sdf.format(payment.getPaymentDate());
	    expiredPaied.addAll(deadlineBusiness.findByPayment(payment));
	    for (PackageCoursesDTO pc : payment.getPackagesCourses()) {
		if (pc.isSingleLesson()) {
		    plainAlreadyPaied.add(pc);
		}
	    }
	    deadlinesExpiredList = deadlineBusiness.findAllExpiredNoPaginated(subscription.getId());
	    plains = plainBusiness.findAllToSignleLesson();
	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return "payment.to.modify";
    }

    public long getInvoiceId() {
	return invoiceId;
    }

    public PaymentDTO getPayment() {
	return payment;
    }

    public String getPaymentDate() {
	return paymentDate;
    }

    public long getPaymentId() {
	return paymentId;
    }

    public Collection<PackageCoursesDTO> getPlainAlreadyPaied() {
	return plainAlreadyPaied;
    }

    public List<PackageCoursesDTO> getPlains() {
	return plains;
    }

    public List<String> getPlainsSelected() {
	return plainsSelected;
    }

    public SubscriptionDTO getSubscription() {
	return subscription;
    }

    public long getSubscriptionId() {
	return subscriptionId;
    }

    public long getSubscriptionIDToShow() {
	return subscriptionIDToShow;
    }

    public Page<SubscriptionDTO> getSubscriptions() {
	return subscriptions;
    }

    public String index() {
	if (logger.isDebugEnabled())
	    logger.debug("Lista iscrizione per nuovo pagamento");
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
	    logger.debug("Inserimento nuovo pagamento");
	try {
	    Calendar dateTmp = Calendar.getInstance();
	    dateTmp.set(Calendar.DAY_OF_MONTH, 1);
	    dateTmp.add(Calendar.MONTH, 1);
	    dateTmp.set(Calendar.HOUR_OF_DAY, 23);
	    dateTmp.set(Calendar.MINUTE, 59);
	    dateTmp.set(Calendar.SECOND, 59);
	    dateTmp.set(Calendar.MILLISECOND, 999);

	    subscription = subscriptionBusiness.findById(subscriptionId);
	   plains = plainBusiness.findAllToSignleLesson();
	    deadlinesExpiredList = deadlineBusiness.findAllExpiredNoPaginated(subscription.getId());
	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return SUCCESS;
    }

    public boolean isAddPayment() {
	return addPayment;
    }

    public String remove() {
	if (logger.isDebugEnabled())
	    logger.debug("Elimino il pagamento con id: " + paymentId);
	try {
	    PaymentDTO paymentTmp = paymentBusiness.findById(paymentId);
	    subscriptionIDToShow = paymentTmp.getSubscription().getId();
	    paymentBusiness.delete(paymentId);
	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return "payment.delete.success";
    }

    public String saveEdit() {
	if (logger.isDebugEnabled())
	    logger.debug("Salvataggio modifiche del pagamento " + payment.getId() + " del: " + paymentDate);
	try {
	    Calendar dateTmp = Calendar.getInstance();
	    dateTmp.set(Calendar.DAY_OF_MONTH, 1);
	    dateTmp.add(Calendar.MONTH, 1);
	    dateTmp.set(Calendar.HOUR_OF_DAY, 23);
	    dateTmp.set(Calendar.MINUTE, 59);
	    dateTmp.set(Calendar.SECOND, 59);
	    dateTmp.set(Calendar.MILLISECOND, 999);

	    if (StringUtils.isBlank(paymentDate)) {
		addActionError("Il campo 'Data pagamento' è obbligatorio");
		subscription = subscriptionBusiness.findById(payment.getSubscription().getId());
		deadlinesExpiredList = deadlineBusiness.findAllExpiredNoPaginated(subscription.getId());
		paymentDate = sdf.format(payment.getPaymentDate());
		expiredPaied.addAll(deadlineBusiness.findByPayment(payment));
		for (PackageCoursesDTO pc : payment.getPackagesCourses()) {
		    if (pc.isSingleLesson()) {
			plainAlreadyPaied.add(pc);
		    }
		}
		plains = plainBusiness.findAll(0, 999).getContent();
		plainsSelectedExtra.clear();
		plainsSelected.clear();
		expiredSelected.clear();
		return "payment.insert.error";
	    }
	    plainsSelected = Util.removeEmptyObjectInList(plainsSelected);
	    plainsSelectedExtra = Util.removeEmptyObjectInList(plainsSelectedExtra);
	    if (plainsSelected.isEmpty() && plainsSelectedExtra.isEmpty()) {
		addActionError("Selezionare almeno un pacchetto corsi");
		subscription = subscriptionBusiness.findById(subscription.getId());
		paymentDate = sdf.format(payment.getPaymentDate());
		deadlinesExpiredList = deadlineBusiness.findAllExpiredNoPaginated(subscription.getId());
		expiredPaied.addAll(deadlineBusiness.findByPayment(payment));
		for (PackageCoursesDTO pc : payment.getPackagesCourses()) {
		    if (pc.isSingleLesson()) {
			plainAlreadyPaied.add(pc);
		    }
		}
		plains = plainBusiness.findAll(0, 999).getContent();
		plainsSelectedExtra.clear();
		plainsSelected.clear();
		expiredSelected.clear();
		return "payment.insert.error";
	    }

	    Calendar paymentD = Calendar.getInstance();
	    paymentD.setTime(sdf.parse(paymentDate));

	    payment.setPaymentDate(paymentD.getTime());

	    double amountToPaid = 0;

	    for (String pc : plainsSelected) {
		if (StringUtils.isNotBlank(pc.trim())) {
		    PackageCoursesDTO plainTmp = plainBusiness.findById(Long.parseLong(pc));
		    if (plainTmp != null) {
			amountToPaid = amountToPaid + plainTmp.getAmount();
		    }
		}
	    }
	    
	    for (String pc : plainsSelectedExtra) {
		if (StringUtils.isNotBlank(pc.trim())) {
		    PackageCoursesDTO plainTmp = plainBusiness.findById(Long.parseLong(pc));
		    if (plainTmp != null) {
			amountToPaid = amountToPaid + plainTmp.getAmount();
		    }
		}
	    }


	    if (payment.getPercentageDiscount() > 0) {
		amountToPaid = amountToPaid - ((amountToPaid * payment.getPercentageDiscount()) / 100);
	    }

	    plainsSelected = Util.removeDuplcatesStringInList(plainsSelected);
	    plainsSelectedExtra = Util.removeDuplcatesStringInList(plainsSelectedExtra);
	    for (String pc : plainsSelected) {
		if (StringUtils.isNotBlank(pc.trim())) {
		    PackageCoursesDTO plainTmp = plainBusiness.findById(Long.parseLong(pc));
		    if (plainTmp != null) {
			payment.getPackagesCourses().add(plainTmp);
		    }
		}
	    }
	    
	    for (String pc : plainsSelectedExtra) {
		if (StringUtils.isNotBlank(pc.trim())) {
		    PackageCoursesDTO plainTmp = plainBusiness.findById(Long.parseLong(pc));
		    if (plainTmp != null) {
			payment.getPackagesCourses().add(plainTmp);
		    }
		}
	    }

	    if (amountToPaid != payment.getAmountPaied()) {
		addActionError("L'importo pagato differisce dall'importo dovuto");
		subscription = subscriptionBusiness.findById(subscription.getId());
		plainsSelectedExtra.clear();
		plainsSelected.clear();
		expiredSelected.clear();
		paymentDate = sdf.format(payment.getPaymentDate());
		deadlinesExpiredList = deadlineBusiness.findAllExpiredNoPaginated(subscription.getId());
		expiredPaied.addAll(deadlineBusiness.findByPayment(payment));
		for (PackageCoursesDTO pc : payment.getPackagesCourses()) {
		    if (pc.isSingleLesson()) {
			plainAlreadyPaied.add(pc);
		    }
		}
		plains = plainBusiness.findAll(0, 999).getContent();
		return "payment.insert.error";
	    }

	    payment = paymentBusiness.update(payment, expiredSelected);
	    paymentId = payment.getId();
	    invoiceId = payment.getInvoice().getId();

	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return "payment.stamp.invoice";
    }

    public String saveInsert() {
	if (logger.isDebugEnabled())
	    logger.debug("Salvataggio nuovo pagamento del: " + paymentDate);
	try {
	    Calendar dateTmp = Calendar.getInstance();
	    dateTmp.set(Calendar.DAY_OF_MONTH, 1);
	    dateTmp.add(Calendar.MONTH, 1);
	    dateTmp.set(Calendar.HOUR_OF_DAY, 23);
	    dateTmp.set(Calendar.MINUTE, 59);
	    dateTmp.set(Calendar.SECOND, 59);
	    dateTmp.set(Calendar.MILLISECOND, 999);

	    if (StringUtils.isBlank(paymentDate)) {
		addActionError("Il campo 'Data pagamento' è obbligatorio");
		subscription = subscriptionBusiness.findById(subscription.getId());
		deadlinesExpiredList = deadlineBusiness.findAllExpiredNoPaginated(subscription.getId());
		plains = plainBusiness.findAll(0, 999).getContent();
		plainsSelectedExtra.clear();
		plainsSelected.clear();
		expiredSelected.clear();
		payment.setAmountPaied(0);
		return "payment.insert.error";
	    }

	    subscription = subscriptionBusiness.findById(subscription.getId());
	    plainsSelected = Util.removeEmptyObjectInList(plainsSelected);
	    plainsSelectedExtra = Util.removeEmptyObjectInList(plainsSelectedExtra);
	    if (plainsSelected.isEmpty() && plainsSelectedExtra.isEmpty()) {
		addActionError("Selezionare almeno un pacchetto corsi");
		subscription = subscriptionBusiness.findById(subscription.getId());
		deadlinesExpiredList = deadlineBusiness.findAllExpiredNoPaginated(subscription.getId());
		plains = plainBusiness.findAll(0, 999).getContent();
		plainsSelectedExtra.clear();
		plainsSelected.clear();
		expiredSelected.clear();
		payment.setAmountPaied(0);
		return "payment.insert.error";
	    }

	    Calendar paymentD = Calendar.getInstance();
	    paymentD.setTime(sdf.parse(paymentDate));

	    payment.setPaymentDate(paymentD.getTime());

	    double amountToPaid = 0;
	    for (String pc : plainsSelected) {
		if (StringUtils.isNotBlank(pc.trim())) {
		    PackageCoursesDTO plainTmp = plainBusiness.findById(Long.parseLong(pc));
		    if (plainTmp != null) {
			amountToPaid = amountToPaid + plainTmp.getAmount();
		    }
		}
	    }

	    for (String pc : plainsSelectedExtra) {
		if (StringUtils.isNotBlank(pc.trim())) {
		    PackageCoursesDTO plainTmp = plainBusiness.findById(Long.parseLong(pc));
		    if (plainTmp != null) {
			amountToPaid = amountToPaid + plainTmp.getAmount();
		    }
		}
	    }

	    if (payment.getPercentageDiscount() > 0) {
		amountToPaid = amountToPaid - ((amountToPaid * payment.getPercentageDiscount()) / 100);
	    }

	    plainsSelected = Util.removeDuplcatesStringInList(plainsSelected);
	    plainsSelectedExtra = Util.removeDuplcatesStringInList(plainsSelectedExtra);
	    for (String pc : plainsSelected) {
		if (StringUtils.isNotBlank(pc.trim())) {
		    PackageCoursesDTO plainTmp = plainBusiness.findById(Long.parseLong(pc));
		    if (plainTmp != null) {
			payment.getPackagesCourses().add(plainTmp);
		    }
		}
	    }

	    for (String pc : plainsSelectedExtra) {
		if (StringUtils.isNotBlank(pc.trim())) {
		    PackageCoursesDTO plainTmp = plainBusiness.findById(Long.parseLong(pc));
		    if (plainTmp != null) {
			payment.getPackagesCourses().add(plainTmp);
		    }
		}
	    }

	    if (amountToPaid != payment.getAmountPaied()) {
		addActionError("L'importo pagato differisce dall'importo dovuto");
		subscription = subscriptionBusiness.findById(subscription.getId());
		deadlinesExpiredList = deadlineBusiness.findAllExpiredNoPaginated(subscription.getId());
		plains = plainBusiness.findAll(0, 999).getContent();
		plainsSelectedExtra.clear();
		plainsSelected.clear();
		expiredSelected.clear();
		payment.setAmountPaied(0);
		return "payment.insert.error";
	    }

	    payment.setSubscription(subscription);
	    payment = paymentBusiness.save(payment, expiredSelected);

	    paymentId = payment.getId();

	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return "payment.insert.invoice";
    }

    public void setAddPayment(boolean addPayment) {
	this.addPayment = addPayment;
    }

    public void setInvoiceId(long invoiceId) {
	this.invoiceId = invoiceId;
    }

    public void setPayment(PaymentDTO payment) {
	this.payment = payment;
    }

    public void setPaymentDate(String paymentDate) {
	this.paymentDate = paymentDate;
    }

    public void setPaymentId(long paymentId) {
	this.paymentId = paymentId;
    }

    public void setPlainAlreadyPaied(Collection<PackageCoursesDTO> plainAlreadyPaied) {
	this.plainAlreadyPaied = plainAlreadyPaied;
    }

    public void setPlains(List<PackageCoursesDTO> plains) {
	this.plains = plains;
    }

    public void setPlainsSelected(List<String> plainsSelected) {
	this.plainsSelected = plainsSelected;
    }

    public void setSubscription(SubscriptionDTO subscription) {
	this.subscription = subscription;
    }

    public void setSubscriptionId(long subscriptionId) {
	this.subscriptionId = subscriptionId;
    }

    public void setSubscriptionIDToShow(long subscriptionIDToShow) {
	this.subscriptionIDToShow = subscriptionIDToShow;
    }

    public void setSubscriptions(Page<SubscriptionDTO> subscriptions) {
	this.subscriptions = subscriptions;
    }

    public List<DeadlineDTO> getDeadlinesExpiredList() {
	return deadlinesExpiredList;
    }

    public void setDeadlinesExpiredList(List<DeadlineDTO> deadlinesExpiredList) {
	this.deadlinesExpiredList = deadlinesExpiredList;
    }

    public List<String> getExpiredSelected() {
	return expiredSelected;
    }

    public void setExpiredSelected(List<String> expiredSelected) {
	this.expiredSelected = expiredSelected;
    }

    public List<DeadlineDTO> getExpiredPaied() {
	return expiredPaied;
    }

    public void setExpiredPaied(List<DeadlineDTO> expiredPaied) {
	this.expiredPaied = expiredPaied;
    }

    public List<String> getPlainsSelectedExtra() {
	return plainsSelectedExtra;
    }

    public void setPlainsSelectedExtra(List<String> plainsSelectedExtra) {
	this.plainsSelectedExtra = plainsSelectedExtra;
    }

}