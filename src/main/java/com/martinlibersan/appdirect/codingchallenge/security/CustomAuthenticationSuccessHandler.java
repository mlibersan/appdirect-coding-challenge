package com.martinlibersan.appdirect.codingchallenge.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private static final Logger logger = Logger.getLogger(CustomAuthenticationSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		logger.info("onAuthenticationSuccess");
		logOpenIdAttributes(request, (OpenIDAuthenticationToken) authentication);
		super.onAuthenticationSuccess(request, response, authentication);
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
