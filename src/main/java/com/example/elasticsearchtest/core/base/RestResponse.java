package com.example.elasticsearchtest.core.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse extends RestResponseBase {
    @JsonProperty("response")
    private Object responseObject;

    public RestResponse() {
        this.version = RestVersion.current().toString();
        this.errorCode = 200;
    }

    public RestResponse(String errorScope, int errorCode, String errorDescription) {
        super(errorScope, errorCode, errorDescription);
    }

    public RestResponse(Object responseObject) {
        this.responseObject = responseObject;
        this.errorCode = 200;
    }

    public Object getResponseObject() {
        return this.responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }
}
