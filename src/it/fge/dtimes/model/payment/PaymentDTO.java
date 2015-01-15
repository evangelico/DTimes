package it.fge.dtimes.model.payment;

import it.fge.dtimes.model.deadline.DeadlineDTO;
import it.fge.dtimes.model.invoice.InvoiceDTO;
import it.fge.dtimes.model.packageCourses.PackageCoursesDTO;
import it.fge.dtimes.model.subscription.SubscriptionDTO;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "payment")
public class PaymentDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date paymentDate;

	@Expose
	@Column(nullable = false)
	private double amountPaied;
	
	@Expose
	@Column(nullable = false)
	private double percentageDiscount;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoiceId")
	private InvoiceDTO invoice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subscriptionId")
	private SubscriptionDTO subscription;

	@ManyToMany
	@JoinTable(name = "payment_packageCourses", joinColumns = {@JoinColumn(name = "paymentId", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "packageCoursesId", referencedColumnName = "id")})
	private List<PackageCoursesDTO> packagesCourses = new ArrayList<PackageCoursesDTO>();

	@OneToMany(mappedBy = "payment")
	private List<DeadlineDTO> deadlines = new ArrayList<DeadlineDTO>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}


	public double getAmountPaied() {
	    return amountPaied;
	}

	public void setAmountPaied(double amountPaied) {
	    this.amountPaied = amountPaied;
	}

	public InvoiceDTO getInvoice() {
		return invoice;
	}

	public void setInvoice(InvoiceDTO invoice) {
		this.invoice = invoice;
	}

	public SubscriptionDTO getSubscription() {
		return subscription;
	}

	public void setSubscription(SubscriptionDTO subscription) {
		this.subscription = subscription;
	}

	public List<PackageCoursesDTO> getPackagesCourses() {
		return packagesCourses;
	}

	public void setPackagesCourses(List<PackageCoursesDTO> packagesCourses) {
		this.packagesCourses = packagesCourses;
	}

	public List<DeadlineDTO> getDeadlines() {
		return deadlines;
	}

	public void setDeadlines(List<DeadlineDTO> deadlines) {
		this.deadlines = deadlines;
	}
	
	

	public double getPercentageDiscount() {
	    return percentageDiscount;
	}

	public void setPercentageDiscount(double percentageDiscount) {
	    this.percentageDiscount = percentageDiscount;
	}

	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("PaymentDTO [id=");
	    builder.append(id);
	    builder.append(", paymentDate=");
	    builder.append(paymentDate);
	    builder.append(", amountPaied=");
	    builder.append(amountPaied);
	    builder.append(", percentageDiscount=");
	    builder.append(percentageDiscount);
	    builder.append("]");
	    return builder.toString();
	}

	
	

}
