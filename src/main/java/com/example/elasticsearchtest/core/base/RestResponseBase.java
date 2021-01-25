package com.example.elasticsearchtest.core.base;

public class RestResponseBase {
    protected String version = RestVersion.current().toString();
    protected String errorScope;
    protected Integer errorCode;
    protected String errorDescription;
    protected String errorDetails;

    public RestResponseBase() {
    }

    public RestResponseBase(String errorScope, int errorCode, String errorDescription) {
        this.errorScope = errorScope;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static boolean isSuccess(RestResponseBase response) {
        return "general".equalsIgnoreCase(response.errorScope) && (200 == response.errorCode || 201 == response.errorCode);
    }

    public String getErrorScope() {
        return this.errorScope;
    }

    public void setErrorScope(String errorScope) {
        this.errorScope = errorScope;
    }

    public Integer getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return this.errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorDetails() {
        return this.errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }
}
