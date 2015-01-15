package it.fge.dtimes.struts.action;

import it.fge.dtimes.business.deadline.DeadlineBusiness;
import it.fge.dtimes.business.payment.PaymentBusiness;
import it.fge.dtimes.business.plain.PlainBusiness;
import it.fge.dtimes.business.subscription.SubscriptionBusiness;
import it.fge.dtimes.model.deadline.DeadlineDTO;
import it.fge.dtimes.model.packageCourses.PackageCoursesDTO;
import it.fge.dtimes.model.payment.PaymentDTO;
import it.fge.dtimes.model.subscription.SubscriptionDTO;
import it.fge.dtimes.model.user.UserDTO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class BaseActionSupport extends ActionSupport implements Preparable {

    private static final long serialVersionUID = 1830479953161142811L;

    protected static Logger logger = Logger.getLogger(BaseActionSupport.class);

    protected static String session_attribute_userHadler = "USER_HANDLER";

    protected UserDTO userDTO;

    // paginatore
    protected int page = 0;

    @Value("${paginator.size}")
    protected int size;

    @Value("#{new java.text.SimpleDateFormat(\"dd/MM/yyyy HH:mm:ss:SSS\").parse(\"${sportingYear.end.date}\")}")
    protected Date sportingYearEndDate;

    protected String nameFilter;

    protected long plainFilter;

    protected SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    private DeadlineBusiness deadlineBusiness;

    @Autowired
    private PaymentBusiness paymentBusiness;

    @Autowired
    private SubscriptionBusiness subscriptionBusiness;

    @Autowired
    private PlainBusiness plainBusiness;

    public double getAmount(long paymentId) {
	if (logger.isDebugEnabled())
	    logger.debug("Calcolo importo da pagare per pagamento: " + paymentId);
	double amount = 0;
	try {
	    PaymentDTO paymentTmp = paymentBusiness.findById(paymentId);
	    if (paymentTmp != null) {
		for (PackageCoursesDTO pc : paymentTmp.getPackagesCourses()) {
		    if (!pc.isSingleLesson()) {
			double amountTmp = pc.getAmount() * deadlineBusiness.countByPaymentAndPlain(paymentTmp, pc);
			amount = amount + amountTmp;
		    } else {
			amount = amount + pc.getAmount();
		    }

		}

	    } else {
		logger.error("Errore: impossibile trovare un pagamento con id: " + paymentId);
		return amount;
	    }

	} catch (Exception e) {
	    logger.error("Errore", e);
	    return amount;
	}

	return amount;
    }

    public long getCertificatesExpired() {
	long count = 0;
	try {
	    count = subscriptionBusiness.findAllCertificatesExpired(new Date());
	} catch (Exception e) {
	    logger.error("Errore", e);
	}
	return count;
    }

    public long getDeadlinesExpired() {
	long count = 0;
	try {
	    Calendar dateTmp = Calendar.getInstance();
	    dateTmp.set(Calendar.DAY_OF_MONTH, 1);
	    dateTmp.add(Calendar.MONTH, 1);
	    dateTmp.set(Calendar.HOUR_OF_DAY, 23);
	    dateTmp.set(Calendar.MINUTE, 59);
	    dateTmp.set(Calendar.SECOND, 59);
	    dateTmp.set(Calendar.MILLISECOND, 999);
	    count = deadlineBusiness.findAllExpired(dateTmp.getTime());
	} catch (Exception e) {
	    logger.error("Errore", e);
	}
	return count;
    }

    public Date getExpiration(long paymentId, long plainId) {
	if (logger.isDebugEnabled())
	    logger.debug("Calcolo data scadenza per pagamento: " + paymentId + " e pacchetto: " + plainId);
	Calendar expireDate = Calendar.getInstance();
	try {
	    PaymentDTO paymentTmp = paymentBusiness.findById(paymentId);
	    if (paymentTmp != null) {
		for (PackageCoursesDTO pc : paymentTmp.getPackagesCourses()) {
		    if (pc.getId() == plainId) {
			// int month = pc.getExpirationType().getMonthNumber();
			Page<DeadlineDTO> dlListTmp = deadlineBusiness.findLastPaiedBySubscriptionIdAndPlainId(paymentId, paymentTmp.getSubscription(), pc);

			if (dlListTmp == null || dlListTmp.getContent().isEmpty()) {
			    logger.error("Errore: impossibile trovare un pagamento con id: " + paymentId + " associato ad una scadenza");
			    return null;
			}

			DeadlineDTO dlTmp = dlListTmp.getContent().get(0);
			expireDate.setTime(dlTmp.getDeadlineDate());

			if (logger.isDebugEnabled())
			    logger.debug("Data scadenza per pacchetto: " + pc.getId() + " --> " + expireDate.getTime());
			break;
		    }
		}

	    } else {
		logger.error("Errore: impossibile trovare un pagamento con id: " + paymentId);
		return null;
	    }

	} catch (Exception e) {
	    logger.error("Errore", e);
	    return null;
	}

	return expireDate.getTime();
    }

    public String getPeriodoPagato(Date expiredDate) {
	String periodo = null;
	SimpleDateFormat sdfPeriodo = new SimpleDateFormat("MMMM - yyyy", Locale.ITALIAN);
	periodo = sdfPeriodo.format(expiredDate);
	return periodo;
    }

    public Date getLastExpiration(long subscriptionId, long plainId) {
	if (logger.isDebugEnabled())
	    logger.debug("Calcolo data scadenza per ultimo pagamento effettuato da: " + subscriptionId + " e pacchetto: " + plainId);
	Calendar expireDate = Calendar.getInstance();
	try {
	    SubscriptionDTO subscriptionTmp = subscriptionBusiness.findById(subscriptionId);
	    PackageCoursesDTO plainTmp = plainBusiness.findById(plainId);
	    if (subscriptionTmp != null) {
		Page<DeadlineDTO> dlListTmp = deadlineBusiness.findLastPaiedBySubscriptionIdAndPlainId(subscriptionTmp, plainTmp);
		if (dlListTmp == null || dlListTmp.getContent().isEmpty()) {
		    logger.error("Errore: impossibile trovare un pagamento associato ad una scadenza");
		    return null;
		}

		DeadlineDTO dlTmp = dlListTmp.getContent().get(0);
		expireDate.setTime(dlTmp.getDeadlineDate());

	    } else {
		logger.error("Errore: impossibile trovare un iscrizione con id: " + subscriptionId);
		return null;
	    }

	} catch (Exception e) {
	    logger.error("Errore", e);
	    return null;
	}

	return expireDate.getTime();
    }

    public String getNameFilter() {
	return nameFilter;
    }

    public Date getNow() {
	return new Date();
    }

    public int getPage() {
	return page;
    }

    public long getPlainFilter() {
	return plainFilter;
    }

    protected HttpServletRequest getRequest() {
	return ServletActionContext.getRequest();
    }

    protected HttpServletResponse getResponse() {
	return ServletActionContext.getResponse();
    }

    protected HttpSession getSession() {
	return ServletActionContext.getRequest().getSession();
    }

    protected Object getSessionAttribute(String name) {
	return ServletActionContext.getRequest().getSession().getAttribute(name);
    }

    public int getSize() {
	return size;
    }

    public UserDTO getUserDTO() {
	return userDTO;
    }

    @Override
    public void prepare() throws Exception {
	logger.debug("prepare()");
	userDTO = (UserDTO) getSessionAttribute(session_attribute_userHadler);

    }

    protected Object removeSessionAttribute(String name) {
	HttpSession session = ServletActionContext.getRequest().getSession();
	Object value = session.getAttribute(name);
	session.removeAttribute(name);
	return value;
    }

    public void setNameFilter(String nameFilter) {
	this.nameFilter = nameFilter;
    }

    public void setPage(int page) {
	this.page = page;
    }

    public void setPlainFilter(long plainFilter) {
	this.plainFilter = plainFilter;
    }

    protected void setSessionAttribute(String name, Object value) {
	ServletActionContext.getRequest().getSession().setAttribute(name, value);
    }

    public void setSize(int size) {
	this.size = size;
    }

    public void setUserDTO(UserDTO userDTO) {
	this.userDTO = userDTO;
    }

    public String getStringDeadlineDate(Date date) {
	String d = "";
	if (date != null) {
	    SimpleDateFormat sdfDeadline = new SimpleDateFormat("MM/yyyy");
	    d = sdfDeadline.format(date);
	}

	return d;
    }

    public Date getSportingYearEndDate() {
	return sportingYearEndDate;
    }

    public void setSportingYearEndDate(Date sportingYearEndDate) {
	this.sportingYearEndDate = sportingYearEndDate;
    }

}
