package it.fge.dtimes.business.subscription;

import java.util.Date;

import it.fge.dtimes.model.subscription.SubscriptionDTO;

import org.springframework.data.domain.Page;

public interface SubscriptionBusiness {

	Page<SubscriptionDTO> findAll(int page, int pageSize) throws Exception;

	SubscriptionDTO findById(long id) throws Exception;

	SubscriptionDTO save(SubscriptionDTO subscription) throws Exception;

	SubscriptionDTO update(SubscriptionDTO subscriptionToUpdate) throws Exception;

	void delete(long subscriptionIDToDelete) throws Exception;

	SubscriptionDTO findByCF(String fiscalCode) throws Exception;

	SubscriptionDTO enable(long subscriptionIDToEnable) throws Exception;

	SubscriptionDTO disable(long subscriptionIDToDisable) throws Exception;

	SubscriptionDTO getFirstSubscription(int page, int pageSize) throws Exception;

	Page<SubscriptionDTO> findByPlain(long plainFilter, int page, int size) throws Exception;

	Page<SubscriptionDTO> findLikeNominative(String nameFilter, int page, int size) throws Exception;

	Page<SubscriptionDTO> findLikeNominativeAndPlain(String nameFilter, long plainFilter, int page, int size) throws Exception;

	long countByPlainInPeriod(long plainId, Date time, Date time2) throws Exception;

	long findAllCertificatesExpired(Date date) throws Exception;

	Page<SubscriptionDTO> findAllCertificatesExpiredLikeNominative(int page, int size, Date date, String nameFilter) throws Exception;

	Page<SubscriptionDTO> findAllCertificatesExpired(int page, int size, Date date) throws Exception;

	long countTotalSubscription() throws Exception;

	long countTotalSubscriptionByPlainAndNominative(String nominative, long plainId) throws Exception;

	long countTotalSubscriptionByNominative(String nominative) throws Exception;

	long countTotalSubscriptionByPlain(long plainId) throws Exception;

}
