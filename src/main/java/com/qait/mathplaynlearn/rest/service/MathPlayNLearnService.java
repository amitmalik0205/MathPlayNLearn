package com.qait.mathplaynlearn.rest.service;

import java.util.ArrayList;
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

import com.qait.mathplaynlearn.domain.Game;
import com.qait.mathplaynlearn.domain.GameDetails;
import com.qait.mathplaynlearn.domain.Group;
import com.qait.mathplaynlearn.domain.GroupMember;
import com.qait.mathplaynlearn.domain.SecurityQuestion;
import com.qait.mathplaynlearn.domain.User;
import com.qait.mathplaynlearn.dto.GroupDTO;
import com.qait.mathplaynlearn.dto.GroupMemberDTO;
import com.qait.mathplaynlearn.enums.MemberStatus;
import com.qait.mathplaynlearn.rest.dto.GameDetailsDTO;
import com.qait.mathplaynlearn.service.GameDetailsService;
import com.qait.mathplaynlearn.service.GameService;
import com.qait.mathplaynlearn.service.GroupService;
import com.qait.mathplaynlearn.service.SecurityQuestionService;
import com.qait.mathplaynlearn.service.UserService;
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
	
	@GET
	@Path("xml-test")
	@Produces(MediaType.APPLICATION_XML)
	public Customer XMLTest() {
		Customer customer = new Customer();
		customer.setId(42);
		customer.setName("Amit Malik");
		customer.setAge(28);
		
		Address address = new Address();
		address.setCity("Delhi");
		address.setCountry("India");
		
		customer.setAddress(address);
		
		return customer;
	}
	
	@GET
	@Path("get-security-questions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSecurityQuestions() {
		SecurityQuestionService questionService = (SecurityQuestionService) appContext
				.getBean("securityQuestionService");
		return Response.status(200).entity(questionService.getAllQuestions())
				.build();
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

	
	@POST
	@Path("recover-password")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response recoverPassword(User user) {
		UserService userService = (UserService) appContext
				.getBean("userService");
		
		MathPlayNLearnServiceResponse response = new MathPlayNLearnServiceResponse();

		User savedUser = userService.getUserWithSecurityQuestion(user.getUserID());
		if (savedUser == null) {

			response.setCode("recoverPassword002");
			response.setMessage(MathPlayPropertiesFileReaderUtil
					.getPropertyValue("recoverPassword002"));

		} else {
			
			SecurityQuestion savedQuestion = savedUser.getSecurityQuestion();
			SecurityQuestion question = user.getSecurityQuestion();

			if (question.getQuestionId().equals(savedQuestion.getQuestionId())) {

				if (savedUser.getAnswer().replaceAll("\\s","").equalsIgnoreCase(user.getAnswer().replaceAll("\\s",""))) {

					response.setCode("recoverPassword001");
					response.setMessage(MathPlayPropertiesFileReaderUtil
							.getPropertyValue("recoverPassword001")
							+ " "
							+ savedUser.getPassword());

				} else {

					response.setCode("recoverPassword004");
					response.setMessage(MathPlayPropertiesFileReaderUtil
							.getPropertyValue("recoverPassword004"));
				}

			} else {
				response.setCode("recoverPassword003");
				response.setMessage(MathPlayPropertiesFileReaderUtil
						.getPropertyValue("recoverPassword003"));
			}
		}

		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Service will send email to the registered email ID.
	 * @param userID
	 * @return
	 *//*
	@POST
	@Path("recover-password")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response recoverPassword(User user) {
		UserService userService = (UserService) appContext
				.getBean("userService");
		MathPlayNLearnServiceResponse response = new MathPlayNLearnServiceResponse();

		User savedUser = userService.getUserByUserId(user.getUserID());
		if (savedUser == null) {

			response.setCode("recoverPassword002");
			response.setMessage(MathPlayPropertiesFileReaderUtil
					.getPropertyValue("recoverPassword002"));

		} else {

			SendPasswordDTO templateModel = new SendPasswordDTO();
			templateModel.setUserID(savedUser.getUserID());
			templateModel.setPassword(savedUser.getPassword());

			boolean isEmailSent = EmailUtil
					.sendEmail(
							MathPlayPropertiesFileReaderUtil
									.getVelocityTemplateProperties("send.password.email.subject"),
							savedUser.getEmail(), EmailType.SEND_PASSWORD,
							templateModel);

			if (isEmailSent) {
				response.setCode("recoverPassword001");
				response.setMessage(MathPlayPropertiesFileReaderUtil
						.getPropertyValue("recoverPassword001"));
			} else {
				response.setCode("recoverPassword003");
				response.setMessage(MathPlayPropertiesFileReaderUtil
						.getPropertyValue("recoverPassword003"));
			}
		}

		return Response.status(200).entity(response).build();
	}*/
	
	
	@POST
	@Path("save-game-score")
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response saveUserGameScore(GameDetailsDTO details) {

		GameDetailsService detailsService = (GameDetailsService) appContext
				.getBean("gameDetailsService");
		UserService userService = (UserService) appContext
				.getBean("userService");
		GameService gameService = (GameService) appContext
				.getBean("gameService");

		MathPlayNLearnServiceResponse response = new MathPlayNLearnServiceResponse();

		response.setCode("saveUserScore001");
		response.setMessage(MathPlayPropertiesFileReaderUtil
				.getPropertyValue("saveUserScore001"));

		User savedUser = userService.getUserByUserId(details.getUserID());

		if (savedUser == null) {
			response.setCode("saveUserScore003");
			response.setMessage(MathPlayPropertiesFileReaderUtil
					.getPropertyValue("saveUserScore003"));
		} else {

			boolean isError = false;
			String gameName = details.getGameName();
			String gameClass = details.getGameClass();
			Game savedGame = gameService.getGameByNameAndClass(gameName,
					gameClass);

			if (savedGame == null) {
				Game newGame = new Game();
				newGame.setGameClass(gameClass);
				newGame.setGameName(gameName);

				boolean isGameSaved = gameService.saveGame(newGame);

				if (isGameSaved) {
					savedGame = gameService.getGameByNameAndClass(gameName,
							gameClass);

				} else {
					isError = true;
					response.setCode("saveUserScore004");
					response.setMessage(MathPlayPropertiesFileReaderUtil
							.getPropertyValue("saveUserScore004"));
				}
			}

			if (!isError) {

				boolean isSaved = false;
				GameDetails savedGameDetails = detailsService
						.getGameDetailsByUserAndGame(savedUser.getId(),
								savedGame.getGameId());

				if (savedGameDetails == null) {
					GameDetails gameDetails = new GameDetails();
					gameDetails.setGame(savedGame);
					gameDetails.setUser(savedUser);
					gameDetails.setLevel(details.getLevel());
					gameDetails.setUserScore(details.getUserScore());
					isSaved = detailsService.saveGameDetails(gameDetails);

				} else {
					savedGameDetails.setLevel(details.getLevel());
					savedGameDetails.setUserScore(details.getUserScore());
					isSaved = detailsService.saveGameDetails(savedGameDetails);
				}

				if (!isSaved) {
					response.setCode("saveUserScore002");
					response.setMessage(MathPlayPropertiesFileReaderUtil
							.getPropertyValue("saveUserScore002"));
				}
			}
		}
		return Response.status(200).entity(response).build();
	}
	
	
	/**
	 * Creates new group
	 * @param userID - Owner of the group
	 * @param groupName - Name of the group
	 * @return
	 */
	@GET
	@Path("create-group")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createGroup(@QueryParam("userID") String userID,
			@QueryParam("groupName") String groupName) {

		UserService userService = (UserService) appContext
				.getBean("userService");
		GroupService groupService = (GroupService) appContext
				.getBean("groupService");
		MathPlayNLearnServiceResponse response = new MathPlayNLearnServiceResponse();

		User user = userService.getUserByUserId(userID);
		if (user == null) {

			response.setCode("createGroup004");
			response.setMessage(MathPlayPropertiesFileReaderUtil
					.getPropertyValue("createGroup004"));

		} else {

			Group savedGroup = groupService.getGroupByGroupName(groupName);
			if (savedGroup != null) {

				response.setCode("createGroup003");
				response.setMessage(MathPlayPropertiesFileReaderUtil
						.getPropertyValue("createGroup003"));

			} else {

				Group newGroup = new Group();
				newGroup.setGroupName(groupName);
				newGroup.setGroupOwner(user);
				boolean isGroupSaved = groupService.saveGroup(newGroup);

				if (isGroupSaved) {

					response.setCode("createGroup001");
					response.setMessage(MathPlayPropertiesFileReaderUtil
							.getPropertyValue("createGroup001"));

				} else {

					response.setCode("createGroup002");
					response.setMessage(MathPlayPropertiesFileReaderUtil
							.getPropertyValue("createGroup002"));
				}
			}
		}

		return Response.status(200).entity(response).build();
	}
	
	@GET
	@Path("group-list-for-owner")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGroupList(@QueryParam("userID") String ownerId) {
		GroupService groupService = (GroupService) appContext
				.getBean("groupService");
		
		List<Group> list = groupService.getGroupListForOwner(ownerId);
		List<GroupDTO> groupList = new ArrayList<GroupDTO>();
		
		for(Group group : list) {
			GroupDTO dto = new GroupDTO();
			dto.setGroupID(group.getGroupID());
			dto.setGroupName(group.getGroupName());
			groupList.add(dto);
		}
		return Response.status(200).entity(groupList).build();
	}
	
	@POST
	@Path("add-member-to-group")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMemberToGroup(GroupMemberDTO member) {
		boolean isSaved = false;
		GroupService groupService = (GroupService) appContext
				.getBean("groupService");
		UserService userService = (UserService) appContext
				.getBean("userService");
		GroupMemberService memberService = (GroupMemberService) appContext
				.getBean("groupMemberService");
		MathPlayNLearnServiceResponse response = new MathPlayNLearnServiceResponse();

		response.setCode("addMemberToGroup001");
		response.setMessage(MathPlayPropertiesFileReaderUtil
				.getPropertyValue("addMemberToGroup001"));

		User savedUser = userService.loadUser(member.getUserID());
		Group savedgroup = groupService.getGroupByGroupId(member.getGroupID());

		if (savedUser != null && savedgroup != null) {
			GroupMember groupMember = new GroupMember();
			groupMember.setGroup(savedgroup);
			groupMember.setMember(savedUser);
			groupMember.setStatus(MemberStatus.WAITING);
			
			isSaved = memberService.saveMember(groupMember);
		}
		
		if(!isSaved) {
			response.setCode("addMemberToGroup002");
			response.setMessage(MathPlayPropertiesFileReaderUtil
					.getPropertyValue("addMemberToGroup002"));
		}

		return Response.status(200).entity(response).build();
	}
}
