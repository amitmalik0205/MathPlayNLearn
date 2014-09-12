package com.qait.mathplaynlearn.rest.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.qait.mathplaynlearn.domain.User;
import com.qait.mathplaynlearn.dto.SendPasswordDTO;
import com.qait.mathplaynlearn.enums.EmailType;
import com.qait.mathplaynlearn.service.UserService;
import com.qait.mathplaynlearn.util.EmailUtil;
import com.qait.mathplaynlearn.util.MathPlayPropertiesFileReaderUtil;

@Path("math-play-service")
public class MathPlayNLearnService {

	private static final Logger logger = Logger
			.getLogger(MathPlayNLearnService.class);

	ApplicationContext appContext = new ClassPathXmlApplicationContext(
			"../applicationContext.xml");

	@Path("/text")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String text() {
		return "Its working";
	}

	@POST
	@Path("register-new-user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerUser(User user) {
		UserService userService = (UserService) appContext
				.getBean("userService");
		return userService.saveUser(user);
	}

	@POST
	@Path("sign-in")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authinticateUser(User user) {
		UserService userService = (UserService) appContext
				.getBean("userService");
		MathPlayNLearnServiceResponse response = new MathPlayNLearnServiceResponse();

		if (userService.authenticateUser(user.getUserID(), user.getPassword()) != null) {
			response.setCode("signIn001");
			response.setMessage(MathPlayPropertiesFileReaderUtil
					.getPropertyValue("signIn001"));
		} else {
			response.setCode("signIn002");
			response.setMessage(MathPlayPropertiesFileReaderUtil
					.getPropertyValue("signIn002"));
		}

		return Response.status(200).entity(response).build();
	}

	/**
	 * Service will return the List of UseID's matching with @param searchStr
	 * @param searchStr
	 *            - Search String
	 * @return - List of UserID's
	 */
	@GET
	@Path("search-user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchUser(@QueryParam("userIDString") String searchStr) {
		UserService userService = (UserService) appContext
				.getBean("userService");

		List<Object[]> list = userService.getMatchingUserID(searchStr);
		return Response.status(200).entity(list).build();
	}

	/**
	 * Service will send email to the registered email ID.
	 * @param userID
	 * @return
	 */
	@POST
	@Path("recover-password")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response recoverPassword(String userID) {
		UserService userService = (UserService) appContext.getBean("userService");
		MathPlayNLearnServiceResponse response = new MathPlayNLearnServiceResponse();
		
		User savedUser = userService.getUserByUserId(userID);
		if(savedUser == null) {
			
			response.setCode("recoverPassword002");
			response.setMessage(MathPlayPropertiesFileReaderUtil.getPropertyValue("recoverPassword002"));
			
		} else {
			
			SendPasswordDTO templateModel = new SendPasswordDTO();
			templateModel.setUserID(savedUser.getUserID());
			templateModel.setPassword(savedUser.getPassword());
			
			boolean isEmailSent = EmailUtil.sendEmail(MathPlayPropertiesFileReaderUtil.getVelocityTemplateProperties("send.password.email.subject"), savedUser.getEmail(), EmailType.SEND_PASSWORD, templateModel);
			
			if(isEmailSent) {
				response.setCode("recoverPassword001");
				response.setMessage(MathPlayPropertiesFileReaderUtil.getPropertyValue("recoverPassword001"));
			} else {
				response.setCode("recoverPassword003");
				response.setMessage(MathPlayPropertiesFileReaderUtil.getPropertyValue("recoverPassword003"));
			}
		}
		
		return Response.status(200).entity(response).build();
	}
}
