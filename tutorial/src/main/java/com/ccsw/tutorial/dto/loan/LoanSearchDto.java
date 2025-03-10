package com.ccsw.tutorial.dto.loan;

import com.ccsw.tutorial.common.pagination.PageableRequest;

/**
 * @author ccsw
 *
 */
public class LoanSearchDto {

    private PageableRequest pageable;
    private Long gameId;
    private Long clientId;
    private String loanDate;

    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }
}
