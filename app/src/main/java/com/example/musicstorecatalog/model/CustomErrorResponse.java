package com.example.musicstorecatalog.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

// Originally written in Cognizant Module 2.3 - 02-we-record-service-validation-error-handling
public class CustomErrorResponse {

    private String errorMsg;
    private int status;
    private String errorCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;

    public CustomErrorResponse() {
    }

    public CustomErrorResponse(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomErrorResponse that = (CustomErrorResponse) o;
        return status == that.status && Objects.equals(errorMsg, that.errorMsg) && Objects.equals(errorCode, that.errorCode) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorMsg, status, errorCode, timestamp);
    }

    @Override
    public String toString() {
        return "CustomErrorResponse{" +
                "errorMsg='" + errorMsg + '\'' +
                ", status=" + status +
                ", errorCode='" + errorCode + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}