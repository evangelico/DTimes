package it.fge.dtimes.model.packageCourses;

import it.fge.dtimes.model.deadline.DeadlineDTO;
import it.fge.dtimes.model.payment.PaymentDTO;
import it.fge.dtimes.model.subscription.SubscriptionDTO;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "packageCourses", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }), @UniqueConstraint(columnNames = { "name" }) })
public class PackageCoursesDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Expose
    @Column(length = 255, nullable = false)
    private String name;

    @Expose
    @Column(length = 500, nullable = true)
    private String description;

    @Expose
    @Column(nullable = false)
    private long amount;

    @Expose
    @Column(nullable = false)
    private double teacherPercentage;

    @Basic
    @Type(type = "true_false")
    @Column(nullable = false, columnDefinition = "char(1) NOT NULL DEFAULT 'F'")
    @Expose
    private boolean subscriptionPlain;

    @Basic
    @Type(type = "true_false")
    @Column(nullable = false, columnDefinition = "char(1) NOT NULL DEFAULT 'F'")
    @Expose
    private boolean singleLesson;

    @Expose
    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private ExpirationType expirationType;

    @ManyToMany(mappedBy = "packagesCourses")
    private List<SubscriptionDTO> subscriptions = new ArrayList<SubscriptionDTO>();

    @ManyToMany(mappedBy = "packagesCourses")
    private List<PaymentDTO> payments = new ArrayList<PaymentDTO>();

    @OneToMany(mappedBy = "packageCourses")
    private List<DeadlineDTO> deadlines = new ArrayList<DeadlineDTO>();

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public long getAmount() {
	return amount;
    }

    public void setAmount(long amount) {
	this.amount = amount;
    }

    public ExpirationType getExpirationType() {
	return expirationType;
    }

    public void setExpirationType(ExpirationType expirationType) {
	this.expirationType = expirationType;
    }

    public List<SubscriptionDTO> getSubscriptions() {
	return subscriptions;
    }

    public void setSubscriptions(List<SubscriptionDTO> subscriptions) {
	this.subscriptions = subscriptions;
    }

    public List<PaymentDTO> getPayments() {
	return payments;
    }

    public void setPayments(List<PaymentDTO> payments) {
	this.payments = payments;
    }

    public List<DeadlineDTO> getDeadlines() {
	return deadlines;
    }

    public void setDeadlines(List<DeadlineDTO> deadlines) {
	this.deadlines = deadlines;
    }

    public double getTeacherPercentage() {
	return teacherPercentage;
    }

    public void setTeacherPercentage(double teacherPercentage) {
	this.teacherPercentage = teacherPercentage;
    }

    public boolean isSubscriptionPlain() {
	return subscriptionPlain;
    }

    public void setSubscriptionPlain(boolean subscriptionPlain) {
	this.subscriptionPlain = subscriptionPlain;
    }

    public boolean isSingleLesson() {
	return singleLesson;
    }

    public void setSingleLesson(boolean singleLesson) {
	this.singleLesson = singleLesson;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("PackageCoursesDTO [id=");
	builder.append(id);
	builder.append(", name=");
	builder.append(name);
	builder.append(", description=");
	builder.append(description);
	builder.append(", amount=");
	builder.append(amount);
	builder.append(", teacherPercentage=");
	builder.append(teacherPercentage);
	builder.append(", subscriptionPlain=");
	builder.append(subscriptionPlain);
	builder.append(", singleLesson=");
	builder.append(singleLesson);
	builder.append(", expirationType=");
	builder.append(expirationType);
	builder.append("]");
	return builder.toString();
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (id ^ (id >>> 32));
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	PackageCoursesDTO other = (PackageCoursesDTO) obj;
	if (id != other.id)
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }

}
