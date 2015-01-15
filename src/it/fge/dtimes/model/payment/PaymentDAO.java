package it.fge.dtimes.model.payment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentDAO extends JpaRepository<PaymentDTO, Long> {

    @Query(value = "SELECT p FROM PaymentDTO p WHERE p.subscription.id = :subscriptionId ORDER BY p.paymentDate DESC", countQuery = "SELECT COUNT(p.id) FROM PaymentDTO p WHERE p.subscription.id = :subscriptionId")
    public abstract Page<PaymentDTO> findAllBySubscription(@Param("subscriptionId") long subscriptionId, Pageable paramPageable);

    @Query(value = "SELECT p FROM PaymentDTO p WHERE p.subscription.id = :subscriptionId ORDER BY p.paymentDate DESC")
    public abstract Page<PaymentDTO> findLastBySubscriptionId(@Param("subscriptionId") long subscriptionId, Pageable paramPageable);

    @Query(value = "SELECT p FROM PaymentDTO p JOIN p.packagesCourses pc WHERE p.subscription.id = :subscriptionId AND pc.id = :plainId ORDER BY p.paymentDate DESC")
    public abstract Page<PaymentDTO> findLastBySubscriptionIdAndPlainId(@Param("subscriptionId") long subscriptionId, @Param("plainId") long plainId, Pageable paramPageable);

    @Query(value = "SELECT p FROM PaymentDTO p WHERE p.paymentDate >= :startDate AND p.paymentDate <= :endDate ORDER BY p.paymentDate DESC", countQuery = "SELECT COUNT(p.id) FROM PaymentDTO p WHERE p.paymentDate >= :startDate AND p.paymentDate <= :endDate")
    public abstract Page<PaymentDTO> findAllInPeriod(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable paramPageable);

    @Query(value = "SELECT p FROM PaymentDTO p JOIN p.packagesCourses pc WHERE p.paymentDate >= :startDate AND p.paymentDate <= :endDate AND pc.id = :plainId ORDER BY p.paymentDate DESC", countQuery = "SELECT COUNT(p.id) FROM PaymentDTO p JOIN p.packagesCourses pc WHERE p.paymentDate >= :startDate AND p.paymentDate <= :endDate AND pc.id = :plainId")
    public abstract Page<PaymentDTO> findAllInPeriodByPlain(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("plainId") long plainId, Pageable paramPageable);

    @Query(value = "SELECT SUM(p.amountPaied) FROM PaymentDTO p JOIN p.packagesCourses pc WHERE p.paymentDate >= :startDate AND p.paymentDate <= :endDate AND pc.id = :plainId")
    public abstract Double getImportPaiedInPeriodByPlain(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("plainId") long plainId);

    @Query(value = "SELECT SUM(p.amountPaied) FROM PaymentDTO p WHERE p.paymentDate >= :startDate AND p.paymentDate <= :endDate")
    public abstract Double getImportPaiedInPeriod(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT p FROM PaymentDTO p JOIN p.packagesCourses pc WHERE pc.id = :plainId ORDER BY p.paymentDate DESC", countQuery = "SELECT COUNT(p.id) FROM PaymentDTO p JOIN p.packagesCourses pc WHERE pc.id = :plainId")
    public abstract Page<PaymentDTO> findAllByPlain(@Param("plainId") long plainId, Pageable paramPageable);

    @Query(value = "SELECT SUM(p.amountPaied) FROM PaymentDTO p JOIN p.packagesCourses pc  WHERE pc.id = :plainId")
    public abstract Double getImportPaiedByPlain(@Param("plainId") long plainId);

    @Query(value = "SELECT SUM(p.amountPaied) FROM PaymentDTO p")
    public abstract Double getImportPaiedByPlain();

    @Query(value = "SELECT p FROM PaymentDTO p JOIN p.packagesCourses pc WHERE p.paymentDate >= :startDate AND p.paymentDate <= :endDate AND pc.id = :plainId AND (p.subscription.name LIKE %:nominative% OR p.subscription.surname LIKE %:nominative%) ORDER BY p.paymentDate DESC", countQuery = "SELECT COUNT(p.id) FROM PaymentDTO p JOIN p.packagesCourses pc WHERE p.paymentDate >= :startDate AND p.paymentDate <= :endDate AND pc.id = :plainId AND (p.subscription.name LIKE %:nominative% OR p.subscription.surname LIKE %:nominative%)")
    public abstract Page<PaymentDTO> findAllInPeriodByPlainAdnNominative(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("nominative") String nameFilter, @Param("plainId") long plainId, Pageable paramPageable);

    @Query(value = "SELECT p FROM PaymentDTO p WHERE p.paymentDate >= :startDate AND p.paymentDate <= :endDate AND (p.subscription.name LIKE %:nominative% OR p.subscription.surname LIKE %:nominative%) ORDER BY p.paymentDate DESC", countQuery = "SELECT COUNT(p.id) FROM PaymentDTO p WHERE p.paymentDate >= :startDate AND p.paymentDate <= :endDate AND (p.subscription.name LIKE %:nominative% OR p.subscription.surname LIKE %:nominative%)")
    public abstract Page<PaymentDTO> findAllInPeriodByNominative(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("nominative") String nameFilter, Pageable paramPageable);

    @Query(value = "SELECT p FROM PaymentDTO p JOIN p.packagesCourses pc WHERE pc.id = :plainId AND (p.subscription.name LIKE %:nominative% OR p.subscription.surname LIKE %:nominative%) ORDER BY p.paymentDate DESC", countQuery = "SELECT COUNT(p.id) FROM PaymentDTO p JOIN p.packagesCourses pc WHERE pc.id = :plainId AND (p.subscription.name LIKE %:nominative% OR p.subscription.surname LIKE %:nominative%)")
    public abstract Page<PaymentDTO> findAllByPlainAndNominative(@Param("nominative") String nameFilter, @Param("plainId") long plainId, Pageable paramPageable);

    @Query(value = "SELECT SUM(p.amountPaied) FROM PaymentDTO p JOIN p.packagesCourses pc WHERE p.paymentDate >= :startDate AND p.paymentDate <= :endDate AND pc.id = :plainId AND (p.subscription.name LIKE %:nominative% OR p.subscription.surname LIKE %:nominative%)")
    public abstract Double getImportPaiedInPeriodByPlainAndNominative(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("nominative") String nameFilter, @Param("plainId") long plainId);

    @Query(value = "SELECT SUM(p.amountPaied) FROM PaymentDTO p WHERE p.paymentDate >= :startDate AND p.paymentDate <= :endDate AND (p.subscription.name LIKE %:nominative% OR p.subscription.surname LIKE %:nominative%)")
    public abstract Double getImportPaiedInPeriodByNominative(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("nominative") String nameFilter);

    @Query(value = "SELECT SUM(p.amountPaied) FROM PaymentDTO p JOIN p.packagesCourses pc WHERE pc.id = :plainId AND (p.subscription.name LIKE %:nominative% OR p.subscription.surname LIKE %:nominative%)")
    public abstract Double getImportPaiedByPlainAndNominative(@Param("nominative") String nameFilter, @Param("plainId") long plainId);

    @Query(value = "SELECT p FROM PaymentDTO p WHERE p.subscription.name LIKE %:nominative% OR p.subscription.surname LIKE %:nominative% ORDER BY p.paymentDate DESC", countQuery = "SELECT COUNT(p.id) FROM PaymentDTO p WHERE p.subscription.name LIKE %:nominative% OR p.subscription.surname LIKE %:nominative%")
    public abstract Page<PaymentDTO> findAllByNominative(@Param("nominative") String nameFilter, Pageable paramPageable);

    @Query(value = "SELECT SUM(p.amountPaied) FROM PaymentDTO p WHERE p.subscription.name LIKE %:nominative% OR p.subscription.surname LIKE %:nominative%")
    public abstract Double getImportPaiedByNominative(@Param("nominative") String nameFilter);

    @Query(value = "SELECT COUNT(p.id) FROM PaymentDTO p JOIN p.packagesCourses pc WHERE pc.id = :plainId")
    public abstract long getCountByPlain(@Param("plainId") long plainId);

    @Query(value = "SELECT COUNT(p.id) FROM PaymentDTO p JOIN p.packagesCourses pc WHERE pc.id = :plainId AND p.paymentDate >= :startDate AND p.paymentDate <= :endDate")
    public abstract long getCountByPlainInPeriod(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("plainId") long plainId);

}
