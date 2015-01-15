package it.fge.dtimes.model.invoice;

import it.fge.dtimes.model.payment.PaymentDTO;
import it.fge.dtimes.model.subscription.SubscriptionDTO;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "invoice")
public class InvoiceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Expose
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private Date lastPrintDate;

    @Expose
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date creationDate;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "invoice")
    private PaymentDTO payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscriptionId")
    private SubscriptionDTO subscription;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public Date getLastPrintDate() {
	return lastPrintDate;
    }

    public void setLastPrintDate(Date lastPrintDate) {
	this.lastPrintDate = lastPrintDate;
    }

    public Date getCreationDate() {
	return creationDate;
    }

    public void setCreationDate(Date creationDate) {
	this.creationDate = creationDate;
    }

    public PaymentDTO getPayment() {
	return payment;
    }

    public void setPayment(PaymentDTO payment) {
	this.payment = payment;
    }

    public SubscriptionDTO getSubscription() {
	return subscription;
    }

    public void setSubscription(SubscriptionDTO subscription) {
	this.subscription = subscription;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("InvoiceDTO[id=");
	builder.append(id);
	builder.append(", lastPrintDate=");
	builder.append(lastPrintDate);
	builder.append(", creationDate=");
	builder.append(creationDate);
	builder.append("]");
	return builder.toString();
    }

}
