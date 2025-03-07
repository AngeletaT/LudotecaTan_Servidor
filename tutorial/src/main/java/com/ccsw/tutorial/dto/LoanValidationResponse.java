package com.ccsw.tutorial.dto;

import java.util.List;

public class LoanValidationResponse {

    private boolean valid;
    private List<String> errorMessages;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
