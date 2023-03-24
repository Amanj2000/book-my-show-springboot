package com.bookmyshow.config;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class FeignErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		byte[] responseBody = getResponseBody(response);
		String message = new String(responseBody, StandardCharsets.UTF_8);
		int status = response.status();

		if(status >= 400 && status < 500) {
			JSONObject json = new JSONObject(message);
			if (status == HttpStatus.NOT_FOUND.value()) {
				return new EntityNotFoundException(json.getString("error"));
			} else {
				return new IllegalArgumentException(json.getString("error"));
			}
		} else if(status >= 500 && status <= 599) {
			return new Exception("error occurred in review-system service while processing request\n" + message);
		} else {
			return new Exception("error occurred while processing request\n" + message);
		}
	}

	private byte[] getResponseBody(Response response) {
		try {
			return Util.toByteArray(response.body().asInputStream());
		} catch (IOException ignored) {
			// ignore
		}
		return new byte[0];
	}
}
