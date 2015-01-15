package it.fge.dtimes.business.payment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import it.fge.dtimes.model.payment.PaymentDTO;

import org.springframework.data.domain.Page;

public interface PaymentBusiness {

    Page<PaymentDTO> findAll(int page, int pageSize) throws Exception;

    PaymentDTO findById(long id) throws Exception;

    PaymentDTO save(PaymentDTO payment, List<String> expiredSelected) throws Exception;

    PaymentDTO update(PaymentDTO paymentToUpdate, List<String> expiredSelected) throws Exception;

    void delete(long paymentIDToDelete) throws Exception;

    Page<PaymentDTO> findAllBySubscription(int page, int size, long subscriptionIDToShow) throws Exception;

    PaymentDTO findLastPaymentBySubscripyion(long subscriptionId) throws Exception;

    PaymentDTO findLastPaymentBySubscripyionAndPlain(long subscriptionId, long plainId) throws Exception;

    void checkPaymentOutOfDate(Date firstDayOfMonth) throws Exception;

    Page<PaymentDTO> findAllInPeriod(Date startDate, Date endDate, int page, int size) throws Exception;

    Page<PaymentDTO> findAllInPeriodByPlain(Date startDate, Date endDate, long plainId, int page, int size) throws Exception;

    Double getImportPaiedInPeriodByPlain(Date startDate, Date endDate, long plainId) throws Exception;

    Double getImportPaiedInPeriod(Date startDate, Date endDate) throws Exception;

    Page<PaymentDTO> findAllByPlain(long plainId, int page, int size) throws Exception;

    Double getImportPaiedByPlain(long plainId) throws Exception;

    Double getImportPaied() throws Exception;

    Page<PaymentDTO> findAllInPeriodByPlainAdnNominative(Date time, Date time2, long plainId, String nameFilter, int page, int size) throws Exception;

    Double getImportPaiedInPeriodByPlainAndNominative(Date time, Date time2, long plainId, String nameFilter) throws Exception;

    Page<PaymentDTO> findAllInPeriodByNominative(Date time, Date time2, String nameFilter, int page, int size) throws Exception;

    Double getImportPaiedInPeriodByNominative(Date time, Date time2, String nameFilter) throws Exception;

    Page<PaymentDTO> findAllByPlainAndNominative(long plainId, String nameFilter, int page, int size) throws Exception;

    Double getImportPaiedByPlainAndNominative(long plainId, String nameFilter) throws Exception;

    Page<PaymentDTO> findAllByNominative(String nameFilter, int page, int size) throws Exception;

    Double getImportPaiedByNominative(String nameFilter) throws Exception;

    long getCountByPlain(long id) throws Exception;

    long getCountByPlainInPeriod(Date time, Date time2, long plainId) throws Exception;

}
