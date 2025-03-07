package com.ccsw.tutorial.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.tutorial.dto.LoanDto;
import com.ccsw.tutorial.dto.LoanSearchDto;
import com.ccsw.tutorial.dto.LoanValidationResponse;
import com.ccsw.tutorial.entities.Loan;
import com.ccsw.tutorial.service.loan.LoanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author ccsw
 *
 */

@Tag(name = "Loan", description = "API of Loan")

@RequestMapping(value = "/loan")
@RestController
@CrossOrigin(origins = "*")
public class LoanController {

    @Autowired
    LoanService loanService;

    @Autowired
    ModelMapper mapper;

    /**
     * Método para recuperar todos los {@link Loan}
     *
     * @return {@link List} de {@link LoanDto}
     */
    @Operation(summary = "Find All", description = "Method that return a list of all Loans")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<LoanDto> findAll() {
        List<Loan> loans = this.loanService.findAll();

        return loans.stream()
                .map(e -> mapper.map(e, LoanDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Método para recuperar un listado paginado de {@link Loan}
     *
     * @param dto dto de búsqueda
     * @return {@link Page} de {@link LoanDto}
     */
    @Operation(summary = "Find Paginated", description = "Method that return a paginated list of Loans")
    @RequestMapping(path = "/page", method = RequestMethod.POST)
    public Page<LoanDto> findPage(@RequestBody LoanSearchDto dto) {

        Page<Loan> page = this.loanService.findPage(dto);

        return new PageImpl<>(
                page.getContent().stream()
                        .map(e -> mapper.map(e, LoanDto.class))
                        .collect(Collectors.toList()),
                page.getPageable(),
                page.getTotalElements());
    }

    /**
     * Método para recuperar un listado paginado y filtrado de {@link Loan}
     * Filtros gameId, clientId, loanDate se incluyen en el dto
     *
     * @param dto dto de búsqueda con los filtros y paginación.
     * @return {@link Page} de {@link LoanDto}
     */
    @Operation(summary = "Find Filtered Paginated", description = "Method that return a paginated and filtered of Loans")
    @RequestMapping(path = "/filter", method = RequestMethod.POST)
    public Page<LoanDto> findPageFiltered(@RequestBody LoanSearchDto dto) {

        Page<Loan> page = this.loanService.findPageFiltered(dto);

        return new PageImpl<>(
                page.getContent().stream()
                        .map(e -> mapper.map(e, LoanDto.class))
                        .collect(Collectors.toList()),
                page.getPageable(),
                page.getTotalElements());
    }

    /**
     * Método para crear o actualizar un {@link Loan}
     *
     * @param dto datos de la entidad
     * @return {@link LoanDto}
     */
    @Operation(summary = "Create/Update", description = "Method that creates or updates a Loan")
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public ResponseEntity<String> save(@RequestBody LoanDto dto) {
        this.loanService.save(dto.getId(), dto);
        return ResponseEntity.ok("{\"message\": \"Se ha realizado correctamente la acción\"}");

    }

    /**
     * Método para eliminar un {@link Loan}
     *
     * @param id PK de la entidad
     * @throws Exception Si no se encuentra el {@link Loan}
     */
    @Operation(summary = "Delete", description = "Method that deletes a Loan")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable Long id) throws Exception {
        this.loanService.delete(id);
        return ResponseEntity.ok("{\"message\": \"Se ha realizado correctamente la acción\"}");
    }

    /**
     * Método para validar un {@link Loan}
     *
     * @param dto datos de la entidad
     * @return {@link LoanValidationResponse}
     */
    @Operation(summary = "Validate Loan", description = "Method that validates a Loan")
    @RequestMapping(path = "/validate", method = RequestMethod.POST)
    public LoanValidationResponse validateLoan(@RequestBody LoanDto dto) {
        return this.loanService.validateLoan(dto);
    }

}