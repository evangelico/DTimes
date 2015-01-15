package it.fge.dtimes.struts.action;

import it.fge.dtimes.business.invoice.InvoiceBusiness;
import it.fge.dtimes.business.payment.PaymentBusiness;
import it.fge.dtimes.business.subscription.SubscriptionBusiness;
import it.fge.dtimes.model.invoice.InvoiceDTO;
import it.fge.dtimes.model.payment.PaymentDTO;
import it.fge.dtimes.model.subscription.SubscriptionDTO;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class InvoiceAction extends BaseActionSupport {
    private static final long serialVersionUID = 1L;

    private static Logger logger = Logger.getLogger(InvoiceAction.class);

    private long paymentId;
    private long invoiceId;

    private InvoiceDTO invoice;
    private PaymentDTO payment;
    private SubscriptionDTO subscription;

    @Autowired
    private InvoiceBusiness invoiceBusiness;

    @Autowired
    private PaymentBusiness paymentBusiness;

    @Autowired
    private SubscriptionBusiness subscriptionBusiness;

    public InvoiceDTO getInvoice() {
	return invoice;
    }

    public long getInvoiceId() {
	return invoiceId;
    }

    public PaymentDTO getPayment() {
	return payment;
    }

    public long getPaymentId() {
	return paymentId;
    }

    public SubscriptionDTO getSubscription() {
	return subscription;
    }

    public String index() {
	if (logger.isDebugEnabled())
	    logger.debug("index()");
	return SUCCESS;
    }

    public String insert() {
	if (logger.isDebugEnabled())
	    logger.debug("Inserimento nuova fattura per pagamento: " + paymentId);

	try {
	    payment = paymentBusiness.findById(paymentId);
	    if (payment != null) {
		subscription = subscriptionBusiness.findById(payment.getSubscription().getId());
	    } else {
		logger.error("Errore: impossibile trovare un pagamento con id: " + paymentId);
		return "error";
	    }
	    invoice = invoiceBusiness.findByPayment(payment);

	    if (invoice != null) {
		logger.error("Errore: è già presente una fattura per il pagamentocon id: " + paymentId);
		return "error";
	    }

	    invoice = new InvoiceDTO();
	    invoice.setCreationDate(new Date());
	    invoice.setSubscription(subscription);
	    invoice = invoiceBusiness.save(invoice);

	    payment.setInvoice(invoice);
	    paymentBusiness.update(payment,null);

	    invoiceId = invoice.getId();
	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return stamp();
    }

    public void setInvoice(InvoiceDTO invoice) {
	this.invoice = invoice;
    }

    public void setInvoiceId(long invoiceId) {
	this.invoiceId = invoiceId;
    }

    public void setPayment(PaymentDTO payment) {
	this.payment = payment;
    }

    public void setPaymentId(long paymentId) {
	this.paymentId = paymentId;
    }

    public void setSubscription(SubscriptionDTO subscription) {
	this.subscription = subscription;
    }

    public String stamp() {
	if (logger.isDebugEnabled())
	    logger.debug("Stampa fattura con id: " + invoiceId);

	try {
	    invoice = invoiceBusiness.findById(invoiceId);
	    payment = paymentBusiness.findById(invoice.getPayment().getId());
	    if (invoice == null) {
		logger.error("Errore: impossibile trovare la fattura con id: " + invoiceId);
		return "error";
	    }

	    invoice.setLastPrintDate(new Date());
	    invoiceBusiness.update(invoice);
	} catch (Exception e) {
	    logger.error("Errore", e);
	    return "error";
	}
	return "invoice.stamp";
    }

}