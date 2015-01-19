package it.fge.dtimes.model.subscription;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import it.fge.dtimes.model.payment.PaymentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface SubscriptionDAO extends JpaRepository<SubscriptionDTO, Long> {

    @Query(value = "SELECT s FROM SubscriptionDTO s WHERE s.fiscalCode = :CF")
    SubscriptionDTO findByCF(@Param("CF") String fiscalCode);

    @Query(value = "SELECT s FROM SubscriptionDTO s WHERE 1 = 1 ORDER BY s.registrationDate ASC", countQuery = "SELECT COUNT(s.id) FROM SubscriptionDTO s WHERE 1 = 1 ORDER BY s.registrationDate ASC")
    Page<SubscriptionDTO> findFirst(Pageable paramPageable);

    @Query(value = "SELECT s FROM SubscriptionDTO s JOIN s.packagesCourses pc WHERE pc.id = :plain ORDER BY s.registrationDate ASC", countQuery = "SELECT COUNT(s.id) FROM SubscriptionDTO s JOIN s.packagesCourses pc WHERE pc.id = :plain ORDER BY s.registrationDate ASC")
    Page<SubscriptionDTO> findByPlain(@Param("plain") long plainFilter, Pageable paramPageable);

    @Query(value = "SELECT s FROM SubscriptionDTO s WHERE s.name LIKE %:nominative% OR s.surname LIKE %:nominative% ORDER BY s.registrationDate ASC", countQuery = "SELECT COUNT(s.id) FROM SubscriptionDTO s WHERE s.name LIKE %:nominative% OR s.surname LIKE %:nominative% ORDER BY s.registrationDate ASC")
    Page<SubscriptionDTO> findLikeNominative(@Param("nominative") String nameFilter, Pageable paramPageable);

    @Query(value = "SELECT s FROM SubscriptionDTO s JOIN s.packagesCourses pc WHERE pc.id = :plain AND (s.name LIKE %:nominative% OR s.surname LIKE %:nominative%) ORDER BY s.registrationDate ASC", countQuery = "SELECT COUNT(s.id) FROM SubscriptionDTO s JOIN s.packagesCourses pc WHERE pc.id = :plain AND (s.name LIKE %:nominative% OR s.surname LIKE %:nominative%) ORDER BY s.registrationDate ASC")
    Page<SubscriptionDTO> findLikeNominativeAndPlain(@Param("nominative") String nameFilter,@Param("plain") long plainFilter, Pageable paramPageable);
    
    @Query(value = "SELECT DISTINCT(s.id) FROM SubscriptionDTO s JOIN s.packagesCourses pc JOIN s.payments p WHERE pc.id = :plain AND p.paymentDate >= :startDate AND p.paymentDate <= :endDate")
    List<SubscriptionDTO> getCountByPlain(@Param("plain") long plainId,@Param("startDate") Date startDate,@Param("endDate") Date endDate);
    
    @Query("SELECT COUNT(s.id) FROM SubscriptionDTO s JOIN s.packagesCourses pc WHERE pc.id = :plain")
    long getCountByPlain(@Param("plain") long plainId);
    
    @Query("SELECT COUNT(s.id) FROM SubscriptionDTO s WHERE s.medicalCertificateDate IS NULL OR s.medicalCertificateDate < :now")
    long getCountCertificatesExpired(@Param("now") Date now);

    @Query(value = "SELECT s FROM SubscriptionDTO s WHERE (s.name LIKE %:nominative% OR s.surname LIKE %:nominative%) AND s.medicalCertificateDate < :now OR s.medicalCertificateDate IS NULL ORDER BY s.medicalCertificateDate ASC", countQuery = "SELECT COUNT(s.id) FROM SubscriptionDTO s WHERE (s.name LIKE %:nominative% OR s.surname LIKE %:nominative%) AND s.medicalCertificateDate < :now OR s.medicalCertificateDate IS NULL ORDER BY s.medicalCertificateDate ASC")
    Page<SubscriptionDTO> findLikeNominative(@Param("nominative") String nameFilter,@Param("now") Date date, Pageable paramPageable);
    
    @Query(value = "SELECT s FROM SubscriptionDTO s WHERE s.medicalCertificateDate < :now OR s.medicalCertificateDate IS NULL ORDER BY s.medicalCertificateDate ASC", countQuery = "SELECT COUNT(s.id) FROM SubscriptionDTO s WHERE s.medicalCertificateDate < :now OR s.medicalCertificateDate IS NULL ORDER BY s.medicalCertificateDate ASC")
    Page<SubscriptionDTO> findLikeNominative(@Param("now") Date date, Pageable paramPageable);

    @Query(value = "SELECT COUNT(s.id) FROM SubscriptionDTO s JOIN s.packagesCourses pc WHERE pc.id = :plain AND (s.name LIKE %:nominative% OR s.surname LIKE %:nominative%)")
	long countTotalSubscriptionByPlainAndNominative(@Param("nominative") String nominative,@Param("plain") long plainId);

    @Query(value = "SELECT COUNT(s.id) FROM SubscriptionDTO s WHERE s.name LIKE %:nominative% OR s.surname LIKE %:nominative%")
	long countTotalSubscriptionByNominative(@Param("nominative") String nominative);

    @Query(value = "SELECT COUNT(s.id) FROM SubscriptionDTO s JOIN s.packagesCourses pc WHERE pc.id = :plain ")
	long countTotalSubscriptionByPlain(@Param("plain") long plainId);
    
   
}
