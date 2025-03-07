package com.ccsw.tutorial.service.loan;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ccsw.tutorial.dto.LoanDto;
import com.ccsw.tutorial.dto.LoanSearchDto;
import com.ccsw.tutorial.dto.LoanValidationResponse;
import com.ccsw.tutorial.entities.Loan;

/**
 * @author ccsw
 *
 */
public interface LoanService {

    /**
     * Recupera un {@link Loan} a través de su ID
     *
     * @param id PK de la entidad
     * @return {@link Loan}
     */
    Loan get(Long id);

    /**
     * Recupera un listado de préstamos {@link Loan}
     *
     * @return {@link List} de {@link Loan}
     */
    List<Loan> findAll();

    /**
     * Método para recuperar un listado paginado de {@link Loan}
     *
     * @param dto dto de búsqueda
     * @return {@link Page} de {@link Loan}
     */
    Page<Loan> findPage(LoanSearchDto dto);

    /**
     * Método para buscar {@link Loan} con filtros
     *
     * @param gameId   Id del juego
     * @param clientId Id del cliente
     * @param loanDate fecha de alquiler "yyyy-MM-dd"
     * @return {@link List} de {@link Loan}
     */
    List<Loan> findFiltered(Long gameId, Long clientId, String loanDate);

    /**
     * Método para recuperar un listado paginado y filtrado de {@link Loan}
     *
     * @param dto dto de búsqueda con los filtros.
     * @return {@link Page} de {@link Loan}
     */
    Page<Loan> findPageFiltered(LoanSearchDto dto);

    /**
     * Método para crear o actualizar un {@link Loan}
     *
     * @param id  PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, LoanDto dto);

    /**
     * Método para eliminar un {@link Loan}
     *
     * @param id PK de la entidad
     */
    void delete(Long id) throws Exception;

    /**
     * Método para validar un {@link Loan}
     *
     * @param dto datos de la entidad
     * @return {@link LoanValidationResponse}
     */
    LoanValidationResponse validateLoan(LoanDto dto);

}
