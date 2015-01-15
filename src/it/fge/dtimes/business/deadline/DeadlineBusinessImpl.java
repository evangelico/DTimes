package it.fge.dtimes.business.deadline;

import it.fge.dtimes.model.deadline.DeadlineDAO;
import it.fge.dtimes.model.deadline.DeadlineDTO;
import it.fge.dtimes.model.packageCourses.PackageCoursesDTO;
import it.fge.dtimes.model.payment.PaymentDTO;
import it.fge.dtimes.model.subscription.SubscriptionDTO;
import it.fge.dtimes.util.Util;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

public class DeadlineBusinessImpl implements DeadlineBusiness {

    @Autowired
    private DeadlineDAO deadlineDAO;

    @Value("#{new java.text.SimpleDateFormat(\"dd/MM/yyyy HH:mm:ss:SSS\").parse(\"${sportingYear.end.date}\")}")
    protected Date sportingYearEndDate;

    @Override
    @Transactional
    public long countByPaymentAndPlain(PaymentDTO paymentTmp, PackageCoursesDTO pc) throws Exception {
	return deadlineDAO.countByPaymentAndPlain(paymentTmp.getId(), pc.getId()).longValue();
    }

    @Override
    @Transactional
    public long countByPaymentAndPlainAndNominative(PaymentDTO pay, PackageCoursesDTO plainTmp, String nominative) throws Exception {
	return deadlineDAO.countByPaymentAndPlainAndNominative(pay.getId(), plainTmp.getId(), nominative).longValue();
    }

    @Override
    public long countByPlain(PackageCoursesDTO pc) throws Exception {
	return deadlineDAO.countByPlain(pc.getId()).longValue();
    }

    @Override
    @Transactional
    public long countByPlainInPeriod(Date time, Date time2, PackageCoursesDTO plainTmp) throws Exception {
	return deadlineDAO.countByPlainInPeriod(time, time2, plainTmp.getId()).longValue();
    }

    @Override
    @Transactional
    public void disableDeadline(long deadlineId) throws Exception {
	DeadlineDTO dlTmp = deadlineDAO.findOne(deadlineId);
	dlTmp.setActive(false);
    }

    @Override
    @Transactional
    public void enableDeadline(long deadlineId) throws Exception {
	DeadlineDTO dlTmp = deadlineDAO.findOne(deadlineId);
	dlTmp.setActive(true);

    }

    @Override
    @Transactional
    public long findAllExpired(Date date) throws Exception {
	return deadlineDAO.findAllExpired(date).longValue();
    }

    @Override
    @Transactional
    public Page<DeadlineDTO> findAllExpired(int page, int pageSize, Date date) throws Exception {
	PageRequest pageRequest = new PageRequest(page, pageSize);
	Page<DeadlineDTO> listDeadlinesTmp = deadlineDAO.findAllExpired(date, pageRequest);

	for (DeadlineDTO dl : listDeadlinesTmp.getContent()) {
	    if (dl.getPackageCourses() != null) {
		dl.getPackageCourses().getId();
	    }
	    if (dl.getPayment() != null) {
		dl.getPayment().getId();
	    }
	    if (dl.getSubscription() != null) {
		dl.getSubscription().getId();
	    }
	}

	return listDeadlinesTmp;
    }

    @Override
    @Transactional
    public Page<DeadlineDTO> findAllExpiredByPlain(int page, int size, Date time, long plainFilter) throws Exception {
	PageRequest pageRequest = new PageRequest(page, size);
	Page<DeadlineDTO> listDeadlinesTmp = deadlineDAO.findAllExpiredByPlain(time, plainFilter, pageRequest);

	for (DeadlineDTO dl : listDeadlinesTmp.getContent()) {
	    if (dl.getPackageCourses() != null) {
		dl.getPackageCourses().getId();
	    }
	    if (dl.getPayment() != null) {
		dl.getPayment().getId();
	    }
	    if (dl.getSubscription() != null) {
		dl.getSubscription().getId();
	    }
	}

	return listDeadlinesTmp;
    }

    @Override
    @Transactional
    public Page<DeadlineDTO> findAllExpiredLikeNominative(int page, int size, Date time, String nameFilter) throws Exception {
	PageRequest pageRequest = new PageRequest(page, size);
	Page<DeadlineDTO> listDeadlinesTmp = deadlineDAO.findAllExpiredLikeNominativeAndPlain(time, nameFilter, pageRequest);

	for (DeadlineDTO dl : listDeadlinesTmp.getContent()) {
	    if (dl.getPackageCourses() != null) {
		dl.getPackageCourses().getId();
	    }
	    if (dl.getPayment() != null) {
		dl.getPayment().getId();
	    }
	    if (dl.getSubscription() != null) {
		dl.getSubscription().getId();
	    }
	}

	return listDeadlinesTmp;
    }

    @Override
    @Transactional
    public Page<DeadlineDTO> findAllExpiredLikeNominativeAndPlain(int page, int size, Date time, String nameFilter, long plainFilter) throws Exception {
	PageRequest pageRequest = new PageRequest(page, size);
	Page<DeadlineDTO> listDeadlinesTmp = deadlineDAO.findAllExpiredLikeNominativeAndPlain(time, nameFilter, plainFilter, pageRequest);

	for (DeadlineDTO dl : listDeadlinesTmp.getContent()) {
	    if (dl.getPackageCourses() != null) {
		dl.getPackageCourses().getId();
	    }
	    if (dl.getPayment() != null) {
		dl.getPayment().getId();
	    }
	    if (dl.getSubscription() != null) {
		dl.getSubscription().getId();
	    }
	}

	return listDeadlinesTmp;
    }

    @Override
    @Transactional
    public List<DeadlineDTO> findAllExpiredNoPaginated(Date date) throws Exception {
	List<DeadlineDTO> listDeadlinesTmp = deadlineDAO.findAllExpiredNoPaginated(date);

	for (DeadlineDTO dl : listDeadlinesTmp) {
	    if (dl.getPackageCourses() != null) {
		dl.getPackageCourses().getId();
	    }
	    if (dl.getPayment() != null) {
		dl.getPayment().getId();
	    }
	    if (dl.getSubscription() != null) {
		dl.getSubscription().getId();
	    }
	}

	return listDeadlinesTmp;
    }

    @Override
    @Transactional
    public List<DeadlineDTO> findAllExpiredNoPaginated(Date time, long id) throws Exception {
	List<DeadlineDTO> listDeadlinesTmp = deadlineDAO.findAllExpiredNoPaginated(time, id);

	for (DeadlineDTO dl : listDeadlinesTmp) {
	    if (dl.getPackageCourses() != null) {
		dl.getPackageCourses().getId();
	    }
	    if (dl.getPayment() != null) {
		dl.getPayment().getId();
	    }
	    if (dl.getSubscription() != null) {
		dl.getSubscription().getId();
	    }
	}

	return listDeadlinesTmp;
    }
    
    @Override
    @Transactional
    public List<DeadlineDTO> findAllExpiredNoPaginated(long id) throws Exception {
	List<DeadlineDTO> listDeadlinesTmp = deadlineDAO.findAllExpiredNoPaginated(id);

	for (DeadlineDTO dl : listDeadlinesTmp) {
	    if (dl.getPackageCourses() != null) {
		dl.getPackageCourses().getId();
	    }
	    if (dl.getPayment() != null) {
		dl.getPayment().getId();
	    }
	    if (dl.getSubscription() != null) {
		dl.getSubscription().getId();
	    }
	}

	return listDeadlinesTmp;
    }

    @Override
    @Transactional
    public Page<DeadlineDTO> findAllHide(int page, int size, Date time) throws Exception {
	PageRequest pageRequest = new PageRequest(page, size);
	Page<DeadlineDTO> listDeadlinesTmp = deadlineDAO.findAllHide(time, pageRequest);

	for (DeadlineDTO dl : listDeadlinesTmp.getContent()) {
	    if (dl.getPackageCourses() != null) {
		dl.getPackageCourses().getId();
	    }
	    if (dl.getPayment() != null) {
		dl.getPayment().getId();
	    }
	    if (dl.getSubscription() != null) {
		dl.getSubscription().getId();
	    }
	}

	return listDeadlinesTmp;
    }

    @Override
    @Transactional
    public Page<DeadlineDTO> findAllHideByPlain(int page, int size, Date time, long plainFilter) throws Exception {
	PageRequest pageRequest = new PageRequest(page, size);
	Page<DeadlineDTO> listDeadlinesTmp = deadlineDAO.findAllHideByPlain(time, plainFilter, pageRequest);

	for (DeadlineDTO dl : listDeadlinesTmp.getContent()) {
	    if (dl.getPackageCourses() != null) {
		dl.getPackageCourses().getId();
	    }
	    if (dl.getPayment() != null) {
		dl.getPayment().getId();
	    }
	    if (dl.getSubscription() != null) {
		dl.getSubscription().getId();
	    }
	}

	return listDeadlinesTmp;
    }

    @Override
    @Transactional
    public Page<DeadlineDTO> findAllHideLikeNominative(int page, int size, Date time, String nameFilter) throws Exception {
	PageRequest pageRequest = new PageRequest(page, size);
	Page<DeadlineDTO> listDeadlinesTmp = deadlineDAO.findAllHideLikeNominativeAndPlain(time, nameFilter, pageRequest);

	for (DeadlineDTO dl : listDeadlinesTmp.getContent()) {
	    if (dl.getPackageCourses() != null) {
		dl.getPackageCourses().getId();
	    }
	    if (dl.getPayment() != null) {
		dl.getPayment().getId();
	    }
	    if (dl.getSubscription() != null) {
		dl.getSubscription().getId();
	    }
	}

	return listDeadlinesTmp;
    }

    @Override
    @Transactional
    public Page<DeadlineDTO> findAllHideLikeNominativeAndPlain(int page, int size, Date time, String nameFilter, long plainFilter) throws Exception {
	PageRequest pageRequest = new PageRequest(page, size);
	Page<DeadlineDTO> listDeadlinesTmp = deadlineDAO.findAllHideLikeNominativeAndPlain(time, nameFilter, plainFilter, pageRequest);

	for (DeadlineDTO dl : listDeadlinesTmp.getContent()) {
	    if (dl.getPackageCourses() != null) {
		dl.getPackageCourses().getId();
	    }
	    if (dl.getPayment() != null) {
		dl.getPayment().getId();
	    }
	    if (dl.getSubscription() != null) {
		dl.getSubscription().getId();
	    }
	}

	return listDeadlinesTmp;
    }

    @Override
    @Transactional
    public DeadlineDTO findById(long expiredSelected) throws Exception {
	DeadlineDTO dl = deadlineDAO.findOne(expiredSelected);

	if (dl.getPackageCourses() != null) {
	    dl.getPackageCourses().getId();
	}
	if (dl.getPayment() != null) {
	    dl.getPayment().getId();
	}
	if (dl.getSubscription() != null) {
	    dl.getSubscription().getId();
	}

	return dl;
    }

    @Override
    @Transactional
    public List<DeadlineDTO> findByPayment(PaymentDTO payment) throws Exception {
	List<DeadlineDTO> listDeadlinesTmp = deadlineDAO.findByPayment(payment.getId());

	for (DeadlineDTO dl : listDeadlinesTmp) {
	    if (dl.getPackageCourses() != null) {
		dl.getPackageCourses().getId();
	    }
	    if (dl.getPayment() != null) {
		dl.getPayment().getId();
	    }
	    if (dl.getSubscription() != null) {
		dl.getSubscription().getId();
	    }
	}

	return listDeadlinesTmp;
    }

    @Override
    @Transactional
    public DeadlineDTO findBySubscriptionIdAndPlainId(SubscriptionDTO subscription, PackageCoursesDTO packageCourses, Date paymentDate) throws Exception {
	Calendar nowTmp = Calendar.getInstance();
	nowTmp.setTime(paymentDate);
	nowTmp.set(Calendar.DAY_OF_MONTH, nowTmp.getActualMaximum(Calendar.DAY_OF_MONTH));
	nowTmp.add(Calendar.MONTH, packageCourses.getExpirationType().getMonthNumber());
	nowTmp.set(Calendar.HOUR_OF_DAY, 23);
	nowTmp.set(Calendar.MINUTE, 59);
	nowTmp.set(Calendar.SECOND, 59);
	nowTmp.set(Calendar.MILLISECOND, 999);
	return deadlineDAO.findBySubscriptionIdAndPlainId(subscription.getId(), packageCourses.getId(), nowTmp.getTime());
    }

    @Override
    @Transactional
    public Page<DeadlineDTO> findLastPaiedBySubscriptionIdAndPlainId(long paymentId, SubscriptionDTO subscription, PackageCoursesDTO pc) throws Exception {
	PageRequest pageRequest = new PageRequest(0, 1);
	return deadlineDAO.findLastPaiedBySubscriptionIdAndPlainId(paymentId, subscription.getId(), pc.getId(), pageRequest);
    }

    @Override
    @Transactional
    public Page<DeadlineDTO> findLastPaiedBySubscriptionIdAndPlainId(SubscriptionDTO subscriptionTmp, PackageCoursesDTO plainTmp) throws Exception {
	PageRequest pageRequest = new PageRequest(0, 1);
	return deadlineDAO.findLastPaiedBySubscriptionIdAndPlainId(subscriptionTmp.getId(), plainTmp.getId(), pageRequest);
    }

    @Override
    @Transactional
    public void generateDeadLines(SubscriptionDTO subscription) throws Exception {

	Calendar endDate = Calendar.getInstance();
	endDate.setTime(sportingYearEndDate);

	for (PackageCoursesDTO pc : subscription.getPackagesCourses()) {
	    Calendar startDate = Calendar.getInstance();
	    startDate.setTime(subscription.getRegistrationDate());
	    int month = pc.getExpirationType().getMonthNumber();
	    do {
		DeadlineDTO dl = new DeadlineDTO();
		dl.setPackageCourses(pc);
		dl.setSubscription(subscription);
		dl.setDeadlineDate(Util.calculateExpirationDate(startDate.getTime(), month).getTime());
		dl.setActive(true);
		save(dl);

		startDate.add(Calendar.MONTH, month);
		if (pc.isSubscriptionPlain()) {
		    startDate.add(Calendar.YEAR, 1);
		}
	    } while (startDate.before(endDate));

	}
    }

    @Override
    @Transactional
    public DeadlineDTO save(DeadlineDTO deadline) throws Exception {
	return deadlineDAO.saveAndFlush(deadline);
    }

    @Override
    @Transactional
    public void setPaymentNUll(long id) throws Exception {
	DeadlineDTO dlTmp = deadlineDAO.findOne(id);
	if (dlTmp != null) {
	    dlTmp.setPayment(null);
	}

    }

    @Override
    @Transactional
    public double sumAllExpired(Date date) throws Exception {
	Object tmp = deadlineDAO.sumAllExpired(date);
	if (tmp instanceof Long) {
	    return (Long) tmp;
	} else if (tmp instanceof Double) {
	    return (Double) tmp;
	} else {
	    return (Integer) tmp;
	}

    }

    @Override
    @Transactional
    public double sumAllExpiredByPlain(Date time, long plainFilter) throws Exception {
	Object tmp = deadlineDAO.sumAllExpiredByPlain(time, plainFilter);

	if (tmp instanceof Long) {
	    return (Long) tmp;
	} else if (tmp instanceof Double) {
	    return (Double) tmp;
	} else {
	    return (Integer) tmp;
	}
    }

    @Override
    @Transactional
    public double sumAllExpiredLikeNominative(Date time, String nameFilter) throws Exception {

	Object tmp = deadlineDAO.sumAllExpiredLikeNominative(time, nameFilter);
	if (tmp instanceof Long) {
	    return (Long) tmp;
	} else if (tmp instanceof Double) {
	    return (Double) tmp;
	} else {
	    return (Integer) tmp;
	}
    }

    @Override
    @Transactional
    public double sumAllExpiredLikeNominativeAndPlain(Date time, String nameFilter, long plainFilter) throws Exception {
	Object tmp = deadlineDAO.sumAllExpiredLikeNominativeAndPlain(time, nameFilter, plainFilter);
	if (tmp instanceof Long) {
	    return (Long) tmp;
	} else if (tmp instanceof Double) {
	    return (Double) tmp;
	} else {
	    return (Integer) tmp;
	}
    }

    @Override
    @Transactional
    public double sumAllHide(Date time) throws Exception {
	Object tmp = deadlineDAO.sumAllHide(time);
	if (tmp instanceof Long) {
	    return (Long) tmp;
	} else if (tmp instanceof Double) {
	    return (Double) tmp;
	} else {
	    return (Integer) tmp;
	}
    }

    @Override
    @Transactional
    public double sumAllHideByPlain(Date time, long plainFilter) throws Exception {
	Object tmp = deadlineDAO.sumAllHideByPlain(time, plainFilter);

	if (tmp instanceof Long) {
	    return (Long) tmp;
	} else if (tmp instanceof Double) {
	    return (Double) tmp;
	} else {
	    return (Integer) tmp;
	}
    }

    @Override
    @Transactional
    public double sumAllHideLikeNominative(Date time, String nameFilter) throws Exception {

	Object tmp = deadlineDAO.sumAllHideLikeNominative(time, nameFilter);
	if (tmp instanceof Long) {
	    return (Long) tmp;
	} else if (tmp instanceof Double) {
	    return (Double) tmp;
	} else {
	    return (Integer) tmp;
	}
    }

    @Override
    @Transactional
    public double sumAllHideLikeNominativeAndPlain(Date time, String nameFilter, long plainFilter) throws Exception {
	Object tmp = deadlineDAO.sumAllHideLikeNominativeAndPlain(time, nameFilter, plainFilter);
	if (tmp instanceof Long) {
	    return (Long) tmp;
	} else if (tmp instanceof Double) {
	    return (Double) tmp;
	} else {
	    return (Integer) tmp;
	}
    }

    @Override
    @Transactional
    public DeadlineDTO update(DeadlineDTO deadline) throws Exception {
	DeadlineDTO deadlineTmp = deadlineDAO.findOne(deadline.getId());
	deadlineTmp.setDeadlineDate(deadline.getDeadlineDate());
	deadlineTmp.setPackageCourses(deadline.getPackageCourses());
	deadlineTmp.setPayment(deadline.getPayment());
	deadlineTmp.setSubscription(deadline.getSubscription());

	return deadlineTmp;
    }

}
