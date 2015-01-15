package it.fge.dtimes.business.subscription;

import it.fge.dtimes.business.deadline.DeadlineBusiness;
import it.fge.dtimes.model.deadline.DeadlineDAO;
import it.fge.dtimes.model.deadline.DeadlineDTO;
import it.fge.dtimes.model.packageCourses.PackageCoursesDAO;
import it.fge.dtimes.model.packageCourses.PackageCoursesDTO;
import it.fge.dtimes.model.subscription.SubscriptionDAO;
import it.fge.dtimes.model.subscription.SubscriptionDTO;
import it.fge.dtimes.util.Util;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

public class SubscriptionBusinessImpl implements SubscriptionBusiness {

    @Autowired
    private SubscriptionDAO subscriptionDAO;

    @Autowired
    private PackageCoursesDAO packageCoursesDAO;

    @Autowired
    private DeadlineDAO deadlineDAO;

    @Autowired
    private DeadlineBusiness deadlineBusiness;

    @Value("#{new java.text.SimpleDateFormat(\"dd/MM/yyyy HH:mm:ss:SSS\").parse(\"${sportingYear.end.date}\")}")
    protected Date sportingYearEndDate;

    @Override
    @Transactional
    public long countByPlainInPeriod(long plainId, Date startDate, Date endDate) throws Exception {
	if (startDate != null && endDate != null) {
	    return subscriptionDAO.getCountByPlain(plainId, startDate, endDate).size();
	} else {
	    return subscriptionDAO.getCountByPlain(plainId);
	}

    }

    @Override
    @Transactional
    public void delete(long subscriptionIDToDelete) throws Exception {
	SubscriptionDTO subscriptionTmp = subscriptionDAO.findOne(subscriptionIDToDelete);

	for (DeadlineDTO d : subscriptionTmp.getDeadlines()) {
	    deadlineDAO.delete(d);
	}

	subscriptionDAO.delete(subscriptionIDToDelete);
    }

    @Override
    @Transactional
    public SubscriptionDTO disable(long subscriptionIDToEnable) throws Exception {
	SubscriptionDTO subscriptionTmp = subscriptionDAO.findOne(subscriptionIDToEnable);
	if (subscriptionTmp != null) {
	    subscriptionTmp.setActive(false);
	}
	return subscriptionTmp;
    }

    @Override
    @Transactional
    public SubscriptionDTO enable(long subscriptionIDToEnable) throws Exception {
	SubscriptionDTO subscriptionTmp = subscriptionDAO.findOne(subscriptionIDToEnable);
	if (subscriptionTmp != null) {
	    subscriptionTmp.setActive(true);
	}
	return subscriptionTmp;
    }

    @Override
    @Transactional
    public Page<SubscriptionDTO> findAll(int page, int pageSize) throws Exception {
	PageRequest pageRequest = new PageRequest(page, pageSize);
	Page<SubscriptionDTO> listSubscriptionTmp = subscriptionDAO.findAll(pageRequest);

	for (SubscriptionDTO subscription : listSubscriptionTmp.getContent()) {
	    if (subscription.getInvoices() != null) {
		subscription.getInvoices().size();
	    }

	    if (subscription.getPackagesCourses() != null) {
		subscription.getPackagesCourses().size();
	    }

	    if (subscription.getPayments() != null) {
		subscription.getPayments().size();
	    }

	    if (subscription.getDeadlines() != null) {
		subscription.getPayments().size();
	    }

	}

	return listSubscriptionTmp;
    }

    @Override
    @Transactional
    public long findAllCertificatesExpired(Date now) throws Exception {
	return subscriptionDAO.getCountCertificatesExpired(now);
    }

    @Override
    @Transactional
    public Page<SubscriptionDTO> findAllCertificatesExpired(int page, int size, Date date) throws Exception {
	PageRequest pageRequest = new PageRequest(page, size);
	Page<SubscriptionDTO> listSubscriptionTmp = subscriptionDAO.findLikeNominative(date, pageRequest);

	for (SubscriptionDTO subscription : listSubscriptionTmp.getContent()) {
	    if (subscription.getInvoices() != null) {
		subscription.getInvoices().size();
	    }

	    if (subscription.getPackagesCourses() != null) {
		subscription.getPackagesCourses().size();
	    }

	    if (subscription.getPayments() != null) {
		subscription.getPayments().size();
	    }

	    if (subscription.getDeadlines() != null) {
		subscription.getPayments().size();
	    }

	}

	return listSubscriptionTmp;
    }

    @Override
    @Transactional
    public Page<SubscriptionDTO> findAllCertificatesExpiredLikeNominative(int page, int size, Date date, String nameFilter) throws Exception {
	PageRequest pageRequest = new PageRequest(page, size);
	Page<SubscriptionDTO> listSubscriptionTmp = subscriptionDAO.findLikeNominative(nameFilter, date, pageRequest);

	for (SubscriptionDTO subscription : listSubscriptionTmp.getContent()) {
	    if (subscription.getInvoices() != null) {
		subscription.getInvoices().size();
	    }

	    if (subscription.getPackagesCourses() != null) {
		subscription.getPackagesCourses().size();
	    }

	    if (subscription.getPayments() != null) {
		subscription.getPayments().size();
	    }

	    if (subscription.getDeadlines() != null) {
		subscription.getPayments().size();
	    }

	}

	return listSubscriptionTmp;
    }

    @Override
    @Transactional
    public SubscriptionDTO findByCF(String fiscalCode) throws Exception {
	SubscriptionDTO subscriptionTmp = subscriptionDAO.findByCF(fiscalCode);

	return subscriptionTmp;
    }

    @Override
    @Transactional
    public SubscriptionDTO findById(long id) throws Exception {
	SubscriptionDTO subscriptionTmp = subscriptionDAO.findOne(id);
	if (subscriptionTmp != null) {
	    if (subscriptionTmp.getInvoices() != null) {
		subscriptionTmp.getInvoices().size();
	    }

	    if (subscriptionTmp.getPackagesCourses() != null) {
		subscriptionTmp.getPackagesCourses().size();
	    }

	    if (subscriptionTmp.getPayments() != null) {
		subscriptionTmp.getPayments().size();
	    }

	    if (subscriptionTmp.getDeadlines() != null) {
		subscriptionTmp.getPayments().size();
	    }
	}
	return subscriptionTmp;
    }

    @Override
    @Transactional
    public Page<SubscriptionDTO> findByPlain(long plainFilter, int page, int size) throws Exception {
	PageRequest pageRequest = new PageRequest(page, size);
	Page<SubscriptionDTO> listSubscriptionTmp = subscriptionDAO.findByPlain(plainFilter, pageRequest);

	for (SubscriptionDTO subscription : listSubscriptionTmp.getContent()) {
	    if (subscription.getInvoices() != null) {
		subscription.getInvoices().size();
	    }

	    if (subscription.getPackagesCourses() != null) {
		subscription.getPackagesCourses().size();
	    }

	    if (subscription.getPayments() != null) {
		subscription.getPayments().size();
	    }

	    if (subscription.getDeadlines() != null) {
		subscription.getPayments().size();
	    }

	}

	return listSubscriptionTmp;
    }

    @Override
    @Transactional
    public Page<SubscriptionDTO> findLikeNominative(String nameFilter, int page, int size) throws Exception {
	PageRequest pageRequest = new PageRequest(page, size);
	Page<SubscriptionDTO> listSubscriptionTmp = subscriptionDAO.findLikeNominative(nameFilter, pageRequest);

	for (SubscriptionDTO subscription : listSubscriptionTmp.getContent()) {
	    if (subscription.getInvoices() != null) {
		subscription.getInvoices().size();
	    }

	    if (subscription.getPackagesCourses() != null) {
		subscription.getPackagesCourses().size();
	    }

	    if (subscription.getPayments() != null) {
		subscription.getPayments().size();
	    }

	    if (subscription.getDeadlines() != null) {
		subscription.getPayments().size();
	    }

	}

	return listSubscriptionTmp;
    }

    @Override
    @Transactional
    public Page<SubscriptionDTO> findLikeNominativeAndPlain(String nameFilter, long plainFilter, int page, int size) throws Exception {
	PageRequest pageRequest = new PageRequest(page, size);
	Page<SubscriptionDTO> listSubscriptionTmp = subscriptionDAO.findLikeNominativeAndPlain(nameFilter, plainFilter, pageRequest);

	for (SubscriptionDTO subscription : listSubscriptionTmp.getContent()) {
	    if (subscription.getInvoices() != null) {
		subscription.getInvoices().size();
	    }

	    if (subscription.getPackagesCourses() != null) {
		subscription.getPackagesCourses().size();
	    }

	    if (subscription.getPayments() != null) {
		subscription.getPayments().size();
	    }

	    if (subscription.getDeadlines() != null) {
		subscription.getPayments().size();
	    }

	}

	return listSubscriptionTmp;
    }

    @Override
    @Transactional
    public SubscriptionDTO getFirstSubscription(int page, int pageSize) throws Exception {
	PageRequest pageRequest = new PageRequest(page, pageSize);
	SubscriptionDTO subscriptionTmp = null;
	Page<SubscriptionDTO> ListsubscriptionTmp = subscriptionDAO.findFirst(pageRequest);
	if (ListsubscriptionTmp != null && !ListsubscriptionTmp.getContent().isEmpty()) {
	    subscriptionTmp = ListsubscriptionTmp.getContent().get(0);
	    if (subscriptionTmp != null) {
		if (subscriptionTmp.getInvoices() != null) {
		    subscriptionTmp.getInvoices().size();
		}

		if (subscriptionTmp.getPackagesCourses() != null) {
		    subscriptionTmp.getPackagesCourses().size();
		}

		if (subscriptionTmp.getPayments() != null) {
		    subscriptionTmp.getPayments().size();
		}

		if (subscriptionTmp.getDeadlines() != null) {
		    subscriptionTmp.getPayments().size();
		}
	    }
	}
	return subscriptionTmp;
    }

    @Override
    @Transactional
    public SubscriptionDTO save(SubscriptionDTO subscription) throws Exception {
	SubscriptionDTO sbTmp = subscriptionDAO.saveAndFlush(subscription);
	deadlineBusiness.generateDeadLines(sbTmp);
	return sbTmp;
    }

    @Override
    @Transactional
    public SubscriptionDTO update(SubscriptionDTO subscriptionToUpdate) throws Exception {
	SubscriptionDTO subscriptionTmp = subscriptionDAO.findOne(subscriptionToUpdate.getId());
	Date oldRegistrationDate = subscriptionTmp.getRegistrationDate();
	subscriptionTmp.setRegistrationDate(subscriptionToUpdate.getRegistrationDate());
	subscriptionTmp.setName(subscriptionToUpdate.getName());
	subscriptionTmp.setSurname(subscriptionToUpdate.getSurname());
	subscriptionTmp.setFiscalCode(subscriptionToUpdate.getFiscalCode());
	subscriptionTmp.setBirthdayDate(subscriptionToUpdate.getBirthdayDate());
	subscriptionTmp.setMedicalCertificateDate(subscriptionToUpdate.getMedicalCertificateDate());
	subscriptionTmp.setAddress(subscriptionToUpdate.getAddress());
	subscriptionTmp.setPhoneNumber(subscriptionToUpdate.getPhoneNumber());
	subscriptionTmp.setCellNumber(subscriptionToUpdate.getCellNumber());
	subscriptionTmp.setEmail(subscriptionToUpdate.getEmail());

	Collection<PackageCoursesDTO> plainToDelete = new HashSet<PackageCoursesDTO>();
	plainToDelete.addAll(subscriptionTmp.getPackagesCourses());
	plainToDelete.removeAll(subscriptionToUpdate.getPackagesCourses());

	if (!plainToDelete.isEmpty()) {
	    for (PackageCoursesDTO plain : plainToDelete) {
		deadlineDAO.deleteAllWithPaymentNullBySubscriptionAndPlain(subscriptionTmp.getId(), plain.getId());

	    }
	}

	if (!DateUtils.isSameDay(oldRegistrationDate, subscriptionToUpdate.getRegistrationDate())) {
	    Collection<PackageCoursesDTO> plainToUpdate = new HashSet<PackageCoursesDTO>();

	    plainToUpdate = CollectionUtils.intersection(subscriptionToUpdate.getPackagesCourses(), subscriptionTmp.getPackagesCourses());

	    if (oldRegistrationDate.before(subscriptionToUpdate.getRegistrationDate())) {
		Calendar dateEndTmp = Calendar.getInstance();
		dateEndTmp.setTime(subscriptionToUpdate.getRegistrationDate());
		dateEndTmp.add(Calendar.MONTH, -1);
		dateEndTmp.set(Calendar.DAY_OF_MONTH, dateEndTmp.getActualMaximum(Calendar.DAY_OF_MONTH));
		dateEndTmp.set(Calendar.HOUR_OF_DAY, 23);
		dateEndTmp.set(Calendar.MINUTE, 59);
		dateEndTmp.set(Calendar.SECOND, 59);

		Calendar dateStartTmp = Calendar.getInstance();
		dateStartTmp.setTime(oldRegistrationDate);
		dateStartTmp.set(Calendar.DAY_OF_MONTH, 1);
		dateStartTmp.set(Calendar.HOUR_OF_DAY, 0);
		dateStartTmp.set(Calendar.MINUTE, 0);
		dateStartTmp.set(Calendar.SECOND, 1);
		for (PackageCoursesDTO plain : plainToUpdate) {

		    deadlineDAO.deleteInPeriodBySubscriptionAndPlain(subscriptionTmp.getId(), plain.getId(), dateStartTmp.getTime(), dateEndTmp.getTime());
		}
	    } else if (oldRegistrationDate.after(subscriptionToUpdate.getRegistrationDate())) {
		for (PackageCoursesDTO plain : plainToUpdate) {
		    Calendar startDate = Calendar.getInstance();
		    startDate.setTime(subscriptionToUpdate.getRegistrationDate());
		    int month = plain.getExpirationType().getMonthNumber();
		    do {
			DeadlineDTO dl = new DeadlineDTO();
			dl.setPackageCourses(plain);
			dl.setSubscription(subscriptionTmp);
			dl.setDeadlineDate(Util.calculateExpirationDate(startDate.getTime(), month).getTime());
			if (deadlineDAO.findBySubscriptionIdAndPlainIdInDate(dl.getSubscription().getId(), dl.getPackageCourses().getId(), dl.getDeadlineDate()) == null) {
			    dl.setActive(true);
			    deadlineBusiness.save(dl);
			}

			startDate.add(Calendar.MONTH, month);
			if (plain.isSubscriptionPlain()) {
			    startDate.add(Calendar.YEAR, 1);
			}
		    } while (startDate.getTime().before(oldRegistrationDate));
		}
	    }
	}

	Collection<PackageCoursesDTO> plainToAdd = new HashSet<PackageCoursesDTO>();
	plainToAdd.addAll(subscriptionToUpdate.getPackagesCourses());
	plainToAdd.removeAll(subscriptionTmp.getPackagesCourses());

	if (!plainToAdd.isEmpty()) {
	    Calendar endDate = Calendar.getInstance();
	    endDate.setTime(sportingYearEndDate);

	    for (PackageCoursesDTO pc : plainToAdd) {
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(subscriptionTmp.getRegistrationDate());
		int month = pc.getExpirationType().getMonthNumber();
		do {
		    DeadlineDTO dl = new DeadlineDTO();
		    dl.setPackageCourses(pc);
		    dl.setSubscription(subscriptionTmp);
		    dl.setDeadlineDate(Util.calculateExpirationDate(startDate.getTime(), month).getTime());
		    dl.setActive(true);
		    deadlineBusiness.save(dl);

		    startDate.add(Calendar.MONTH, month);
		    if (pc.isSubscriptionPlain()) {
			startDate.add(Calendar.YEAR, 1);
		    }
		} while (startDate.before(endDate));

	    }

	}

	subscriptionTmp.setPackagesCourses(subscriptionToUpdate.getPackagesCourses());

	return subscriptionTmp;
    }

}
