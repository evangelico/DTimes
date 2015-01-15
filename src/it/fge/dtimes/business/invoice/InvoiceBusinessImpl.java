package it.fge.dtimes.business.invoice;

import it.fge.dtimes.model.invoice.*;
import it.fge.dtimes.model.packageCourses.PackageCoursesDAO;
import it.fge.dtimes.model.payment.PaymentDTO;
import it.fge.dtimes.model.subscription.SubscriptionDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

public class InvoiceBusinessImpl implements InvoiceBusiness {

	@Autowired
	private SubscriptionDAO subscriptionDAO;

	@Autowired
	private PackageCoursesDAO packageCoursesDAO;

	@Autowired
	private InvoiceDAO invoiceDAO;

	@Override
	@Transactional
	public Page<InvoiceDTO> findAll(int page, int pageSize) throws Exception {
		PageRequest pageRequest = new PageRequest(page, pageSize);
		Page<InvoiceDTO> listInvoiceTmp = invoiceDAO.findAll(pageRequest);

		for (InvoiceDTO invoice : listInvoiceTmp.getContent()) {
			if (invoice.getPayment() != null) {
				invoice.getPayment().getId();
			}

			if (invoice.getSubscription() != null) {
				invoice.getSubscription().getId();
			}

		}

		return listInvoiceTmp;
	}

	@Override
	@Transactional
	public InvoiceDTO findById(long id) throws Exception {
		InvoiceDTO invoiceTmp = invoiceDAO.findOne(id);
		if (invoiceTmp != null) {
			if (invoiceTmp.getPayment() != null) {
				invoiceTmp.getPayment().getId();
			}

			if (invoiceTmp.getSubscription() != null) {
				invoiceTmp.getSubscription().getId();
			}
		}
		return invoiceTmp;
	}

	@Override
	@Transactional
	public InvoiceDTO save(InvoiceDTO invoice) throws Exception {
		return invoiceDAO.saveAndFlush(invoice);
	}

	@Override
	@Transactional
	public InvoiceDTO update(InvoiceDTO invoiceToUpdate) throws Exception {
		InvoiceDTO invoiceTmp = invoiceDAO.findOne(invoiceToUpdate.getId());
		invoiceTmp.setSubscription(invoiceToUpdate.getSubscription());
		invoiceTmp.setLastPrintDate(invoiceToUpdate.getLastPrintDate());
		invoiceTmp.setCreationDate(invoiceToUpdate.getCreationDate());

		return invoiceTmp;
	}

	@Override
	@Transactional
	public void delete(long invoiceIDToDelete) throws Exception {
		invoiceDAO.delete(invoiceIDToDelete);
	}

	@Override
	@Transactional
	public InvoiceDTO findByPayment(PaymentDTO payment) throws Exception {
		InvoiceDTO invoiceTmp = invoiceDAO.findByPayment(payment.getId());
		return invoiceTmp;
	}

}
