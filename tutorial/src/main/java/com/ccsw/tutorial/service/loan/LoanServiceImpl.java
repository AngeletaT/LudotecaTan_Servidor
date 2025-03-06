package com.ccsw.tutorial.service.loan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.dto.LoanDto;
import com.ccsw.tutorial.dto.LoanSearchDto;
import com.ccsw.tutorial.entities.Loan;
import com.ccsw.tutorial.infrastructure.specifications.LoanSpecification;
import com.ccsw.tutorial.repository.ClientRepository;
import com.ccsw.tutorial.repository.GameRepository;
import com.ccsw.tutorial.repository.LoanRepository;

import jakarta.transaction.Transactional;

/**
 * @author ccsw
 *
 */
@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    GameRepository gameRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Loan get(Long id) {
        return this.loanRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Loan> findAll() {
        return this.loanRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Loan> findPage(LoanSearchDto dto) {
        return this.loanRepository.findAll(Specification.where(null), dto.getPageable().getPageable());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Loan> findPageFiltered(LoanSearchDto dto) {
        Specification<Loan> spec = Specification.where(null);

        if (dto.getGameId() != null) {
            spec = spec.and(LoanSpecification.gameId(dto.getGameId()));
        }

        if (dto.getClientId() != null) {
            spec = spec.and(LoanSpecification.clientId(dto.getClientId()));
        }

        if (dto.getLoanDate() != null && !dto.getLoanDate().isEmpty()) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dto.getLoanDate());
                spec = spec.and(LoanSpecification.activeOnDate(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return this.loanRepository.findAll(spec, dto.getPageable().getPageable());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Loan> findFiltered(Long gameId, Long clientId, String loanDate) {
        Specification<Loan> spec = Specification.where(null);

        if (gameId != null) {
            spec = spec.and(LoanSpecification.gameId(gameId));
        }

        if (clientId != null) {
            spec = spec.and(LoanSpecification.clientId(clientId));
        }

        if (loanDate != null && !loanDate.isEmpty()) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(loanDate);
                spec = spec.and(LoanSpecification.activeOnDate(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return this.loanRepository.findAll(spec);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, LoanDto data) {
        Loan loan;

        if (id == null) {
            loan = new Loan();
        } else {
            loan = this.loanRepository.findById(id).orElse(null);
        }

        BeanUtils.copyProperties(data, loan, "id", "game", "client", "rentalDate", "returnDate");

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            loan.setRentalDate(sdf.parse(data.getRentalDate()));
            loan.setReturnDate(sdf.parse(data.getReturnDate()));
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing date" + e.getMessage(), e);
        }

        loan.setGame(this.gameRepository.findById(data.getGame().getId()).orElse(null));
        loan.setClient(this.clientRepository.findById(data.getClient().getId()).orElse(null));

        this.loanRepository.save(loan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {
        if (this.get(id) == null) {
            throw new Exception("Not exists");
        }

        this.loanRepository.deleteById(id);
    }
}
