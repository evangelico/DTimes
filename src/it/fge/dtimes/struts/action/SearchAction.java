package it.fge.dtimes.struts.action;

import it.fge.dtimes.business.deadline.DeadlineBusiness;
import it.fge.dtimes.business.payment.PaymentBusiness;
import it.fge.dtimes.business.plain.PlainBusiness;
import it.fge.dtimes.business.subscription.SubscriptionBusiness;
import it.fge.dtimes.model.deadline.DeadlineDTO;
import it.fge.dtimes.model.packageCourses.PackageCoursesDTO;
import it.fge.dtimes.model.payment.PaymentDTO;
import it.fge.dtimes.model.subscription.SubscriptionDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

public class SearchAction extends BaseActionSupport {
    private static final long serialVersionUID = 1L;

    private static Logger logger = Logger.getLogger(SearchAction.class);

    private Page<DeadlineDTO> deadlines;

    private double totalUnpaid;

    private double totalPaid;

    private double teacherToPaid;

    private String paymentPeriod;
    private Map<String, String> paymentsPeriod = new HashMap<String, String>();

    private SimpleDateFormat sdfPeriodCod = new SimpleDateFormat("MM_yyyy", Locale.ITALIAN);
    private SimpleDateFormat sdfPeriodLabel = new SimpleDateFormat("MMMM yyyy", Locale.ITALIAN);

    private Page<PaymentDTO> payments;

    private Page<SubscriptionDTO> subscriptions;

    private long plainId;

    private Page<PackageCoursesDTO> plains;

    private List<PackageCoursesDTO> plainsSelectList = new ArrayList<PackageCoursesDTO>();

    private long deadlineId;

    @Autowired
    private PlainBusiness plainBusiness;

    @Autowired
    private DeadlineBusiness deadlineBusiness;

    @Autowired
    private PaymentBusiness paymentBusiness;

    @Autowired
    private SubscriptionBusiness subscriptionBusiness;

    public String disableDeadline() {
	if (logger.isDebugEnabled())
	    logger.debug("Nascondo la scandeza con id: " + deadlineId);
	try {

	    if (deadlineId > 0) {
		deadlineBusiness.disableDeadline(deadlineId);
	    }

	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}

	return "deadline.disable.ok";
    }

    public String enableDeadline() {
	if (logger.isDebugEnabled())
	    logger.debug("Attivo la scandeza con id: " + deadlineId);
	try {

	    if (deadlineId > 0) {
		deadlineBusiness.enableDeadline(deadlineId);
	    }

	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}

	return "deadline.enable.ok";
    }

    public String certificatesExpired() {
	if (logger.isDebugEnabled())
	    logger.debug("Visualizzo la lista degli iscritti con certificato medico assente o scaduto");
	try {

	    if (StringUtils.isNotBlank(nameFilter)) {
		subscriptions = subscriptionBusiness.findAllCertificatesExpiredLikeNominative(page, size, new Date(), nameFilter);
	    } else {
		subscriptions = subscriptionBusiness.findAllCertificatesExpired(page, size, new Date());
	    }
	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}

	return SUCCESS;
    }

    public long getCountSubscriptionByPlain(long plainId) {
	if (logger.isDebugEnabled())
	    logger.debug("Numero iscritti per il corso: " + plainId + " nel periodo: " + paymentPeriod);
	long count = 0;
	try {
	    count = subscriptionBusiness.countByPlainInPeriod(plainId, null, null);

	} catch (Exception e) {
	    logger.error("Errore", e);
	    count = 0;
	}

	return count;
    }

    public Page<DeadlineDTO> getDeadlines() {
	return deadlines;
    }

    public String getPaymentPeriod() {
	return paymentPeriod;
    }

    public Page<PaymentDTO> getPayments() {
	return payments;
    }

    public Map<String, String> getPaymentsPeriod() {
	return paymentsPeriod;
    }

    public long getPlainId() {
	return plainId;
    }

    public Page<PackageCoursesDTO> getPlains() {
	return plains;
    }

    public List<PackageCoursesDTO> getPlainsSelectList() {
	return plainsSelectList;
    }

    public Page<SubscriptionDTO> getSubscriptions() {
	return subscriptions;
    }

    public double getTeacherToPaid() {
	return teacherToPaid;
    }

    public double getTotalAmountByPlain(long plainId, String paymentPeriod) {
	if (logger.isDebugEnabled())
	    logger.debug("Incasso totale il corso: " + plainId + " nel periodo: " + paymentPeriod);
	double totalAmount = 0;
	try {
	    PackageCoursesDTO plainTmp = plainBusiness.findById(plainId);

	    if (StringUtils.isNotBlank(paymentPeriod)) {
		Calendar startPeriod = Calendar.getInstance();
		String month = paymentPeriod.trim().split("_")[0];
		String year = paymentPeriod.trim().split("_")[1];
		startPeriod.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		startPeriod.set(Calendar.YEAR, Integer.parseInt(year));
		startPeriod.set(Calendar.DAY_OF_MONTH, 1);
		startPeriod.set(Calendar.HOUR_OF_DAY, 0);
		startPeriod.set(Calendar.MINUTE, 0);
		startPeriod.set(Calendar.SECOND, 0);
		startPeriod.set(Calendar.MILLISECOND, 0);

		Calendar endPeriod = Calendar.getInstance();
		endPeriod.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		endPeriod.set(Calendar.YEAR, Integer.parseInt(year));
		endPeriod.set(Calendar.DAY_OF_MONTH, endPeriod.getActualMaximum(Calendar.DAY_OF_MONTH));
		endPeriod.set(Calendar.HOUR_OF_DAY, 23);
		endPeriod.set(Calendar.MINUTE, 59);
		endPeriod.set(Calendar.SECOND, 59);
		endPeriod.set(Calendar.MILLISECOND, 999);
		if (!plainTmp.isSingleLesson()) {
		    long countPayment = deadlineBusiness.countByPlainInPeriod(startPeriod.getTime(), endPeriod.getTime(), plainTmp);
		    totalAmount = countPayment * plainTmp.getAmount();
		} else {
		    long countPayment = paymentBusiness.getCountByPlainInPeriod(startPeriod.getTime(), endPeriod.getTime(), plainTmp.getId());
		    totalAmount = countPayment * plainTmp.getAmount();
		}
	    } else {
		if (!plainTmp.isSingleLesson()) {
		    long countPayment = deadlineBusiness.countByPlain(plainTmp);
		    totalAmount = countPayment * plainTmp.getAmount();
		} else {
		    long countPayment = paymentBusiness.getCountByPlain(plainTmp.getId());
		    totalAmount = countPayment * plainTmp.getAmount();
		}
	    }

	} catch (Exception e) {
	    logger.error("Errore", e);
	    totalAmount = 0;
	}

	return totalAmount;
    }

    public double getTotalPaid() {
	return totalPaid;
    }

    public String deadlinesHide() {
	if (logger.isDebugEnabled())
	    logger.debug("Visualizzo la lista delle scadenza nascoste");
	totalUnpaid = 0;
	try {
	    Calendar dateTmp = Calendar.getInstance();
	    dateTmp.set(Calendar.DAY_OF_MONTH, 1);
	    dateTmp.add(Calendar.MONTH, 1);
	    dateTmp.set(Calendar.HOUR_OF_DAY, 23);
	    dateTmp.set(Calendar.MINUTE, 59);
	    dateTmp.set(Calendar.SECOND, 59);
	    dateTmp.set(Calendar.MILLISECOND, 999);

	    plainsSelectList = plainBusiness.findAll(0, 999).getContent();

	    if (StringUtils.isNotBlank(nameFilter) && plainFilter > 0) {
		deadlines = deadlineBusiness.findAllHideLikeNominativeAndPlain(page, size, dateTmp.getTime(), nameFilter, plainFilter);
		if (deadlines.getContent() != null && !deadlines.getContent().isEmpty()) {
		    totalUnpaid = deadlineBusiness.sumAllHideLikeNominativeAndPlain(dateTmp.getTime(), nameFilter, plainFilter);
		}
	    } else if (StringUtils.isNotBlank(nameFilter)) {
		deadlines = deadlineBusiness.findAllHideLikeNominative(page, size, dateTmp.getTime(), nameFilter);
		if (deadlines.getContent() != null && !deadlines.getContent().isEmpty()) {
		    totalUnpaid = deadlineBusiness.sumAllHideLikeNominative(dateTmp.getTime(), nameFilter);
		}
	    } else if (plainFilter > 0) {
		deadlines = deadlineBusiness.findAllHideByPlain(page, size, dateTmp.getTime(), plainFilter);
		if (deadlines.getContent() != null && !deadlines.getContent().isEmpty()) {
		    totalUnpaid = deadlineBusiness.sumAllHideByPlain(dateTmp.getTime(), plainFilter);
		}
	    } else {
		deadlines = deadlineBusiness.findAllHide(page, size, dateTmp.getTime());
		if (deadlines.getContent() != null && !deadlines.getContent().isEmpty()) {
		    totalUnpaid = deadlineBusiness.sumAllHide(dateTmp.getTime());
		}
	    }
	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return SUCCESS;
    }

    public double getTotalToTeacherByPlain(long plainId, String paymentPeriod) {
	if (logger.isDebugEnabled())
	    logger.debug("Corrispettivo per il corso: " + plainId + " nel periodo: " + paymentPeriod);
	double totalToTeacher = 0;
	try {
	    PackageCoursesDTO plainTmp = plainBusiness.findById(plainId);
	    if (plainTmp.getTeacherPercentage() > 0) {
		if (StringUtils.isNotBlank(paymentPeriod)) {
		    Calendar startPeriod = Calendar.getInstance();
		    String month = paymentPeriod.trim().split("_")[0];
		    String year = paymentPeriod.trim().split("_")[1];
		    startPeriod.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		    startPeriod.set(Calendar.YEAR, Integer.parseInt(year));
		    startPeriod.set(Calendar.DAY_OF_MONTH, 1);
		    startPeriod.set(Calendar.HOUR_OF_DAY, 0);
		    startPeriod.set(Calendar.MINUTE, 0);
		    startPeriod.set(Calendar.SECOND, 0);
		    startPeriod.set(Calendar.MILLISECOND, 0);

		    Calendar endPeriod = Calendar.getInstance();
		    endPeriod.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		    endPeriod.set(Calendar.YEAR, Integer.parseInt(year));
		    endPeriod.set(Calendar.DAY_OF_MONTH, endPeriod.getActualMaximum(Calendar.DAY_OF_MONTH));
		    endPeriod.set(Calendar.HOUR_OF_DAY, 23);
		    endPeriod.set(Calendar.MINUTE, 59);
		    endPeriod.set(Calendar.SECOND, 59);
		    endPeriod.set(Calendar.MILLISECOND, 999);
		    if (!plainTmp.isSingleLesson()) {
			long countPayment = deadlineBusiness.countByPlainInPeriod(startPeriod.getTime(), endPeriod.getTime(), plainTmp);
			long totalAmountTmp = countPayment * plainTmp.getAmount();

			totalToTeacher = (totalAmountTmp * plainTmp.getTeacherPercentage()) / 100;
		    } else {
			long countPayment = paymentBusiness.getCountByPlainInPeriod(startPeriod.getTime(), endPeriod.getTime(), plainTmp.getId());
			long totalAmountTmp = countPayment * plainTmp.getAmount();

			totalToTeacher = (totalAmountTmp * plainTmp.getTeacherPercentage()) / 100;
		    }
		} else {
		    if (!plainTmp.isSingleLesson()) {
			long countPayment = deadlineBusiness.countByPlain(plainTmp);
			long totalAmountTmp = countPayment * plainTmp.getAmount();
			totalToTeacher = (totalAmountTmp * plainTmp.getTeacherPercentage()) / 100;
		    } else {
			long countPayment = paymentBusiness.getCountByPlain(plainTmp.getId());
			long totalAmountTmp = countPayment * plainTmp.getAmount();
			totalToTeacher = (totalAmountTmp * plainTmp.getTeacherPercentage()) / 100;
		    }
		}
	    }

	} catch (Exception e) {
	    logger.error("Errore", e);
	    totalToTeacher = 0;
	}

	return totalToTeacher;
    }

    public double getTotalUnpaid() {
	return totalUnpaid;
    }

    public String paymentExpired() {
	if (logger.isDebugEnabled())
	    logger.debug("Visualizzo la lista degli iscritti con abbonamenti scaduti");
	totalUnpaid = 0;
	try {
	    Calendar dateTmp = Calendar.getInstance();
	    dateTmp.set(Calendar.DAY_OF_MONTH, 1);
	    dateTmp.add(Calendar.MONTH, 1);
	    dateTmp.set(Calendar.HOUR_OF_DAY, 23);
	    dateTmp.set(Calendar.MINUTE, 59);
	    dateTmp.set(Calendar.SECOND, 59);
	    dateTmp.set(Calendar.MILLISECOND, 999);

	    plainsSelectList = plainBusiness.findAll(0, 999).getContent();

	    if (StringUtils.isNotBlank(nameFilter) && plainFilter > 0) {
		deadlines = deadlineBusiness.findAllExpiredLikeNominativeAndPlain(page, size, dateTmp.getTime(), nameFilter, plainFilter);
		if (deadlines.getContent() != null && !deadlines.getContent().isEmpty()) {
		    totalUnpaid = deadlineBusiness.sumAllExpiredLikeNominativeAndPlain(dateTmp.getTime(), nameFilter, plainFilter);
		}
	    } else if (StringUtils.isNotBlank(nameFilter)) {
		deadlines = deadlineBusiness.findAllExpiredLikeNominative(page, size, dateTmp.getTime(), nameFilter);
		if (deadlines.getContent() != null && !deadlines.getContent().isEmpty()) {
		    totalUnpaid = deadlineBusiness.sumAllExpiredLikeNominative(dateTmp.getTime(), nameFilter);
		}
	    } else if (plainFilter > 0) {
		deadlines = deadlineBusiness.findAllExpiredByPlain(page, size, dateTmp.getTime(), plainFilter);
		if (deadlines.getContent() != null && !deadlines.getContent().isEmpty()) {
		    totalUnpaid = deadlineBusiness.sumAllExpiredByPlain(dateTmp.getTime(), plainFilter);
		}
	    } else {
		deadlines = deadlineBusiness.findAllExpired(page, size, dateTmp.getTime());
		if (deadlines.getContent() != null && !deadlines.getContent().isEmpty()) {
		    totalUnpaid = deadlineBusiness.sumAllExpired(dateTmp.getTime());
		}
	    }
	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}

	return SUCCESS;
    }

    public String payments() {
	if (logger.isDebugEnabled())
	    logger.debug("Visualizzo la lista dei pagamenti effettuati per il periodo: " + paymentPeriod + " - corso: " + plainId);
	try {
	    totalPaid = 0;
	    Calendar now = Calendar.getInstance();
	    if (StringUtils.isNotBlank(paymentPeriod) && plainId > 0) {
		Calendar startPeriod = Calendar.getInstance();
		String month = paymentPeriod.trim().split("_")[0];
		String year = paymentPeriod.trim().split("_")[1];
		startPeriod.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		startPeriod.set(Calendar.YEAR, Integer.parseInt(year));
		startPeriod.set(Calendar.DAY_OF_MONTH, 1);
		startPeriod.set(Calendar.HOUR_OF_DAY, 0);
		startPeriod.set(Calendar.MINUTE, 0);
		startPeriod.set(Calendar.SECOND, 0);
		startPeriod.set(Calendar.MILLISECOND, 0);

		Calendar endPeriod = Calendar.getInstance();
		endPeriod.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		endPeriod.set(Calendar.YEAR, Integer.parseInt(year));
		endPeriod.set(Calendar.DAY_OF_MONTH, endPeriod.getActualMaximum(Calendar.DAY_OF_MONTH));
		endPeriod.set(Calendar.HOUR_OF_DAY, 23);
		endPeriod.set(Calendar.MINUTE, 59);
		endPeriod.set(Calendar.SECOND, 59);
		endPeriod.set(Calendar.MILLISECOND, 999);

		if (StringUtils.isBlank(nameFilter)) {
		    payments = paymentBusiness.findAllInPeriodByPlain(startPeriod.getTime(), endPeriod.getTime(), plainId, page, size);
		} else {
		    payments = paymentBusiness.findAllInPeriodByPlainAdnNominative(startPeriod.getTime(), endPeriod.getTime(), plainId, nameFilter, page, size);
		}
		if (payments.getContent() != null && !payments.getContent().isEmpty()) {
		    PackageCoursesDTO plainTmp = plainBusiness.findById(plainId);
		    for (PaymentDTO pay : payments.getContent()) {
			if (StringUtils.isBlank(nameFilter)) {
			    long dlCount = deadlineBusiness.countByPaymentAndPlain(pay, plainTmp);
			    totalPaid = totalPaid + (dlCount * plainTmp.getAmount());
			} else {
			    long dlCount = deadlineBusiness.countByPaymentAndPlainAndNominative(pay, plainTmp, nameFilter);
			    totalPaid = totalPaid + (dlCount * plainTmp.getAmount());
			}
		    }

		    if (totalPaid > 0) {
			if (plainTmp != null && plainTmp.getTeacherPercentage() > 0) {
			    teacherToPaid = (totalPaid * plainTmp.getTeacherPercentage()) / 100;
			}
		    }
		}

	    } else if (StringUtils.isNotBlank(paymentPeriod)) {
		Calendar startPeriod = Calendar.getInstance();
		String month = paymentPeriod.trim().split("_")[0];
		String year = paymentPeriod.trim().split("_")[1];
		startPeriod.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		startPeriod.set(Calendar.YEAR, Integer.parseInt(year));
		startPeriod.set(Calendar.DAY_OF_MONTH, 1);
		startPeriod.set(Calendar.HOUR_OF_DAY, 0);
		startPeriod.set(Calendar.MINUTE, 0);
		startPeriod.set(Calendar.SECOND, 0);
		startPeriod.set(Calendar.MILLISECOND, 0);

		Calendar endPeriod = Calendar.getInstance();
		endPeriod.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		endPeriod.set(Calendar.YEAR, Integer.parseInt(year));
		endPeriod.set(Calendar.DAY_OF_MONTH, endPeriod.getActualMaximum(Calendar.DAY_OF_MONTH));
		endPeriod.set(Calendar.HOUR_OF_DAY, 23);
		endPeriod.set(Calendar.MINUTE, 59);
		endPeriod.set(Calendar.SECOND, 59);
		endPeriod.set(Calendar.MILLISECOND, 999);
		if (StringUtils.isBlank(nameFilter)) {
		    payments = paymentBusiness.findAllInPeriod(startPeriod.getTime(), endPeriod.getTime(), page, size);
		} else {
		    payments = paymentBusiness.findAllInPeriodByNominative(startPeriod.getTime(), endPeriod.getTime(), nameFilter, page, size);
		}
		if (payments.getContent() != null && !payments.getContent().isEmpty()) {
		    if (StringUtils.isBlank(nameFilter)) {
			totalPaid = paymentBusiness.getImportPaiedInPeriod(startPeriod.getTime(), endPeriod.getTime()).doubleValue();
		    } else {
			totalPaid = paymentBusiness.getImportPaiedInPeriodByNominative(startPeriod.getTime(), endPeriod.getTime(), nameFilter).doubleValue();
		    }

		}
	    } else if (plainId > 0) {
		if (StringUtils.isBlank(nameFilter)) {
		    payments = paymentBusiness.findAllByPlain(plainId, page, size);
		} else {
		    payments = paymentBusiness.findAllByPlainAndNominative(plainId, nameFilter, page, size);
		}
		if (payments.getContent() != null && !payments.getContent().isEmpty()) {
		    PackageCoursesDTO plainTmp = plainBusiness.findById(plainId);
		    for (PaymentDTO pay : payments.getContent()) {
			if (StringUtils.isBlank(nameFilter)) {
			    long dlCount = deadlineBusiness.countByPaymentAndPlain(pay, plainTmp);
			    totalPaid = totalPaid + (dlCount * plainTmp.getAmount());
			} else {
			    long dlCount = deadlineBusiness.countByPaymentAndPlainAndNominative(pay, plainTmp, nameFilter);
			    totalPaid = totalPaid + (dlCount * plainTmp.getAmount());
			}
		    }

		    if (totalPaid > 0) {
			if (plainTmp != null && plainTmp.getTeacherPercentage() > 0) {
			    teacherToPaid = (totalPaid * plainTmp.getTeacherPercentage()) / 100;
			}
		    }
		}
	    } else if (StringUtils.isNotBlank(nameFilter)) {
		payments = paymentBusiness.findAllByNominative(nameFilter, page, size);
		if (payments.getContent() != null && !payments.getContent().isEmpty()) {
		    totalPaid = paymentBusiness.getImportPaiedByNominative(nameFilter).doubleValue();
		}
	    } else {
		payments = paymentBusiness.findAll(page, size);
		if (payments.getContent() != null && !payments.getContent().isEmpty()) {
		    totalPaid = paymentBusiness.getImportPaied().doubleValue();
		}
	    }

	    Calendar startDate = Calendar.getInstance();
	    SubscriptionDTO subscriptionTmp = subscriptionBusiness.getFirstSubscription(0, 1);
	    if (subscriptionTmp == null) {
		startDate.set(Calendar.DAY_OF_MONTH, 1);
		startDate.set(Calendar.MONTH, 8);
		startDate.add(Calendar.YEAR, -1);
		startDate.set(Calendar.HOUR_OF_DAY, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.MILLISECOND, 000);
	    } else {
		startDate.setTime(subscriptionTmp.getRegistrationDate());

		do {
		    paymentsPeriod.put(sdfPeriodCod.format(startDate.getTime()), StringUtils.capitalize(sdfPeriodLabel.format(startDate.getTime())));
		    startDate.add(Calendar.MONTH, 1);
		} while (startDate.before(now));
	    }

	    plains = plainBusiness.findAll(0, 9999);

	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}

	return SUCCESS;
    }

    public String plains() {
	if (logger.isDebugEnabled())
	    logger.debug("Visualizzo gli incassi per corso nel periodo: " + paymentPeriod);
	totalUnpaid = 0;
	try {
	    totalPaid = 0;
	    teacherToPaid = 0;
	    Calendar now = Calendar.getInstance();

	    if (StringUtils.isNotBlank(paymentPeriod)) {
		Calendar startPeriod = Calendar.getInstance();
		String month = paymentPeriod.trim().split("_")[0];
		String year = paymentPeriod.trim().split("_")[1];
		startPeriod.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		startPeriod.set(Calendar.YEAR, Integer.parseInt(year));
		startPeriod.set(Calendar.DAY_OF_MONTH, 1);
		startPeriod.set(Calendar.HOUR_OF_DAY, 0);
		startPeriod.set(Calendar.MINUTE, 0);
		startPeriod.set(Calendar.SECOND, 0);
		startPeriod.set(Calendar.MILLISECOND, 0);

		Calendar endPeriod = Calendar.getInstance();
		endPeriod.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		endPeriod.set(Calendar.YEAR, Integer.parseInt(year));
		endPeriod.set(Calendar.DAY_OF_MONTH, endPeriod.getActualMaximum(Calendar.DAY_OF_MONTH));
		endPeriod.set(Calendar.HOUR_OF_DAY, 23);
		endPeriod.set(Calendar.MINUTE, 59);
		endPeriod.set(Calendar.SECOND, 59);
		endPeriod.set(Calendar.MILLISECOND, 999);

		plains = plainBusiness.findAll(page, size);
		if (plains.getContent() != null && !plains.getContent().isEmpty()) {
		    for (PackageCoursesDTO pc : plains) {
			if (!pc.isSingleLesson()) {
			    long countDl = deadlineBusiness.countByPlainInPeriod(startPeriod.getTime(), endPeriod.getTime(), (pc));
			    long totalPaidTmp = countDl * pc.getAmount();
			    totalPaid = totalPaid + totalPaidTmp;
			    teacherToPaid = teacherToPaid + ((totalPaidTmp * pc.getTeacherPercentage()) / 100);
			} else {
			    long totalPaidTmp = pc.getAmount();
			    totalPaid = totalPaid + totalPaidTmp;
			    teacherToPaid = teacherToPaid + ((totalPaidTmp * pc.getTeacherPercentage()) / 100);
			}
		    }
		}

	    } else {
		plains = plainBusiness.findAll(page, size);
		if (plains.getContent() != null && !plains.getContent().isEmpty()) {
		    for (PackageCoursesDTO pc : plains) {
			if (!pc.isSingleLesson()) {
			    long countDl = deadlineBusiness.countByPlain(pc);

			    long totalPaidTmp = pc.getAmount() * countDl;
			    totalPaid = totalPaid + totalPaidTmp;
			    teacherToPaid = teacherToPaid + ((totalPaidTmp * pc.getTeacherPercentage()) / 100);
			} else {
			    long totalPaidTmp = pc.getAmount();
			    totalPaid = totalPaid + totalPaidTmp;
			    teacherToPaid = teacherToPaid + ((totalPaidTmp * pc.getTeacherPercentage()) / 100);
			}
		    }
		}
	    }

	    Calendar startDate = Calendar.getInstance();
	    SubscriptionDTO subscriptionTmp = subscriptionBusiness.getFirstSubscription(0, 1);
	    if (subscriptionTmp == null) {
		startDate.set(Calendar.DAY_OF_MONTH, 1);
		startDate.set(Calendar.MONTH, 8);
		startDate.add(Calendar.YEAR, -1);
		startDate.set(Calendar.HOUR_OF_DAY, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.MILLISECOND, 000);
	    } else {
		startDate.setTime(subscriptionTmp.getRegistrationDate());

		do {
		    paymentsPeriod.put(sdfPeriodCod.format(startDate.getTime()), StringUtils.capitalize(sdfPeriodLabel.format(startDate.getTime())));
		    startDate.add(Calendar.MONTH, 1);
		} while (startDate.before(now));
	    }

	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}

	return SUCCESS;
    }

    public void setDeadlines(Page<DeadlineDTO> deadlines) {
	this.deadlines = deadlines;
    }

    public void setPaymentPeriod(String paymentPeriod) {
	this.paymentPeriod = paymentPeriod;
    }

    public void setPayments(Page<PaymentDTO> payments) {
	this.payments = payments;
    }

    public void setPaymentsPeriod(Map<String, String> paymentsPeriod) {
	this.paymentsPeriod = paymentsPeriod;
    }

    public void setPlainId(long plainId) {
	this.plainId = plainId;
    }

    public void setPlains(Page<PackageCoursesDTO> plains) {
	this.plains = plains;
    }

    public void setPlainsSelectList(List<PackageCoursesDTO> plainsSelectList) {
	this.plainsSelectList = plainsSelectList;
    }

    public void setSubscriptions(Page<SubscriptionDTO> subscriptions) {
	this.subscriptions = subscriptions;
    }

    public void setTeacherToPaid(double teacherToPaid) {
	this.teacherToPaid = teacherToPaid;
    }

    public void setTotalPaid(double totalPaid) {
	this.totalPaid = totalPaid;
    }

    public void setTotalUnpaid(double totalUnpaid) {
	this.totalUnpaid = totalUnpaid;
    }

    public long getDeadlineId() {
	return deadlineId;
    }

    public void setDeadlineId(long deadlineId) {
	this.deadlineId = deadlineId;
    }

}