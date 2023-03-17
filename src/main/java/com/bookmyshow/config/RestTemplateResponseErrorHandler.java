package com.bookmyshow.config;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.ResponseErrorHandler;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
		return (httpResponse.getStatusCode().series() == CLIENT_ERROR || httpResponse.getStatusCode().series() == SERVER_ERROR);
	}

	@Override
	public void handleError(ClientHttpResponse httpResponse) throws IOException {
		byte[] responseBody = getResponseBody(httpResponse);
		String message = new String(responseBody,StandardCharsets.UTF_8);

		if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
			System.out.println("error occurred in review-system service while processing request\n" + message);
		} else if (httpResponse.getStatusCode().series() == CLIENT_ERROR) {
			JSONObject json = new JSONObject(message);
			if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new EntityNotFoundException(json.getString("error"));
			} else {
				throw new IllegalArgumentException(json.getString("error"));
			}
		}
	}

	protected byte[] getResponseBody(ClientHttpResponse response) {
		try {
			return FileCopyUtils.copyToByteArray(response.getBody());
		}
		catch (IOException ex) {
			// ignore
		}
		return new byte[0];
	}
}
