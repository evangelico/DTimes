package it.fge.dtimes.model.deadline;

import it.fge.dtimes.model.packageCourses.PackageCoursesDTO;
import it.fge.dtimes.model.payment.PaymentDTO;
import it.fge.dtimes.model.subscription.SubscriptionDTO;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "deadline")
public class DeadlineDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Expose
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private Date deadlineDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paymentId")
    private PaymentDTO payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscriptionId")
    private SubscriptionDTO subscription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packageCoursesId")
    private PackageCoursesDTO packageCourses;

    @Basic
    @Type(type = "true_false")
    @Column(nullable = false, columnDefinition = "char(1) NOT NULL DEFAULT 'T'")
    @Expose
    private boolean active;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public Date getDeadlineDate() {
	return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
	this.deadlineDate = deadlineDate;
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

    public PackageCoursesDTO getPackageCourses() {
	return packageCourses;
    }

    public void setPackageCourses(PackageCoursesDTO packageCourses) {
	this.packageCourses = packageCourses;
    }

    public boolean isActive() {
	return active;
    }

    public void setActive(boolean active) {
	this.active = active;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("DeadlineDTO [id=");
	builder.append(id);
	builder.append(", deadlineDate=");
	builder.append(deadlineDate);
	builder.append(", active=");
	builder.append(active);
	builder.append("]");
	return builder.toString();
    }

}
