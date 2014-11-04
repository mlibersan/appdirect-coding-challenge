package com.martinlibersan.appdirect.codingchallenge.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private static final Logger logger = Logger.getLogger(CustomAuthenticationFailureHandler.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		logger.info("onAuthenticationFailure");
		logOpenIdAttributes(request, getOpenIdAuthenticationToken(exception));
		super.onAuthenticationFailure(request, response, exception);
	}

	private OpenIDAuthenticationToken getOpenIdAuthenticationToken(AuthenticationException exception) {
		return ((OpenIDAuthenticationToken) exception.getAuthentication());
	}

	private void logOpenIdAttributes(HttpServletRequest request, OpenIDAuthenticationToken openIdAuthenticationToken) throws ServletException {
		if (openIdAuthenticationToken != null) {
			for (OpenIDAttribute openIDAttribute : openIdAuthenticationToken.getAttributes()) {
				if (attributeHasValue(openIDAttribute)) {
					openIDAttribute.getName();
					openIDAttribute.getValues().get(0);
					logger.info("   " + openIDAttribute.getName() + " " + openIDAttribute.getValues().get(0));
				}
			}
		}
	}

	private boolean attributeHasValue(OpenIDAttribute openIDAttribute) {
		return openIDAttribute.getValues() != null && openIDAttribute.getValues().size() > 0;
	}
}