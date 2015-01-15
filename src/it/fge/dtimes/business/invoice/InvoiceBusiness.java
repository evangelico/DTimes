package it.fge.dtimes.business.invoice;

import it.fge.dtimes.model.invoice.InvoiceDTO;
import it.fge.dtimes.model.payment.PaymentDTO;

import org.springframework.data.domain.Page;

public interface InvoiceBusiness {

    Page<InvoiceDTO> findAll(int page, int pageSize) throws Exception;

    InvoiceDTO findById(long id) throws Exception;

    InvoiceDTO save(InvoiceDTO invoice) throws Exception;

    InvoiceDTO update(InvoiceDTO invoiceToUpdate) throws Exception;

    void delete(long invoiceIDToDelete) throws Exception;

	InvoiceDTO findByPayment(PaymentDTO payment) throws Exception;

}
