package it.fge.dtimes.model.subscription;

import it.fge.dtimes.model.deadline.DeadlineDTO;
import it.fge.dtimes.model.invoice.InvoiceDTO;
import it.fge.dtimes.model.packageCourses.PackageCoursesDTO;
import it.fge.dtimes.model.payment.PaymentDTO;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "subscription", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }), @UniqueConstraint(columnNames = { "fiscalCode" }) })
public class SubscriptionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Expose
	@Column(length = 255, nullable = false)
	private String name;

	@Expose
	@Column(length = 255, nullable = false)
	private String surname;

	@Expose
	@Column(length = 255, nullable = true)
	private String fiscalCode;

	@Expose
	@Basic
	@Type(type = "true_false")
	@Column(nullable = false)
	private boolean active;

	@Expose
	@Column(length = 255, nullable = true)
	private String placeOfBirth;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date birthdayDate;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date medicalCertificateDate;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date registrationDate;

	@Expose
	@Column(length = 255, nullable = false)
	private String address;

	@Expose
	@Column(length = 255, nullable = true)
	private String email;

	@Expose
	@Column(length = 255, nullable = true)
	private String phoneNumber;

	@Expose
	@Column(length = 255, nullable = true)
	private String cellNumber;

	@OneToMany(mappedBy = "subscription")
	private List<PaymentDTO> payments = new ArrayList<PaymentDTO>();

	@ManyToMany
	@JoinTable(name = "subscription_packageCourses", joinColumns = { @JoinColumn(name = "subscriptionId", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "packageCoursesId", referencedColumnName = "id") })
	private List<PackageCoursesDTO> packagesCourses = new ArrayList<PackageCoursesDTO>();

	@OneToMany(mappedBy = "subscription")
	private List<InvoiceDTO> invoices = new ArrayList<InvoiceDTO>();

	@OneToMany(mappedBy = "subscription")
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFiscalCode() {
		return fiscalCode;
	}

	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}

	public Date getBirthdayDate() {
		return birthdayDate;
	}

	public void setBirthdayDate(Date birthdayDate) {
		this.birthdayDate = birthdayDate;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCellNumber() {
		return cellNumber;
	}

	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<PaymentDTO> getPayments() {
		return payments;
	}

	public void setPayments(List<PaymentDTO> payments) {
		this.payments = payments;
	}

	public List<PackageCoursesDTO> getPackagesCourses() {
		return packagesCourses;
	}

	public void setPackagesCourses(List<PackageCoursesDTO> packagesCourses) {
		this.packagesCourses = packagesCourses;
	}

	public List<InvoiceDTO> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<InvoiceDTO> invoices) {
		this.invoices = invoices;
	}

	public List<DeadlineDTO> getDeadlines() {
		return deadlines;
	}

	public void setDeadlines(List<DeadlineDTO> deadlines) {
		this.deadlines = deadlines;
	}

	public Date getMedicalCertificateDate() {
		return medicalCertificateDate;
	}

	public void setMedicalCertificateDate(Date medicalCertificateDate) {
		this.medicalCertificateDate = medicalCertificateDate;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SubscriptionDTO [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", surname=");
		builder.append(surname);
		builder.append(", fiscalCode=");
		builder.append(fiscalCode);
		builder.append(", active=");
		builder.append(active);
		builder.append(", birthdayDate=");
		builder.append(birthdayDate);
		builder.append(", medicalCertificateDate=");
		builder.append(medicalCertificateDate);
		builder.append(", registrationDate=");
		builder.append(registrationDate);
		builder.append(", address=");
		builder.append(address);
		builder.append(", email=");
		builder.append(email);
		builder.append(", phoneNumber=");
		builder.append(phoneNumber);
		builder.append(", cellNumber=");
		builder.append(cellNumber);
		builder.append("]");
		return builder.toString();
	}

}
