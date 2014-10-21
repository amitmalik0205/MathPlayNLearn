package com.qait.mathplaynlearn.rest.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.qait.mathplaynlearn.domain.Game;
import com.qait.mathplaynlearn.domain.GameDetails;
import com.qait.mathplaynlearn.domain.Group;
import com.qait.mathplaynlearn.domain.GroupMember;
import com.qait.mathplaynlearn.domain.SecurityQuestion;
import com.qait.mathplaynlearn.domain.User;
import com.qait.mathplaynlearn.dto.CreateGroupResponseDTO;
import com.qait.mathplaynlearn.dto.GetInvitationsDTO;
import com.qait.mathplaynlearn.dto.GroupDTO;
import com.qait.mathplaynlearn.dto.GroupMemberDTO;
import com.qait.mathplaynlearn.dto.GroupMemberInfoDTO;
import com.qait.mathplaynlearn.dto.GroupScoreDTO;
import com.qait.mathplaynlearn.dto.UpdateInvitationStatusDTO;
import com.qait.mathplaynlearn.enums.MemberStatus;
import com.qait.mathplaynlearn.rest.dto.GameDetailsDTO;
import com.qait.mathplaynlearn.service.GameDetailsService;
import com.qait.mathplaynlearn.service.GameService;
import com.qait.mathplaynlearn.service.GroupMemberService;
import com.qait.mathplaynlearn.service.GroupService;
import com.qait.mathplaynlearn.service.SecurityQuestionService;
import com.qait.mathplaynlearn.service.UserService;
import com.qait.mathplaynlearn.util.MathPlayPropertiesFileReaderUtil;

@Path("math-play-service")
public class MathPlayNLearnService {

	private static final Logger logger = Logger
			.getLogger(MathPlayNLearnService.class);

	@Autowired
	private SecurityQuestionService questionService;

	@Autowired
	private GameDetailsService detailsService;

	@Autowired
	private GameService gameService;

	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private GroupMemberService memberService;

	@Autowired
	@Qualifier("apnConfiguration")
	private Properties apnConfigurationProperties;

	ApplicationContext appContext = new ClassPathXmlApplicationContext(
			"../applicationContext.xml");

	@Path("/text")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String text() throws IOException {
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
		return Response.status(200).entity(questionService.getAllQuestions())
				.build();
	}

	@POST
	@Path("register-new-user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerUser(User user) {
		return userService.saveUser(user);
	}

	@POST
	@Path("sign-in")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authinticateUser(User user) {
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
	 * 
	 * @param searchStr
	 *            - Search String
	 * @return - List of UserID's
	 */
	@GET
	@Path("search-user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchUser(@QueryParam("userIDString") String searchStr,
			@QueryParam("groupID") long groupID) {
		List<GroupMemberInfoDTO> groupList = userService
				.getMatchingUserIDForGroup(searchStr, groupID);
		List<GroupMemberInfoDTO> fullList = new CopyOnWriteArrayList<GroupMemberInfoDTO>(
				userService.getMatchingUserID(searchStr));

		for (GroupMemberInfoDTO outer : groupList) {
			for (GroupMemberInfoDTO inner : fullList) {
				if (outer.getUserKey() == inner.getUserKey()) {
					fullList.remove(inner);
					break;
				}
			}
		}

		groupList.addAll(fullList);

		return Response.status(200).entity(groupList).build();
	}

	@POST
	@Path("recover-password")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response recoverPassword(User user) {
		MathPlayNLearnServiceResponse response = new MathPlayNLearnServiceResponse();

		User savedUser = userService.getUserWithSecurityQuestion(user
				.getUserID());
		if (savedUser == null) {

			response.setCode("recoverPassword002");
			response.setMessage(MathPlayPropertiesFileReaderUtil
					.getPropertyValue("recoverPassword002"));

		} else {

			SecurityQuestion savedQuestion = savedUser.getSecurityQuestion();
			SecurityQuestion question = user.getSecurityQuestion();

			if (question.getQuestionId().equals(savedQuestion.getQuestionId())) {

				if (savedUser
						.getAnswer()
						.replaceAll("\\s", "")
						.equalsIgnoreCase(
								user.getAnswer().replaceAll("\\s", ""))) {

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
	 * 
	 * @param userID
	 * @return
	 */
	/*
	 * @POST
	 * 
	 * @Path("recover-password")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response
	 * recoverPassword(User user) { UserService userService = (UserService)
	 * appContext .getBean("userService"); MathPlayNLearnServiceResponse
	 * response = new MathPlayNLearnServiceResponse();
	 * 
	 * User savedUser = userService.getUserByUserId(user.getUserID()); if
	 * (savedUser == null) {
	 * 
	 * response.setCode("recoverPassword002");
	 * response.setMessage(MathPlayPropertiesFileReaderUtil
	 * .getPropertyValue("recoverPassword002"));
	 * 
	 * } else {
	 * 
	 * SendPasswordDTO templateModel = new SendPasswordDTO();
	 * templateModel.setUserID(savedUser.getUserID());
	 * templateModel.setPassword(savedUser.getPassword());
	 * 
	 * boolean isEmailSent = EmailUtil .sendEmail(
	 * MathPlayPropertiesFileReaderUtil
	 * .getVelocityTemplateProperties("send.password.email.subject"),
	 * savedUser.getEmail(), EmailType.SEND_PASSWORD, templateModel);
	 * 
	 * if (isEmailSent) { response.setCode("recoverPassword001");
	 * response.setMessage(MathPlayPropertiesFileReaderUtil
	 * .getPropertyValue("recoverPassword001")); } else {
	 * response.setCode("recoverPassword003");
	 * response.setMessage(MathPlayPropertiesFileReaderUtil
	 * .getPropertyValue("recoverPassword003")); } }
	 * 
	 * return Response.status(200).entity(response).build(); }
	 */

	@POST
	@Path("save-game-score")
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response saveUserGameScore(GameDetailsDTO details) {
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
	 * 
	 * @param userID
	 *            - Owner of the group
	 * @param groupName
	 *            - Name of the group
	 * @return
	 */
	@GET
	@Path("create-group")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public Response createGroup(@QueryParam("userID") String userID,
			@QueryParam("groupName") String groupName) {
		CreateGroupResponseDTO response = new CreateGroupResponseDTO();

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

					// Add owner as group member
					Group createdGroup = groupService
							.getGroupByGroupName(groupName);
					GroupMember groupMember = new GroupMember(user,
							createdGroup, MemberStatus.ACCEPTED);
					memberService.saveMember(groupMember);

					response.setCode("createGroup001");
					response.setMessage(MathPlayPropertiesFileReaderUtil
							.getPropertyValue("createGroup001"));
					response.setGroupID(createdGroup.getGroupID());

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
		List<Group> list = groupService.getGroupListForOwner(ownerId);
		List<GroupDTO> groupList = new ArrayList<GroupDTO>();

		for (Group group : list) {
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
		MathPlayNLearnServiceResponse response = new MathPlayNLearnServiceResponse();

		response.setCode("addMemberToGroup001");
		response.setMessage(MathPlayPropertiesFileReaderUtil
				.getPropertyValue("addMemberToGroup001"));

		User savedUser = userService.getUser(member.getUserKey());
		Group savedgroup = groupService.getGroupByGroupId(member.getGroupID());

		if (savedUser != null && savedgroup != null) {
			GroupMember groupMember = new GroupMember(savedUser, savedgroup,
					MemberStatus.WAITING);
			isSaved = memberService.saveMember(groupMember);
		}

		if (!isSaved) {

			response.setCode("addMemberToGroup002");
			response.setMessage(MathPlayPropertiesFileReaderUtil
					.getPropertyValue("addMemberToGroup002"));
		} /*
		 * else { //Send Push Notification to user User groupOwner =
		 * groupService.getGroupOwner(savedgroup.getGroupID());
		 * 
		 * if(groupOwner != null) {
		 * 
		 * String certificateFileName =
		 * apnConfigurationProperties.getProperty("certificate.file"); String
		 * password =
		 * apnConfigurationProperties.getProperty("certificate.file.password");
		 * 
		 * StringBuilder message = new StringBuilder(groupOwner.getUserID());
		 * message.append(" has requested you to join group ");
		 * message.append(savedgroup.getGroupName());
		 * 
		 * Resource resource =
		 * appContext.getResource("classpath:/apn/"+certificateFileName);
		 * 
		 * try {
		 * MathPlayNLearnUtil.sendNotification(resource.getFile().toString(),
		 * password, savedUser.getDeviceToken(), message.toString()); } catch
		 * (Exception e) { e.printStackTrace();
		 * logger.fatal(MathPlayNLearnUtil.getExceptionDescriptionString(e)); }
		 * } }
		 */

		return Response.status(200).entity(response).build();
	}

	@GET
	@Path("get-group-members/{groupID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listGroupMembers(@PathParam("groupID") long groupID) {
		return Response.status(200)
				.entity(memberService.getMembersInfoByGroup(groupID)).build();
	}

	@POST
	@Path("delete-member-from-group")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteMember(GroupMemberDTO dto) {
		MathPlayNLearnServiceResponse response = new MathPlayNLearnServiceResponse();

		boolean isDeleted = memberService.deleteGroupMember(dto.getGroupID(),
				dto.getUserKey());

		if (isDeleted) {
			response.setCode("deleteMemberFromGroup001");
			response.setMessage(MathPlayPropertiesFileReaderUtil
					.getPropertyValue("deleteMemberFromGroup001"));
		} else {
			response.setCode("deleteMemberFromGroup002");
			response.setMessage(MathPlayPropertiesFileReaderUtil
					.getPropertyValue("deleteMemberFromGroup002"));
		}

		return Response.status(200).entity(response).build();
	}

	@POST
	@Path("delete-group")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteGroup(GroupMemberDTO dto) {
		MathPlayNLearnServiceResponse response = new MathPlayNLearnServiceResponse();

		Group savedGroup = groupService.getGroupByGroupId(dto.getGroupID());

		if (savedGroup != null) {
			boolean isDeleted = groupService.delete(savedGroup);

			if (isDeleted) {
				response.setCode("deleteGroup001");
				response.setMessage(MathPlayPropertiesFileReaderUtil
						.getPropertyValue("deleteGroup001"));

			} else {
				response.setCode("deleteGroup003");
				response.setMessage(MathPlayPropertiesFileReaderUtil
						.getPropertyValue("deleteGroup003"));
			}

		} else {
			response.setCode("deleteGroup002");
			response.setMessage(MathPlayPropertiesFileReaderUtil
					.getPropertyValue("deleteGroup002"));
		}

		return Response.status(200).entity(response).build();
	}

	@POST
	@Path("get-group-score")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGroupScore(GroupScoreDTO dto) {
		Game savedGame = gameService.getGameByNameAndClass(dto.getGameName(),
				dto.getGameClass());

		List<Object[]> list = new ArrayList<Object[]>();

		if (savedGame != null) {
			list = detailsService.getScoreForGroup(dto.getGroupID(),
					savedGame.getGameId());
		} else {

			MathPlayNLearnServiceResponse response = new MathPlayNLearnServiceResponse();

			response.setCode("getGroupScore001");
			response.setMessage(MathPlayPropertiesFileReaderUtil
					.getPropertyValue("getGroupScore001"));

			return Response.status(200).entity(response).build();
		}
		return Response.status(200).entity(list).build();
	}

	@GET
	@Path("get-total-user-score/{groupID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTotalScoreForUser(@PathParam("groupID") long groupID) {
		List<Object[]> list = new ArrayList<Object[]>();

		list = detailsService.getTotalScoreForUser(groupID);

		return Response.status(200).entity(list).build();
	}

	@GET
	@Path("get-invitations/{userID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInvitations(@PathParam("userID") String userID) {

		List<GetInvitationsDTO> list = memberService
				.getGroupInvitationsForUser(userID);
		MathPlayNLearnServiceResponse response = new MathPlayNLearnServiceResponse();

		if (list == null) {
			response.setCode("getInvitations001");
			response.setMessage(MathPlayPropertiesFileReaderUtil
					.getPropertyValue("getInvitations001"));
			return Response.status(200).entity(response).build();
		}

		return Response.status(200).entity(list).build();
	}

	@POST
	@Path("update-invitation-status")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateInvitationStatus(UpdateInvitationStatusDTO dto) {
		MathPlayNLearnServiceResponse response = new MathPlayNLearnServiceResponse();
		GroupMember member = memberService.getGroupMemberByID(dto.getGroupID(),
				dto.getMemberID());

		response.setCode("updateInvitationStatus003");
		response.setMessage(MathPlayPropertiesFileReaderUtil
				.getPropertyValue("updateInvitationStatus003"));

		if (member == null) {
			response.setCode("updateInvitationStatus002");
			response.setMessage(MathPlayPropertiesFileReaderUtil
					.getPropertyValue("updateInvitationStatus002"));
		} else {

			member.setStatus(dto.getStatus());
			boolean isSaved = memberService.saveMember(member);

			if (!isSaved) {
				response.setCode("updateInvitationStatus001");
				response.setMessage(MathPlayPropertiesFileReaderUtil
						.getPropertyValue("updateInvitationStatus001"));
			}
		}

		return Response.status(200).entity(response).build();
	}

	@GET
	@Path("get-invitation-count/{userID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInvitationCount(@PathParam("userID") String userID) {
		Long count = memberService.getInvitationCount(userID);
		MathPlayNLearnServiceResponse response = new MathPlayNLearnServiceResponse();

		if (count == null) {
			response.setCode("getInvitationcount001");
			response.setMessage(MathPlayPropertiesFileReaderUtil
					.getPropertyValue("getInvitationcount001"));
			return Response.status(200).entity(response).build();
		}

		return Response.status(200).entity(count).build();
	}
	
	@GET
	@Path("group-list-for-member/{userID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGroupListForMember(@PathParam("userID") String userID) {
		List<GroupDTO> list = groupService.getGroupListForMember(userID);
	
		return Response.status(200).entity(list).build();
	}
}
