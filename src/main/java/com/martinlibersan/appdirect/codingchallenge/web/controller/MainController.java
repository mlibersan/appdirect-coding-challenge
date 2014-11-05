package com.martinlibersan.appdirect.codingchallenge.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.martinlibersan.appdirect.codingchallenge.web.model.Person;
import com.martinlibersan.appdirect.codingchallenge.web.service.OAuthSignatureService;
import com.martinlibersan.appdirect.codingchallenge.web.service.PersonService;
import com.martinlibersan.appdirect.codingchallenge.web.service.SubscriptionXMLParser;
import com.martinlibersan.appdirect.codingchallenge.web.service.UserXMLParser;
import com.martinlibersan.appdirect.codingchallenge.web.service.XmlParser;

/**
 * This class is the Main Controller for the web application
 *
 */
@Controller
public class MainController {

	private static final Logger logger = Logger.getLogger(MainController.class);
	
	//Developer key to sign each OAuth request to AppDirect
	//and validate outgoing requests from AppDirect 
	private static final String OAUTH_CONSUMER_KEY = "martin-libersan-services-conseil-coding-challenge-16426";
	private static final String OAUTH_CONSUMER_SECRET = "3hDydzrm0lrIxCTO";

	@Autowired
	private PersonService personService;

	@Autowired
	private XmlParser xmlParser;

	@Autowired
	private OAuthSignatureService oauthSignatureService;

	@Autowired
	private SubscriptionXMLParser subscriptionXMLParser;

	@Autowired
	private UserXMLParser userXMLParser;

	/**
	 * Display default page
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/")
	public String people(Model model) {
		logger.info("defaul view");
		return "index";
	}

	/**
	 * Display user creation screen
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/create")
	public String createPeople(Model model) {
		logger.info("create user");
		model.addAttribute("person", new Person());
		return "people_add";
	}

	/**
	 * Display user modification screen
	 * 
	 * @param model
	 * @param personOpenId
	 * @return
	 */
	@RequestMapping("/edit")
	public String editPeople(Model model, String personOpenId) {
		logger.info("edit user");
		logger.info("   personOpenId " + personOpenId);
		Person person = personService.findPersonByOpenId(personOpenId);
		model.addAttribute("person", person);
		return "people_edit";
	}

	/**
	 * Display xml response of an appdirect enpoint url associated 
	 * to the user creation transaction. 
	 * 
	 * @param model
	 * @param personId
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/xml/{personId}")
	public String xmlPeople(Model model, @PathVariable("personId") Integer personId) throws UnsupportedEncodingException {
		logger.info("view user xml originating request");
		logger.info("   personId " + personId);
		Person person = personService.findPersonById(personId);
		String unformattedXml = new String(person.getXml(), "UTF-8");
		String formattedXml = xmlParser.format(unformattedXml);
		logger.debug(formattedXml);
		model.addAttribute("formattedXml", formattedXml);
		model.addAttribute("person", person);
		return "people_xml";
	}

	/**
	 * List user of the application
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String listPeople(Model model) {
		logger.info("list user");
		model.addAttribute("peopleList", personService.listPeople());
		return "people_list";
	}

	/**
	 * Create a user
	 * 
	 * @param model
	 * @param person
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPerson(Model model, @ModelAttribute("person") Person person, BindingResult result) {
		logger.info("add user");
		personService.addPerson(person);
		return "index";
	}

	/**
	 * Update a user
	 * 
	 * @param model
	 * @param person
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updatePerson(Model model, @ModelAttribute("person") Person person, BindingResult result) {
		logger.info("update user");
		logger.info("   personId " + person.getId());
		Person personToUpdate = personService.findPersonById(person.getId());
		if (personToUpdate != null) {
			personToUpdate.setOpenId(person.getOpenId());
			personToUpdate.setFirstName(person.getFirstName());
			personToUpdate.setLastName(person.getLastName());
			personService.updatePerson(personToUpdate);
		}
		return "index";
	}

	/**
	 * Delete a user
	 * 
	 * @param model
	 * @param personId
	 * @return
	 */
	@RequestMapping("/delete/{personId}")
	public String deletePerson(Model model, @PathVariable("personId") Integer personId) {
		logger.info("remove user");
		logger.info("   personId " + personId);
		personService.removePerson(personId);
		return "index";
	}

	/**
	 * Handle order subscription and create a user based on creator information.
	 * 
	 * @param model
	 * @param endpointUrl
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/subscription/order")
	public String orderSubscription(Model model, String endpointUrl, HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("order subscription");
		logger.info("   endpoint URL : " + endpointUrl);

		try {
			if ("https://www.appdirect.com/rest/api/events/dummyOrder".equals(endpointUrl)) {
				// Development Mode no need to verify AppDirecy dummy endpoint
			} else {
				oauthSignatureService.verifyRequest(OAUTH_CONSUMER_KEY, OAUTH_CONSUMER_SECRET, request);
			}
			try {
				String endpointUrlResponse = oauthSignatureService.requestURL(OAUTH_CONSUMER_KEY, OAUTH_CONSUMER_SECRET, endpointUrl);
				Person person = subscriptionXMLParser.parse(endpointUrlResponse);
				Person personAlreadyExist = personService.findPersonByOpenId(person.getOpenId());
				if (personAlreadyExist != null) {
					personAlreadyExist.setXml(person.getXml());
					personService.updatePerson(personAlreadyExist);
				}else {
					personService.addPerson(person);
				}
				String unformattedXml = new String(person.getXml(), "UTF-8");
				String formattedXml = xmlParser.format(unformattedXml);
				logger.debug(formattedXml);
				model.addAttribute("subscription", person);
			} catch (Throwable e) {
				e.printStackTrace();
				model.addAttribute("exception", e);
				return "subscription_order_error";
			}
		} catch (Throwable e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "OAuth Signature refused");
		}
		return "subscription_order_success";
	}

	/**
	 * Handle subscription cancellation and delete the user based based on creator information.
	 * 
	 * @param model
	 * @param endpointUrl
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/subscription/cancel")
	public String cancelSubscription(Model model, String endpointUrl, HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("cancel subscription");
		logger.info("   endpoint URL : " + endpointUrl);

		try {
			if ("https://www.appdirect.com/rest/api/events/dummyCancel".equals(endpointUrl)) {
				// Development Mode no need to verify AppDirecy dummy endpoint
			} else {
				oauthSignatureService.verifyRequest(OAUTH_CONSUMER_KEY, OAUTH_CONSUMER_SECRET, request);
			}
			try {
				String endpointUrlResponse = oauthSignatureService.requestURL(OAUTH_CONSUMER_KEY, OAUTH_CONSUMER_SECRET, endpointUrl);
				Person person = subscriptionXMLParser.parse(endpointUrlResponse);
				Person personToRemoved = personService.findPersonByOpenId(person.getOpenId());
				if (personToRemoved != null) {
					personService.removePerson(personToRemoved.getId());
				}
				String unformattedXml = new String(person.getXml(), "UTF-8");
				String formattedXml = xmlParser.format(unformattedXml);
				logger.debug(formattedXml);
				model.addAttribute("subscription", person);
			} catch (Throwable e) {
				e.printStackTrace();
				model.addAttribute("exception", e);
				return "subscription_cancel_error";
			}
		} catch (Throwable e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "OAuth Signature refused");
		}
		return "subscription_cancel_success";
	}

	
	/**
	 * Handle user assignment and create the user
	 * 
	 * @param model
	 * @param endpointUrl
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/user/assignment")
	public String assignUser(Model model, String endpointUrl, HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("user assignment");
		logger.info("   endpoint URL : " + endpointUrl);

		try {
			if ("https://www.appdirect.com/rest/api/events/dummyAssign".equals(endpointUrl)) {
				// Development Mode no need to verify AppDirecy dummy endpoint
			} else {
				oauthSignatureService.verifyRequest(OAUTH_CONSUMER_KEY, OAUTH_CONSUMER_SECRET, request);
			}
			try {
				String endpointUrlResponse = oauthSignatureService.requestURL(OAUTH_CONSUMER_KEY, OAUTH_CONSUMER_SECRET, endpointUrl);
				Person person = userXMLParser.parse(endpointUrlResponse);
				Person personAlreadyExist = personService.findPersonByOpenId(person.getOpenId());
				if (personAlreadyExist != null) {
					personAlreadyExist.setXml(person.getXml());
					personService.updatePerson(personAlreadyExist);
				}else {
					personService.addPerson(person);
				}
				String unformattedXml = new String(person.getXml(), "UTF-8");
				String formattedXml = xmlParser.format(unformattedXml);
				logger.debug(formattedXml);
				model.addAttribute("user", person);
			} catch (Throwable e) {
				e.printStackTrace();
				model.addAttribute("exception", e);
				return "user_assignment_error";
			}
		} catch (Throwable e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "OAuth Signature refused");
		}
		return "user_assignment_success";
	}

	/**
	 * Handle user unassigment and delete the user
	 * 
	 * @param model
	 * @param endpointUrl
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/user/unassignment")
	public String unassignUser(Model model, String endpointUrl, HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("/user/assignment subscription");
		logger.info("   endpoint URL : " + endpointUrl);

		try {
			if ("https://www.appdirect.com/rest/api/events/dummyUnassign".equals(endpointUrl)) {
				// Development Mode no need to verify AppDirecy dummy endpoint
			} else {
				oauthSignatureService.verifyRequest(OAUTH_CONSUMER_KEY, OAUTH_CONSUMER_SECRET, request);
			}
			try {
				String endpointUrlResponse = oauthSignatureService.requestURL(OAUTH_CONSUMER_KEY, OAUTH_CONSUMER_SECRET, endpointUrl);
				Person person = userXMLParser.parse(endpointUrlResponse);
				Person personToRemoved = personService.findPersonByOpenId(person.getOpenId());
				if (personToRemoved != null) {
					personService.removePerson(personToRemoved.getId());
				}
				String unformattedXml = new String(person.getXml(), "UTF-8");
				String formattedXml = xmlParser.format(unformattedXml);
				logger.debug(formattedXml);
				model.addAttribute("user", person);
			} catch (Throwable e) {
				e.printStackTrace();
				model.addAttribute("exception", e);
				return "user_assignment_error";
			}
		} catch (Throwable e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "OAuth Signature refused");
		}
		return "user_unassignment_success";
	}
	
	/**
	 * Handle openId login
	 * 
	 * @param openidUrl
	 * @param error
	 * @param logout
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "openidUrl", required = false) String openidUrl,@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout,HttpServletRequest request) {
		String openIdUrl = request.getParameter("openidUrl");
		logger.info("login openIdUrl " + openIdUrl );
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		
		if (openIdUrl == null) {
			//Development Mode with Google
			//If no openIdUrl is specified we test locally with google
			//and create a test user in database with my google account open id url
			String testGoogleOpenId = "https://www.google.com/accounts/o8/id?id=AItOawm4dZwYExaP8gWMZsPCUEZkPUZQ4n0Hvmc";
			Person person = personService.findPersonByOpenId(testGoogleOpenId);
			if (person == null) {
				person =  new Person();
				person.setFirstName("Martin");
				person.setLastName("Libersan");
				person.setOpenId(testGoogleOpenId);
				personService.addPerson(person);
			}
			openIdUrl = "https://www.google.com/accounts/o8/id";
		}
		
		model.addObject("openIdUrl", openIdUrl);
		model.setViewName("login_openid");

		return model;

	}
	
}
