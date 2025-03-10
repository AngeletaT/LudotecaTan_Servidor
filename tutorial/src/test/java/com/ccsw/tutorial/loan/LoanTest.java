package com.ccsw.tutorial.loan;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ccsw.tutorial.dto.loan.LoanDto;
import com.ccsw.tutorial.entities.Loan;
import com.ccsw.tutorial.repository.LoanRepository;
import com.ccsw.tutorial.service.loan.LoanServiceImpl;

@ExtendWith(MockitoExtension.class)
public class LoanTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanServiceImpl loanService;

    // TEST LIST ALL LOANS
    @Test
    public void findAllShouldReturnAllLoans() {

        List<Loan> list = new ArrayList<>();
        list.add(mock(Loan.class));

        when(loanRepository.findAll()).thenReturn(list);

        List<Loan> loans = loanService.findAll();

        assertNotNull(loans);
        assertEquals(1, loans.size());
    }

    // TEST SAVE LOAN
    public static final String LOAN_NAME = "Loan1";

    @Test
    public void saveNotExistsLoanIdShouldInsert() {

        LoanDto loanDto = new LoanDto();
        loanDto.setRentalDate("2025-03-01");
        loanDto.setReturnDate("2025-03-10");

        ArgumentCaptor<Loan> loan = ArgumentCaptor.forClass(Loan.class);

        loanService.save(null, loanDto);

        verify(loanRepository).save(loan.capture());

        assertEquals("2025-03-01", loan.getValue().getRentalDate().toString());
        assertEquals("2025-03-10", loan.getValue().getReturnDate().toString());
    }

    // TEST UPDATE LOAN
    public static final Long EXISTS_LOAN_ID = 1L;

    @Test
    public void saveExistsLoanIdShouldUpdate() {

        LoanDto loanDto = new LoanDto();
        loanDto.setRentalDate("2025-03-01");
        loanDto.setReturnDate("2025-03-10");

        Loan loan = mock(Loan.class);
        when(loanRepository.findById(EXISTS_LOAN_ID)).thenReturn(Optional.of(loan));

        loanService.save(EXISTS_LOAN_ID, loanDto);

        verify(loanRepository).save(loan);
    }

    // TEST DELETE LOAN
    @Test
    public void deleteExistsLoanIdShouldDelete() throws Exception {

        Loan loan = mock(Loan.class);
        when(loanRepository.findById(EXISTS_LOAN_ID)).thenReturn(Optional.of(loan));

        loanService.delete(EXISTS_LOAN_ID);

        verify(loanRepository).deleteById(EXISTS_LOAN_ID);
    }

    // TEST GET LOAN
    public static final Long NOT_EXISTS_LOAN_ID = 0L;

    @Test
    public void getExistsLoanIdShouldReturnLoan() {

        Loan loan = mock(Loan.class);
        when(loan.getId()).thenReturn(EXISTS_LOAN_ID);
        when(loanRepository.findById(EXISTS_LOAN_ID)).thenReturn(Optional.of(loan));

        Loan loanResponse = loanService.get(EXISTS_LOAN_ID);

        assertNotNull(loanResponse);
        assertEquals(EXISTS_LOAN_ID, loan.getId());
    }

    @Test
    public void getNotExistsLoanIdShouldReturnNull() {

        when(loanRepository.findById(NOT_EXISTS_LOAN_ID)).thenReturn(Optional.empty());

        Loan loan = loanService.get(NOT_EXISTS_LOAN_ID);

        assertNull(loan);
    }
}