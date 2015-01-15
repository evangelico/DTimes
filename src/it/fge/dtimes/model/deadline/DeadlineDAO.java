package it.fge.dtimes.model.deadline;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeadlineDAO extends JpaRepository<DeadlineDTO, Long> {

    @Query(value = "SELECT COUNT(d.id) FROM deadline d WHERE d.packageCoursesId = :plainId AND d.paymentId = :payId AND d.active='T'", nativeQuery = true)
    public abstract BigInteger countByPaymentAndPlain(@Param("payId") long payId, @Param("plainId") long plainId);

    @Query(value = "SELECT COUNT(d.id) FROM deadline d JOIN subscription s WHERE d.subscriptionId = s.id AND d.packageCoursesId = :plainId AND d.paymentId = :payId AND (s.name LIKE %:nominative% OR s.surname LIKE %:nominative%) AND d.active='T'", nativeQuery = true)
    public abstract BigInteger countByPaymentAndPlainAndNominative(@Param("payId") long payId, @Param("plainId") long plainId, @Param("nominative") String nominative);

    @Query(value = "SELECT COUNT(d.id) FROM deadline d WHERE d.packageCoursesId = :plainId AND d.paymentId IS NOT NULL AND d.active='T'", nativeQuery = true)
    public abstract BigInteger countByPlain(@Param("plainId") long plainId);

    @Query(value = "SELECT COUNT(d.id) FROM deadline d WHERE d.packageCoursesId = :plainId AND d.deadlineDate >= :startDate AND d.deadlineDate <= :endDate AND d.active='T'", nativeQuery = true)
    public abstract BigInteger countByPlainInPeriod(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("plainId") long plainId);

    @Modifying
    @Query(value = "DELETE d FROM deadline d WHERE d.paymentId IS NULL AND d.subscriptionId = :subscriptionId AND d.packageCoursesId = :plainId ", nativeQuery = true)
    public abstract void deleteAllWithPaymentNullBySubscriptionAndPlain(@Param("subscriptionId") long subscriptionId, @Param("plainId") long plainId);

    @Query(value = "SELECT COUNT(DISTINCT(d.subscriptionId)) FROM deadline d JOIN packageCourses p WHERE d.packageCoursesId = p.id AND (d.paymentId IS NULL AND (d.deadlineDate <= :date OR p.subscriptionPlain = 'T')) AND d.active='T'", nativeQuery = true)
    public abstract BigInteger findAllExpired(@Param("date") Date date);

    @Query(value = "SELECT d FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND d.active='T'", countQuery = "SELECT COUNT(d.id) FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND d.active='T'")
    public abstract Page<DeadlineDTO> findAllExpired(@Param("date") Date date, Pageable paramPageable);

    @Query(value = "SELECT d FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND d.packageCourses.id = :plain AND d.active='T'", countQuery = "SELECT COUNT(d.id) FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND d.packageCourses.id = :plain AND d.active='T'")
    public abstract Page<DeadlineDTO> findAllExpiredByPlain(@Param("date") Date time, @Param("plain") long plainFilter, Pageable paramPageable);

    @Query(value = "SELECT d FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND d.packageCourses.id = :plain AND (d.subscription.name LIKE %:nominative% OR d.subscription.surname LIKE %:nominative%) AND d.active='T'", countQuery = "SELECT COUNT(d.id) FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND d.packageCourses.id = :plain AND (d.subscription.name LIKE %:nominative% OR d.subscription.surname LIKE %:nominative%) AND d.active='T'")
    public abstract Page<DeadlineDTO> findAllExpiredLikeNominativeAndPlain(@Param("date") Date time, @Param("nominative") String nameFilter, @Param("plain") long plainFilter, Pageable paramPageable);

    @Query(value = "SELECT d FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND (d.subscription.name LIKE %:nominative% OR d.subscription.surname LIKE %:nominative%) AND d.active='T'", countQuery = "SELECT COUNT(d.id) FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND (d.subscription.name LIKE %:nominative% OR d.subscription.surname LIKE %:nominative%) AND d.active='T'")
    public abstract Page<DeadlineDTO> findAllExpiredLikeNominativeAndPlain(@Param("date") Date time, @Param("nominative") String nameFilter, Pageable paramPageable);

    @Query(value = "SELECT d FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND d.active='T'")
    public abstract List<DeadlineDTO> findAllExpiredNoPaginated(@Param("date") Date date);

    @Query(value = "SELECT d FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND d.subscription.id = :subId AND d.active='T'")
    public abstract List<DeadlineDTO> findAllExpiredNoPaginated(@Param("date") Date date, @Param("subId") long id);

    @Query(value = "SELECT d FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND d.active='F'", countQuery = "SELECT COUNT(d.id) FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND d.active='F'")
    public abstract Page<DeadlineDTO> findAllHide(@Param("date") Date date, Pageable paramPageable);

    @Query(value = "SELECT d FROM DeadlineDTO d WHERE d.payment.id = :payId AND d.active='T'")
    public abstract List<DeadlineDTO> findByPayment(@Param("payId") long id);

    @Query(value = "SELECT d FROM DeadlineDTO d WHERE d.subscription.id = :subscriptionId AND d.packageCourses.id = :plainId AND d.deadlineDate <= :date AND d.payment IS NULL AND d.active='T'")
    public abstract DeadlineDTO findBySubscriptionIdAndPlainId(@Param("subscriptionId") long subscriptionId, @Param("plainId") long plainId, @Param("date") Date date);
    
    @Query(value = "SELECT d FROM DeadlineDTO d WHERE d.subscription.id = :subscriptionId AND d.packageCourses.id = :plainId AND d.deadlineDate = :date")
    public abstract DeadlineDTO findBySubscriptionIdAndPlainIdInDate(@Param("subscriptionId") long subscriptionId, @Param("plainId") long plainId, @Param("date") Date date);

    @Query(value = "SELECT d FROM DeadlineDTO d WHERE d.subscription.id = :subscriptionId AND d.packageCourses.id = :plainId AND d.payment.id = :payId AND d.active='T' ORDER BY d.deadlineDate DESC")
    public abstract Page<DeadlineDTO> findLastPaiedBySubscriptionIdAndPlainId(@Param("payId") long paymentId, @Param("subscriptionId") long subscriptionId, @Param("plainId") long plainId, Pageable paramPageable);

    @Query(value = "SELECT d FROM DeadlineDTO d WHERE d.subscription.id = :subscriptionId AND d.packageCourses.id = :plainId AND d.payment IS NOT NULL AND d.active='T' ORDER BY d.deadlineDate DESC")
    public abstract Page<DeadlineDTO> findLastPaiedBySubscriptionIdAndPlainId(@Param("subscriptionId") long subscriptionId, @Param("plainId") long plainId, Pageable paramPageable);

    @Modifying
    @Query(value = "UPDATE deadline d SET d.paymentId = NULL WHERE d.subscriptionId = :subscriptionId AND d.packageCoursesId = :plainId AND d.paymentId = :paymentId", nativeQuery = true)
    public abstract void setPaymentNullBySubscriptionAndPaymentAndPlain(@Param("subscriptionId") long subscriptionId, @Param("paymentId") long paymentId, @Param("plainId") long plainId);

    @Query(value = "SELECT SUM(d.packageCourses.amount) FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND d.active='T'")
    public abstract Object sumAllExpired(@Param("date") Date date);

    @Query(value = "SELECT SUM(d.packageCourses.amount) FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND d.packageCourses.id = :plain AND d.active='T'")
    public abstract Object sumAllExpiredByPlain(@Param("date") Date time, @Param("plain") long plainFilter);

    @Query(value = "SELECT SUM(d.packageCourses.amount) FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND (d.subscription.name LIKE %:nominative% OR d.subscription.surname LIKE %:nominative%) AND d.active='T'")
    public abstract Object sumAllExpiredLikeNominative(@Param("date") Date time, @Param("nominative") String nameFilter);

    @Query(value = "SELECT SUM(d.packageCourses.amount) FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND d.packageCourses.id = :plain AND (d.subscription.name LIKE %:nominative% OR d.subscription.surname LIKE %:nominative%) AND d.active='T'")
    public abstract Object sumAllExpiredLikeNominativeAndPlain(@Param("date") Date time, @Param("nominative") String nameFilter, @Param("plain") long plainFilter);

    @Query(value = "SELECT d FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND d.packageCourses.id = :plain AND d.active='F'", countQuery = "SELECT COUNT(d.id) FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND d.packageCourses.id = :plain AND d.active='F'")
    public abstract Page<DeadlineDTO> findAllHideByPlain(@Param("date") Date time, @Param("plain") long plainFilter, Pageable paramPageable);

    @Query(value = "SELECT d FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND (d.subscription.name LIKE %:nominative% OR d.subscription.surname LIKE %:nominative%) AND d.active='F'", countQuery = "SELECT COUNT(d.id) FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND (d.subscription.name LIKE %:nominative% OR d.subscription.surname LIKE %:nominative%) AND d.active='F'")
    public abstract Page<DeadlineDTO> findAllHideLikeNominativeAndPlain(@Param("date") Date time, @Param("nominative") String nameFilter, Pageable paramPageable);

    @Query(value = "SELECT d FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND d.packageCourses.id = :plain AND (d.subscription.name LIKE %:nominative% OR d.subscription.surname LIKE %:nominative%) AND d.active='F'", countQuery = "SELECT COUNT(d.id) FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND d.packageCourses.id = :plain AND (d.subscription.name LIKE %:nominative% OR d.subscription.surname LIKE %:nominative%) AND d.active='F'")
    public abstract Page<DeadlineDTO> findAllHideLikeNominativeAndPlain(@Param("date") Date time, @Param("nominative") String nameFilter, @Param("plain") long plainFilter, Pageable paramPageable);

    @Query(value = "SELECT SUM(d.packageCourses.amount) FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND d.active='F'")
    public abstract Object sumAllHide(@Param("date") Date date);

    @Query(value = "SELECT SUM(d.packageCourses.amount) FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND d.packageCourses.id = :plain AND d.active='F'")
    public abstract Object sumAllHideByPlain(@Param("date") Date time, @Param("plain") long plainFilter);

    @Query(value = "SELECT SUM(d.packageCourses.amount) FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND (d.subscription.name LIKE %:nominative% OR d.subscription.surname LIKE %:nominative%) AND d.active='F'")
    public abstract Object sumAllHideLikeNominative(@Param("date") Date time, @Param("nominative") String nameFilter);

    @Query(value = "SELECT SUM(d.packageCourses.amount) FROM DeadlineDTO d WHERE (d.payment IS NULL AND (d.deadlineDate <= :date OR d.packageCourses.subscriptionPlain = 'T')) AND d.packageCourses.id = :plain AND (d.subscription.name LIKE %:nominative% OR d.subscription.surname LIKE %:nominative%) AND d.active='F'")
    public abstract Object sumAllHideLikeNominativeAndPlain(@Param("date") Date time, @Param("nominative") String nameFilter, @Param("plain") long plainFilter);

    @Query(value = "SELECT d FROM DeadlineDTO d WHERE d.payment IS NULL AND d.subscription.id = :subId AND d.active='T'")
    public abstract List<DeadlineDTO> findAllExpiredNoPaginated(@Param("subId") long id);

    @Modifying
    @Query(value = "DELETE d FROM deadline d WHERE d.paymentId IS NULL AND d.subscriptionId = :subscriptionId AND d.packageCoursesId = :plainId AND d.deadlineDate >= :startDate AND d.deadlineDate <= :endDate", nativeQuery = true)
    public abstract void deleteInPeriodBySubscriptionAndPlain(@Param("subscriptionId") long subscriptionId, @Param("plainId") long plainId, @Param("startDate") Date oldRegistrationDate, @Param("endDate") Date registrationDate);

}
