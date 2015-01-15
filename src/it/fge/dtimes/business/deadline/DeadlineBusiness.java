package it.fge.dtimes.business.deadline;

import it.fge.dtimes.model.deadline.DeadlineDTO;
import it.fge.dtimes.model.packageCourses.PackageCoursesDTO;
import it.fge.dtimes.model.payment.PaymentDTO;
import it.fge.dtimes.model.subscription.SubscriptionDTO;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

public interface DeadlineBusiness {

	DeadlineDTO save(DeadlineDTO deadline) throws Exception;

	DeadlineDTO findBySubscriptionIdAndPlainId(SubscriptionDTO subscription, PackageCoursesDTO packageCourses, Date now) throws Exception;

	DeadlineDTO update(DeadlineDTO deadline) throws Exception;


	Page<DeadlineDTO> findAllExpired(int page, int pageSize, Date date) throws Exception;

	long findAllExpired(Date date) throws Exception;

	double sumAllExpired(Date date) throws Exception;

	void generateDeadLines(SubscriptionDTO subscription) throws Exception;

	Page<DeadlineDTO> findAllExpiredLikeNominativeAndPlain(int page, int size, Date time, String nameFilter, long plainFilter) throws Exception;

	double sumAllExpiredLikeNominativeAndPlain(Date time, String nameFilter, long plainFilter) throws Exception;

	Page<DeadlineDTO> findAllExpiredLikeNominative(int page, int size, Date time, String nameFilter) throws Exception;

	double sumAllExpiredLikeNominative(Date time, String nameFilter) throws Exception;

	Page<DeadlineDTO> findAllExpiredByPlain(int page, int size, Date time, long plainFilter) throws Exception;

	double sumAllExpiredByPlain(Date time, long plainFilter) throws Exception;

	List<DeadlineDTO> findAllExpiredNoPaginated(Date date) throws Exception;

	List<DeadlineDTO> findAllExpiredNoPaginated(Date time, long id) throws Exception;

	DeadlineDTO findById(long expiredSelected) throws Exception;

	Page<DeadlineDTO> findLastPaiedBySubscriptionIdAndPlainId(long paymentId, SubscriptionDTO subscription, PackageCoursesDTO pc) throws Exception;

	List<DeadlineDTO> findByPayment(PaymentDTO payment) throws Exception;

	Page<DeadlineDTO> findLastPaiedBySubscriptionIdAndPlainId(SubscriptionDTO subscriptionTmp, PackageCoursesDTO plainTmp) throws Exception;

	long countByPaymentAndPlain(PaymentDTO paymentTmp, PackageCoursesDTO pc) throws Exception;

	long countByPlain(PackageCoursesDTO pc) throws Exception;

	void setPaymentNUll(long id) throws Exception;

	long countByPaymentAndPlainAndNominative(PaymentDTO pay, PackageCoursesDTO plainTmp, String nominative) throws Exception;

	long countByPlainInPeriod(Date time, Date time2, PackageCoursesDTO packageCoursesDTO) throws Exception;

	void disableDeadline(long deadlineId) throws Exception;

	void enableDeadline(long deadlineId) throws Exception;

	Page<DeadlineDTO> findAllHideLikeNominativeAndPlain(int page, int size, Date time, String nameFilter, long plainFilter) throws Exception;

	double sumAllHideLikeNominativeAndPlain(Date time, String nameFilter, long plainFilter) throws Exception;

	Page<DeadlineDTO> findAllHideLikeNominative(int page, int size, Date time, String nameFilter)  throws Exception;

	double sumAllHideLikeNominative(Date time, String nameFilter) throws Exception;

	Page<DeadlineDTO> findAllHideByPlain(int page, int size, Date time, long plainFilter)  throws Exception;

	double sumAllHideByPlain(Date time, long plainFilter) throws Exception;

	Page<DeadlineDTO> findAllHide(int page, int size, Date time) throws Exception;

	double sumAllHide(Date time) throws Exception;

	List<DeadlineDTO> findAllExpiredNoPaginated(long id) throws Exception;


}
