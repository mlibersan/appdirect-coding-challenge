package com.martinlibersan.appdirect.codingchallenge.web.service;

import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import net.oauth.OAuth;
import net.oauth.OAuthException;
import net.oauth.OAuthProblemException;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OAuthSignatureService {

	private static final Logger logger = Logger.getLogger(OAuthSignatureService.class); 

	@Autowired
	private XmlParser xmlParser;

	public String requestURL(String consumerKey, String consumerSecret, String endpointURL) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException,
			IOException {
		logger.info("sign");
		logger.info("   requestURL " + endpointURL);
		OAuthConsumer consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
		URL url = new URL(endpointURL);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		// sign the request (consumer is a Signpost DefaultOAuthConsumer)
		consumer.sign(request);
		// send the request
		request.connect();

		StringWriter writer = new StringWriter();
		IOUtils.copy(request.getInputStream(), writer, request.getContentEncoding());
		String response = writer.toString();
		logger.info("\n" + xmlParser.format(response));
		return response;
	}

	public void verifyRequest(String consumerKey, String consumerSecret, HttpServletRequest request) throws OAuthProblemException, IOException, OAuthException, URISyntaxException {
		logger.info("verify ");
		String requestURL = getEndpointURL(request);
		
		net.oauth.OAuthMessage message = net.oauth.server.OAuthServlet.getMessage(request, requestURL);
		message.requireParameters(OAuth.OAUTH_CONSUMER_KEY, OAuth.OAUTH_SIGNATURE_METHOD, OAuth.OAUTH_SIGNATURE);

		net.oauth.OAuthConsumer consumer = new net.oauth.OAuthConsumer(null, consumerKey, consumerSecret, null);
		net.oauth.OAuthAccessor accessor = new net.oauth.OAuthAccessor(consumer);
		net.oauth.signature.OAuthSignatureMethod.newSigner(message, accessor).validate(message);
	}

	protected String getEndpointURL(HttpServletRequest request) throws MalformedURLException, URISyntaxException {
		logger.info("   headers : ");
		Enumeration<String> headerNames = request.getHeaderNames();
		boolean isRequestBehindProxy = false;
		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
			String headerValue = request.getHeader(headerName);
			logger.info("      " + headerName + " " + headerValue);
			if ("x-forwarded-proto".equals(headerName)) {
				isRequestBehindProxy = true;
			}
			
		}
		
		String containerEndpointURLString = net.oauth.server.OAuthServlet.getRequestURL(request);
		if (isRequestBehindProxy) {
			URL endpointURL = new URL(containerEndpointURLString);
			String host = request.getHeader("host");
			String scheme = request.getHeader("x-forwarded-proto");		
			URI proxyEndpointURL = new URI(scheme, host, endpointURL.getPath(), null);
			logger.info("   proxy endpoint URL      " + proxyEndpointURL.toString());
			return proxyEndpointURL.toString();
		}else {
			logger.info("   container endpoint URL      " + containerEndpointURLString);
			return containerEndpointURLString;
		}
	}

}
