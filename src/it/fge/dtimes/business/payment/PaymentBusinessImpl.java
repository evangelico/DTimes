package it.fge.dtimes.business.payment;

import it.fge.dtimes.business.deadline.DeadlineBusiness;
import it.fge.dtimes.model.deadline.DeadlineDAO;
import it.fge.dtimes.model.deadline.DeadlineDTO;
import it.fge.dtimes.model.invoice.InvoiceDAO;
import it.fge.dtimes.model.packageCourses.PackageCoursesDAO;
import it.fge.dtimes.model.packageCourses.PackageCoursesDTO;
import it.fge.dtimes.model.payment.PaymentDAO;
import it.fge.dtimes.model.payment.PaymentDTO;
import it.fge.dtimes.model.subscription.SubscriptionDAO;
import it.fge.dtimes.model.subscription.SubscriptionDTO;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

public class PaymentBusinessImpl implements PaymentBusiness {

    private static Logger logger = Logger.getLogger(PaymentBusinessImpl.class);

    @Autowired
    private SubscriptionDAO subscriptionDAO;

    @Autowired
    private PackageCoursesDAO packageCoursesDAO;

    @Autowired
    private PaymentDAO paymentDAO;

    @Autowired
    private InvoiceDAO invoiceDAO;

    @Autowired
    private DeadlineDAO deadlineDAO;

    @Autowired
    DeadlineBusiness deadlineBusiness;

    @Override
    @Transactional
    public Page<PaymentDTO> findAll(int page, int pageSize) throws Exception {
	PageRequest pageRequest = new PageRequest(page, pageSize);
	Page<PaymentDTO> listPaymentTmp = paymentDAO.findAll(pageRequest);

	for (PaymentDTO payment : listPaymentTmp.getContent()) {
	    if (payment.getInvoice() != null) {
		payment.getInvoice().getId();
	    }

	    if (payment.getPackagesCourses() != null) {
		payment.getPackagesCourses().size();
	    }

	    if (payment.getSubscription() != null) {
		payment.getSubscription().getId();
	    }

	    if (payment.getDeadlines() != null) {
		payment.getDeadlines().size();
	    }

	}

	return listPaymentTmp;
    }

    @Override
    @Transactional
    public PaymentDTO findById(long id) throws Exception {
	PaymentDTO paymentTmp = paymentDAO.findOne(id);
	if (paymentTmp != null) {
	    if (paymentTmp.getInvoice() != null) {
		paymentTmp.getInvoice().getId();
	    }

	    if (paymentTmp.getPackagesCourses() != null) {
		paymentTmp.getPackagesCourses().size();
	    }

	    if (paymentTmp.getSubscription() != null) {
		paymentTmp.getSubscription().getId();
	    }
	    if (paymentTmp.getDeadlines() != null) {
		paymentTmp.getDeadlines().size();
	    }
	}
	return paymentTmp;
    }

    @Override
    @Transactional
    public PaymentDTO save(PaymentDTO payment, List<String> deadlineId) throws Exception {

	payment = paymentDAO.saveAndFlush(payment);

	for (String dl : deadlineId) {
	    if (StringUtils.isBlank(dl.trim())) {
		continue;
	    }
	    DeadlineDTO dlTmp = deadlineBusiness.findById(Long.parseLong(dl.trim()));
	    if (dlTmp != null) {
		dlTmp.setPayment(payment);
		deadlineBusiness.update(dlTmp);
	    } else {
		logger.warn("Nessuna scadenza trovata con id: " + dl.trim());
	    }
	}

	return payment;
    }

    @Override
    @Transactional
    public PaymentDTO update(PaymentDTO paymentToUpdate, List<String> deadlineId) throws Exception {
	PaymentDTO paymentTmp = paymentDAO.findOne(paymentToUpdate.getId());

	paymentTmp.setAmountPaied(paymentToUpdate.getAmountPaied());

	paymentTmp.setPercentageDiscount(paymentToUpdate.getPercentageDiscount());
	paymentTmp.setPaymentDate(paymentToUpdate.getPaymentDate());

	Collection<PackageCoursesDTO> plainToUpdatePayment = new HashSet<PackageCoursesDTO>();
	plainToUpdatePayment.addAll(paymentTmp.getPackagesCourses());
	plainToUpdatePayment.removeAll(paymentToUpdate.getPackagesCourses());

	if (!plainToUpdatePayment.isEmpty()) {
	    for (PackageCoursesDTO plain : plainToUpdatePayment) {
		deadlineDAO.setPaymentNullBySubscriptionAndPaymentAndPlain(paymentTmp.getSubscription().getId(), paymentTmp.getId(), plain.getId());
	    }

	}

	paymentTmp.setPackagesCourses(paymentToUpdate.getPackagesCourses());
	if (deadlineId != null && !deadlineId.isEmpty()) {
	    Collection<DeadlineDTO> dlToRemove = new HashSet<DeadlineDTO>();
	    dlToRemove.addAll(paymentTmp.getDeadlines());

	    for (String dl : deadlineId) {
		if (StringUtils.isBlank(dl.trim())) {
		    continue;
		}
		DeadlineDTO dlRemove = deadlineBusiness.findById(Long.parseLong(dl.trim()));
		if (dlRemove != null) {
		    dlToRemove.remove(dlRemove);
		}
	    }

	    if (dlToRemove != null && !dlToRemove.isEmpty()) {
		for (DeadlineDTO dl : dlToRemove) {
		    deadlineBusiness.setPaymentNUll(dl.getId());
		}
	    }
	}

	if (deadlineId == null || deadlineId.isEmpty()) {
	    logger.warn("Attenzione: nessuna scadenza selezionata per il pagamento: " + paymentTmp.getId());
	} else {
	    for (String dl : deadlineId) {
		if (StringUtils.isBlank(dl.trim())) {
		    continue;
		}
		DeadlineDTO dlTmp = deadlineBusiness.findById(Long.parseLong(dl.trim()));
		if (dlTmp != null) {
		    dlTmp.setPayment(paymentTmp);
		} else {
		    logger.warn("Nessuna scadenza trovata con id: " + dl.trim());
		}
	    }
	}
	if (paymentToUpdate.getInvoice() != null) {
	    paymentTmp.setInvoice(paymentToUpdate.getInvoice());
	}

	if (paymentTmp.getInvoice() != null) {
	    paymentTmp.getInvoice().getId();
	}

	return paymentTmp;
    }

    @Override
    @Transactional
    public void delete(long paymentIDToDelete) throws Exception {
	PaymentDTO paymentTmp = paymentDAO.findOne(paymentIDToDelete);
	if (paymentTmp != null) {
	    if (paymentTmp.getInvoice() != null) {
		invoiceDAO.delete(paymentTmp.getInvoice().getId());
	    }

	    if (paymentTmp.getDeadlines() != null) {
		for (DeadlineDTO dl : paymentTmp.getDeadlines()) {
		    dl.setPayment(null);
		}
	    }
	    paymentDAO.delete(paymentIDToDelete);
	}

    }

    @Override
    @Transactional
    public Page<PaymentDTO> findAllBySubscription(int page, int size, long subscriptionIDToShow) {
	PageRequest pageRequest = new PageRequest(page, size);
	Page<PaymentDTO> listPaymentTmp = paymentDAO.findAllBySubscription(subscriptionIDToShow, pageRequest);
	for (PaymentDTO payment : listPaymentTmp.getContent()) {
	    if (payment.getInvoice() != null) {
		payment.getInvoice().getId();
	    }

	    if (payment.getPackagesCourses() != null) {
		payment.getPackagesCourses().size();
	    }

	    if (payment.getSubscription() != null) {
		payment.getSubscription().getId();
	    }

	    if (payment.getDeadlines() != null) {
		payment.getDeadlines().size();
	    }

	}

	return listPaymentTmp;
    }

    @Override
    @Transactional
    public PaymentDTO findLastPaymentBySubscripyion(long subscriptionId) throws Exception {
	PaymentDTO paymentTmp = null;
	PageRequest pageRequest = new PageRequest(0, 1);
	Page<PaymentDTO> ListPaymentTmp = paymentDAO.findLastBySubscriptionId(subscriptionId, pageRequest);
	if (ListPaymentTmp != null && !ListPaymentTmp.getContent().isEmpty()) {
	    paymentTmp = ListPaymentTmp.getContent().get(0);

	    if (paymentTmp.getInvoice() != null) {
		paymentTmp.getInvoice().getId();
	    }

	    if (paymentTmp.getPackagesCourses() != null) {
		paymentTmp.getPackagesCourses().size();
	    }

	    if (paymentTmp.getSubscription() != null) {
		paymentTmp.getSubscription().getId();
	    }

	    if (paymentTmp.getDeadlines() != null) {
		paymentTmp.getDeadlines().size();
	    }
	}
	return paymentTmp;
    }

    @Override
    @Transactional
    public PaymentDTO findLastPaymentBySubscripyionAndPlain(long subscriptionId, long plainId) throws Exception {
	PaymentDTO paymentTmp = null;
	PageRequest pageRequest = new PageRequest(0, 1);
	Page<PaymentDTO> ListPaymentTmp = paymentDAO.findLastBySubscriptionIdAndPlainId(subscriptionId, plainId, pageRequest);
	if (ListPaymentTmp != null && !ListPaymentTmp.getContent().isEmpty()) {
	    paymentTmp = ListPaymentTmp.getContent().get(0);

	    if (paymentTmp.getInvoice() != null) {
		paymentTmp.getInvoice().getId();
	    }

	    if (paymentTmp.getPackagesCourses() != null) {
		paymentTmp.getPackagesCourses().size();
	    }

	    if (paymentTmp.getSubscription() != null) {
		paymentTmp.getSubscription().getId();
	    }

	    if (paymentTmp.getDeadlines() != null) {
		paymentTmp.getDeadlines().size();
	    }
	}
	return paymentTmp;
    }

    @Override
    @Transactional
    public void checkPaymentOutOfDate(Date firstDayOfMonth) throws Exception {
	List<SubscriptionDTO> listSubscription = subscriptionDAO.findAll();

	if (listSubscription == null || listSubscription.isEmpty()) {
	    logger.info("Non sono preserni iscritti");
	} else {
	    for (SubscriptionDTO subscription : listSubscription) {

		List<PackageCoursesDTO> subscriptionPlains = subscription.getPackagesCourses();
		if (subscriptionPlains == null || subscriptionPlains.isEmpty()) {
		    logger.info(subscription.getSurname() + " " + subscription.getName() + " non si è iscritto a nessun corso ");
		} else {

		}

	    }

	}

    }

    @Override
    @Transactional
    public Page<PaymentDTO> findAllInPeriod(Date startDate, Date endDate, int page, int size) throws Exception {
	PageRequest pageRequest = new PageRequest(page, size);
	Page<PaymentDTO> listPaymentTmp = paymentDAO.findAllInPeriod(startDate, endDate, pageRequest);

	for (PaymentDTO payment : listPaymentTmp.getContent()) {
	    if (payment.getInvoice() != null) {
		payment.getInvoice().getId();
	    }

	    if (payment.getPackagesCourses() != null) {
		payment.getPackagesCourses().size();
	    }

	    if (payment.getSubscription() != null) {
		payment.getSubscription().getId();
	    }

	    if (payment.getDeadlines() != null) {
		payment.getDeadlines().size();
	    }

	}

	return listPaymentTmp;
    }

    @Override
    @Transactional
    public Page<PaymentDTO> findAllInPeriodByPlain(Date startDate, Date endDate, long plainId, int page, int size) throws Exception {
	PageRequest pageRequest = new PageRequest(page, size);
	Page<PaymentDTO> listPaymentTmp = paymentDAO.findAllInPeriodByPlain(startDate, endDate, plainId, pageRequest);

	for (PaymentDTO payment : listPaymentTmp.getContent()) {
	    if (payment.getInvoice() != null) {
		payment.getInvoice().getId();
	    }

	    if (payment.getPackagesCourses() != null) {
		payment.getPackagesCourses().size();
	    }

	    if (payment.getSubscription() != null) {
		payment.getSubscription().getId();
	    }

	    if (payment.getDeadlines() != null) {
		payment.getDeadlines().size();
	    }

	}

	return listPaymentTmp;
    }

    @Override
    @Transactional
    public Double getImportPaiedInPeriodByPlain(Date startDate, Date endDate, long plainId) throws Exception {
	return paymentDAO.getImportPaiedInPeriodByPlain(startDate, endDate, plainId);
    }

    @Override
    @Transactional
    public Double getImportPaiedInPeriod(Date startDate, Date endDate) throws Exception {
	return paymentDAO.getImportPaiedInPeriod(startDate, endDate);
    }

    @Override
    @Transactional
    public Page<PaymentDTO> findAllByPlain(long plainId, int page, int size) throws Exception {
	PageRequest pageRequest = new PageRequest(page, size);
	Page<PaymentDTO> listPaymentTmp = paymentDAO.findAllByPlain(plainId, pageRequest);

	for (PaymentDTO payment : listPaymentTmp.getContent()) {
	    if (payment.getInvoice() != null) {
		payment.getInvoice().getId();
	    }

	    if (payment.getPackagesCourses() != null) {
		payment.getPackagesCourses().size();
	    }

	    if (payment.getSubscription() != null) {
		payment.getSubscription().getId();
	    }

	    if (payment.getDeadlines() != null) {
		payment.getDeadlines().size();
	    }

	}

	return listPaymentTmp;
    }

    @Override
    @Transactional
    public Double getImportPaiedByPlain(long plainId) throws Exception {

	return paymentDAO.getImportPaiedByPlain(plainId);
    }

    @Override
    @Transactional
    public Double getImportPaied() throws Exception {
	return paymentDAO.getImportPaiedByPlain();
    }

    @Override
    @Transactional
    public Page<PaymentDTO> findAllInPeriodByPlainAdnNominative(Date time, Date time2, long plainId, String nameFilter, int page, int size) throws Exception {
	PageRequest pageRequest = new PageRequest(page, size);
	Page<PaymentDTO> listPaymentTmp = paymentDAO.findAllInPeriodByPlainAdnNominative(time, time2, nameFilter, plainId, pageRequest);

	for (PaymentDTO payment : listPaymentTmp.getContent()) {
	    if (payment.getInvoice() != null) {
		payment.getInvoice().getId();
	    }

	    if (payment.getPackagesCourses() != null) {
		payment.getPackagesCourses().size();
	    }

	    if (payment.getSubscription() != null) {
		payment.getSubscription().getId();
	    }

	    if (payment.getDeadlines() != null) {
		payment.getDeadlines().size();
	    }

	}

	return listPaymentTmp;
    }

    @Override
    @Transactional
    public Double getImportPaiedInPeriodByPlainAndNominative(Date time, Date time2, long plainId, String nameFilter) throws Exception {
	return paymentDAO.getImportPaiedInPeriodByPlainAndNominative(time, time2, nameFilter, plainId);
    }

    @Override
    @Transactional
    public Page<PaymentDTO> findAllInPeriodByNominative(Date time, Date time2, String nameFilter, int page, int size) throws Exception {
	PageRequest pageRequest = new PageRequest(page, size);
	Page<PaymentDTO> listPaymentTmp = paymentDAO.findAllInPeriodByNominative(time, time2, nameFilter, pageRequest);

	for (PaymentDTO payment : listPaymentTmp.getContent()) {
	    if (payment.getInvoice() != null) {
		payment.getInvoice().getId();
	    }

	    if (payment.getPackagesCourses() != null) {
		payment.getPackagesCourses().size();
	    }

	    if (payment.getSubscription() != null) {
		payment.getSubscription().getId();
	    }

	    if (payment.getDeadlines() != null) {
		payment.getDeadlines().size();
	    }

	}

	return listPaymentTmp;
    }

    @Override
    @Transactional
    public Double getImportPaiedInPeriodByNominative(Date time, Date time2, String nameFilter) throws Exception {
	return paymentDAO.getImportPaiedInPeriodByNominative(time, time2, nameFilter);
    }

    @Override
    @Transactional
    public Page<PaymentDTO> findAllByPlainAndNominative(long plainId, String nameFilter, int page, int size) throws Exception {
	PageRequest pageRequest = new PageRequest(page, size);
	Page<PaymentDTO> listPaymentTmp = paymentDAO.findAllByPlainAndNominative(nameFilter, plainId, pageRequest);

	for (PaymentDTO payment : listPaymentTmp.getContent()) {
	    if (payment.getInvoice() != null) {
		payment.getInvoice().getId();
	    }

	    if (payment.getPackagesCourses() != null) {
		payment.getPackagesCourses().size();
	    }

	    if (payment.getSubscription() != null) {
		payment.getSubscription().getId();
	    }

	    if (payment.getDeadlines() != null) {
		payment.getDeadlines().size();
	    }

	}

	return listPaymentTmp;
    }

    @Override
    @Transactional
    public Double getImportPaiedByPlainAndNominative(long plainId, String nameFilter) throws Exception {
	return paymentDAO.getImportPaiedByPlainAndNominative(nameFilter, plainId);
    }

    @Override
    @Transactional
    public Page<PaymentDTO> findAllByNominative(String nameFilter, int page, int size) throws Exception {
	PageRequest pageRequest = new PageRequest(page, size);
	Page<PaymentDTO> listPaymentTmp = paymentDAO.findAllByNominative(nameFilter, pageRequest);

	for (PaymentDTO payment : listPaymentTmp.getContent()) {
	    if (payment.getInvoice() != null) {
		payment.getInvoice().getId();
	    }

	    if (payment.getPackagesCourses() != null) {
		payment.getPackagesCourses().size();
	    }

	    if (payment.getSubscription() != null) {
		payment.getSubscription().getId();
	    }

	    if (payment.getDeadlines() != null) {
		payment.getDeadlines().size();
	    }

	}

	return listPaymentTmp;
    }

    @Override
    @Transactional
    public Double getImportPaiedByNominative(String nameFilter) throws Exception {
	return paymentDAO.getImportPaiedByNominative(nameFilter);
    }

    @Override
    @Transactional
    public long getCountByPlain(long plainId) throws Exception {
	return paymentDAO.getCountByPlain(plainId);
    }

    @Override
    @Transactional
    public long getCountByPlainInPeriod(Date startDate, Date endDate, long plainId) throws Exception {
	return paymentDAO.getCountByPlainInPeriod(startDate, endDate, plainId);
    }
}
