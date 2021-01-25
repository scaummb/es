package com.example.elasticsearchtest.core.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.management.RuntimeErrorException;

/**
 * @author moubin.mo
 * @date: 2021/1/25 18:24
 */

public class BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

	public BaseController() {
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handleException(Exception ex){
		RestResponse restResponse;
		String exMessage;
		if (ex instanceof RuntimeErrorException) {
			RuntimeErrorException errorException = (RuntimeErrorException)ex;
			String errorDetail = this.getErrorDetail(ex);
			exMessage = "";
			restResponse = new RestResponse();
			restResponse.setErrorDetails(String.format("Request [%s]: %s", Thread.currentThread().getId(), errorDetail));
			LOGGER.error(String.format("Exception in processStat request [%s]: %s",  Thread.currentThread().getId(), errorDetail));
		} else if (ex instanceof BindException) {
			LOGGER.error(String.format("Exception in processStat request [%s]: %s",  Thread.currentThread().getId(), ex.getMessage() != null ? ex.getMessage() : ""), ex);
			BindException bindException = (BindException)ex;
			FieldError fieldError = bindException.getFieldError();
			exMessage = fieldError.getDefaultMessage();
			String scope = "general";
			int errorCode = 506;
			if (!StringUtils.isEmpty(exMessage)) {
				String[] message = exMessage.split(",");
				scope = message.length > 1 ? message[0] : scope;
				errorCode = message.length > 1 ? Integer.parseInt(message[1]) : errorCode;
				exMessage = message.length > 2 ? message[2] : exMessage;
			}

			String localizedMessage = "error";
			restResponse = new RestResponse(scope, errorCode, localizedMessage);
			restResponse.setErrorDetails(String.format("Request [%s]: %s",  Thread.currentThread().getId(), exMessage));
		} else {
			LOGGER.error(String.format("Exception in processStat request [%s]: %s",  Thread.currentThread().getId(), ex.getMessage() != null ? ex.getMessage() : ""), ex);
			String localizedMessage = "general";
			restResponse = new RestResponse("general", 500, localizedMessage);
			restResponse.setErrorDetails(String.format("Request [%s]: %s",  Thread.currentThread().getId(), ex.toString()));
		}

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity(restResponse, responseHeaders, HttpStatus.OK);
	}

	private String getErrorDetail(Exception ex) {
		String errorDetail;
		if (ex.getCause() != null) {
			errorDetail = ex.getCause().toString();
		} else {
			errorDetail = ex.toString();
		}
		return errorDetail;
	}

}
