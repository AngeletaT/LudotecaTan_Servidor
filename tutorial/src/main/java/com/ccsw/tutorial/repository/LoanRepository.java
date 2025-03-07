package com.ccsw.tutorial.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ccsw.tutorial.entities.Loan;

/**
 * @author ccsw
 *
 */
public interface LoanRepository extends JpaRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {

    @Query("select l from Loan l where l.game.id = :gameId and :date between l.rentalDate and l.returnDate and (:excludeId is null or l.id <> :excludeId)")
    List<Loan> findLoansForGameOnDate(@Param("gameId") Long gameId, @Param("date") Date date,
            @Param("excludeId") Long excludeId);

    @Query("select l from Loan l where l.client.id = :clientId and :date between l.rentalDate and l.returnDate and (:excludeId is null or l.id <> :excludeId)")
    List<Loan> findLoansForClientOnDate(@Param("clientId") Long clientId, @Param("date") Date date,
            @Param("excludeId") Long excludeId);
}