package com.elliothutchinson.cms.dto;

public class SuccessMessage {

    private String success;

    public SuccessMessage(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
