package com.qait.mathplaynlearn.rest.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.qait.mathplaynlearn.domain.User;
import com.qait.mathplaynlearn.service.UserService;

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

}
