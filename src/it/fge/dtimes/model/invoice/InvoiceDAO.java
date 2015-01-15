package it.fge.dtimes.model.invoice;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface InvoiceDAO extends JpaRepository<InvoiceDTO, Long> {

	@Query(value = "SELECT i FROM InvoiceDTO i WHERE i.payment.id = :paymentId")
	InvoiceDTO findByPayment(@Param("paymentId") long paymentId);

}
